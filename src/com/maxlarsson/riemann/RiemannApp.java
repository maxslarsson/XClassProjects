package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;

import java.util.function.DoubleUnaryOperator;

public class RiemannApp {
    public static void main(String[] args) {
        Polynomial poly = new Polynomial(new double[]{0, -4, 0.5});

        double xLower = -7;
        double xUpper = 13;
        int subintervals = 10;

        LeftHandPlot leftHandPlot = new LeftHandPlot(poly, xLower, xUpper, subintervals);
        RightHandPlot rightHandPlot = new RightHandPlot(poly, xLower, xUpper, subintervals);
        TrapezoidPlot trapezoidPlot = new TrapezoidPlot(poly, xLower, xUpper, subintervals);
        MidpointPlot midpointPlot = new MidpointPlot(poly, xLower, xUpper, subintervals);

        AbstractRiemann[] plots = new AbstractRiemann[]{leftHandPlot, rightHandPlot, trapezoidPlot, midpointPlot};

        for (AbstractRiemann plot : plots) {
            plot.plotPolynomial();
            plot.plotAccFnc();
            plot.drawRiemannSlices();
            System.out.println(plot.getClass().getName() + ": " + plot.getIntervalArea());
        }

        System.out.println("Approximation of Pi:");

        DoubleUnaryOperator function = (x) -> Math.sqrt(1 - Math.pow(x, 2));

        LeftHandPlotExtended leftHandPlotExtended = new LeftHandPlotExtended(function, -1, 1, 35);
        leftHandPlotExtended.plotPolynomial();
        leftHandPlotExtended.drawRiemannSlices();
        // Times 2 to get the approximation of area under the entire circle, since we are only plotting a semicircle
        System.out.println(leftHandPlotExtended.getClass().getName() + ": " + leftHandPlotExtended.getIntervalArea() * 2);

        TrapezoidPlotExtended trapezoidPlotExtended = new TrapezoidPlotExtended(function, -1, 1, 35);
        trapezoidPlotExtended.plotPolynomial();
        trapezoidPlotExtended.drawRiemannSlices();
        // Times 2 to get the approximation of area under the entire circle, since we are only plotting a semicircle
        System.out.println(trapezoidPlotExtended.getClass().getName() + ": " + trapezoidPlotExtended.getIntervalArea() * 2);
    }
}
