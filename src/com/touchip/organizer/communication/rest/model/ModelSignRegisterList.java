package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.touchip.organizer.communication.rest.model.ModelSignRegisterList.ModelSignRegister;

public class ModelSignRegisterList extends ArrayList <ModelSignRegister> {

     private static final long serialVersionUID = 6980760542124438061L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class ModelSignRegister extends BaseModel {
          @JsonProperty ( "Meeting" ) public String           time;
          @JsonProperty ( "AmountOfAttendees" ) public String amountOfAttendees;
     }
}