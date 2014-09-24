package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelHotspotsAndTrades extends BaseModel {
     // list of all hotspots
     @JsonProperty ( "HotSpots" ) public HotspotsList           hotSpotWrapperList;
     // list of all hotspots with X,Y=-1 (unallocated, unasigned)
     @JsonProperty ( "UnassignedHotSpots" ) public HotspotsList unassignHotspotsWrapperList;
     // list of all trades
     @JsonProperty ( "Trades" ) public ArrayList <String>       trades;
}