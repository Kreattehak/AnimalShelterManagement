package com.company.AnimalShelterManagement.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

class AnimalShelterException extends RuntimeException {

    private AnimalShelterException() {
    }

    AnimalShelterException(String message) {
        super(message);
    }

    static Map<String, String> toMap(String... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("Invalid entries!");
        }

        return IntStream.range(0, entries.length / 2)
                .map(i -> i * 2)
                .collect(LinkedHashMap::new,
                        (m, i) -> m.put(entries[i], entries[i + 1]),
                        Map::putAll);
    }
}
