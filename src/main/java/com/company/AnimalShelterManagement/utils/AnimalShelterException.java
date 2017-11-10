package com.company.AnimalShelterManagement.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

class AnimalShelterException extends RuntimeException {

    private AnimalShelterException() {
    }

    AnimalShelterException(String message) {
        super(message);
    }

    static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
        if (entries.length % 2 == 1) {
            throw new IllegalArgumentException("Invalid entries!");
        }

        return IntStream.range(0, entries.length / 2)
                .map(i -> i * 2)
                .collect(HashMap::new,
                        (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
                        Map::putAll);
    }
}
