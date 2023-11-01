package io.perfecto.utilities.application;

import io.perfecto.utilities.extendedmobiledriver.ExtendedMobileDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApplicationInstaller {
    private final ExtendedMobileDriver driver;
    private String localPath;

    public ApplicationInstaller(ExtendedMobileDriver driver) {
        this.driver = driver;
    }


}
