package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class ModelAssetsList extends ArrayList <com.touchip.organizer.communication.rest.model.ModelAssetsList.POJORoboSingleAsset> {
     private static final long serialVersionUID = -3625616915281289658L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboSingleAsset {
          @JsonProperty ( "Description" ) public String description;
          @JsonProperty ( "CompanyId" ) public int      companyId;
          @JsonProperty ( "TagId" ) public String       tagId;
          @JsonProperty ( "AssetId" ) public String     id;
     }
}