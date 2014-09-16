package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class MeetingPlanNamesList extends ArrayList <com.touchip.organizer.communication.rest.model.MeetingPlanNamesList.POJORoboSingleMeetingPlanName> {

     private static final long serialVersionUID = 6571466907097709518L;

     @JsonIgnoreProperties (
               ignoreUnknown = true ) public static class POJORoboSingleMeetingPlanName {
          @JsonProperty ( "PlanName" ) public String name;
          @JsonProperty ( "PlanId" ) public String   id;
     }

}
