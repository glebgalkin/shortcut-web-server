package com.gleb.generics.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTest {

    @Test
    public void getStringValueByExistingKey(){
        Cache<String, Integer> cache = new Cache<>(3);
        cache.put("Hello", 1);
        cache.put("World", 2);
        cache.put("Web", 3);

        assertEquals(1, cache.get("Hello"));
        assertEquals(2, cache.get("World"));
        assertEquals(3, cache.get("Web"));
        assertNull(cache.get("Invalid Key"));
    }

    @Test
    public void getDoubleValueByExistingKey(){
        Cache<Double, Character> cache = new Cache<>(4);
        cache.put(12.344, 'A');
        cache.put(1.57777, 'B');
        cache.put(99_111.22, 'C');
        cache.put(0.000, 'D');

        assertEquals('A', cache.get(12.344));
        assertEquals('B', cache.get(1.57777));
        assertEquals('C', cache.get(99_111.22));
        assertEquals('D', cache.get(0.000));
        assertNull(cache.get(-1.2));
    }

    @Test
    public void validateItemRemovalOnceMapIsFull(){
        Cache<String, Number> cache = new Cache<>(2);
        cache.put("Hello", 1);
        cache.put("Shortcut", 2);
        cache.put("World", 3);
        assertNull(cache.get("Hello"));
    }

    @Test
    public void validateContainsKey(){
        Cache<String, Number> cache = new Cache<>(2);
        cache.put("Hello", 4.5);
        assertTrue(cache.containsKey("Hello"));
        assertFalse(cache.containsKey("SomeRandomKey"));
    }
}
