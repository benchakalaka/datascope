package com.touchip.organizer.communication.rest.request.listener;

import java.util.List;
import java.util.Map;

import quickutils.core.QUFactory.QCollection;
import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QPreconditions;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelFileList;
import com.touchip.organizer.communication.rest.request.GetInductionFileRequest;
import com.touchip.organizer.utils.Utils;

public class GetInductionFileListRequestListener implements RequestListener <ModelFileList> {

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public GetInductionFileListRequestListener ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          Utils.showCustomToast(activity, "Error occured during invocation of web service.", R.drawable.hide_hotspot);
          QLog.debug(e.getMessage());
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelFileList result) {

          if ( !QPreconditions.isNull(result) && !QPreconditions.isNullOrEmpty(result.fileNames) ) {
               ListView lw = new ListView(this.activity);
               final StableArrayAdapter adapter = new StableArrayAdapter(this.activity, android.R.layout.simple_list_item_1, result.fileNames);
               lw.setAdapter(adapter);

               Dialog d = new Dialog(activity);
               d.requestWindowFeature(Window.FEATURE_NO_TITLE);
               d.setContentView(lw);
               d.show();

               lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override public void onItemClick(AdapterView <?> parent, final View view, int position, long id) {
                         final String item = (String) parent.getItemAtPosition(position);

                         GetInductionFileRequest request = new GetInductionFileRequest(item);
                         activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetInductionFileListener(activity, item));

                         QLog.debug("Gryzim file " + item);
                    }
               });

          } else {
               Utils.showCustomToast(activity, "There are no files found.", R.drawable.hide_hotspot);
          }

     }

     private class StableArrayAdapter extends ArrayAdapter <String> {

          Map <String, Integer> mIdMap = QCollection.newHashMap();

          public StableArrayAdapter ( Context context , int textViewResourceId , List <String> objects ) {
               super(context, textViewResourceId, objects);
               for ( int i = 0; i < objects.size(); ++i ) {
                    mIdMap.put(objects.get(i), i);
               }
          }

          @Override public long getItemId(int position) {
               String item = getItem(position);
               return mIdMap.get(item);
          }

          @Override public boolean hasStableIds() {
               return true;
          }

     }
}
