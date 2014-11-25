package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelMeetingPlanList;

public class GetMeetingPlanRequest extends SpringAndroidSpiceRequest <ModelMeetingPlanList> {

     // Variable array for POST request
     private final Map <String, String> vars;

     public GetMeetingPlanRequest ( Map <String, String> requestVariables ) {
          super(ModelMeetingPlanList.class);
          this.vars = requestVariables;
          QLog.debug("Post....." + RestAddresses.GET_MEETING_PLAN);
     }

     @Override public ModelMeetingPlanList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_MEETING_PLAN, vars, ModelMeetingPlanList.class);
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
