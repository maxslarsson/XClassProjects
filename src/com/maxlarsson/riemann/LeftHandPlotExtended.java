package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import java.awt.geom.Path2D;
import java.util.function.DoubleUnaryOperator;

public class LeftHandPlotExtended extends AbstractRiemannExtended {
    public LeftHandPlotExtended(DoubleUnaryOperator function, double xLower, double xUpper, int subintervals) {
        super(function, xLower, xUpper, subintervals);
    }

    public LeftHandPlotExtended(DoubleUnaryOperator function, double xLower, double xUpper, int subintervals, PlotFrame plotFrame) {
        super(function, xLower, xUpper, subintervals, plotFrame);
    }

    @Override
    public double getSubintervalArea(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = function.applyAsDouble(leftBorder);

        return width * height;
    }

    @Override
    void drawSlice(double leftBorder, double rightBorder) {
        double width = rightBorder - leftBorder;
        double height = function.applyAsDouble(leftBorder);
        DrawableShape rect = DrawableShape.createRectangle(leftBorder + width/2, height/2, width, Math.abs(height));
        plotFrame.addDrawable(rect);
    }
}
