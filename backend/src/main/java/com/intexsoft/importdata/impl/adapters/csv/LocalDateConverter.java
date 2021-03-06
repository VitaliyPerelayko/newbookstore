package com.intexsoft.importdata.impl.adapters.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateConverter extends AbstractBeanField<LocalDate> {
    private final static Logger LOGGER = LogManager.getLogger(LocalDateConverter.class);

    @Override
    protected Object convert(String value) {
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
