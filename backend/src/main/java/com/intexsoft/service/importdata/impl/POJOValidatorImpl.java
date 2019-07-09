package com.intexsoft.service.importdata.impl;

import com.intexsoft.service.importdata.POJOValidator;
import com.intexsoft.service.importdata.pojo.ObjectsForBindings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;
import java.util.stream.Stream;

@Service
public class POJOValidatorImpl implements POJOValidator {

    private final static Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private final static Logger LOGGER = LogManager.getLogger(POJOValidatorImpl.class);

    @Override
    public  <T extends ObjectsForBindings> List<T> validateAndDistinct(List<T> list) {
        Stream<T> stream = list.stream().filter(this::valid);
        final HashMap<String, T> uniqueValues = new HashMap<>();
        stream.forEach(value -> uniqueValues.put(value.getUniqueValue(), value));
        return new ArrayList<>(uniqueValues.values());
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
