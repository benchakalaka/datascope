package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelRiskSchedule;

public class GetActivitiesAndRisksRequest extends SpringAndroidSpiceRequest <ModelRiskSchedule> {

     // Variable array for POST request
     private final Map <String, Integer> requestVariables;

     public GetActivitiesAndRisksRequest ( Map <String, Integer> var ) {
          super(ModelRiskSchedule.class);
          this.requestVariables = var;
          QLog.debug("Post....." + RestAddresses.GET_ACTIVITIES_AND_RISKS);
     }

     /**
      * Network request
      */
     @Override public ModelRiskSchedule loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_ACTIVITIES_AND_RISKS, requestVariables, ModelRiskSchedule.class);
     }

     /**
      * This method generates a unique cache key for this request.
      * 
      * @return string representation of cache word
      */
     public String createCacheKey() {
          return getClass().getSimpleName() + ".cache";
     }
}
