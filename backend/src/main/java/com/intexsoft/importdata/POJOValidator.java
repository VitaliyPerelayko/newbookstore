package com.intexsoft.importdata;

import com.intexsoft.importdata.pojo.ObjectsForBindings;

import java.util.List;

public interface POJOValidator {

    <T extends ObjectsForBindings> List<T> validateAndDistinct(List<T> list);

    <T extends ObjectsForBindings> boolean valid(T object);
}
