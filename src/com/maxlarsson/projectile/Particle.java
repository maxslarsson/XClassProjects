package com.maxlarsson.projectile;

import org.opensourcephysics.display.Circle;

public class Particle extends Circle {
    /**
     * Represents the x and y velocity in m/s
     */
    double vX;
    double aX;
    double vY;
    double aY;
    double mass;
    double radius;

    Particle(double x, double y, double vX, double vY, double mass, double radius) {
        super(x, y);
        this.vX = vX;
        this.vY = vY;
        this.mass = mass;
        this.radius = radius;
    }

    Particle(double vX, double vY, double mass, double radius) {
        super();
        this.vX = vX;
        this.vY = vY;
        this.mass = mass;
        this.radius = radius;
    }

    double effectOfGravity(double deltaTime) {
        return -9.8 * Math.pow(deltaTime, 2);
    }

    /**
     * Calculate a step, and the effects of gravity
     * @param deltaTime The seconds that have passed
     */
    public void step(double deltaTime) {
        vY += effectOfGravity(deltaTime);
        y += vY;
    }

    /**
     * Calculate a step, and the effects of gravity and air/water pressure
     * @param deltaTime The seconds that have passed
     * @param pressure The air or water pressure the particle is travelling through
     */
    public void step(double deltaTime, double pressure) {
        double weight = mass * effectOfGravity(deltaTime);

        double dragConstant = 0.02;
        double crossSectionalArea = Math.PI * Math.pow(radius, 2);
        double drag = pressure * dragConstant * crossSectionalArea * Math.pow(vY, 2) / (2 * mass);

        double totalNetForce = weight + drag;

        aY = totalNetForce / mass;

        vY += aY;
        y += vY;
    }
}
