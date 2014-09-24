package com.touchip.organizer.communication.rest.request.listener;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.activities.custom.components.GridViewChooseWhiteBoardAdapter;
import com.touchip.organizer.communication.rest.model.ModelPathId;
import com.touchip.organizer.communication.rest.model.ModelWhiteboards;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;

public class ResponsePathsCreationTime implements RequestListener <ModelWhiteboards> {

     private Dialog                  dialog;
     private GridView                gridViewWhiteBoards;

     // Reference to activity, for updating ui components
     protected SpiceFragmentActivity activity;

     // Constructor that accept activity
     public ResponsePathsCreationTime ( SpiceFragmentActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update UI
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
     }

     @Override public void onRequestSuccess(final ModelWhiteboards wboards) {
          this.activity.dissmissProgressDialog();
          if ( null != wboards ) {
               dialog = new Dialog(activity);
               dialog.setContentView(R.layout.dialog_choose_white_board);
               dialog.setTitle("Choose whiteboard : " + GlobalConstants.SITE_PLAN_IMAGE_NAME);
               dialog.setCancelable(true);
               dialog.setCanceledOnTouchOutside(true);
               gridViewWhiteBoards = (GridView) dialog.findViewById(R.id.gridView1);
               final GridViewChooseWhiteBoardAdapter grisViewCustomeAdapter = new GridViewChooseWhiteBoardAdapter(activity, wboards.whiteboards);
               gridViewWhiteBoards.setAdapter(grisViewCustomeAdapter);
               // Handling touch/click Event on GridView Item
               gridViewWhiteBoards.setOnItemClickListener(new OnItemClickListener() {

                    @Override public void onItemClick(AdapterView <?> adapter, View v, int position, long arg3) {
                         GeneralWhiteBoardActivity.WHITEBOARD_TYPE = GlobalConstants.GWD;
                         if ( wboards.whiteboards.isEmpty() ) {
                              GlobalConstants.LAST_CLICKED_WHITE_BOARD = null;
                              activity.startActivity(new Intent(activity, GeneralWhiteBoardActivity_.class));
                         } else {
                              Map <String, String> params = new HashMap <String, String>();
                              params.put(HTTP_PARAMS.PATH_ID, String.valueOf(wboards.whiteboards.get(position - 1).id));
                              GlobalConstants.LAST_CLICKED_WHITE_BOARD = wboards.whiteboards.get(position - 1);
                              SuperRequest <ModelPathId> request = new SuperRequest <ModelPathId>(ModelPathId.class, RestAddresses.GET_PATH, null, params);
                              activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseDownloadPathById(activity));
                         }

                         dialog.hide();
                    }
               });
               dialog.show();
          }
     }
}