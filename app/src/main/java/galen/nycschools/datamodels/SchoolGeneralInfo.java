package galen.nycschools.datamodels;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Random;

public class SchoolGeneralInfo implements Serializable {

    public final String dbn;
    public final String name;
    public final String location;
    public final String graduationRate;

    public SchoolGeneralInfo(
            @NotNull String dbn,
            @NotNull String name,
            @NotNull String location,
            @NotNull String graduationRate
    ) {
        this.dbn = dbn;
        this.name = name;
        this.location = location;
        this.graduationRate = graduationRate;
    }

    public boolean isRecommended() {
        try {
            return Integer.parseInt(graduationRate) > 0.8;
        } catch(Exception e){
            return false;
        }
    }
}
