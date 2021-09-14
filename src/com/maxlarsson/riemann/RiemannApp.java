package com.maxlarsson.riemann;

import org.dalton.polyfun.Polynomial;

public class RiemannApp {
    public static void main(String[] args) {
        Polynomial poly = new Polynomial(new double[]{0, -4, 0.5});

        LeftHandPlot leftHandPlot = new LeftHandPlot(poly, 3, 13, 10);
        leftHandPlot.plotPolynomial();
        leftHandPlot.plotAccFnc();
        leftHandPlot.drawRiemannSlices();
        System.out.println(leftHandPlot.getIntervalArea());
    }
}
