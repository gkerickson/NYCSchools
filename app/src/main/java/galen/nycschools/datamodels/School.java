package galen.nycschools.datamodels;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class School {

    public final String name;
    public final String location;
    public final String grades;

    public School(@NotNull String name, @NotNull String location, @NotNull String grades) {
        this.name = name;
        this.location = location;
        this.grades = grades;
    }

    private static final Random generator = new Random();

    public boolean isRecommended() {
        return generator.nextInt(101) > 90;
    }
}
