package com.intexsoft.importdata.impl.adapters.csv;

import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BigDecimalConverter extends AbstractBeanField<BigDecimal> {
    private final static Logger LOGGER = LogManager.getLogger(BigDecimalConverter.class);

    @Override
    protected Object convert(String value) {
        BigDecimal price;
        try {
            price = new BigDecimal(value);
        } catch (NumberFormatException e) {
            LOGGER.error(e+" value {} can not be parsed\n price is null now", value);
            return null;
        }
        return price;
    }
}
