package com.gleb.web.concurrency;

import lombok.NonNull;

import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static final List<String> THREAD_NAMES = List.of(
            "Napoleon Bonaparte", "Julius Caesar", "Genghis Khan",
            "Vlad the Impaler", "Joseph Stalin", "Nikola Tesla",
            "Albert Einstein", "Winston Churchill", "The Terminator",
            "Darth Vader", "Elon Musk", "John Wick", "Batman",
            "The Joker", "Sherlock Holmes", "Spartacus"
    );

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public Thread newThread(@NonNull Runnable r) {
        int index = counter.getAndIncrement() % THREAD_NAMES.size();
        String name = THREAD_NAMES.get(index);
        return new Thread(r, name);
    }
}
