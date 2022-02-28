package com.maxlarsson.springs;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class Spring1DVertical extends AbstractSimulation {
    PlotFrame plotFrame;
    Circle point;
    double velocity = 0;

    @Override
    public void initialize() {
        plotFrame = new PlotFrame("x", "y", "Spring 1D Vertical");

        point = new Circle(0, 0);

        plotFrame.addDrawable(point);

        point.color = new Color(0, 255, 0);

        plotFrame.setPreferredMinMax(-10, 10, -20, 20); // Scale of graph.
        plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }

    @Override
    protected void doStep() {
        double deltaTime = 0.05;

        double k = 20;
        double x = point.getY();
        double m = 5;

        double Fs = -k * x;
        double Fg = 9.81 * m;
        double a = Fs - Fg;

        velocity += a * deltaTime;
        point.setY(x + velocity * deltaTime);
    }

    /**
     * Required main method, runs the simulation.
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new Spring1DVertical());
    }
}
