import com.maxlarsson.riemann.LeftHandPlot;
import com.maxlarsson.riemann.TrapezoidPlot;
import org.dalton.polyfun.Polynomial;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrapezoidPlotTest {
    @Test
    public void slice() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        TrapezoidPlot trapezoidPlot = new TrapezoidPlot(poly, 0, 2, 10);
        assertEquals(22, trapezoidPlot.getSubintervalArea(0, 2), 0);
    }

    @Test
    public void area() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        TrapezoidPlot trapezoidPlot = new TrapezoidPlot(poly, -10, 10, 10);
        assertEquals(1420, trapezoidPlot.getIntervalArea(), 0);
    }
}
