package com.example.coronainfo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CountryTranslation {

    @JsonProperty("Country")
    private String english;
    @JsonProperty("ThreeLetterSymbol")
    private String isoCode;
    @JsonProperty("Translation")
    private String portuguese;

    public String getEnglish() {
        return english;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getPortuguese() {
        return portuguese;
    }

}
