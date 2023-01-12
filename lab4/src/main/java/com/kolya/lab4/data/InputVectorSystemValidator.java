package com.kolya.lab4.data;

import java.util.List;

public class InputVectorSystemValidator {

    public boolean validate(InputVectorSystem system) {

        if (system == null) {
            return false;
        }

        if (system.numbers == null) {
            return false;
        }

        if (system.numbers.size() == 0) {
            return false;
        }

        List<Double> first = system.numbers.get(0);

        if (first == null || first.isEmpty()) {
            return false;
        }

        for (int i = 1; i < system.numbers.size(); i++) {

            List<Double> vector = system.numbers.get(i);
            if (vector == null || vector.size() != first.size()) {
                return false;
            }
        }

        return true;
    }

}
