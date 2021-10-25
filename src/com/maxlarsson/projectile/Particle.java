package com.maxlarsson.projectile;

import org.opensourcephysics.display.Circle;

public class Particle extends Circle {
    /**
     * Represents the x and y velocity in m/s
     */
    double vX;
    double aX = 0;
    double vY;
    double aY = -9.8;
    double mass;
    double radius;

    Particle(double x, double y, double vX, double vY, double mass, double radius) {
        super(x, y);
        this.vX = vX;
        this.vY = vY;
        this.mass = mass;
        this.radius = radius;
    }

    /**
     * Calculate a step, and the effects of gravity
     * @param deltaTime The seconds that have passed
     */
    public void step(double deltaTime) {
        y += vY * deltaTime;
        x += vX * deltaTime;
        vY += aY * deltaTime;
        vX += aX * deltaTime;
    }

    /**
     * Calculate a step, and the effects of gravity and air/water pressure
     * @param deltaTime The seconds that have passed
     * @param pressure The air or water pressure the particle is travelling through
     */
    public void step(double deltaTime, double pressure) {
        double weight = mass * 9.8;

        double dragConstant = 0.02;
        double crossSectionalArea = Math.PI * Math.pow(radius, 2);
        double dragY = pressure * dragConstant * crossSectionalArea * Math.pow(vY, 2) / (2 * mass);
        double dragX = pressure * dragConstant * crossSectionalArea * Math.pow(vX, 2) / (2 * mass);

        double netForceY = dragY - weight;

        y += vY * deltaTime;
        x += vX * deltaTime;

        vY += aY * deltaTime;
        vX += aX * deltaTime;

        aY = netForceY / mass;
        aX = -dragX;
    }
}
