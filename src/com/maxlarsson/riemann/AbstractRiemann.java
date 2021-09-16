package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractRiemann {
    PlotFrame plotFrame = new PlotFrame("x", "y", "Riemann Sums");
    Polynomial poly;
    Trail polyTrail = new Trail();
    Trail accFnTrail = new Trail();
    double xLower;
    double xUpper;
    int subintervals;
    int width = 50;
    int height = 50;

    /**
     * Construct a new instance of the class
     *
     * @param polynomial The polynomial to use in the calculation of the area.
     * @param xLower The lower bound of where to find the area.
     * @param xUpper The upper bound of where to find the area.
     * @param subintervals The number of triangles to use to find the area of the polynomial.
     */
    AbstractRiemann(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        this.poly = polynomial;
        this.xLower = Math.min(xLower, xUpper);
        this.xUpper = Math.max(xLower, xUpper);
        this.subintervals = subintervals;

        configPlotFrame();
    }

    /**
     * Calculate the width of a single slice.
     *
     * @return A double of the delta
     */
    double calculateDeltaX() {
        return (xUpper - xLower) / subintervals;
    }

    /**
     * Set the properties of the plot frame such as size, visibility, default close operation, and the preferred min and max of the x axis and the y axis.
     */
    void configPlotFrame() {
        // Configure the plot frame.
        plotFrame.setSize(400, 400); // window size
        plotFrame.setPreferredMinMax(-width, width, -height, height); // x and y ranges
        plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);  // if you want closing the graph to end the program
        plotFrame.setVisible(true); // need this to show the graph, it is false by default

        plotFrame.addDrawable(polyTrail); // add the trail to the plot frame
        polyTrail.color = Color.RED;   // optional specify color

        plotFrame.addDrawable(accFnTrail); // add the trail to the plot frame
        accFnTrail.color = Color.GREEN;   // optional specify color
    }

    /**
     * Draw all the slices for this poly that are in the interval.
     */
    void drawRiemannSlices() {
        double delta = calculateDeltaX();
        for (double i = xLower; i <= xUpper; i += delta) {
            drawSlice(i, i+delta);
        }
    }

    /**
     * Calculate the area of each subinterval.
     *
     * @return A double array with the area of each subinterval.
     */
    double[] arrayOfIntervals() {
        double[] intervals = new double[subintervals];
        double delta = calculateDeltaX();
        int index = 0;
        for (double i = xLower; i < xUpper; i += delta) {
            intervals[index] = getSubintervalArea(i, i+delta);
            index++;
        }
        return intervals;
    }

    /**
     * Estimate the area under the poly using Riemann sums.
     *
     * @return The estimated area under the curve
     */
    public double getIntervalArea() {
        double sum = 0;
        for (double interval : arrayOfIntervals()) {
            sum += interval;
        }
        return sum;
    }

    /**
     * Draw the accumulation function.
     */
    void plotAccFnc() {
        int i = 0;
        double runningSum = 0;
        accFnTrail.addPoint(xLower, 0);
        for (double interval : arrayOfIntervals()) {
            runningSum += interval;
            accFnTrail.addPoint(xLower + (i * calculateDeltaX()) + 1, runningSum);
            i++;
        }
    }

    /**
     * Draw the poly on the plot frame.
     */
    void plotPolynomial() {
        double step = 0.1;

        // TODO: What if this is a function that will never reach the width (e.g.) goes straight up? Well this becomes an infinite loop. Make it so that does NOT happen
        for (double x = -width; x <= width; x += step) {
            double y = poly.eval(x);

            polyTrail.addPoint(x, poly.eval(x));
        }
    }

    /**
     * Estimate the area under the poly for the given slice, or subinterval. This is abstract because the implementation depends on which Riemann Rule you use.
     *
     * @param leftBorder The left x-coordinate of the slice
     * @param rightBorder The right x-coordinate of the slice
     * @return The area of this slice.
     */
    public abstract double getSubintervalArea(double leftBorder, double rightBorder);

    /**
     * Draw a single slice, or subinterval, under the poly. This is abstract because the implementation depends on which Riemann Rule you use.
     *
     * @param leftBorder The left x-coordinate of the slice
     * @param rightBorder The right x-coordinate of the slice
     */
    abstract void drawSlice(double leftBorder, double rightBorder);
}