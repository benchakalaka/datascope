package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

// ArrayList as base class since the root element is a JSON array
public class CapturedImagesUrlList extends ArrayList <com.touchip.organizer.communication.rest.model.CapturedImagesUrlList.POJORoboUrlImageAddress> {
     private static final long serialVersionUID = 8192333539004718470L;

     @JsonIgnoreProperties (
                    ignoreUnknown = true ) public static class POJORoboUrlImageAddress {
          @JsonProperty ( "ImageName" ) public String imageName;
     }
}
