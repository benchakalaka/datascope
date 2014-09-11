package com.touchip.organizer.communication.rest.request;

import java.util.HashMap;
import java.util.Map;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.DatesToHighlightList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

public class GetDatesToHighlightRequest extends SpringAndroidSpiceRequest <DatesToHighlightList> {

     // Variable array for POST request
     private static Map <String, String> vars = new HashMap <String, String>();

     public GetDatesToHighlightRequest () {
          super(DatesToHighlightList.class);
          Utils.logw("Post....." + RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR);
     }

     /**
      * Network request
      */
     @Override public DatesToHighlightList loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          return getRestTemplate().postForObject(RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR, vars, DatesToHighlightList.class);
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
