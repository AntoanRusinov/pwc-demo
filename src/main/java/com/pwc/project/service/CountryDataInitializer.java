package com.pwc.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pwc.project.model.Country;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountryDataInitializer {

    public static List<Country> COUNTRIES_LIST = new ArrayList<>();

    @Value("${classpath:/countries.json}")
    private File initFile;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void initCountries() throws IOException {
        String jsonToStringFile = FileUtils.readFileToString(initFile, StandardCharsets.UTF_8);
        COUNTRIES_LIST = Arrays.asList(objectMapper.readValue(jsonToStringFile, Country[].class));
    }

    public static List<String> getAllCountriesAbbreviations() {
        return COUNTRIES_LIST.parallelStream()
                .map(Country::getAbbreviation)
                .collect(Collectors.toList());
    }

}