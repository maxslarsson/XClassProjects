package com.maxlarsson.matrix;

import java.util.Scanner;

public class MatrixExtension {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Usage
        System.out.print("Enter number of rows AND columns the square matrix should have (e.g. '3' for a 3x3 matrix): ");
        int n = sc.nextInt();

        Matrix matrix = new Matrix(n, n);

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                System.out.printf("Enter the double in column %d and row %d: ", c+1, r+1);
                double d = sc.nextDouble();
                matrix.setEntry(r, c, d);
            }
        }

        System.out.println();
        System.out.println("Original matrix:");
        System.out.println(matrix);
        System.out.println();
        System.out.println("Inverted matrix:");
        System.out.println(matrix.invert());
    }
}
