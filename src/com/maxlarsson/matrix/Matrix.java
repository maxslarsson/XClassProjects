package com.maxlarsson.matrix;

public class Matrix {
    private double[][] matrix;

    Matrix() {
        this.matrix = new double[1][];
    }

    Matrix(double[][] matrix) {
        this.matrix = matrix;
    }

    Matrix(int rows, int columns) {
        this.matrix = new double[rows][columns];
    }

    Matrix(int rows, int columns, double value) {
        this.matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                this.matrix[i][j] = value;
            }
        }
    }

    int	getColumnCount() {
        return this.matrix[0].length;
    }

    double[][] getMatrix() {
        return this.matrix;
    }

    int getRowCount() {
        return this.matrix.length;
    }

    double getValueAt(int row, int column) {
        return this.matrix[row][column];
    }

    Matrix plus(Matrix matrix) {
        // Check that there are the same number of rows
        assert this.getRowCount() == matrix.getRowCount();
        // Check that there are the same number of columns
        assert this.getColumnCount() == matrix.getColumnCount();

        Matrix newMatrix = new Matrix(this.getRowCount(), this.getColumnCount());

        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                newMatrix.setEntry(i, j, this.getValueAt(i, j) + matrix.getValueAt(i, j));
            }
        }

        return newMatrix;
    }

    void setEntry(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    Matrix switchRows(int firstrow, int secondrow) {
        double[] firstrowInMatrix = this.matrix[firstrow];
        double[] secondrowInMatrix = this.matrix[secondrow];

        Matrix newMatrix = new Matrix(this.getMatrix());

        for (int i = 0; i < this.matrix[firstrow].length; i++) {
            newMatrix.setEntry(firstrow, i, this.matrix[secondrow][i]);
        }

        for (int i = 0; i < this.matrix[secondrow].length; i++) {
            newMatrix.setEntry(secondrow, i, this.matrix[firstrow][i]);
        }

        return newMatrix;
    }

    public String toString() {
        StringBuilder toBeReturned = new StringBuilder();
        for (double[] row : this.matrix) {
            for (double value : row) {
                toBeReturned.append(value);
                toBeReturned.append(" ");
            }
            toBeReturned.append("\n");
        }
        return toBeReturned.toString();
    }
}
