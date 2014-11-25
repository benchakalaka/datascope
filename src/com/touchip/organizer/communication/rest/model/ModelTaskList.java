package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ModelTaskList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelTaskList.POJORoboTask> {

     private static final long serialVersionUID = 1L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboTask {
          @JsonProperty ( "HotspotTaskId" ) public int  taskId;
          @JsonProperty ( "CompanyId" ) public int      companyId;
          @JsonProperty ( "Description" ) public String description;
     }
}
