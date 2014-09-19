package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class BaseModel {
     @JsonProperty ( "ErrorMessage" ) public String   errorMessage;
     @JsonProperty ( "WarningMessage" ) public String warningMessage;
}