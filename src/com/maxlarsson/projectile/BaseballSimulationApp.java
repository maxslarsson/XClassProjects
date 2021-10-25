package com.maxlarsson.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class BaseballSimulationApp extends AbstractSimulation {
    PlotFrame plotFrame = new PlotFrame("x", "y", "Baseball Simulation");

    int greenMonsterHeight = 10;
    int greenMonsterX = 100;

    Particle baseball;

    /**
     * Technically optional, but the simulation won't work without it.
     * Adds options to the Control Panel.
     */
    @Override
    public void reset() {
        control.setValue("Velocity X", 40);
        control.setValue("Velocity Y", 15);
    }

    @Override
    public void initialize() {
        double startingVX = control.getDouble("Velocity X");
        double startingVY = control.getDouble("Velocity Y");

        baseball = new Particle(0, 1, startingVX, startingVY, 0.141748, 0.075);

        plotFrame.addDrawable(baseball);

        Rectangle wall = new Rectangle(greenMonsterX, 0, 1, greenMonsterHeight);
        DrawableShape drawableWall = new DrawableShape(wall, 0, 0);
        drawableWall.color = new Color(0, 255, 0);
        plotFrame.addDrawable(drawableWall);

        baseball.color = new Color(0, 0, 255);

        plotFrame.setPreferredMinMax(-1, 110, -1, 35); // Scale of graph.
        plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }

    @Override
    protected void doStep() {
        double deltaTime = 1.0/32.0;
        double airpressure = 1.225;

        // NOTE: Collision detection is NOT amazing. It's a very hacky solution that requires that the deltaTime is very low

        double xBounceBuffer = 1;

        Boolean withinWallX = baseball.getX() >= greenMonsterX - xBounceBuffer && baseball.getX() <= greenMonsterX + xBounceBuffer;
        Boolean belowWall = baseball.getY() <= greenMonsterHeight;

        // Ideally, this collision detection would be rewriten such that the previous point is stored, and a linear
        // interpreation between the last and the current point is made, and then it checks if that line passes through
        // the wall, and if it does, that it also does that in the positive X direction

        if (withinWallX && belowWall) {
            baseball.aX = -baseball.aX;
            baseball.vX = -baseball.vX;
        }

        baseball.step(deltaTime, airpressure);
    }

    /**
     * Required main method, runs the simulation.
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new BaseballSimulationApp());
    }
}
