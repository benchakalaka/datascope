package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.touchip.organizer.communication.rest.model.ModelDelieveriesList.POJORoboDeliveryGates;

public class ModelDelieveriesList extends ArrayList <POJORoboDeliveryGates> {
     private static final long serialVersionUID = -3625616915281289658L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboDeliveryGates {

          @JsonProperty ( "GateName" ) public String                             gateName;
          @JsonProperty ( "SlotDeliveries" ) public List <POJORoboDelieverySlot> slots;
     }

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class POJORoboDelieverySlot {
          @JsonProperty ( "Slot" ) public String                 slot;
          @JsonProperty ( "DeliveriesAmount" ) public int        deliveriesAmount;
          @JsonProperty ( "PendingDeliveries" ) public String[]  pending;
          @JsonProperty ( "AcceptedDeliveries" ) public String[] accepted;
     }
}
