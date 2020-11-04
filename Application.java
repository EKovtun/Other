package ru.ekovtun;

import java.util.*;

class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> matrixArray = new ArrayList<>();
        while (scanner.hasNextInt())
            matrixArray.add(scanner.nextInt());

        int matrixSize = (int) Math.sqrt(matrixArray.size());

        int newSize = 2;
        while (newSize < matrixSize)
            newSize *= 2;

        int[][] matrix = new int[newSize][newSize];
        for (int i = 0; i < matrixSize; ++i)
            for (int j = 0; j < matrixSize; ++j)
                matrix[i][j] = matrixArray.get(i * matrixSize + j);

        for(int i = newSize - matrixSize; i != 0; --i) {
            matrix[newSize - i][newSize - i] = 1;
        }

        int[][][] res = execute(matrix);
        printMatrix(shrinkTo(res[0], matrixSize));
        printMatrix(shrinkTo(res[1], matrixSize));
        printMatrix(shrinkTo(res[2], matrixSize));
    }

    private static int[][] shrinkTo(int[][] matrix, int newSize) {
        int[][] shrinkedMatrix = new int[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                shrinkedMatrix[i][j] = matrix[i][j];
            }
        }
        return shrinkedMatrix;
    }

    private static void printMatrix(int[][] matrix) {
        for (int[] vector : matrix) {
            for (int elem : vector)
                System.out.printf("%d ", elem);
            System.out.println();
        }
    }



    private static int[][] mul(int[][] first, int[][] second) {
//        int[][] result = new int[first.length][second[0].length];
//        for (int i = 0; i < first.length; ++i)
//            for (int ii = 0; ii < second[0].length; ++ii) {
//                int sum = 0;
//                for (int iii = 0; iii < second[0].length; ++iii)
//                    sum += first[i][iii] * second[iii][ii];
//                result[i][ii] = sum;
//        }
//
//        return result;
        int m1ColLength = first[0].length;
        int m2RowLength = second.length;

        int mRRowLength = first.length;
        int mRColLength = second[0].length;
        int[][] result = new int[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {
            for(int j = 0; j < mRColLength; j++) {
                for(int k = 0; k < m1ColLength; k++) {
                    int sum = result[i][j];
                    sum += (first[i][k] * second[k][j]) % 2;
                    sum %= 2;
                    result[i][j] = sum;
                }
            }
        }
        return result;
    }

    private static int[][] mulNumb(int[][] first, int numb) {
        return first;

//        int[][] result = new int[first.length][first[0].length];
//        for(int i = 0; i < first.length; ++i)
//            for(int ii = 0; ii < first[0].length; ++ii)
//                result[i][ii] = first[i][ii] * numb;
//
//        return result;
    }

    private static int[][] subtract(int[][] first, int[][] second) {
        int[][] result = new int[first.length][first[0].length];
        for(int i = 0; i < first.length; ++i)
            for(int ii = 0; ii < first[0].length; ++ii) {
                if (first[i][ii] - second[i][ii] < 0)
                    result[i][ii] = 1;
                else
                    result[i][ii] = first[i][ii] - second[i][ii];
            }

        return result;
    }

    private static int[][] addition(int[][] first, int[][] second) {
        int[][] result = new int[first.length][first[0].length];
        for(int i = 0; i < first.length; ++i)
            for(int ii = 0; ii < first[0].length; ++ii)
                result[i][ii] = (first[i][ii] + second[i][ii]) % 2;

        return result;
    }

    private static int[][] transpose(int[][] matrix) {
        int[][] transposed = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }

    private static int[][] reverse(int[][] matrix) {
        if (matrix.length == 1) return matrix;

//        if (matrix.length == 2) {
//            int[][] result = new int[2][2];
//
//            int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
//            if (det == 0) return matrix;
//            result[0][0] = matrix[1][1] / det;
//            result[0][1] = -matrix[0][1] / det;
//            result[1][0] = -matrix[1][0] / det;
//            result[1][1] = matrix[0][0] / det;
//
//            return matrix;
//        }

        int[][] A11 = new int[matrix.length / 2][matrix.length / 2];
        int[][] A12 = new int[matrix.length / 2][matrix.length / 2];
        int[][] A21 = new int[matrix.length / 2][matrix.length / 2];
        int[][] A22 = new int[matrix.length / 2][matrix.length / 2];

        for(int i = 0; i < matrix.length; ++i)
            for(int ii = 0; ii < matrix.length; ++ii)
                if (i < matrix.length / 2){
                    if (ii < matrix.length / 2)
                        A11[i][ii] = matrix[i][ii];
                    else
                        A12[i][ii - matrix.length / 2] = matrix[i][ii];
                } else {
                    if (ii < matrix.length / 2)
                        A21[i - matrix.length / 2][ii] = matrix[i][ii];
                    else
                        A22[i - matrix.length / 2][ii - matrix.length / 2] = matrix[i][ii];
                }

        int[][] rA11 = reverse(A11);
        int[][] lambd = mul(mul(A21, rA11), A12);
        lambd = subtract(A22, lambd);
        int[][] rlambd = reverse(lambd);

        int[][] nA11 = addition(rA11, mul(mul(mul(mul(rA11, A12), rlambd), A21), rA11));
        int[][] nA12 = mul(mul(mulNumb(rA11, -1), A12), rlambd);
        int[][] nA21 = mul(mul(mulNumb(rlambd, -1), A21), rA11);
        int[][] nA22 = rlambd;

        int[][] result = new int[nA11.length + nA21.length][nA11[0].length + nA12[0].length];
        for(int i = 0; i < nA11.length; ++i)
            for(int ii = 0; ii < nA11[0].length; ++ii)
                result[i][ii] = nA11[i][ii];
        for(int i = 0; i < nA12.length; ++i)
            for(int ii = 0; ii < nA12[0].length; ++ii)
                result[i][ii + nA11[0].length] = nA12[i][ii];
        for(int i = 0; i < nA21.length; ++i)
            for(int ii = 0; ii < nA21[0].length; ++ii)
                result[i + nA11.length][ii] = nA21[i][ii];
        for(int i = 0; i < nA22.length; ++i)
            for(int ii = 0; ii < nA22[0].length; ++ii)
                result[i + nA11.length][ii + nA21[0].length] = nA22[i][ii];
        return result;
    }

    private static int[][][] execute(int[][] A) {
        if (A.length == 1) {
            int[][] L = {{1}};

            int index = 0;
            for (; index < A[0].length; ++index)
                if (A[0][index] != 0)
                    break;

            int[][] P = new int[A[0].length][A[0].length];
            P[0][index] = 1;
            P[index][0] = 1;
            for(int i = 1; i < A[0].length; ++i) {
                if (i == index)
                    continue;
                P[i][i] = 1;
            }

            int[][] U = mul(A, P);
            return new int[][][]{ L, U, P };
        } else {
            int[][] B = new int[A.length / 2][A[0].length];
            int[][] C = new int[A.length / 2][A[0].length];
            for (int i = 0; i < A.length; ++i)
                for (int ii = 0; ii < A[0].length; ++ii)
                    if (i < A.length / 2)
                        B[i][ii] = A[i][ii];
                    else
                        C[i - A.length / 2][ii] = A[i][ii];

            int[][][] res = execute(B);
            int[][] L1 = res[0];
            int[][] U1 = res[1];
            int[][] P1 = res[2];

            int[][] D = mul(C, transpose(P1));

            int[][] E = new int[U1.length][A.length / 2];
            for (int i = 0; i < U1.length; ++i)
                System.arraycopy(U1[i], 0, E[i], 0, A.length / 2);
            int[][] F = new int[D.length][A.length / 2];
            for (int i = 0; i < D.length; ++i)
                System.arraycopy(D[i], 0, F[i], 0, A.length / 2);

            int[][] G = subtract(D, mul(mul(F, reverse(E)), U1));
            int[][] Gshtr = new int[G.length][A[0].length - A.length / 2];
            for (int i = 0; i < G.length; ++i)
                System.arraycopy(G[i], G[0].length - (A[0].length - A.length / 2), Gshtr[i], 0, A[0].length - A.length / 2);

            res = execute(Gshtr);
            int[][] L2 = res[0];
            int[][] U2 = res[1];
            int[][] P2 = res[2];

            int[][] P3 = new int[A[0].length][A[0].length];
            for(int i = 0; i < A.length / 2; ++i)
                P3[i][i] = 1;
            for(int i = A.length / 2; i < A[0].length; ++i)
                for(int ii = A.length / 2; ii < A[0].length; ++ii)
                    P3[i][ii] = P2[i - A.length / 2][ii - A.length / 2];

            int[][] H = mul(U1, transpose(P3));

            int[][] Lr = new int[A.length][A.length];
            int[][] FE = mul(F, reverse(E));
            for(int i = 0; i < A.length; ++i)
                for(int ii = 0; ii < A.length; ++ii)
                    if (i < A.length / 2) {
                        if (ii < i + 1)
                            Lr[i][ii] = L1[i][ii];
                    } else {
                        if (ii < A.length / 2)
                            Lr[i][ii] = FE[i - A.length / 2][ii];
                        else
                            if (ii < A.length / 2 + i + 1)
                                Lr[i][ii] = L2[i - A.length / 2][ii - A.length / 2];
                    }

            int[][] Ur = new int[A.length][A[0].length];
            for(int i = 0; i < A.length; ++i)
                for(int ii = 0; ii < A[0].length; ++ii) {
                    if (i < A.length / 2) {
                        if (ii > i - 1)
                            Ur[i][ii] = H[i][ii];
                    } else {
                        if (ii > H.length - 1)
                            Ur[i][ii] = U2[i - A.length / 2][ii - H.length];
                    }
                }

            return new int[][][]{ Lr, Ur, mul(P3, P1) };
        }
    }
}