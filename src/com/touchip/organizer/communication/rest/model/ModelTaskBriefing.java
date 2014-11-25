package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties (
          ignoreUnknown = true ) public class ModelTaskBriefing {

     @JsonProperty ( "SiteName" ) public String                            siteName;
     @JsonProperty ( "ScopeOfWork" ) public String                         scopeOfWork;
     @JsonProperty ( "Supervisor" ) public String                          supervisor;
     @JsonProperty ( "SupervisionDate" ) public String                     supervisionDate;
     @JsonProperty ( "Reviewer" ) public String                            reviewer;
     @JsonProperty ( "ReviewingDate" ) public String                       reviewingDate;

     @JsonProperty ( "Controls" ) public ModelSafetyAndEnvironmentalControlList safetyAndEnvironmentalControls;
}
