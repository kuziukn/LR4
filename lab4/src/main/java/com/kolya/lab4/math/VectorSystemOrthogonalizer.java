package com.kolya.lab4.math;


import java.util.ArrayList;
import java.util.List;

public class VectorSystemOrthogonalizer implements Orthogonalizer {

    public List<Vector> orthogonalize(List<Vector> vectors, List<List<Vector>> steps) {

        List<Vector> result = new ArrayList<>();

        for (Vector v : vectors) {
            List<Vector> currSteps = new ArrayList<>();
            Vector u = orthogonalizeOne(v, result, currSteps);

            if (!u.isZeroLength()) {
                result.add(u);
                steps.add(currSteps);
            }
        }

        return result.stream().map(Vector::normalized).toList();
    }

    private Vector orthogonalizeOne(Vector v, List<Vector> vectors, List<Vector> steps) {

        steps.add(v);

        for (Vector u : vectors) {
            v = v.subtract(u.project(v));
            steps.add(v);
        }
        return v;
    }

}
