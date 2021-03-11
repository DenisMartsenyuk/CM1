package models;

import java.util.*;

public class DoubleMatrix {
    private List<List<Double>> matrix;

    public DoubleMatrix(int row, int column) {
        matrix = new ArrayList<>();
        for (int i = 0; i < row; ++i) {
            matrix.add(new ArrayList<>(column));
            for (int j = 0; j < column; ++j) {
                matrix.get(i).add(0.0);
            }
        }
    }

    public void setElement(Double value, int row, int column) {
        matrix.get(row).set(column, value);
    }

    public Double getElenent(int row, int column) {
        return matrix.get(row).get(column);
    }

    public Integer getRows() {
        return matrix.size();
    }

    public Integer getColumns() {
        return matrix.get(0).size();
    }

    public List<Double> getAbsRowWithoutLast(int row) {
        List<Double> rowAbs = new ArrayList<Double> (matrix.get(row).subList(0, matrix.get(row).size() - 1));
        for (int i = 0; i < rowAbs.size(); ++i) {
            rowAbs.set(i, Math.abs(rowAbs.get(i)));
        }
        return rowAbs;
    }

    public Double getAbsSumRowWithoutLast(int row) {
        List<Double> rowAbsElements = getAbsRowWithoutLast(row);
        Double sum = 0.0;
        for (int i = 0; i < rowAbsElements.size(); ++i) {
            sum += rowAbsElements.get(i);
        }
        return sum;
    }

    public void rearrange(List<Integer> permutations) {
        DoubleMatrix rearrangeMatrix = new DoubleMatrix(this.getRows(), this.getColumns());
        for (int i = 0; i < permutations.size(); ++i) {
            int newIndex = permutations.get(i);
            List<Double> row = matrix.get(i);
            rearrangeMatrix.matrix.set(newIndex, row);
        }
        this.matrix = rearrangeMatrix.matrix;
    }

    public Double getNorma() {
        Set<Double> sums = new HashSet<>();
        for (int i = 0; i < getRows(); ++i) {
            Double sum = 0.0;
            for (int j = 0; j < getColumns(); ++j) {
                sum += Math.abs(getElenent(i, j));
            }
            sums.add(sum);
        }
        return Collections.max(sums);
    }

    public Double getMaxColumn(int index) {
        Set<Double> colomn = new HashSet<>();
        for (int i = 0; i < getRows(); ++i) {
            colomn.add(getElenent(i, index));
        }
        return Collections.max(colomn);
    }

    public DoubleMatrix absSubtract(DoubleMatrix subtrahend) {
        DoubleMatrix result = new DoubleMatrix(getRows(), getColumns());
        for (int i = 0; i < getRows(); ++i) {
            for (int j = 0; j < getColumns(); ++j) {
                result.setElement(Math.abs(getElenent(i, j) - subtrahend.getElenent(i, j)), i, j);
            }
        }
        return result;
    }

    public void print(String format) {
        for (int i = 0; i < getRows(); ++i) {
            String st = "";
            for (int j = 0; j < getColumns(); ++j) {
                System.out.printf(format, getElenent(i, j));
            }
            System.out.println();
        }
    }
}