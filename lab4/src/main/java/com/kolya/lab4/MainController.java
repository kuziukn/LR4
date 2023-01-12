package com.kolya.lab4;


import com.kolya.lab4.data.InputVectorSystem;
import com.kolya.lab4.data.InputVectorSystemValidator;
import com.kolya.lab4.data.OrthogonalizedVectorSystem;
import com.kolya.lab4.math.Orthogonalizer;
import com.kolya.lab4.math.Vector;
import com.kolya.lab4.persistence.Entry;
import com.kolya.lab4.persistence.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    private final Repository<OrthogonalizedVectorSystem> repository;
    private final Orthogonalizer orthogonalizer;

    @Autowired
    private MainController(Repository<OrthogonalizedVectorSystem> repository, Orthogonalizer orthogonalizer) {
        this.repository = repository;
        this.orthogonalizer = orthogonalizer;
    }

    @PostMapping("orthogonalize")
    public ResponseEntity<?> orthogonalize(@RequestBody InputVectorSystem input) {

        InputVectorSystemValidator validator = new InputVectorSystemValidator();

        if (!validator.validate(input)) {
            return ResponseEntity.badRequest().build();
        }

        List<Vector> vectorSystem = input.numbers.stream().map(Vector::new).toList();

        List<List<Vector>> steps = new ArrayList<>();

        List<Vector> orthogonalizedVectorSystem = orthogonalizer.orthogonalize(vectorSystem, steps);

        OrthogonalizedVectorSystem result = new OrthogonalizedVectorSystem();

        result.vectors = orthogonalizedVectorSystem.stream().map(Vector::toList).toList();

        List<List<List<Double>>> resultSteps = new ArrayList<>();
        for (List<Vector> vectors : steps) {
            resultSteps.add(vectors.stream().map(Vector::toList).toList());
        }
        result.steps = resultSteps;

        Entry<OrthogonalizedVectorSystem> resultEntry = repository.create(result);

        return ResponseEntity.ok(resultEntry);

    }

    @GetMapping("/get")
    public ResponseEntity<?> getResult(@RequestParam long id, @RequestParam boolean steps) {

        return repository.get(id)
                .map(x -> {
                    if (!steps) {
                        x.element().steps = null;
                    }
                    return x;
                })
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

}
