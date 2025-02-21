package com.gleb.generics.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 Задача 2: Параметризованный кэш с ограниченным размером
Описание:
Реализуйте класс Cache<K, V>, представляющий обобщенный кэш, где:
K – тип ключа.
V – тип значения.

Кэш должен иметь ограничение на количество хранимых элементов.
Если размер кэша превышает лимит, самый старый элемент (тот, который был добавлен первым) должен быть удалён.

Требования:
1. Поля:
maxSize – максимальное количество элементов в кэше (задается при создании объекта).
Внутренняя структура данных для хранения пар ключ-значение (например, LinkedHashMap).


2. Методы:
put(K key, V value) – добавить элемент в кэш. Если ключ уже существует, обновить значение.
get(K key) – получить значение по ключу. Если ключ отсутствует, вернуть null.
containsKey(K key) – проверить наличие ключа.
 */
public class Cache<K, V> {
    private final int maxSize;
    private final LinkedHashMap<K, V> map;

    public Cache(int maxSize) {
        this.maxSize = maxSize;
        this.map = new LinkedHashMap<>();
    }

    public void put(K k, V v) {
        if (map.size() == maxSize) {
            K key = map.firstEntry().getKey();
            map.remove(key);
        }
        map.put(k, v);
    }

    public V get(K k) {
        if (!map.containsKey(k)) return null;
        return map.get(k);
    }

    public boolean containsKey(K k) {
        return map.containsKey(k);
    }

    public void print() {
        for (Map.Entry<K, V> e : map.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue());
        }
    }
}

