package data;

import java.util.*;


/**
 * @author prf
 * This class model a tabular view of transactions
 */

public class Data {
    private List<Example> data;
    private int numberOfExamples;
    private List<Attribute> explanatorySet;

    //TODO: controllare visibilità inner class, TODO Test this class
    class Example implements Comparable<Example> {
        List<Object> example = new ArrayList<>();

        void add(Object a) {
            this.example.add(a);
        }

        Object get(int i) {
            return this.example.get(i);
        }

        @Override
        public int compareTo(Example ex) {
            int comp = 0;
            Iterator<Object> it1 = this.example.iterator();
            Iterator<Object> it2 = ex.example.iterator();

			while(it1.hasNext() && it2.hasNext())
			{
				comp = ((Comparable)it1.next()).compareTo(it2.next());
				if (comp != 0)
					break;
			}
			return comp;
		}

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder();

            for (Object o : example) {
                str.append(o.toString() + ", ");
            }
            return str.toString();
        }
    }


    public Data() {

        Example ex0 = new Example();
        Example ex1 = new Example();
        Example ex2 = new Example();
        Example ex3 = new Example();
        Example ex4 = new Example();
        Example ex5 = new Example();
        Example ex6 = new Example();
        Example ex7 = new Example();
        Example ex8 = new Example();
        Example ex9 = new Example();
        Example ex10 = new Example();
        Example ex11 = new Example();
        Example ex12 = new Example();
        Example ex13 = new Example();

        ex0.add("sunny");
        ex1.add("sunny");
        ex2.add("overcast");
        ex3.add("rain");
        ex4.add("rain");
        ex5.add("rain");
        ex6.add("overcast");
        ex7.add("sunny");
        ex8.add("sunny");
        ex9.add("rain");
        ex10.add("sunny");
        ex11.add("overcast");
        ex12.add("overcast");
        ex13.add("rain");

        ex0.add(new Double(37.5));
        ex1.add(new Double(38.7));
        ex2.add(new Double(37.5));
        ex3.add(new Double(20.5));
        ex4.add(new Double(20.7));
        ex5.add(new Double(21.2));
        ex6.add(new Double(20.5));
        ex7.add(new Double(21.2));
        ex8.add(new Double(21.2));
        ex9.add(new Double(19.8));
        ex10.add(new Double(3.5));
        ex11.add(new Double(3.6));
        ex12.add(new Double(3.5));
        ex13.add(new Double(3.2));

        ex0.add("high");
        ex1.add("high");
        ex2.add("high");
        ex3.add("high");
        ex4.add("normal");
        ex5.add("normal");
        ex6.add("normal");
        ex7.add("high");
        ex8.add("normal");
        ex9.add("normal");
        ex10.add("normal");
        ex11.add("high");
        ex12.add("normal");
        ex13.add("high");

        ex0.add("weak");
        ex1.add("strong");
        ex2.add("weak");
        ex3.add("weak");
        ex4.add("weak");
        ex5.add("strong");
        ex6.add("strong");
        ex7.add("weak");
        ex8.add("weak");
        ex9.add("weak");
        ex10.add("strong");
        ex11.add("strong");
        ex12.add("weak");
        ex13.add("strong");

        ex0.add("no");
        ex1.add("no");
        ex2.add("yes");
        ex3.add("yes");
        ex4.add("yes");
        ex5.add("no");
        ex6.add("yes");
        ex7.add("no");
        ex8.add("yes");
        ex9.add("yes");
        ex10.add("yes");
        ex11.add("yes");
        ex12.add("yes");
        ex13.add("no");

        TreeSet<Example> tempData = new TreeSet();
        tempData.add(ex0);
        tempData.add(ex1);
        tempData.add(ex2);
        tempData.add(ex3);
        tempData.add(ex4);
        tempData.add(ex5);
        tempData.add(ex6);
        tempData.add(ex7);
        tempData.add(ex8);
        tempData.add(ex9);
        tempData.add(ex10);
        tempData.add(ex11);
        tempData.add(ex12);
        tempData.add(ex13);

        this.data = new ArrayList<>(tempData);
        this.numberOfExamples = this.data.size();


        TreeSet<String> outlookValues = new TreeSet<>();
        outlookValues.add("sunny");
        outlookValues.add("overcast");
        outlookValues.add("rain");
        DiscreteAttribute outlook = new DiscreteAttribute("Outlook", 0, outlookValues);

        TreeSet<String> temperatureValues = new TreeSet<>();
        temperatureValues.add("hot");
        temperatureValues.add("mild");
        temperatureValues.add("cool");
        ContinuousAttribute temperature = new ContinuousAttribute("Temperature", 1, 3.2, 38.7);

        TreeSet<String> humidityValues = new TreeSet<>();
        humidityValues.add("high");
        humidityValues.add("normal");
        DiscreteAttribute humidity = new DiscreteAttribute("Humidity", 2, humidityValues);

        TreeSet<String> windValues = new TreeSet<>();
        windValues.add("weak");
        windValues.add("strong");
        DiscreteAttribute wind = new DiscreteAttribute("Wind", 3, windValues);

        TreeSet<String> playTennisValues = new TreeSet<>();
        playTennisValues.add("yes");
        playTennisValues.add("no");
        DiscreteAttribute playTennis = new DiscreteAttribute("Play Tennis", 4, playTennisValues);

        this.explanatorySet = new LinkedList<>();
        this.explanatorySet.add(outlook);
        this.explanatorySet.add(temperature);
        this.explanatorySet.add(humidity);
        this.explanatorySet.add(wind);
        this.explanatorySet.add(playTennis);

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
    //TODO: review efficiency of method
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

    //TODO: test
    Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute) {
        Double sum = 0.0;
        for (Integer i : idList) {
            sum += (Double) this.getAttributeValue(i, attribute.getIndex());
        }
        return sum / idList.size();
    }

    public static void main(String args[]) {
        Data trainingSet = new Data();
        System.out.println(trainingSet);


        Set<Integer> as = new HashSet<>();
        as.add(0);
        as.add(1);
        as.add(2);
        as.add(3);
        as.add(4);
        as.add(5);
        as.add(6);
        as.add(7);
        as.add(8);
        as.add(9);
        as.add(10);
        as.add(11);
        as.add(12);
        as.add(13);

        System.out.println(trainingSet.computePrototype(as, trainingSet.getAttribute(0)));
        System.out.println(trainingSet.computePrototype(as, trainingSet.getAttribute(1)));
        System.out.println(trainingSet.computePrototype(as, trainingSet.getAttribute(2)));
        System.out.println(trainingSet.computePrototype(as, trainingSet.getAttribute(3)));
        System.out.println(trainingSet.computePrototype(as, trainingSet.getAttribute(4)));

        System.out.println(trainingSet.getAttributeValue(0, 0));
        System.out.println(trainingSet.getAttributeValue(13, 0));

        System.out.println(trainingSet.getItemSet(1));
        System.out.println(trainingSet.getItemSet(3));

        System.out.println(trainingSet.compare(0, 0));
        System.out.println(trainingSet.compare(2, 7));

        try {
            System.out.println(Arrays.toString(trainingSet.sampling(4)));
        } catch (OutOfRangeSampleSize e) {
            e.printStackTrace();
        }
    }
}

























