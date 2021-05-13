package com.example.coronainfo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"id","Country","Continent","TwoLetterSymbol","ThreeLetterSymbol",
        "ThreeLetterSymbol", "Case_Fatality_Rate","Test_Percentage","Recovery_Proporation",
        "NewCases","NewDeaths","NewRecovered","ActiveCases","TotalTests","one_Caseevery_X_ppl",
        "one_Deathevery_X_ppl", "one_Testevery_X_ppl","Deaths_1M_pop","Serious_Critical",
        "Tests_1M_Pop","TotCases_1M_Pop"})

public class CovidResult {

    //class que vai ter as variavel do json

    @JsonProperty("rank")
    private String rank;
    @JsonProperty("Population")
    private String population;
    @JsonProperty("TotalCases")
    private String totalCases;
    @JsonProperty("TotalDeaths")
    private String totalDeaths;
    @JsonProperty("TotalRecovered")
    private String totalRecovered;
    @JsonProperty("Infection_Risk")
    private String infection_Risk;

    public String getPopulation() {
        return population;
    }

    public String getRank() {
        return rank;
    }

    public String getInfection_Risk() {
        return infection_Risk;
    }

    public String getTotalCases() {
        return totalCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }
}
