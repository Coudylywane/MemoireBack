package com.example.construction.services;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Utilitaire {

    public MappingJacksonValue getFilter(Object o, String filterName, String propertyName) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyName);

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter(filterName, filter);

        MappingJacksonValue filtres = new MappingJacksonValue(o);

        filtres.setFilters(listDeNosFiltres);

        return filtres;
    }

    public MappingJacksonValue getFilterList(Object o, String filterName1, String propertyName1, String filterName2, String propertyName2) {
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(propertyName1);
        SimpleBeanPropertyFilter filter2 = SimpleBeanPropertyFilter.serializeAllExcept(propertyName2);

        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter(filterName1, filter).addFilter(filterName2, filter2);

        MappingJacksonValue filtres = new MappingJacksonValue(o);

        filtres.setFilters(listDeNosFiltres);

        return filtres;
    }

    public String generateAlphaNumeriqueRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
    
        String generatedString = random.ints(leftLimit, rightLimit + 1)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(targetStringLength)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    
        return generatedString;
    }

}
