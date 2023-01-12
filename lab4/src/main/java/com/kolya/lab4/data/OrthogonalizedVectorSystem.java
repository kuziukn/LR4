package com.kolya.lab4.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

public class OrthogonalizedVectorSystem implements Serializable {
    public List<List<Double>> vectors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<List<List<Double>>> steps;
}
