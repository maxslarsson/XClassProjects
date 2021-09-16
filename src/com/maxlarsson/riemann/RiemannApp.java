package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;

public class RiemannApp {
    public static void main(String[] args) {
        Polynomial poly = new Polynomial(new double[]{0, -4, 0.5});

        double xLower = 3;
        double xUpper = 13;
        int subintervals = 10;

        LeftHandPlot leftHandPlot = new LeftHandPlot(poly, xLower, xUpper, subintervals);
        RightHandPlot rightHandPlot = new RightHandPlot(poly, xLower, xUpper, subintervals);
        TrapezoidPlot trapezoidPlot = new TrapezoidPlot(poly, xLower, xUpper, subintervals);

        AbstractRiemann[] plots = new AbstractRiemann[]{leftHandPlot, rightHandPlot, trapezoidPlot};

        for (AbstractRiemann plot : plots) {
            plot.plotPolynomial();
            plot.plotAccFnc();
            plot.drawRiemannSlices();
            System.out.println(plot.getClass().getName() + ": " + plot.getIntervalArea());
        }
    }
}
