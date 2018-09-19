package problem.component;

// 这个类用来存储一组解
public class SolutionVector {

    private String[] solutionVector;

    public SolutionVector(int size) {

        solutionVector = new String[size];
    }

    public void set(int index, String value) {

        if (index < 0 || index >= this.solutionVector.length) {
            throw new RuntimeException();
        }

        solutionVector[index] = value;
    }

    public String get(int index) {

        return solutionVector[index];
    }

    public SolutionVector cloneSolutionVector() {

        SolutionVector newSolutionVector
                = new SolutionVector(this.solutionVector.length);

        for (int i = 0; i < this.solutionVector.length; i++) {

            newSolutionVector.set(i, this.solutionVector[i]);
        }

        return newSolutionVector;
    }

    public int size() {

        return this.solutionVector.length;
    }
}
