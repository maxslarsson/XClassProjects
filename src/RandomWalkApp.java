import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.PlotFrame;


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
public class RandomWalkApp extends AbstractSimulation {
    // Set up global variables.
    PlotFrame plotFrame = new PlotFrame("x", "meters from ground", "Random Walk");
    Circle[] circles = new Circle[50];
    double totalTime;

    /**
     * Technically optional, but the simulation won't work without it.
     * Adds options to the Control Panel.
     */
    @Override
    public void reset() {
        control.setValue("Starting Y position", 0);
        control.setValue("Starting X position", 0);
    }

    /**
     * Technically optional, but the simulation won't work without it.
     * Tied to the "Initialize" button. Gets information from the Control Panel and
     * configures the plot frame.
     */
    @Override
    public void initialize() {
        // Get information from the control panel.
        double startingY = control.getDouble("Starting Y position");
        double startingX = control.getDouble("Starting X position");

        for (int i = 0; i < circles.length; i++) {
            circles[i] = new Circle();
            circles[i].setX(startingX);
            circles[i].setY(startingY);
            plotFrame.addDrawable(circles[i]);
        }

        // Instead of appending x, y coordinates to plot frame,
        //    add the Circle which maintains its own x, y.

        // Configure plot frame
        plotFrame.setPreferredMinMax(-50, 50, -50, 50); // Scale of graph.
        plotFrame.setDefaultCloseOperation(3); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }

    /**
     * Required method, invoked once every 1/10 second until Stop is pressed.
     * The doStep method is also called when the Step button is pressed.
     */
    public void doStep() {
        for (Circle circle : circles) {
            int[] possibleMoveX = new int[]{-1, 0, 1};
            int[] possibleMoveY = new int[]{-1, 0, 1};

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive

            int randomMoveX = (int) Math.floor(Math.random() * possibleMoveX.length);
            int randomMoveY = (int) Math.floor(Math.random() * possibleMoveY.length);

            // Change y. (It will re-draw itself.)
            circle.setX(circle.getX() + possibleMoveX[randomMoveX]);
            circle.setY(circle.getY() + possibleMoveY[randomMoveY]);
        }

        totalTime++;
    }

    /**
     * Required main method, runs the simulation.
     * @param args
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new RandomWalkApp());
    }
}
