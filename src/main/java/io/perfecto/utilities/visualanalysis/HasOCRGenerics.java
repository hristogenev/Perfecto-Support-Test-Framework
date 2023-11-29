package io.perfecto.utilities.visualanalysis;

public interface HasOCRGenerics {

  public String ocrGeneric = null;

  public default HasOCRGenerics withOcrGeneric(String ocrGeneric) {
    ocrGeneric = ocrGeneric;
    return this;
  }
}
