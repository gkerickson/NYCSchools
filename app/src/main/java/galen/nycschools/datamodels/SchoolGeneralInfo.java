package galen.nycschools.datamodels;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Random;

public class SchoolGeneralInfo implements Serializable {

    public final String dbn;
    public final String name;
    public final String location;
    public final int graduationRate;

    public SchoolGeneralInfo(
            @NotNull String dbn,
            @NotNull String name,
            String location,
            int graduationRate
    ) {
        this.dbn = dbn;
        this.name = name;
        this.location = location;
        this.graduationRate = graduationRate;
    }

    public static class ComparatorImpl implements Comparator<SchoolGeneralInfo> {
        @Override
        public int compare(SchoolGeneralInfo school1, SchoolGeneralInfo school2) {
            return school1.name.compareTo(school2.name);
        }
    }
}
