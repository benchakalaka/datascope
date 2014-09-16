package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class CompaniesAndHotspots extends BaseModel {
     @JsonProperty ( "Companies" ) public CompaniesList         companyWrappersList;
     @JsonProperty ( "HotSpots" ) public HotspotsList           hotSpotWrapperList;
     @JsonProperty ( "UnassignedHotSpots" ) public HotspotsList unassignHotspotsWrapperList;
     @JsonProperty ( "SiteAreas" ) public ArrayList <String>    areas;
     @JsonProperty ( "CurrentDate" ) public String              today;
     @JsonProperty ( "PathId" ) public int                      pathId;
     @JsonProperty ( "Base64SitePlanImage" ) public String      sitePlanImage;
     @JsonProperty ( "Base64DrawingPaths" ) public String       drawingPaths;
}