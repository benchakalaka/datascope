package com.touchip.organizer.communication.rest.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ModelUser extends BaseModel {
     @JsonProperty ( "Id" ) public int           userId;
     @JsonProperty ( "LastName" ) public String  lastName;
     @JsonProperty ( "FirstName" ) public String firstName;
     @JsonProperty ( "CompanyId" ) public int    companyId;
}