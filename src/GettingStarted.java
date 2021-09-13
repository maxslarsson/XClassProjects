import org.dalton.polyfun.Polynomial;

import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import java.awt.Color;

public class GettingStarted {
    public static void main(String[] args) {
//        Polynomial fx = new Polynomial(new double[]{5, 1, 3}); // pass in the coefficients
        Polynomial fx = new Polynomial(new double[]{0, 0, 1}); // pass in the coefficients
        System.out.println("f(x) = " + fx);

        // Get a coefficient
        System.out.println("The coefficient for x^2 is: " + fx.getCoefficientAtTerm(2));  // term is the exponent of x
        System.out.println("The coefficient for x^1 is: " + fx.getCoefficientAtTerm(1));  // term is the exponent of x

        // Alternatively, get all coefficients in one array
        double[] coefs = fx.getCoefficientArray();
        System.out.println("The coefficient for x^2 is: " + coefs[2]);

        // Evaluate the polynomial
        System.out.println("f(5) = " + fx.eval(5));

        PlotFrame plotFrame = new PlotFrame("x", "y", "Plotting Examples");

        // Configure the plot frame.
        plotFrame.setSize(400, 400); // window size
        plotFrame.setPreferredMinMax(0, 10, 0, 15); // x and y ranges
        plotFrame.setDefaultCloseOperation(3);  // if you want closing the graph to end the program
        plotFrame.setVisible(true); // need this to show the graph, it is false by default

        // Plotting lines

        // OPTION 1: Append connected points

        // THIS IS WHAT DOES NOT MATCH
        plotFrame.setLineColor(0, Color.RED);   // optional set line color
        plotFrame.setConnected(true); // connect the points

        for (int x = 0; x <= 10; x++) {
            plotFrame.append(0, x, fx.eval(x));
        }
//        plotFrame.append(0, 1, fx.eval(1));
//        plotFrame.append(0, 0, fx.eval(0));
//        plotFrame.append(1, 2, fx.eval(2)); // a different datasetIndex creates a new line
//        plotFrame.append(1, 3, fx.eval(3)); // a different datasetIndex creates a new line

        // Make green line

        // THIS WAS DONE BY REVERSE ENGINEERING THE CODE
        Polynomial vx = new Polynomial(new double[]{1, 2, 3});

        plotFrame.setLineColor(1, Color.GREEN);   // optional set line color
        plotFrame.setConnected(true); // connect the points

        for (int x = 0; x <= 10; x++) {
            plotFrame.append(1, x, vx.eval(x));
        }



        Polynomial gx = new Polynomial(new double[]{5, 0, 0, 0, 4}); // pass in the coefficients

        // OPTION 2: Add a Trail
        Trail trail = new Trail();
        plotFrame.addDrawable(trail); // add the trail to the plot frame
        trail.color = Color.YELLOW;   // optional specify color
        for (int x = 0; x <= 10; x++) {
            trail.addPoint(x, gx.eval(x));
        }
//        trail.addPoint(0, 0);
//        trail.addPoint(4, 3);
//        trail.addPoint(8, 9);
//        trail.addPoint(16, 18);

        System.out.println("\nExercise:");

        // Step 1
        System.out.println("1. g(x) = " + gx);

        // Step 2


        System.out.println("2. v(2) = " + vx.eval(2));

        // Step 3
        double[] fxcoeffs = fx.getCoefficientArray();
        double[] vxcoeffs = vx.getCoefficientArray();

        assert fxcoeffs.length == vxcoeffs.length;

        double[] added = new double[fxcoeffs.length];
        for (int i = 0; i < fxcoeffs.length; i++) {
            added[i] = fxcoeffs[i] + vxcoeffs[i];
        }

        Polynomial addedpoly = new Polynomial(added);

        System.out.println("3. f(x) + v(x) = " + addedpoly);

        // Step 4
        System.out.println("4. " + vx.getCoefficientAtTerm(1));

        // Step 5
        System.out.println("5. Coefficients for v(x):");
        for (double coeff : vx.getCoefficientArray()) {
            System.out.println(coeff);
        }
    }

    /**
     * Go to /test/GettingStartedTest.java to see how to test this method with Junit.
     *
     * @param a first num to add
     * @param b second num to add
     * @return the sum
     */
    public static int add(int a, int b) {
        return a + b;
    }
}
