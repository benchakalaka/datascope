package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.MeetingPlanNamesList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetMeetingPlanNamesRequest extends SpringAndroidSpiceRequest <MeetingPlanNamesList> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public GetMeetingPlanNamesRequest () {
          super(MeetingPlanNamesList.class);
          Utils.logw("Post....." + RestAddresses.GET_MEETINGPLAN_NAMES);
     }

     /**
      * Network request
      */
     @Override public MeetingPlanNamesList loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }
          vars.put("markerId", GlobalConstants.LAST_CLICKED_MARKER_ID);
          vars.put("date", GlobalConstants.SITE_PLAN_IMAGE_NAME);
          return getRestTemplate().postForObject(RestAddresses.GET_MEETINGPLAN_NAMES, vars, MeetingPlanNamesList.class);
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
