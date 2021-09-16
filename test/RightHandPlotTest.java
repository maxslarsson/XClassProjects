import com.maxlarsson.riemann.RightHandPlot;
import org.dalton.polyfun.Polynomial;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RightHandPlotTest {
    @Test
    public void slice() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        RightHandPlot rightHandPlot = new RightHandPlot(poly, 0, 2, 10);
        assertEquals(38, rightHandPlot.getSubintervalArea(0, 2), 0);
    }

    @Test
    public void area() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        RightHandPlot rightHandPlot = new RightHandPlot(poly, -10, 10, 10);
        assertEquals(1500, rightHandPlot.getIntervalArea(), 0);
    }
}
