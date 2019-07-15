package com.intexsoft.importdata.impl.adapters.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    private final static Logger LOGGER = LogManager.getLogger(LocalDateAdapter.class);


    @Override
    public LocalDate unmarshal(String value) {
        LocalDate date;
        try {
            date = LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            LOGGER.error(e+"\n this date is null now");
            return null;
        }
        return date;
    }

    @Override
    public String marshal(LocalDate v) {
        return null;
    }
}
