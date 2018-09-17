package problem.component;

public class SolutionVector {

    private int[] solutionVector;

    public SolutionVector(int size) {

        solutionVector = new int[size];
    }

    public void set(int index, int value) {

        if (index < 0 || index >= this.solutionVector.length) {
            throw new RuntimeException();
        }

        solutionVector[index] = value;
    }
}
