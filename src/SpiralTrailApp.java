import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import java.util.Arrays;
import java.util.stream.IntStream;

/***
 * MovingBallApp is an extension of AbstractSimulation (an abstract class).
 *
 * An abstract class is a class that almost works except that the author intentionally left some methods empty. It is up
 * to the programmer who uses it to complete those empty methods for the class to fully work.
 *
 * In AbstractSimulation, these methods need to be completed:
 *     1. reset (adds options to the Control Panel)
 *     2. initialize (gets data from the Control Panel and sets up the graph)
 *     3. doStep (actions to take to do the animation, invoked every 1/10 second)
 *
 * You also need a main method to run the simulation (scroll to bottom).
 */
public class SpiralTrailApp extends AbstractSimulation {
    // Set up global variables.
    PlotFrame plotFrame = new PlotFrame("x", "meters from ground", "Spiral Trail");
    Trail trail = new Trail();
    int totalTime;
    int[] xMoves = new int[]{-1,  0, 1, 1, 0, 0, -1, -1, -1,  0,  0,  0, 1, 1, 1, 1, 0, 0, 0, 0, -1, -1, -1, -1, -1};
    int[] yMoves = new int[]{ 0, -1, 0, 0, 1, 1,  0,  0,  0, -1, -1, -1, 0, 0, 0, 0, 1, 1, 1, 1,  0,  0,  0,  0, 0};

    /**
     * Technically optional, but the simulation won't work without it.
     * Tied to the "Initialize" button. Gets information from the Control Panel and
     * configures the plot frame.
     */
    @Override
    public void initialize() {
        DrawableShape rect = DrawableShape.createRectangle(3, 3, 4, 4);
        plotFrame.addDrawable(rect);
        plotFrame.addDrawable(trail);

        // putting dots along the width
        for (int i = 1; i < 6; i++) {
            // putting dots along the height
            for (int j = 1; j < 6; j++) {
                Circle circle = new Circle();
                circle.setX(i);
                circle.setY(j);
                plotFrame.addDrawable(circle);
            }
        }

        // Configure plot frame
        plotFrame.setPreferredMinMax(0, 6, 0, 6); // Scale of graph.
        plotFrame.setDefaultCloseOperation(3); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }

    /**
     * Required method, invoked once every 1/10 second until Stop is pressed.
     * The doStep method is also called when the Step button is pressed.
     */
    public void doStep() {
        // Change y. (It will re-draw itself.)

        assert xMoves.length == yMoves.length;

        if (totalTime < xMoves.length) {
            int[] xNew = Arrays.copyOfRange(xMoves, 0, (int) totalTime);
            int xPos = IntStream.of(xNew).sum();

            int[] yNew = Arrays.copyOfRange(yMoves, 0, (int) totalTime);
            int yPos = IntStream.of(yNew).sum();

            // 3 comes from it being the center point
            trail.addPoint(3 + xPos, 3 + yPos);
        }

        totalTime++;
    }

    /**
     * Required main method, runs the simulation.
     * @param args
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new SpiralTrailApp());
    }
}
