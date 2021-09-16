package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;
import org.opensourcephysics.display.Drawable;
import org.opensourcephysics.display.DrawableShape;

import java.awt.geom.Path2D;

public class TrapezoidPlot extends AbstractRiemann {
    public TrapezoidPlot(Polynomial polynomial, double xLower, double xUpper, int subintervals) {
        super(polynomial, xLower, xUpper, subintervals);
    }

    @Override
    public double getSubintervalArea(double leftBorder, double rightBorder) {
        double b1 = poly.eval(leftBorder);
        double b2 = poly.eval(rightBorder);

        return (rightBorder - leftBorder) * ((b1 + b2) / 2);
    }

    @Override
    void drawSlice(double leftBorder, double rightBorder) {
        double b1 = poly.eval(leftBorder);
        double b2 = poly.eval(rightBorder);

        Path2D trapezoid = new Path2D.Double();

        // Bottom left corner
        trapezoid.moveTo(leftBorder, 0);
        // Bottom right corner
        trapezoid.lineTo(rightBorder, 0);
        // Upper right corner
        trapezoid.lineTo(rightBorder, b2);
        // Upper left corner
        trapezoid.lineTo(leftBorder, b1);
        // Go back to bottom left corner to finish trapezoid
        trapezoid.lineTo(leftBorder, 0);

        trapezoid.closePath();

        plotFrame.addDrawable(new DrawableShape(trapezoid, 0, 0));
    }
}
