package galen.nycschools;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class School {
    public String name;
    public School(@NotNull String name) {
        this.name = name;
    }

    private static final Random generator = new Random();

    public boolean isRecommended() {
        return generator.nextInt(101) > 90;
    }
}
