package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class SafetyAndEnvironmentalControlList extends
          ArrayList <com.touchip.organizer.communication.rest.model.SafetyAndEnvironmentalControlList.POJORoboSafetyAndEnvironmentalControl> {

     private static final long serialVersionUID = -4609952629590245152L;

     @JsonIgnoreProperties (
               ignoreUnknown = true ) public static class POJORoboSafetyAndEnvironmentalControl {
          @JsonProperty ( "ControlName" ) public String controlName;
     }
}
