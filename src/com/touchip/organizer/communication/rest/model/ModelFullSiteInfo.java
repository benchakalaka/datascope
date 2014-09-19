package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelFullSiteInfo extends BaseModel {
     // list of all companies
     @JsonProperty ( "Companies" ) public CompaniesList         companyWrappersList;
     // list of all hotspots
     @JsonProperty ( "HotSpots" ) public HotspotsList           hotSpotWrapperList;
     // list of all hotspots with X,Y=-1 (unallocated, unasigned)
     @JsonProperty ( "UnassignedHotSpots" ) public HotspotsList unassignHotspotsWrapperList;
     // list of areas/floors
     @JsonProperty ( "SiteAreas" ) public ArrayList <String>    areas;
     // current date from server
     @JsonProperty ( "CurrentDate" ) public String              today;
     // drawing path id
     @JsonProperty ( "PathId" ) public int                      pathId;
     // site plan layout encoded as base64
     @JsonProperty ( "Base64SitePlanImage" ) public String      sitePlanImage;
     // drawing paths (serialized list of PathSerializable objects)
     @JsonProperty ( "Base64DrawingPaths" ) public String       drawingPaths;
     // current area/floor for displaying on UI
     @JsonProperty ( "CurrentArea" ) public String              currentArea;
}