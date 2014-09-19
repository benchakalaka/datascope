package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelPathList extends BaseModel {
     @JsonProperty ( "Base64DrawingPaths" ) public String drawingPaths;
}