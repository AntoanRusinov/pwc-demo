package com.pwc.project.service;

import com.pwc.project.model.Country;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoutingService {

    public List<String> findRoute(String origin, String destination) {

        validateUserInput(origin, destination);

        List<String> path = new ArrayList<>();
        return searchRoute(origin, destination, path);
    }

    private List<String> searchRoute(String from, String to, List<String> path) {
        path.add(from);

        if (from.equals(to)) {
            return new ArrayList<>(List.of(from));
        }

        Country fromCountry = findCountryByAbbreviation(from);

        if (fromCountry.getBorders().contains(to)) {
            path.add(to);
            return path;
        }

        for (String currentNeighbour : fromCountry.getBorders()) {

            if (!path.contains(currentNeighbour)) {
                List<String> result = searchRoute(currentNeighbour, to, path);

                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    private void validateUserInput(String origin, String destination) {
        List<String> allCountriesAbbreviations = CountryDataInitializer.getAllCountriesAbbreviations();
        if (!allCountriesAbbreviations.containsAll(new ArrayList<>(List.of(origin, destination)))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid abbreviation!");
        }

        validateForBorders(origin, destination);
    }

    private Country findCountryByAbbreviation(String abbreviation) {
        return CountryDataInitializer.COUNTRIES_LIST.parallelStream()
                .filter(country -> abbreviation.equals(country.getAbbreviation()))
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No such country for abbreviation: " + abbreviation));
    }

    private void validateForBorders(String origin, String destination) {
        Country originCountry = findCountryByAbbreviation(origin);
        Country destinationCountry = findCountryByAbbreviation(destination);

        if (originCountry.getBorders().isEmpty() || destinationCountry.getBorders().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Route not found!");
        }
    }

}