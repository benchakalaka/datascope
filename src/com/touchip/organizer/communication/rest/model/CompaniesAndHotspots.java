package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties (
<<<<<<< HEAD
          ignoreUnknown = true ) public class CompaniesAndHotspots {
     @JsonProperty ( "CompanyWrappersList" ) public CompaniesList         companyWrappersList;
     @JsonProperty ( "HotSpotWrapperList" ) public HotspotsList           hotSpotWrapperList;
     @JsonProperty ( "UnassignedHotSpopWrapperList" ) public HotspotsList unassignHotspotsWrapperList;

=======
               ignoreUnknown = true ) public class CompaniesAndHotspots {
     @JsonProperty ( "CompanyWrappersList" ) public CompaniesList companyWrappersList;
     @JsonProperty ( "HotSpotWrapperList" ) public HotspotsList   hotSpotWrapperList;
>>>>>>> 78b3b7a5c2e64f02d5ae56556b9490c6f58c1ad9
}
