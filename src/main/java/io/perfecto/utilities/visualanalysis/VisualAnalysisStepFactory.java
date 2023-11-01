package io.perfecto.utilities.visualanalysis;

import org.openqa.selenium.remote.RemoteWebDriver;

public class VisualAnalysisStepFactory {

    private final RemoteWebDriver driver;

    public VisualAnalysisStepFactory(RemoteWebDriver driver){
        this.driver = driver;
    }

    public EditTextSet getEditTextSetStep() {
        return new EditTextSet(driver);
    }

    public TextButtonClick getTextButtonClickStep() {
        return new TextButtonClick(driver);
    }

    public TextCheckpoint getTextCheckpointStep() {
        return new TextCheckpoint(driver);
    }

    public EditImageGet getEditImageGetStep() { return new EditImageGet(driver); }

    public EditImageSet getEditImageSetStep() {
        return new EditImageSet(driver);
    }

    public EditTextGet getEditTextGetStep() { return new EditTextGet(driver); }
}
