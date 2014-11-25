package com.touchip.organizer.communication.rest.request.listener;

import quickutils.core.QUFactory.QNotifications;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.dialogs.CDialogSignRegister;
import com.touchip.organizer.communication.rest.model.ModelUserList;
import com.touchip.organizer.communication.rest.model.ModelUserList.ModelUser;
import com.touchip.organizer.utils.Utils;

public class ResponseRegisterOperatives implements RequestListener <ModelUser> {

     private final SuperActivity activity;

     public ResponseRegisterOperatives ( SuperActivity act ) {
          this.activity = act;
     }

     @Override public void onRequestFailure(SpiceException ex) {
          ex.printStackTrace();
          QNotifications.showShortToast(activity, R.string.connection_problem);
          this.activity.dissmissProgressDialog();
     }

     @Override public void onRequestSuccess(ModelUser modelUser) {
          this.activity.dissmissProgressDialog();
          if ( (null != modelUser) && (null != modelUser.firstName) ) {
               Utils.showCustomToast(this.activity, modelUser.firstName + " " + modelUser.lastName + "  has been succesfully added to sing register", R.drawable.success);

               if ( null == CDialogSignRegister.lvSignedIn.getAdapter() ) {
                    ModelUserList mul = new ModelUserList();
                    mul.add(modelUser);
                    CDialogSignRegister.lvSignedIn.setAdapter(new CDialogSignRegister.ListViewSignedUsersAdapter(this.activity, mul));
                    return;
               }

               ((CDialogSignRegister.ListViewSignedUsersAdapter) CDialogSignRegister.lvSignedIn.getAdapter()).addSignedUser(modelUser);
          } else {

               if ( null != modelUser ) {
                    Utils.showCustomToast(this.activity, modelUser.errorMessage, R.drawable.trade);
               }
          }
     }
}