package com.intexsoft.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Category {
    @JsonProperty("horror")
    HORROR,
    @JsonProperty("drama")
    DRAMA,
    @JsonProperty("detective")
    DETECTIVE,
    @JsonProperty("comedy")
    COMEDY
}
