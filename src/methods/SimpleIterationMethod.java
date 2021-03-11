package methods;

import models.DoubleMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleIterationMethod {

    private DoubleMatrix doDiagonalDominance(DoubleMatrix matrix) {
        List<Integer> permutations = new ArrayList<>();
        for (int i = 0; i < matrix.getRows(); ++i) {
            List<Double> row = matrix.getAbsRowWithoutLast(i);
            Double maxInRow = Collections.max(row);
            Integer index = row.indexOf(maxInRow);
            Double sumAbsRow = matrix.getAbsSumRowWithoutLast(i);
            if (maxInRow < sumAbsRow - maxInRow) {
                return null;
            }
            permutations.add(index);
        }
        matrix.rearrange(permutations);
        return matrix;
    }

    private DoubleMatrix reduceMatrix(DoubleMatrix matrix) {
        for (int i = 0; i < matrix.getRows(); ++i) {
            Double divider = matrix.getElenent(i, i);
            for (int j = 0; j < matrix.getColumns(); ++j) {
                Double element = matrix.getElenent(i, j);
                element /= divider;
                matrix.setElement(element, i, j);
            }
        }
        return matrix;
    }

    private DoubleMatrix getC(DoubleMatrix matrix) {
        DoubleMatrix c = new DoubleMatrix(matrix.getRows(), matrix.getColumns() - 1);
        for (int i = 0; i < c.getRows(); ++i) {
            for (int j = 0; j < c.getColumns(); ++j) {
                if (i == j) {
                    c.setElement(0.0, i, j);
                }
                else {
                    c.setElement(matrix.getElenent(i, j) * (-1), i, j);
                }
            }
        }
        return c;
    }

    private DoubleMatrix getD(DoubleMatrix matrix) {
        DoubleMatrix d = new DoubleMatrix(matrix.getRows(), 1);
        for (int i = 0; i < d.getRows(); ++i) {
            d.setElement(matrix.getElenent(i, matrix.getColumns() - 1), i, 0);
        }
        return d;
    }


    private void iteration(DoubleMatrix c, DoubleMatrix d, Double accuracy) {
        DoubleMatrix currentApprox = d;
        DoubleMatrix matrixInfelicity = new DoubleMatrix(d.getRows(), 1);

        int counter = 0;
        double infelicity = accuracy;
        while (infelicity >= accuracy) {
            DoubleMatrix lastApprox = currentApprox;
            currentApprox = new DoubleMatrix(d.getRows(), 1);
            for (int i = 0; i < c.getColumns(); ++i) {
                Double result = 0.0;
                for (int j = 0; j < c.getRows(); ++j) {
                    result += c.getElenent(i, j) * lastApprox.getElenent(j, 0);
                }
                result += d.getElenent(i, 0);
                currentApprox.setElement(result, i, 0);
            }
            matrixInfelicity = currentApprox.absSubtract(lastApprox);
            infelicity = matrixInfelicity.getMaxColumn(0);
            counter++;
        }
        System.out.println("Вектор X:");
        currentApprox.print("%10.7f");
        System.out.println("Количество итераций:");
        System.out.println(counter);
        System.out.println("Вектор погрешностей:");
        matrixInfelicity.print("%10.7f");

    }

    public void calculation(DoubleMatrix matrix, Double accuracy) {
        System.out.println("Исходная матрица:");
        matrix.print("%6.1f");
        matrix = doDiagonalDominance(matrix);
        if (matrix == null) {
            System.out.println("Условие сходимости не выполнено!");
            return;
        }
        System.out.println("Матрица с диагональным преобладанием:");
        matrix.print("%6.1f");
        matrix = reduceMatrix(matrix);
        DoubleMatrix c = getC(matrix);
        if (c.getNorma() >= 1) {
            System.out.println("Условие сходимости не выполнено!");
            return;
        }
        DoubleMatrix d = getD(matrix);
        iteration(c, d, accuracy);
    }
}