package com.touchip.organizer.communication.rest.model;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties ( ignoreUnknown = true ) public class ModelFileList {

     private static final long                               serialVersionUID = 1L;

     @JsonProperty ( "FileNames" ) public ArrayList <String> fileNames;
}
