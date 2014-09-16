package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

public class CompaniesList extends ArrayList <com.touchip.organizer.communication.rest.model.CompaniesList.POJORoboCompany> {
     private static final long serialVersionUID = -2340523686326068509L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboCompany {
          @JsonProperty ( "Colour" ) public String      colour;
          @JsonProperty ( "CompanyName" ) public String companyName;
          @JsonProperty ( "CompanyId" ) public int      companyId;
          @JsonProperty ( "HasTrades" ) public boolean  hasTrades;
          @JsonProperty ( "HasAssets" ) public boolean  hasAssets;
          @JsonProperty ( "IsOnSite" ) public boolean   onsite;
     }
}