package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ModelPathsCreationTimeList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList.POJORoboPathCreationTime> {

     private static final long serialVersionUID = 6980760542124438061L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboPathCreationTime {
          @JsonProperty ( "Id" ) public int      id;
          @JsonProperty ( "Time" ) public String time;
          @JsonProperty ( "Name" ) public String name;
     }

}
