package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties (
          ignoreUnknown = true ) public class RiskSchedule {

     @JsonProperty ( "ProjectName" ) public String           projectName;
     @JsonProperty ( "CompanyName" ) public String           companyName;
     @JsonProperty ( "Mean" ) public String                  mean;
     @JsonProperty ( "StartDate" ) public String             startDate;
     @JsonProperty ( "EndDate" ) public String               endDate;
     @JsonProperty ( "Scope" ) public String                 scope;

     @JsonProperty ( "Risks" ) public ActivitiesAndRisksList activitiesAndRisksList;
}
