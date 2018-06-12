package data;

import java.sql.SQLException;
import java.util.*;

import database.*;


/**
 * @author prf
 * This class model a tabular view of transactions
 */

public class Data {
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> explanatorySet;

    public Data(String tableName) throws SQLException, DatabaseConnectionException, ClassNotFoundException, EmptySetException, NoValueException {
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

    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

    public int getNumberOfAttributes() {
        return this.explanatorySet.size();
    }

    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return this.data.get(exampleIndex).get(attributeIndex);
    }

    public Attribute getAttribute(int index) {
        return this.explanatorySet.get(index);
    }

    public Tuple getItemSet(int index) {
        //TODO uniformare  i costruttori di cont e discr attribute per eliminare i cast

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
     * @param k
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
     * element wise tuple comparison. Given 2 index i,j check if tuple at index i and tuple at index j are equals.
     *
     * @param i
     * @param j
     * @return
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
        String out = "";
        //concatenate table header
        for (Attribute attr : explanatorySet) {
            out += attr.getName() + ",";
        }
        //concatenate table data
        out += "\n";
        for (int i = 0; i < this.numberOfExamples; i++) {
            out += (i) + ": ";
            for (int j = 0; j < this.getNumberOfAttributes(); j++) {
                if (j == this.getNumberOfAttributes() - 1)
                    out += this.getAttributeValue(i, j);
                else
                    out += this.getAttributeValue(i, j) + ",";
            }
            out += "\n";
        }

        return out;

    }

    //TODO: test
    Object computePrototype(Set<Integer> idList, Attribute attribute) {
        if (attribute instanceof DiscreteAttribute)
            return computePrototype(idList, (DiscreteAttribute) attribute);

        return computePrototype(idList, (ContinuousAttribute) attribute);
    }

    /**
     * Return the most frequent attribute's value in tuples indexed by idList
     *
     * @param idList
     * @param attribute
     * @return
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

    Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
        Double sum = 0.0;
        for (Integer i : idList) {
            sum += (Double) this.getAttributeValue(i, attribute.getIndex());
        }
        return sum / idList.size();
    }
}

























