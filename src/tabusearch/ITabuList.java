package tabusearch;


public class ITabuList {

    public ITabuList(Problem p, int mode) {
        matrix = new int[p.getNumberOfOperations()][p.getNumberOfOperations()];
        L = 10 + p.getNumberOfJobs() / (p.getNumberOfMachines() * 2);
        setLength(L);
        this.p = p;
        this.mode = mode;
    }

    private Problem p;
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
                int l = 0;
                if (numberOfIterationsOfNoImprovement < K) {
                    l = L;
                } else if (numberOfIterationsOfNoImprovement < K * 2) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - K)) / ((float) (5 * K));
                    l = (int) (a * L + L);

                } else if (numberOfIterationsOfNoImprovement < K * 3) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - 2 * K) * 4) / ((float) (5 * K));
                    l = (int) (a * L + 1.2 * L);

                } else if (numberOfIterationsOfNoImprovement < K * 4) {
                    l = 2 * L;
                } else {
                    l = L;
                }
                if (l != getLength()) {
                    System.out.print(l + "  ");
                }
                setLength(l);

            } else if (mode == 2) {
                int K = 800 / 5;
                int n = p.getNumberOfJobs();
                int l = 0;
                if (numberOfIterationsOfNoImprovement < K) {
                    l = n;
                } else if (numberOfIterationsOfNoImprovement < K * 2) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - K)) / ((float) (3 * K));
                    l = (int) (n - n * a);

                } else if (numberOfIterationsOfNoImprovement < K * 3) {
                    l = 2 * n / 3;

                } else if (numberOfIterationsOfNoImprovement < K * 4) {
                    float a = ((float) (numberOfIterationsOfNoImprovement - 3 * K) * 4) / ((float) (3 * K));
                    l = (int) (2 * n / 3 + n * a);
                } else if (numberOfIterationsOfNoImprovement < K * 5) {
                    l = 2 * n;
                } else {
                    l = n;
                }
                if (l != getLength()) {
                    System.out.print(l + "  ");
                }
                setLength(l);
            } else {
                int l = mode;
                if (l != getLength()) {
                    System.out.print(l + "  ");
                }
                setLength(l);
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

    private int length = 10;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
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
