package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

public class LeftHandPlot extends AbstractRiemann {
    public LeftHandPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        super(polynomial, xLower, xUpper, subintervals);
    }

    public LeftHandPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals, PlotFrame plotFrame) {
        super(polynomial, xLower, xUpper, subintervals, plotFrame);
    }

    @Override
    public double getSubintervalArea(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = poly.eval(leftBorder);

        return width * height;
    }

    @Override
    void drawSlice(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = poly.eval(leftBorder);
        DrawableShape rect = DrawableShape.createRectangle(leftBorder + width/2, height/2, width, Math.abs(height));
        plotFrame.addDrawable(rect);
    }
}
