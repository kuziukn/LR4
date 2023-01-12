package com.kolya.lab4.persistence;

import java.io.Serializable;
import java.util.Optional;

public interface Repository<E extends Serializable> {
    Entry<E> create(E element);
    Optional<Entry<E>> get(Long id);
}
