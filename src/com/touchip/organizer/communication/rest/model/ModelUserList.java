package com.touchip.organizer.communication.rest.model;

import java.io.File;
import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.touchip.organizer.communication.rest.model.ModelUserList.ModelUser;

public class ModelUserList extends ArrayList <ModelUser> {

     private static final long serialVersionUID = 6980760542124438061L;

     @JsonIgnoreProperties ( ignoreUnknown = true ) public static class ModelUser extends BaseModel {
          @JsonProperty ( "Id" ) public int             userId;
          @JsonProperty ( "LastName" ) public String    lastName;
          @JsonProperty ( "FirstName" ) public String   firstName;
          @JsonProperty ( "CompanyId" ) public int      companyId;
          @JsonProperty ( "Colour" ) public String      companyColour;
          @JsonProperty ( "CompanyName" ) public String companyName;
          @JsonProperty ( "File" ) public File          file;
          @JsonProperty ( "PinCode" ) public String     pin;
     }
}