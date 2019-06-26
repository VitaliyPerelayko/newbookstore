package com.intexsoft.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

/**
 * DTO for entity Publisher
 */
public class PublisherDTO {

    @PositiveOrZero(message = "Id of publisher must be positive or zero")
    private Long id;

    @NotBlank(message = "Name of publisher must be not blank")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
