package com.touchip.organizer.communication.rest.request;

import java.util.Map;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.touchip.organizer.communication.rest.model.ModelFileList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;

public class GetInductionFileListRequest extends SpringAndroidSpiceRequest <ModelFileList> {

     // Variable array for POST request
     private final Map <String, String> vars = QCollection.newHashMap();

     public GetInductionFileListRequest () {
          super(ModelFileList.class);
          QLog.debug("Post....." + RestAddresses.GET_INDUCTION_FILE_LIST);
     }

     @Override public ModelFileList loadDataFromNetwork() throws Exception {
          vars.put(HTTP_PARAMS.SITE_ID, String.valueOf(GlobalConstants.LAST_CLICKED_MARKER_ID));
          return getRestTemplate().postForObject(RestAddresses.GET_INDUCTION_FILE_LIST, vars, ModelFileList.class);
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
