package com.intexsoft.importdata.impl.adapters.xml;

import com.intexsoft.dao.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CategoryAdapter extends XmlAdapter<String, Category> {
    private final static Logger LOGGER = LogManager.getLogger(CategoryAdapter.class);

    @Override
    public Category unmarshal(String value) {
        Category category;
        try {
            category = Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            LOGGER.error(e + "\n category is null now");
            return null;
        }
        return category;
    }

    @Override
    public String marshal(Category v) {
        return null;
    }
}
