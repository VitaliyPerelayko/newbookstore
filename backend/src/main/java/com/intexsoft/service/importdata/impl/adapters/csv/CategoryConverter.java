package com.intexsoft.service.importdata.impl.adapters.csv;

import com.intexsoft.dao.model.Category;
import com.opencsv.bean.AbstractBeanField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CategoryConverter extends AbstractBeanField<Category> {
    private final static Logger LOGGER = LogManager.getLogger(CategoryConverter.class);

    @Override
    protected Object convert(String value) {
        Category category;
        try {
            category = Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error(e + "\n category is null now");
            return null;
        }
        return category;
    }
}
