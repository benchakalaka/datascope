package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QPreconditions;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.communication.rest.model.ModelSuitableOperativesList;
import com.touchip.organizer.utils.Utils;

public class ResponseGetSuitableOperativesList implements RequestListener <ModelSuitableOperativesList> {

     private final ADrawingCompanies activity;

     public ResponseGetSuitableOperativesList ( Activity act ) {
          this.activity = (ADrawingCompanies) act;

     }

     @Override public void onRequestFailure(SpiceException ex) {
          ex.printStackTrace();
          Utils.showCustomToast(activity, R.string.connection_problem, R.drawable.hide_hotspot);
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelSuitableOperativesList operatives) {
          if ( !QPreconditions.isNullOrEmpty(operatives) ) {
               String[] stringArray = new String[operatives.size()];
               for ( int i = 0; i < operatives.size(); i++ ) {
                    stringArray[i] = operatives.get(i).fullName;
               }

               Dialog dialog = new Dialog(activity);
               AlertDialog.Builder builder = new AlertDialog.Builder(activity).setTitle(R.string.suiatble_operatives);

               ListView modeList = new ListView(activity);
               ArrayAdapter <String> modeAdapter = new ArrayAdapter <String>(activity, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
               modeList.setAdapter(modeAdapter);
               builder.setView(modeList);
               dialog = builder.create();
               dialog.setCanceledOnTouchOutside(true);

               modeList.setOnItemClickListener(new OnItemClickListener() {

                    @Override public void onItemClick(AdapterView <?> adapter, View view, int pos, long arg3) {
                         String mapType = adapter.getItemAtPosition(pos).toString();
                         // dialog.dismiss();
                    }
               });

               dialog.show();
          } else {
               Utils.showCustomToast(activity, R.string.there_is_no_suitable_operatives, R.drawable.trade);
          }
          this.activity.dissmissProgressDialog();
     }
}
