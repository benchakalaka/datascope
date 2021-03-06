package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ModelHotspotsList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelHotspotsList.POJORoboHotspot> {
     private static final long serialVersionUID = -3625616915281289658L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboHotspot {
          @JsonProperty ( "Id" ) public int                          id;
          @JsonProperty ( "Amount" ) public int                      amount;
          @JsonProperty ( "X" ) public double                        x;
          @JsonProperty ( "Y" ) public double                        y;
          @JsonProperty ( "Type" ) public String                     type;
          @JsonProperty ( "Description" ) public String              description;
          @JsonProperty ( "ValidFromDate" ) public String            validFromDate;
          @JsonProperty ( "ValidToDate" ) public String              validToDate;
          @JsonProperty ( "CompanyId" ) public int                   companyId;
          @JsonProperty ( "AssetId" ) public int                     assetId;
          @JsonProperty ( "IsClosed" ) public boolean                isCompleted;
          @JsonProperty ( "Tasks" ) public ModelTaskList             taskList;
          @JsonProperty ( "BriefingTasks" ) public ModelTaskBriefing briefingTasks;
          @JsonProperty ( "Risks" ) public ModelRiskSchedule         risks;
     }
}