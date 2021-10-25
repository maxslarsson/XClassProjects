package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractRiemann {
    PlotFrame plotFrame;
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
        this.xLower = xLower;
        this.xUpper = xUpper;
//        this.xLower = Math.min(xLower, xUpper);
//        this.xUpper = Math.max(xLower, xUpper);
        this.subintervals = subintervals;
        this.plotFrame = new PlotFrame("x", "y", "Riemann Sums");

        // Only configPlotFrame if plotFrame is not passed in
        configPlotFrame();

        addTrailsToPlotFrame();
    }

    AbstractRiemann(Polynomial polynomial, double xLower, double xUpper, int subintervals, PlotFrame plotFrame) {
        this.poly = polynomial;
        this.xLower = xLower;
        this.xUpper = xUpper;
//        this.xLower = Math.min(xLower, xUpper);
//        this.xUpper = Math.max(xLower, xUpper);
        this.subintervals = subintervals;
        this.plotFrame = plotFrame;

        addTrailsToPlotFrame();
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
    }

    /**
     * Add the polyTrail and accFnTrail to the plotFrame
     */
    void addTrailsToPlotFrame() {
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

        int stopCondition = (int) ((xUpper - xLower)/delta);
        for (int i = 0; i < stopCondition; i++) {
            double realI = xLower + delta*i;

            drawSlice(realI, realI+delta);
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
        for (int i = 0; i < subintervals; i++) {
            double realI = xLower + delta*i;
            intervals[i] = getSubintervalArea(realI, realI+delta);
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
    // TODO: Fix accumulation function so that it starts at x=0, not xLower
    // Ig now it will not work with the arrayOfIntervals I have
    void plotAccFnc() {
        if (xLower != 0 && xUpper != 0) {
            try {
                Class[] cArg = new Class[5];
                cArg[0] = Polynomial.class;
                cArg[1] = double.class;
                cArg[2] = double.class;
                cArg[3] = int.class;
                cArg[4] = PlotFrame.class;
                Constructor<? extends AbstractRiemann> c = this.getClass().getConstructor(cArg);
                if ((xLower > 0 && xUpper > 0) || (xLower < 0 && xUpper < 0)) {
                    AbstractRiemann self = c.newInstance(poly, 0, Math.max(xLower, xUpper), subintervals, plotFrame);
                    self.plotAccFnc();
                } else if ((xLower < 0 && xUpper > 0) || (xLower > 0 && xUpper < 0)) {
                    AbstractRiemann leftHandSide = c.newInstance(poly, 0, Math.min(xLower, xUpper), subintervals, plotFrame);
                    leftHandSide.plotAccFnc();
                    AbstractRiemann rightHandSide = c.newInstance(poly, 0, Math.max(xLower, xUpper), subintervals, plotFrame);
                    rightHandSide.plotAccFnc();
                } else {
                    System.err.println("ERROR! This else clause should NOT be able to be reached...");
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            int i = 1;
            double runningSum = 0;
            accFnTrail.addPoint(xLower, 0);
            for (double interval : arrayOfIntervals()) {
                System.out.println(interval);
                runningSum += interval;
                accFnTrail.addPoint(xLower + (i * calculateDeltaX()), runningSum);
                i++;
            }
        }
    }

    /**
     * Draw the poly on the plot frame.
     */
    void plotPolynomial() {
        double step = 0.1;

//        for (double i = -width; i < width; i += step) {
        int stopCondition = (int) (width * 2 / step);
        for (int i = 0; i < stopCondition; i++) {
            double x = -width + step*i;
            double y = poly.eval(x);

            polyTrail.addPoint(x, y);
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