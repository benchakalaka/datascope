package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ModelDatesToHighlightList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList.POJORoboOneDateToHighlight> {

     private static final long serialVersionUID = -6526936570082631009L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboOneDateToHighlight {
          @JsonProperty ( "Date" ) public String          date;
          @JsonProperty ( "Floors" ) public List <String> floors;
     }
}
