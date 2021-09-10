import org.junit.Test;

import static java.lang.Double.NaN;
import static org.junit.Assert.*;

public class QuadraticTest {
    @Test
    public void getA() {
        Quadratic q = new Quadratic(1, 2, 3);
        assertEquals(1, q.getA(), 0);
    }

    @Test
    public void getB() {
        Quadratic q = new Quadratic(1, 2, 3);
        assertEquals(q.getB(), 2, 0);
    }

    @Test
    public void getC() {
        Quadratic q = new Quadratic(1, 2, 3);
        assertEquals(q.getC(), 3, 0);
    }

    @Test
    public void hasRealRoots() {
        Quadratic no_real_roots = new Quadratic(1, 3, 4);
        assertFalse(no_real_roots.hasRealRoots());

        Quadratic one_real_roots = new Quadratic(-4, 12, -9);
        assertTrue(one_real_roots.hasRealRoots());

        Quadratic two_real_roots = new Quadratic(2, -11, 5);
        assertTrue(two_real_roots.hasRealRoots());
    }

    @Test
    public void numberOfRoots() {
        Quadratic no_real_roots = new Quadratic(1, 3, 4);
        assertEquals(no_real_roots.numberOfRoots(), 0, 0);

        Quadratic one_real_roots = new Quadratic(-4, 12, -9);
        assertEquals(one_real_roots.numberOfRoots(), 1, 0);

        Quadratic two_real_roots = new Quadratic(2, -11, 5);
        assertEquals(two_real_roots.numberOfRoots(), 2, 0);
    }

    @Test
    public void getRootArray() {
        Quadratic no_real_roots = new Quadratic(1, 3, 4);
        assertArrayEquals(no_real_roots.getRootArray(), new double[]{NaN, NaN}, 0.0);

        Quadratic one_real_roots = new Quadratic(-4, 12, -9);
        assertArrayEquals(one_real_roots.getRootArray(), new double[]{1.5, 1.5}, 0.0);

        Quadratic two_real_roots = new Quadratic(2, -11, 5);
        assertArrayEquals(two_real_roots.getRootArray(), new double[]{5, 0.5}, 0.0);
    }

    @Test
    public void getAxisOfSymmetry() {
        Quadratic q = new Quadratic(1, -6, 5);
        assertEquals(q.getAxisOfSymmetry(), 3, 0);
    }

    @Test
    public void getExtremeValue() {
        Quadratic q = new Quadratic(2, -12, 16);
        assertEquals(q.getExtremeValue(), -2, 0);
    }

    @Test
    public void isMax() {
        Quadratic max = new Quadratic(-3, -4, -1);
        assertTrue(max.isMax());

        Quadratic min = new Quadratic(9, 5, -16);
        assertFalse(min.isMax());
    }

    @Test
    public void evaluateWith() {
        Quadratic q = new Quadratic(2, -12, 16);
        assertEquals(q.evaluateWith(3), -2, 0);
    }
}
