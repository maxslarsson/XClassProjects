package com.maxlarsson.springs;

import org.opensourcephysics.display.Circle;

public class Spring2D extends Circle {
    double springConstant;
    double springMass;
    double payloadMass;
    double displacement = 0;
    double length;
    double vX = 0;
    double vY = 0;

    public Spring2D(double anchorX, double anchorY, double springConstant, double payloadMass, double springMass, double length) {
        super(anchorX, anchorY-length);
        this.springConstant = springConstant;
        this.springMass = springMass;
        this.payloadMass = payloadMass;
        this.length = length;
    }

    /**
     * Calculate a step, and the effects of gravity
     * @param deltaTime The seconds that have passed
     */
    public void step(double deltaTime) {
        double Fg = 9.8 * (springMass+payloadMass);
        double Fspring = - springConstant * displacement;

        // Prevent compression
        if (Fspring < 0) Fspring = 0;

        double Fnety = Fspring - Fg;
        vY += Fnety / (springMass + payloadMass) * deltaTime;
        displacement += vY * deltaTime;
        y += vY * deltaTime;
    }
}
