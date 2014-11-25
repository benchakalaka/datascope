package com.touchip.organizer.communication.rest.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class ModelDelivery {

     @JsonProperty ( "Deliveries" ) public List <POJORoboDelieveryInfo> deliveriesTable;
     @JsonProperty ( "GatesGrid" ) public ModelDelieveriesList               deliveries;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboDelieveryInfo {
          @JsonProperty ( "DeliveryId" ) public int              refId;
          @JsonProperty ( "CompanyName" ) public String          company;
          @JsonProperty ( "DeliveryTime" ) public String         deliveryTime;
          @JsonProperty ( "Description" ) public String          description;
          @JsonProperty ( "Gate" ) public String                 gate;
          @JsonProperty ( "DeliveryDurationHHmm" ) public String duration;
          @JsonProperty ( "Status" ) public String               status;
          @JsonProperty ( "ContactName" ) public String          contact;
     }
}
