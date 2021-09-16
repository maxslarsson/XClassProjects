import com.maxlarsson.riemann.LeftHandPlot;
import org.dalton.polyfun.Polynomial;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LeftHandPlotTest {
    @Test
    public void slice() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        LeftHandPlot leftHandPlot = new LeftHandPlot(poly, 0, 2, 10);
        assertEquals(6, leftHandPlot.getSubintervalArea(0, 2), 0);
    }

    @Test
    public void area() {
        Polynomial poly = new Polynomial(new double[]{3, 4, 2});
        LeftHandPlot leftHandPlot = new LeftHandPlot(poly, -10, 10, 10);
        assertEquals(1340, leftHandPlot.getIntervalArea(), 0);
    }
}
