package i_generics.task_1;

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
public class Solution {
    public static void main(String[] args) {
        List<Number> numbers = List.of(15, 12.45, 12F);
        List<Number> copy = new ArrayList<>();
        copyIf((n) -> n.intValue() / 2 == 6, numbers, copy);
        System.out.println(copy);

        List<String> strings = List.of("Hello", "World", "Wide", "Web", "Sun");
        List<String> sCopy = new ArrayList<>();
        copyIf((s) -> s.startsWith("W"), strings, sCopy);
        System.out.println(sCopy);
    }

    public static <T> void copyIf(Predicate<T> pr, List<? extends T> source, List<? super T> destination){
        for(T t : source){
            if(pr.test(t)){
                destination.add(t);
            }
        }
    }
}

