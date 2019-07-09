package com.intexsoft.service.importdata;

import com.intexsoft.service.importdata.pojo.ObjectsForBindings;

import java.util.Collection;
import java.util.List;

public interface POJOValidator {

    <T extends ObjectsForBindings> List<T> validateAndDistinct(List<T> list);

    <T extends ObjectsForBindings> boolean valid(T object);
}
