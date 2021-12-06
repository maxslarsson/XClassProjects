package com.maxlarsson.matrix;

public class Matrix {
    private final double[][] matrix;

    Matrix() {
        this.matrix = new double[1][];
    }

    Matrix(double[][] matrix) {
        int lengthOfFirstRow = matrix[0].length;

        for (double[] row : matrix) {
            if (row.length != lengthOfFirstRow) {
                throw new IllegalArgumentException("Matrix's rows must all have the same length");
            }
        }

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

    /**
     * A static method to generate an n by n identity matrix
     * @param n The number of rows and columns for the identity matrix
     * @return An identity matrix of size n by n
     */
    static Matrix identity(int n) {
        Matrix matrix = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            matrix.setEntry(i, i, 1);
        }
        return matrix;
    }

    /**
     * Returns if this matrix is square
     * @return If this matrix is square
     */
    private Boolean isSquare() {
        return this.getRowCount() == this.getColumnCount();
    }

    /**
     * Return an augmented matrix, which is the matrix concatenated to the identity matrix.
     * @return This matrix concatenated to its identity matrix
     */
    Matrix augment() {
        if (!this.isSquare()) {
            throw new IllegalStateException("You can only augment a square matrix");
        }

        Matrix identity = Matrix.identity(this.getRowCount());
        Matrix matrix = new Matrix(this.getRowCount(), this.getColumnCount()*2);
        for (int i = 0; i < this.getRowCount(); i++) {
            for (int j = 0; j < this.getColumnCount(); j++) {
                matrix.setEntry(i, j, this.getValueAt(i,j));
                matrix.setEntry(i, j+this.getColumnCount(), identity.getValueAt(i,j));
            }
        }
        return matrix;
    }

    /**
     * Get how many columns there are.
     * @return The number of columns
     */
    int	getColumnCount() {
        return this.matrix[0].length;
    }

    /**
     * Get the matrix array.
     * @return The matrix array
     */
    double[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Get how many rows there are.
     * @return The number of rows
     */
    int getRowCount() {
        return this.matrix.length;
    }

    /**
     * Get the specific value at the given row and column.
     * @param row The row index
     * @param column The column index
     * @return The value at those indices
     */
    double getValueAt(int row, int column) {
        return this.matrix[row][column];
    }

    /**
     * Inverts a matrix.
     * @return An inverted matrix
     */
    Matrix invert() {
        Matrix augmented_matrix = this.augment();
        Matrix reduced_row_matrix = augmented_matrix.rowReduce();
        Matrix inverted_matrix = new Matrix(reduced_row_matrix.getRowCount(), reduced_row_matrix.getColumnCount()/2);
        for (int i = 0; i < inverted_matrix.getRowCount(); i++) {
            for (int j = 0; j < inverted_matrix.getColumnCount(); j++) {
                inverted_matrix.setEntry(i, j, reduced_row_matrix.getValueAt(i, j+reduced_row_matrix.getColumnCount()/2));
            }
        }
        return inverted_matrix;
    }

    /**
     * Adds a scalar multiple of the first row to the second row.
     * @param scalar Scalar to multiply the source row by
     * @param sourceRow The row to multiply the scalar by and add to destRow
     * @param destRow Add scalar * sourceRow to destRow and set destRow equal to the result
     * @return A new matrix with this operation performed
     */
    Matrix linearCombRows(double scalar, int sourceRow, int destRow) {
        Matrix result  = new Matrix(this.getMatrix());

        for (int i = 0; i < this.getColumnCount(); i++) {
            result.setEntry(destRow, i, this.getValueAt(destRow, i) + this.getValueAt(sourceRow, i)*scalar);
        }

        return result;
    }

    /**
     * Add matrices and return the sum.
     * @param matrix The matrix to add to this matrix
     * @return The new matrix of the sum of each element
     */
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

    /**
     * Row reduces a matrix.
     * @return A new matrix in row reduced form
     */
    Matrix rowReduce() {
        Matrix m = new Matrix(this.getMatrix());
        int lead = 0;

        for (int r = 0; r < this.getRowCount(); r++) {
            if (this.getColumnCount() <= lead) {
                return m;
            }
            int i = r;
            while (m.getValueAt(i, lead) == 0) {
                i++;
                if (this.getRowCount() == i) {
                    i = r;
                    lead++;
                    if (this.getColumnCount() == lead) {
                        return m;
                    }
                }
            }
            if (i != r) {
                m = this.switchRows(i, r);
            }
            m = this.scalarTimesRow(m.getValueAt(r, lead), r);
            // Divide row r by M[r, lead]
            for (int j = 0; j < this.getRowCount(); j++) {
                if (j != r) {
                    m = this.linearCombRows(m.getValueAt(j, lead), r, j);
                    //Subtract M[i, lead] multiplied by row r from row i
                }
            }
            lead++;
        }

        return m;
    }

    /**
     * Multiplies a scalar times a row.
     * @param scalar The scalar to multiply the row by
     * @param row The index of the row to multiply with the scalar
     * @return The matrix with the row multiplied by the scalar
     */
    Matrix scalarTimesRow(double scalar, int row) {
        Matrix result = new Matrix(this.getMatrix());

        for (int i = 0; i < this.getColumnCount(); i++) {
            result.setEntry(row, i, result.getValueAt(row, i) * scalar);
        }

        return result;
    }

    /**
     * Set a specific value at the given row and column.
     * @param row The index of the row
     * @param column The index of the column
     * @param value The value to put at (row, column)
     */
    void setEntry(int row, int column, double value) {
        this.matrix[row][column] = value;
    }

    /**
     * Switch two rows of a matrix.
     * @param firstrow The index of the first row
     * @param secondrow The index of the second row
     * @return A new matrix with the two rows switched
     */
    Matrix switchRows(int firstrow, int secondrow) {
        double[] firstrowInMatrix = this.matrix[firstrow];
        double[] secondrowInMatrix = this.matrix[secondrow];

        Matrix newMatrix = new Matrix(this.getMatrix());

        for (int i = 0; i < this.matrix[firstrow].length; i++) {
            newMatrix.setEntry(firstrow, i, this.getValueAt(secondrow, i));
        }

        for (int i = 0; i < this.matrix[secondrow].length; i++) {
            newMatrix.setEntry(secondrow, i, this.getValueAt(firstrow, i));
        }

        return newMatrix;
    }

    /**
     * Multiply this matrix to the given matrix and return the product.
     * @param matrix The matrix to multiply this matrix by
     * @return A new matrix, which is the dot product of this matrix and the matrix passed in as an argument
     */
    Matrix times(Matrix matrix) {
        if (this.getColumnCount() != matrix.getRowCount()) {
            throw new IllegalArgumentException("The number of columns in this matrix does not match the number of rows in the matrix argument");
        }

        Matrix result = new Matrix(this.getRowCount(), matrix.getColumnCount());
        for (int row_idx = 0; row_idx < this.getRowCount(); row_idx++) {
            double[] row = matrix.getMatrix()[row_idx];
            for (int col_idx = 0; col_idx < matrix.getColumnCount(); col_idx++) {
                double[] column = new double[matrix.getRowCount()];
                for (int i = 0; i < matrix.getRowCount(); i++) {
                    column[i] = matrix.getValueAt(i, col_idx);
                }

                double sum = 0;
                for (int i = 0; i < this.getColumnCount(); i++) {
                    sum += row[i]*column[i];
                }

                result.setEntry(row_idx, col_idx, sum);
            }
        }
        return result;
    }

    /**
     * Composes a string representation of the matrix
     * @return The string representation of the matrix
     */
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
