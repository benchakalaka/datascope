package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

// ArrayList as base class since the root element is a JSON array
public class GoogleMapMarkersList extends ArrayList <com.touchip.organizer.communication.rest.model.GoogleMapMarkersList.POJORoboMarkers> {
     private static final long serialVersionUID = 8192333539004718470L;

     @JsonIgnoreProperties (
                    ignoreUnknown = true ) public static class POJORoboMarkers {
          @JsonProperty ( "Latitude" ) public double  latitude;
          @JsonProperty ( "Longitude" ) public double longitude;
          @JsonProperty ( "Id" ) public int           id;
          @JsonProperty ( "Title" ) public String     title;
     }
}
