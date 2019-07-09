package com.intexsoft.service.importdata.impl.adapters.json;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateConverter extends StdConverter<String, LocalDate> {

    private final static Logger LOGGER = LogManager.getLogger(LocalDateConverter.class);

    @Override
    public LocalDate convert(String value) {
        LocalDate date;
        try {
            date = LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            LOGGER.error(e+"\n this date is null now");
            return null;
        }
        return date;
    }
}
