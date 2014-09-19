package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.MeetingPlanList;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanRequest extends SpringAndroidSpiceRequest <MeetingPlanList> {

     // Variable array for POST request
     private final Map <String, String> vars;

     public GetMeetingPlanRequest ( Map <String, String> requestVariables ) {
          super(MeetingPlanList.class);
          this.vars = requestVariables;
          Utils.logw("Post....." + RestAddresses.GET_MEETING_PLAN);
     }

     @Override public MeetingPlanList loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_MEETING_PLAN, vars, MeetingPlanList.class);
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
