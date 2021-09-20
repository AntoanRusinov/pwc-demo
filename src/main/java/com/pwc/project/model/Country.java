package com.pwc.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Country {

    @JsonProperty("cca3")
    private String abbreviation;

    private String region;

    private List<String> borders;

}