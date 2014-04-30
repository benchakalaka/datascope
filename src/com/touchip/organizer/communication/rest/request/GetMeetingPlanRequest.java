package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.MeetingPlanList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanRequest extends SpringAndroidSpiceRequest <MeetingPlanList> {

     // Variable array for POST request
     private final Map <String, String> vars = new HashMap <String, String>();

     public GetMeetingPlanRequest () {
          super(MeetingPlanList.class);
          Utils.logw("Post....." + RestAddresses.GET_MEETING_PLAN);
     }

     @Override public MeetingPlanList loadDataFromNetwork() throws Exception {
          vars.put("markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.put("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
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
