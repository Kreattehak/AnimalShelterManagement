package com.company.AnimalShelterManagement.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * There is javax.persistence.EntityNotFoundException class but this implementation provides
 * more meaningful message which is generated from params that user has or has not entered
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(
                clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return entity.toUpperCase() + " was not found for parameters: " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, String... entries) {
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