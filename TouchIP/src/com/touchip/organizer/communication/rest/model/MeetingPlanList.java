package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class MeetingPlanList extends ArrayList <com.touchip.organizer.communication.rest.model.MeetingPlanList.POJORoboMeetingPlanItem> {

     private static final long serialVersionUID = 6571466907097709518L;

     @JsonIgnoreProperties (
                    ignoreUnknown = true ) public static class POJORoboMeetingPlanItem {
          @JsonProperty ( "Description" ) public String description;
          @JsonProperty ( "Type" ) public int           type;
     }

}
