package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class ModelSuitableOperativesList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelSuitableOperativesList.POJORoboSuitableOperative> {

     private static final long serialVersionUID = -4784513458091459212L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboSuitableOperative {
          @JsonProperty ( "OperativeId" ) public int id;
          @JsonProperty ( "Fullname" ) public String fullName;
     }

}
