package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.TaskBriefing;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class GetTaskBriefingRequest extends SpringAndroidSpiceRequest <TaskBriefing> {

     // Variable array for POST request
     private final Map <String, Integer> requestVariables;

     public GetTaskBriefingRequest ( Map <String, Integer> var ) {
          super(TaskBriefing.class);
          this.requestVariables = var;
          Utils.logw("Post....." + RestAddresses.GET_TASK_BRIEFING);
     }

     /**
      * Network request
      */
     @Override public TaskBriefing loadDataFromNetwork() throws Exception {
          return getRestTemplate().postForObject(RestAddresses.GET_TASK_BRIEFING, requestVariables, TaskBriefing.class);
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
