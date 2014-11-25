package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class ModelCompaniesAndHotspots {
     @JsonProperty ( "CompanyWrappersList" ) public ModelCompaniesList         companyWrappersList;
     @JsonProperty ( "HotSpotWrapperList" ) public ModelHotspotsList           hotSpotWrapperList;
     @JsonProperty ( "UnassignedHotSpopWrapperList" ) public ModelHotspotsList unassignHotspotsWrapperList;
     @JsonProperty ( "CurrentDate" ) public String                             today;
}