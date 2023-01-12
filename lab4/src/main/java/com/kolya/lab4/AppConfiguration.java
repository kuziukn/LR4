package com.kolya.lab4;

import com.kolya.lab4.data.OrthogonalizedVectorSystem;
import com.kolya.lab4.math.Orthogonalizer;
import com.kolya.lab4.math.VectorSystemOrthogonalizer;
import com.kolya.lab4.persistence.FileRepository;
import com.kolya.lab4.persistence.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.nio.file.Path;

@Configuration
public class AppConfiguration {

    String path = "database";

    @Bean
    @Scope("singleton")
    public Repository<OrthogonalizedVectorSystem> vectorSystemRepository() throws IOException {
        return new FileRepository<>(Path.of(path));
    }

    @Bean
    @Scope("singleton")
    public Orthogonalizer orthogonalizer() {
        return new VectorSystemOrthogonalizer();
    }
}
