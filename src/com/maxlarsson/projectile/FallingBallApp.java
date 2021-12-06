package com.maxlarsson.projectile;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import javax.swing.*;
import java.awt.*;

public class FallingBallApp extends AbstractSimulation {
    // Particle without air pressure is at index 0
    // Particle with air pressure is at index 1
    private final Particle[] particles = new Particle[]{new Particle(-5, 100, 0, 0, 3, 2), new Particle(5, 100, 0, 0, 3, 2)};
    PlotFrame simulationPlotFrame = new PlotFrame("x", "y", "Falling Ball Simulation");
    PlotFrame positionOverTimePlotFrame = new PlotFrame("seconds", "meters from ground", "Position over Time");
    Trail[] positionOverTimeTrails = new Trail[particles.length];
    PlotFrame velocityOverTimePlotFrame = new PlotFrame("seconds", "vel (m/s)", "Velocity over Time");
    Trail[] velocityOverTimeTrails = new Trail[particles.length];
    PlotFrame accelerationOverTimePlotFrame = new PlotFrame("seconds", "acc (m/s)²", "Acceleration over Time");
    Trail[] accelerationOverTimeTrails = new Trail[particles.length];
    long steps = 0;

    @Override
    public void initialize() {
        for (Particle particle : particles) {
            simulationPlotFrame.addDrawable(particle);
        }

        for (int i = 0; i < particles.length; i++) {
            positionOverTimeTrails[i] = new Trail();
            velocityOverTimeTrails[i] = new Trail();
            accelerationOverTimeTrails[i] = new Trail();
        }

        for (Trail trail : positionOverTimeTrails) {
            positionOverTimePlotFrame.addDrawable(trail);
        }

        for (Trail trail : velocityOverTimeTrails) {
            velocityOverTimePlotFrame.addDrawable(trail);
        }

        for (Trail trail : accelerationOverTimeTrails) {
            accelerationOverTimePlotFrame.addDrawable(trail);
        }

        Color[] colors = new Color[]{new Color(255, 0, 0), new Color(0, 255, 0)};
        for (int i = 0; i < colors.length; i++) {
            particles[i].color = colors[i];
            positionOverTimeTrails[i].color = colors[i];
            velocityOverTimeTrails[i].color = colors[i];
            accelerationOverTimeTrails[i].color = colors[i];
        }

        // Configure plot frame
        simulationPlotFrame.setPreferredMinMax(-7, 7, 0, 100); // Scale of graph.
        positionOverTimePlotFrame.setPreferredMinMax(-1, 15, 0, 100);
        velocityOverTimePlotFrame.setPreferredMinMax(-1, 8, -50, 50);
        accelerationOverTimePlotFrame.setPreferredMinMax(-1, 8, -50, 50);

        for (PlotFrame plotFrame : new PlotFrame[]{simulationPlotFrame, positionOverTimePlotFrame, velocityOverTimePlotFrame, accelerationOverTimePlotFrame}) {
            plotFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Make it so x'ing out of the graph stops the program.
            plotFrame.setVisible(true); // Required to show plot frame.
        }
    }

    @Override
    protected void doStep() {
        steps++;

        double deltaTime = 1.0/4.0;
        double airpressure = 1.225;

        particles[0].step(deltaTime);
        particles[1].step(deltaTime, airpressure);

        double x = steps * deltaTime;

        for (int i = 0; i < particles.length; i++) {
            positionOverTimeTrails[i].addPoint(x, particles[i].getY());
            velocityOverTimeTrails[i].addPoint(x, particles[i].vY);
            accelerationOverTimeTrails[i].addPoint(x, particles[i].aY);
        }
    }

    /**
     * Required main method, runs the simulation.
     */
    public static void main(String[] args) {
        SimulationControl.createApp(new FallingBallApp());
    }
}
