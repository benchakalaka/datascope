package com.touchip.organizer.communication.rest.request.listener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.activities.custom.components.GridViewChooseWhiteBoardAdapter;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class GetPathsCreationTimeRequestListener implements RequestListener <PathsCreationTimeList> {

     private Dialog     dialog;
     private GridView   gridViewWhiteBoards;

     // Reference to activity, for updating ui components
     protected Activity activity;

     // Constructor that accept activity
     public GetPathsCreationTimeRequestListener ( Activity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          DrawingCompaniesActivity.dissmissProgressDialog();
          // update UI
          Utils.showToast(activity, R.string.connection_problem, true);
          Utils.logw(e.getMessage());
     }

     @Override public void onRequestSuccess(final PathsCreationTimeList array) {
          DrawingCompaniesActivity.dissmissProgressDialog();
          if ( !Utils.isNullOrEmpty(array) ) {
               dialog = new Dialog(activity);
               dialog.setContentView(R.layout.dialog_choose_white_board);
               dialog.setTitle("Choose whiteboard : " + GlobalConstants.SITE_PLAN_IMAGE_NAME);
               dialog.setCancelable(true);
               dialog.setCanceledOnTouchOutside(true);
               gridViewWhiteBoards = (GridView) dialog.findViewById(R.id.gridView1);
               final GridViewChooseWhiteBoardAdapter grisViewCustomeAdapter = new GridViewChooseWhiteBoardAdapter(activity, array);
               gridViewWhiteBoards.setAdapter(grisViewCustomeAdapter);
               // Handling touch/click Event on GridView Item
               gridViewWhiteBoards.setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(AdapterView <?> adapter, View v, int position, long arg3) {
                         GlobalConstants.LAST_CLICKED_WHITE_BOARD = array.get(position - 1);
                         GeneralWhiteBoardActivity_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
                         activity.startActivity(new Intent(activity, GeneralWhiteBoardActivity_.class));
                         dialog.hide();
                    }
               });
               dialog.show();
          } else {
               Utils.showCustomToast(activity, "Creating new whiteboard", R.drawable.add_whiteboard);
               GeneralWhiteBoardActivity_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
               GeneralWhiteBoardActivity_.IS_WHITEBOARD_NEW = true;
               activity.startActivity(new Intent(activity, GeneralWhiteBoardActivity_.class));
          }
     }

}
