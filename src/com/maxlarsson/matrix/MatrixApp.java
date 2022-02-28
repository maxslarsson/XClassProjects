package com.maxlarsson.matrix;

public class MatrixApp {
    static Matrix matrix = new Matrix(new double[][]{
        {1, 0, 0},
        {0, 2, 0},
        {0, 0, 3}
    });

    public static void main(String[] args) {
        System.out.println("Original:");
        System.out.println(matrix);
        System.out.println();
        System.out.println("Inverted:");
        System.out.println(matrix.invert());
    }
}
