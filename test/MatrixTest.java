import com.maxlarsson.matrix.Matrix;
import org.junit.Test;

public class MatrixTest {
    @Test
    public void invertMatrix() {
        Matrix matrix = new Matrix(new double[][]{
                {1, 9, 2},
                {8, 3, 7},
                {4, 6, 5}
        });
        System.out.println(matrix.invert());
    }
}
