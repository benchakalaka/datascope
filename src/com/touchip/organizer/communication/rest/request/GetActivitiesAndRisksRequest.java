package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.RiskSchedule;
import com.touchip.organizer.utils.Utils;

public class GetActivitiesAndRisksRequest extends SpringAndroidSpiceRequest <RiskSchedule> {

     // Variable array for POST request
     private final Map <String, Integer> requestVariables;

     public GetActivitiesAndRisksRequest ( Map <String, Integer> var ) {
          super(RiskSchedule.class);
          this.requestVariables = var;
          Utils.logw("Post....." + RestAddresses.GET_ACTIVITIES_AND_RISKS);
     }

     /**
      * Network request
      */
     @Override public RiskSchedule loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_ACTIVITIES_AND_RISKS, requestVariables, RiskSchedule.class);
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
