package com.maxlarsson.springs;

import org.opensourcephysics.display.Circle;

public class Spring1D extends Circle {
    double springConstant;
    double springMass;
    double payloadMass;
    double displacement = 0;
    double length;
    double v = 0;

    public Spring1D(double anchorX, double anchorY, double springConstant, double payloadMass, double springMass, double length) {
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
        v += Fnety / (springMass + payloadMass) * deltaTime;
        displacement += v * deltaTime;
        y += v * deltaTime;
    }
}
