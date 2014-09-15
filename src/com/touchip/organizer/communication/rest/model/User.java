package com.touchip.organizer.communication.rest.model;

import java.io.File;

import org.codehaus.jackson.annotate.JsonProperty;

public class User extends BaseModel {
     @JsonProperty ( "Id" ) public int           userId;
     @JsonProperty ( "LastName" ) public String  lastName;
     @JsonProperty ( "FirstName" ) public String firstName;
     @JsonProperty ( "CompanyId" ) public int    companyId;
     @JsonProperty ( "File" ) public File        file;
}