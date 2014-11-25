package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QLog;
import quickutils.core.QUFactory.QNotifications;
import quickutils.core.QUFactory.QPreconditions;
import quickutils.core.QUFactory.QSystem;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard_;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.GridViewChooseWhiteBoardAdapter;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;

public class ResponseGetPathsCreationTime implements RequestListener <ModelPathsCreationTimeList> {

     private Dialog          dialog;
     private GridView        gridViewWhiteBoards;

     // Reference to activity, for updating ui components
     protected SuperActivity activity;

     // Constructor that accept activity
     public ResponseGetPathsCreationTime ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException e) {
          this.activity.dissmissProgressDialog();
          // update UI
          QNotifications.showShortToast(activity, R.string.connection_problem);
          QLog.debug(e.getMessage());
     }

     @Override public void onRequestSuccess(final ModelPathsCreationTimeList array) {
          this.activity.dissmissProgressDialog();
          if ( !QPreconditions.isNullOrEmpty(array) ) {
               dialog = Utils.getConfiguredDialog(activity);
               dialog.setContentView(R.layout.dialog_choose_white_board);
               gridViewWhiteBoards = (GridView) dialog.findViewById(R.id.gridView1);
               final GridViewChooseWhiteBoardAdapter grisViewCustomeAdapter = new GridViewChooseWhiteBoardAdapter(activity, array, dialog);
               gridViewWhiteBoards.setAdapter(grisViewCustomeAdapter);
               // Handling touch/click Event on GridView Item
               gridViewWhiteBoards.setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(AdapterView <?> adapter, View v, int position, long arg3) {
                         GlobalConstants.LAST_CLICKED_WHITE_BOARD = array.get(position - 1);
                         AGeneralWhiteBoard_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
                         dialog.dismiss();
                         QSystem.navigateToActivity(activity, AGeneralWhiteBoard_.class);
                    }
               });
               dialog.show();
          } else {
               Utils.showCustomToast(activity, "Creating new whiteboard", R.drawable.add);
               AGeneralWhiteBoard_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
               AGeneralWhiteBoard_.IS_WHITEBOARD_NEW = true;
               QSystem.navigateToActivity(activity, AGeneralWhiteBoard_.class);
          }
     }

}
