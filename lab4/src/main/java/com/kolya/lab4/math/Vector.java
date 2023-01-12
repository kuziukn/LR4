package com.kolya.lab4.math;

import java.util.Arrays;
import java.util.List;

public class Vector {

    private final double[] array;
    public final int size;

    public Vector(int size) {
        if (size < 1) {
            throw new RuntimeException();
        }

        this.size = size;
        array = new double[size];
    }


    public Vector(List<Double> list) {

        if (list.isEmpty()) {
            throw new RuntimeException();
        }

        this.size = list.size();
        array = list.stream().mapToDouble(x -> x).toArray();
    }

    public void set(int index, double value) {
        array[index] = value;
    }

    public double get(int index) {
        return array[index];
    }

    public Vector subtract(Vector other) {

        if (other.size != size) {
            throw new RuntimeException();
        }

        Vector result = new Vector(size);

        for (int i = 0; i < size; i++) {
            result.set(i, get(i) - other.get(i));
        }

        return result;
    }

    public List<Double> toList() {
        return Arrays.stream(array).boxed().toList();
    }


    public double dot(Vector other) {
        if (other.size != size) {
            throw new RuntimeException();
        }

        double result = 0;
        for (int i = 0; i < size; i++) {

            result += get(i) * other.get(i);

        }
        return result;
    }

    public double magnitude() {
        return Math.sqrt(dot(this));
    }

    public Vector normalized() {

        double magnitude = magnitude();

        Vector result = new Vector(size);

        if (doubleApproxEquals(magnitude, 0)) {
            return result;
        }

        for (int i = 0; i < size; i++) {
            result.set(i, get(i) / magnitude);
        }
        return result;
    }

    public Vector scaled(double scalar) {
        Vector result = new Vector(size);

        for (int i = 0; i < size; i++) {
            result.set(i, get(i) * scalar);
        }

        return result;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append("{ ");

        for (int i = 0; i < size; i++) {
            builder.append(get(i)).append(" ");
        }

        builder.append("}");
        return builder.toString();
    }

    public boolean isZeroLength() {
        return doubleApproxEquals(magnitude(), 0);
    }

    public static Vector projection(Vector u, Vector v) {

        double scalar = u.dot(v) / u.dot(u);
        return u.scaled(scalar);
    }

    public Vector project(Vector other) {
        return Vector.projection(this, other);
    }

    private final static double EPSILON = 0.000001;

    private static boolean doubleApproxEquals(double a, double b) {
        return Math.abs(a - b) <= EPSILON;
    }



}
