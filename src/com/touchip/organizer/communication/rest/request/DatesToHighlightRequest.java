package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelDatesToHighlightList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;

public class DatesToHighlightRequest extends SpringAndroidSpiceRequest <ModelDatesToHighlightList> {

     // Variable array for POST request
     private static Map <String, String> vars = QCollection.newHashMap();

     public DatesToHighlightRequest () {
          super(ModelDatesToHighlightList.class);
          QLog.debug("Post....." + RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR);
     }

     /**
      * Network request
      */
     @Override public ModelDatesToHighlightList loadDataFromNetwork() throws Exception {
          // if map exist and has elements clear it, instead of creating a new one
          if ( null != vars && !vars.isEmpty() ) {
               vars.clear();
          }

          vars.put(HTTP_PARAMS.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
          return getRestTemplate().postForObject(RestAddresses.GET_LIST_OF_DATES_TO_HIGHLIGHT_IN_CALENDAR, vars, ModelDatesToHighlightList.class);
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
