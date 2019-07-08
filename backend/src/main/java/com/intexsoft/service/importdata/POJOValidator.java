package com.intexsoft.service.importdata;

import com.intexsoft.service.importdata.pojo.ObjectsForBindings;

import java.util.Collection;
import java.util.List;

public interface POJOValidator {

    <T extends ObjectsForBindings> Collection<T> distinct(List<T> list);

    <T extends ObjectsForBindings> boolean valid(T object);
}
