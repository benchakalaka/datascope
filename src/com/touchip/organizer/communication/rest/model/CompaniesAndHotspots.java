package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class CompaniesAndHotspots {
     @JsonProperty ( "CompanyWrappersList" ) public CompaniesList         companyWrappersList;
     @JsonProperty ( "HotSpotWrapperList" ) public HotspotsList           hotSpotWrapperList;
     @JsonProperty ( "UnassignedHotSpopWrapperList" ) public HotspotsList unassignHotspotsWrapperList;
     @JsonProperty ( "CurrentDate" ) public String                        today;
}