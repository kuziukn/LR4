package com.kolya.lab4.math;

import java.util.List;

public interface Orthogonalizer {

    List<Vector> orthogonalize(List<Vector> vectors, List<List<Vector>> steps);

}
