package data;

import java.sql.SQLException;
import java.util.*;

import database.*;


/**
 * This class models a tabular view of transactions with header and data fields. The elements of table are accessible
 * through related indexes of lists that models header and fields, taking appropriate values.
 *
 * @author Pio Raffaele Fina
 * @version 1.0
 */

public class Data {
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> explanatorySet;

    /**
     * Constructs an instance of Data table by calling the related database table.
     *
     * @param tableName name of database table that will be builds
     * @throws SQLException in case of SQL error
     * @throws DatabaseConnectionException in case of connection problems
     * @throws EmptySetException in case of missing results in result set
     * @throws DatabaseQueryException in case of missing resources
     */
    public Data(String tableName) throws SQLException, DatabaseConnectionException, EmptySetException, DatabaseQueryException {
        DbAccess db = new DbAccess();
        db.initConnection();
        TableData td = new TableData(db);
        TableSchema schema = new TableSchema(db, tableName);
        data=td.getDistinctTransactions(tableName);
        numberOfExamples = data.size();
        explanatorySet = new ArrayList<>();

        for(int i=0; i<schema.getNumberOfAttributes(); i++){
            TableSchema.Column col = schema.getColumn(i);
            if(schema.getColumn(i).isNumber()){
                Double min = (Double) td.getAggregateColumnValue(tableName, col, QUERY_TYPE.MIN);
                Double max = (Double) td.getAggregateColumnValue(tableName, col, QUERY_TYPE.MAX);
                explanatorySet.add(new ContinuousAttribute(col.getColumnName(), i, min, max));
            }else{
                TreeSet<String> set = new TreeSet<>();
                for(Object o: td.getDistinctColumnValues(tableName, col)){
                    set.add((String) o);
                }
                explanatorySet.add(new DiscreteAttribute(col.getColumnName(), i, set));
            }
        }

        db.closeConnection();
    }

    /**
     * Getter of the number of examples.
     *
     * @return number of examples
     */
    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

    /**
     * Getter of the number of attributes.
     *
     * @return number of attributes
     */
    public int getNumberOfAttributes() {
        return this.explanatorySet.size();
    }

    /**
     * Getter of the values of element identifieds by row (tuples) and col (attribute) indexes.
     *
     * @param exampleIndex tuples index
     * @param attributeIndex attribute index
     * @return element value looked for
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return this.data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * Getter of the attribute by index
     *
     * @param index the attribute index
     * @return attribute looked for
     */
    public Attribute getAttribute(int index) {
        return this.explanatorySet.get(index);
    }

    /**
     * Get attribute name
     *
     * @param index the attribute index
     * @return attribute looked for
     */
    public String getAttributeName(int index) {
        return this.explanatorySet.get(index).getName();
    }


    /**
     * Getter of the tuple by index
     *
     * @param index the item index
     * @return tuple looked for
     */
    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(explanatorySet.size());
        for (int i = 0; i < explanatorySet.size(); i++) {
            Attribute currAttr = explanatorySet.get(i);
            if (currAttr instanceof DiscreteAttribute) {
                DiscreteItem tupleItem = new DiscreteItem((DiscreteAttribute) currAttr,
                        (String) this.getAttributeValue(index, i));
                tuple.add(tupleItem, i);
            } else if (currAttr instanceof ContinuousAttribute) {
                ContinuousItem tupleItem = new ContinuousItem(currAttr,
                        (Double) this.getAttributeValue(index, i));
                tuple.add(tupleItem, i);
            }
        }
        return tuple;
    }

    /**
     * Generate array of k unique random indexes for centroids
     *
     * @param k random int number
     * @return array of indexes
     */
    public int[] sampling(int k) throws OutOfRangeSampleSize {
        if (k <= 0 || k > this.getNumberOfExamples()) {
            throw new OutOfRangeSampleSize("Numero di clusters out-of-range");
        }

        int centroidIndexes[] = new int[k];
        Random rand = new Random(System.currentTimeMillis());

        //generate random indexes for centroids
        for (int i = 0; i < centroidIndexes.length; i++) {

            boolean found = false;
            int c;
            //check the uniqueness of centroid index
            do {
                found = false;
                c = rand.nextInt(this.getNumberOfExamples());

                for (int j = 0; j < i; j++) {
                    if (this.compare(centroidIndexes[j], c)) {
                        found = true;
                        break;
                    }
                }
            }
            while (found);
            centroidIndexes[i] = c;
        }
        return centroidIndexes;
    }

    /**
     * Given 2 index i,j check if tuple at index i and tuple at index j are equals.
     *
     * @param i first tuple index
     * @param j second tuple index
     * @return true if tuples are element-wise equals, false otherwise.
     */
    private boolean compare(int i, int j) {
        boolean isTrue = true;
        if (i != j) {
            for (int k = 0; k < this.getNumberOfAttributes(); k++) {
                if (!(this.getAttributeValue(i, k).equals(this.getAttributeValue(j, k)))) {
                    isTrue = false;
                    break;
                }
            }
        }
        return isTrue;
    }


    public String toString() {
        StringBuilder out = new StringBuilder();

        //concatenate table header
        for (Attribute attr : explanatorySet) {
            out.append( attr.getName());
            out.append(",");
        }

        //concatenate table data
        out.append(System.lineSeparator());
        for (int i = 0; i < this.numberOfExamples; i++) {
            out.append(i);
            out.append(": ");
            for (int j = 0; j < this.getNumberOfAttributes(); j++) {
                if (j == this.getNumberOfAttributes() - 1){
                    out.append( this.getAttributeValue(i, j));
                } else {

                    out.append(this.getAttributeValue(i, j));
                    out.append(",");
                }
            }
            out.append(System.lineSeparator());
        }

        return out.toString();

    }

    /**
     * Dispatch type of attribute using RTTI
     *
     * @param idList subset of tuples represented by row index
     * @param attribute a generic attribute
     * @return calls to compute prototype with right parameter
     */
    Object computePrototype(Set<Integer> idList, Attribute attribute) {
        if (attribute instanceof DiscreteAttribute)
            return computePrototype(idList, (DiscreteAttribute) attribute);

        return computePrototype(idList, (ContinuousAttribute) attribute);
    }

    /**
     * Return the most frequent Discrete attribute's value in tuples indexed by idList
     *
     * @param idList subset of tuples represented by row index
     * @param attribute discrete attribute
     * @return the most frequent Discrete attribute's value in tuples indexed by idList
     */
    String computePrototype(Set<Integer> idList, DiscreteAttribute attribute) {
        Iterator<String> it = attribute.iterator();

        String maxValue = it.next();
        int maxFreq = attribute.frequency(this, idList, maxValue);

        while (it.hasNext()) {
            String currentValue = it.next();
            int currentFreq = attribute.frequency(this, idList, currentValue);

            if (currentFreq >= maxFreq) {
                maxValue = currentValue;
                maxFreq = currentFreq;
            }
        }

        return maxValue;
    }

    /**
     * Return the most frequent Continuous attribute's value in tuples indexed by idList
     *
     * @param idList subset of tuples represented by row index
     * @param attribute continuous attribute
     * @return the most frequent Continuous attribute's value in tuples indexed by idList
     */
    Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
        Double sum = 0.0;
        for (Integer i : idList) {
            sum += (Double) this.getAttributeValue(i, attribute.getIndex());
        }
        return sum / idList.size();
    }
}

























