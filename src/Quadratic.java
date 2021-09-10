public class Quadratic {
    private double a;
    private double b;
    private double c;

    public Quadratic(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double discriminant() {
        // b^2 - 4ac
        return Math.pow(this.b, 2) - 4 * this.a * this.c;
    }

    public double getA() {
        return this.a;
    }

    public double getB() {
        return this.b;
    }

    public double getC() {
        return this.c;
    }

    public boolean hasRealRoots() {
        return this.discriminant() >= 0;
    }

    public int numberOfRoots() {
        double discriminant = this.discriminant();
        if (discriminant < 0) {
            return 0;
        } else if (discriminant == 0) {
            return 1;
        } else {
            // If this else condition is reached, discriminant > 0
            return 2;
        }
    }

    public double[] getRootArray() {
        double root_one = (-this.b + Math.sqrt(this.discriminant())) / (2 * this.a);
        double root_two = (-this.b - Math.sqrt(this.discriminant())) / (2 * this.a);
        return new double[]{root_one, root_two};
    }

    public double getAxisOfSymmetry() {
        return -this.b / (2 * this.a);
    }

    public double getExtremeValue() {
        return this.evaluateWith(this.getAxisOfSymmetry());
    }

    public boolean isMax() {
        return !(this.a > 0);
    }

    public double evaluateWith(double x) {
        // y = ax^2 + bx + c
        return this.a * Math.pow(x, 2) + this.b * x + this.c;
    }
}
