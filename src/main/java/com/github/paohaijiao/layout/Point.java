package com.github.paohaijiao.layout;

public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point other) {
        return new Point(this.x + other.x, this.y + other.y);
    }

    public Point subtract(Point other) {
        return new Point(this.x - other.x, this.y - other.y);
    }

    public Point multiply(double scalar) {
        return new Point(this.x * scalar, this.y * scalar);
    }

    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public Point normalize() {
        double len = length();
        if (len == 0) return new Point(0, 0);
        return new Point(x / len, y / len);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }
}
