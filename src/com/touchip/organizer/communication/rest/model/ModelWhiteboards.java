package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelWhiteboards extends BaseModel {
     @JsonProperty ( "PathsTimes" ) public PathsCreationTimeList whiteboards;
}