package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.POJOValidator;
import com.intexsoft.service.importdata.pojo.ObjectsForBindings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class POJOValidatorImpl implements POJOValidator {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private final static Logger LOGGER = LogManager.getLogger(POJOValidatorImpl.class);

    private static POJOValidator instance = null;

    private POJOValidatorImpl(){}

    //TODO: make thread safe
    public static POJOValidator getValidator(){
        if (instance == null){
            instance = new POJOValidatorImpl();
        }
        return instance;
    }

    @Override
    public  <T extends ObjectsForBindings> Collection<T> distinct(List<T> list) {
        Stream<T> stream = list.stream().filter(this::valid);
        final HashMap<String, T> uniqueValues = new HashMap<>();
        stream.forEach(value -> uniqueValues.put(value.getUniqueValue(), value));
        return uniqueValues.values();
    }

    @Override
    public  <T extends ObjectsForBindings> boolean valid(T object) {
        Set<ConstraintViolation<T>> violationSet = VALIDATOR.validate(object);
        if (violationSet.isEmpty()) {
            return true;
        } else {
            violationSet.forEach(LOGGER::error);
            LOGGER.warn("Entity {} with name or code \"{}\" wasn't imported from file, because of invalid data",
                    object.getClass().getSimpleName(), object.getUniqueValue());
        }
        return false;
    }
}
