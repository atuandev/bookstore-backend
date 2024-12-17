package com.iuh.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ReviewStatus {
    @JsonProperty("PENDING")
    PENDING,
    @JsonProperty("REVIEWED")
    REVIEWED,
    @JsonProperty("DELETED")
    DELETED
}
