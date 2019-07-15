package com.intexsoft.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

/**
 * DTO for entity Publisher
 */
public class PublisherDTO {

    private Long id;

    @NotBlank(message = "Name of publisher must be not blank")
    @Size(max = 50, message = "Number of characters in publisher's name must be less than 50")
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
