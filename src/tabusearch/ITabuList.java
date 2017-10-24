package tabusearch;


public class ITabuList {

    public ITabuList(Problem p, int mode) {
        matrix = new int[p.getNumberOfOperations()][p.getNumberOfOperations()];
        L = 10 + p.getNumberOfJobs() / (p.getNumberOfMachines() * 2);
        setLength(L);
        this.mode = mode;
    }

    public int mode;
    private int L;
    private int[][] matrix;

    public int[][] getMatrix() {
        return matrix;
    }

    public void update(Move m, int iterationCount, int numberOfIterationsOfNoImprovement, Phase phase) {
        try {
            matrix[m.getInversion().get(0).getId()][m.getInversion().get(1)
                    .getId()] = iterationCount + 1;
            if (m.getNumberOfOperationsInInversion() == 3) {
                matrix[m.getInversion().get(1).getId()][m.getInversion().get(2)
                        .getId()] = iterationCount + 1;
                matrix[m.getInversion().get(0).getId()][m.getInversion().get(2)
                        .getId()] = iterationCount + 1;
            }
/*            if (iterationCount / getLambda() == 0) {
                randomlyChooseMinAndMax();
            }*/
            if (mode == 1) {
                int K = 800 / 4;
                if (numberOfIterationsOfNoImprovement < K) {
                    setLength(L);
                } else if (numberOfIterationsOfNoImprovement < K * 2) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - K)) / ((float) (5 * K));
                    int l = (int) (a * L + L);
                    setLength(l);
                } else if (numberOfIterationsOfNoImprovement < K * 3) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - 2 * K) * 4) / ((float) (5 * K));
                    int l = (int) (a * L + 1.2 * L);
                    setLength(l);
                } else if (numberOfIterationsOfNoImprovement < K * 4) {
                    setLength(2 * L);
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid move.");
        } finally {
            updateListLength(phase);
        }
    }

    private void randomlyChooseMinAndMax() {
        //todo
    }

    private void updateListLength(Phase phase) {
        //todo
    }

    public boolean isAllowed(Move m, int k) {
        boolean res = checkTabuStatus(m.getInversion().get(0).getId(), m
                .getInversion().get(1).getId(), k + 1);
        if (m.getNumberOfOperationsInInversion() == 3) {
            res = res
                    && checkTabuStatus(m.getInversion().get(0).getId(), m
                    .getInversion().get(2).getId(), k + 1)
                    && checkTabuStatus(m.getInversion().get(1).getId(), m
                    .getInversion().get(2).getId(), k + 1);
        }
        return res;
    }

    private boolean checkTabuStatus(int i, int j, int k) {
        return (getMatrix()[j][i] + getLength()) <= k;
    }

    private  int length = 10;

    public  int getLength() {
        return length;
    }

    public  void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        int[][] m = getMatrix();
        String res = "";
        for (int i = 0; i < m.length; i++) {
            res += i + ": |";
            for (int j = 0; j < m.length; j++) {
                res += (matrix[i][j] + " ");
            }
            res += ("|\n");
        }
        return res;
    }

}
