package com.kolya.lab4.persistence;

public record Entry<E>(Long id, E element) { }
