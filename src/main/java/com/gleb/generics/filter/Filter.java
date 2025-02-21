package com.gleb.generics.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/*
Задача 1: Фильтрация объектов (PECS)
Описание:
Реализуйте метод copyIf, который принимает:
Список-источник (source), содержащий элементы типа T или его подтипов (? extends T).
Список-назначение (destination), который может принимать элементы типа T или его супертипов (? super T).
Условие-фильтр в виде интерфейса Predicate<T>.
Метод должен копировать элементы из источника в назначение, если они удовлетворяют условию.

 */
public class Filter {

    public static <T> void copyIf(Predicate<T> pr, List<? extends T> source, List<? super T> destination) {
        for (T t : source) {
            if (pr.test(t)) {
                destination.add(t);
            }
        }
    }
}
