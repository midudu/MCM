package problem.component;

/**
 * To store a group of solution.
 */
public class SolutionVector {

    /* A vector to store the solution */
    private String[] solutionVector;

    /**
     * Constructor.
     *
     * @param size the number of the variables in the current group of solution
     */
    public SolutionVector(int size) {

        solutionVector = new String[size];
    }

    /**
     * To set the value of a specific component in the current group of solution
     *
     * @param index the index of the component
     * @param value the value of the component
     */
    public void set(int index, String value) {

        if (index < 0 || index >= this.solutionVector.length) {
            throw new RuntimeException();
        }

        solutionVector[index] = value;
    }

    /**
     * To get the value of a specific component in the current group of solution
     *
     * @param index the index of the component
     * @return the value of the component
     */
    public String get(int index) {

        return solutionVector[index];
    }

    /**
     * To get a copy of the current group of the solution.
     *
     * @return a copy of the current group of the solution
     */
    public SolutionVector cloneSolutionVector() {

        SolutionVector newSolutionVector
                = new SolutionVector(this.solutionVector.length);

        for (int i = 0; i < this.solutionVector.length; i++) {

            newSolutionVector.set(i, this.solutionVector[i]);
        }

        return newSolutionVector;
    }

    /**
     * To get the number of the components in the current group of solution
     *
     * @return the number of the components in the current group of solution
     */
    public int size() {

        return this.solutionVector.length;
    }
}
