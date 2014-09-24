package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelPathId extends BaseModel {
     @JsonProperty ( "PathId" ) public int                pathId;
     @JsonProperty ( "Base64DrawingPaths" ) public String drawingPaths;
}
