package com.intexsoft.dao.importdata.impl.adapters;

import com.intexsoft.dao.model.Category;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CategotyAdapter extends XmlAdapter<String, Category> {
    @Override
    public Category unmarshal(String v) throws Exception {
        return Category.valueOf(v.toUpperCase());
    }

    @Override
    public String marshal(Category v) throws Exception {
        return null;
    }
}