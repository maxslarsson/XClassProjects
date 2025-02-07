package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

public class RightHandPlot extends AbstractRiemann {
    public RightHandPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        super(polynomial, xLower, xUpper, subintervals);
    }

    public RightHandPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals, PlotFrame plotFrame) {
        super(polynomial, xLower, xUpper, subintervals, plotFrame);
    }

    @Override
    public double getSubintervalArea(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = poly.eval(rightBorder);

        return width * height;
    }

    @Override
    void drawSlice(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = poly.eval(rightBorder);
        DrawableShape rect = DrawableShape.createRectangle(leftBorder + width/2, height/2, width, Math.abs(height));
        plotFrame.addDrawable(rect);
    }
}
