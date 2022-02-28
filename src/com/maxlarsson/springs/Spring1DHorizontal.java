package com.maxlarsson.springs;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class Spring1DHorizontal extends AbstractSimulation {
    PlotFrame plotFrame;
    Circle point;
    double velocity = 0;

    @Override
    public void initialize() {
        plotFrame = new PlotFrame("x", "y", "Spring 1D Horizontal");

        point = new Circle(-10, 0);

        plotFrame.addDrawable(point);

        point.color = new Color(0, 255, 0);

        plotFrame.setPreferredMinMax(-15, 15, -10, 10); // Scale of graph.
        plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }

    @Override
    protected void doStep() {
        double deltaTime = 0.05;

        double k = 10;
        double x = point.getX();
        double m = 1;

        double F = -k * x;
        double a = F / m;

        velocity += a * deltaTime;
        point.setX(x + velocity * deltaTime);
    }

    /**
     * Required main method, runs the simulation.
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new Spring1DHorizontal());
    }
}
