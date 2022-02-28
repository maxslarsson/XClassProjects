package com.maxlarsson.springs;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class BungeeCord extends AbstractSimulation {
    double springConstant = 21;
    Spring1D spring;
    Circle anchor;
    PlotFrame plotFrame;

    @Override
    public void initialize() {
        plotFrame = new PlotFrame("x", "y", "Bungee Cord");

        spring = new Spring1D(0, 100, springConstant, 50, 10, 40);
        anchor = new Circle(0, 100);

        plotFrame.addDrawable(spring);
        plotFrame.addDrawable(anchor);

        spring.color = new Color(0, 255, 0);
        anchor.color = new Color(0, 255, 0);

        plotFrame.setPreferredMinMax(-10, 10, -10, 110); // Scale of graph.
        plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Make it so x'ing out of the graph stops the program.
        plotFrame.setVisible(true); // Required to show plot frame.
    }


    @Override
    protected void doStep() {
        double deltaTime = 0.2;
        spring.step(deltaTime);
    }

    /**
     * Required main method, runs the simulation.
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new BungeeCord());
    }
}
