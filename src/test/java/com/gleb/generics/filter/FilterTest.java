package com.gleb.generics.filter;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {

    @Test
    public void copyListIfEven(){
        // given
        List<Number> numbers = List.of(15, 12, 17, 19, 21, 23);
        List<Number> result = new ArrayList<>();

        // when
        Filter.copyIf((number ) -> number.intValue() % 2 == 0, numbers, result);

        // then
        assertEquals(1, result.size());
        Number n = result.getFirst();
        assertEquals(12, n.intValue());
    }

    @Test
    public void copyListIfStringBeginsWithA(){
        List<String> strings = List.of("Hello", "World", "Wide", "Web", "Sun");
        List<String> result = new ArrayList<>();
        Filter.copyIf((word ) -> word.startsWith("W"), strings, result);

        assertEquals(3, result.size());
        assertEquals("World", result.get(0));
        assertEquals("Wide", result.get(1));
        assertEquals("Web", result.get(2));
    }
}
