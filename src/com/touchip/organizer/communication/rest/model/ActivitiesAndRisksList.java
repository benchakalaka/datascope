package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ActivitiesAndRisksList extends ArrayList <com.touchip.organizer.communication.rest.model.ActivitiesAndRisksList.POJORoboSingleActivityAndRisk> {

     private static final long serialVersionUID = 7172488928045727949L;

     @JsonIgnoreProperties (
               ignoreUnknown = true ) public static class POJORoboSingleActivityAndRisk
     {
          @JsonProperty ( "RiskName" ) public String    riskName;
          @JsonProperty ( "Description" ) public String description;
     }
}
