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

    AbstractRiemann(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        this.poly = polynomial;
        this.xLower = Math.min(xLower, xUpper);
        this.xUpper = Math.max(xLower, xUpper);
        this.subintervals = subintervals;

        configPlotFrame();
    }

    double calculateDeltaX() {
        return (xUpper - xLower) / subintervals;
    }

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

    void drawRiemannSlices() {
        double delta = calculateDeltaX();
        for (double i = xLower; i <= xUpper; i += delta) {
            drawSlice(i, i+delta);
        }
    }

    double[] arrayOfIntervals() {
        double[] intervals = new double[subintervals + 1];
        double delta = calculateDeltaX();
        int index = 0;
        for (double i = xLower; i <= xUpper; i += delta) {
            intervals[index] = getSubintervalArea(i, i+delta);
            index++;
        }
        return intervals;
    }

    double getIntervalArea() {
        double sum = 0;
        for (double interval : arrayOfIntervals()) {
            sum += interval;
        }
        return sum;
    }

    void plotAccFnc() {
        int i = 0;
        double runningSum = 0;
        for (double interval : arrayOfIntervals()) {
            System.out.println(interval);
            runningSum += Math.abs(interval);
            accFnTrail.addPoint(xLower + (i * calculateDeltaX()), runningSum);
            i++;
        }
    }

    void plotPolynomial() {
        double step = 0.1;

        for (double x = -width; x <= width; x += step) {
            polyTrail.addPoint(x, poly.eval(x));
        }
    }

    abstract double getSubintervalArea(double leftBorder, double rightBorder);
    abstract void drawSlice(double leftBorder, double rightBorder);
}