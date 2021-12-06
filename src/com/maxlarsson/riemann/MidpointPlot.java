package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import java.awt.geom.Path2D;

public class MidpointPlot extends AbstractRiemann {
    public MidpointPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        super(polynomial, xLower, xUpper, subintervals);
    }

    public MidpointPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals, PlotFrame plotFrame) {
        super(polynomial, xLower, xUpper, subintervals, plotFrame);
    }

    @Override
    public double getSubintervalArea(double leftBorder, double rightBorder) {
        double midpoint = leftBorder + (rightBorder-leftBorder) / 2;

        return (rightBorder - leftBorder) * poly.eval(midpoint);
    }

    @Override
    void drawSlice(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double midpoint = leftBorder + (rightBorder-leftBorder) / 2;
        double height = poly.eval(midpoint);
        DrawableShape rect = DrawableShape.createRectangle(leftBorder + width/2, height/2, width, Math.abs(height));
        plotFrame.addDrawable(rect);
    }
}
