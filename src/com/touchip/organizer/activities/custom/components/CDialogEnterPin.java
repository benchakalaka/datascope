package com.touchip.organizer.activities.custom.components;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.Selection;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SpiceFragmentActivity;
import com.touchip.organizer.communication.rest.model.ModelUser;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseLogin;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.dialog_user_password ) public class CDialogEnterPin extends RelativeLayout {

     // ===================== views
     @ViewById public TextView           tw1 , tw2 , tw3 , tw4 , tw5 , tw6 , tw7 , tw8 , tw9 , tw0 , twc , twLogin;
     @ViewById EditText                  etPassword;

     // ====================== variable
     private final SpiceFragmentActivity activity;

     public CDialogEnterPin ( Context context , SpiceFragmentActivity act ) {
          super(context);
          this.activity = act;
     }

     @AfterViews void afterViews() {
          InputMethodManager imm = (InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(etPassword.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

     }

     @Click void twLogin() {
          HashMap <String, String> params = new HashMap <String, String>();
          params.put(HTTP_PARAMS.PIN, etPassword.getText().toString());

          SuperRequest <ModelUser> request = new SuperRequest <ModelUser>(ModelUser.class, RestAddresses.LOGIN, null, params);
          this.activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseLogin(this.activity));
     }

     @Click void tw1() {
          tw1.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "1");
          setSelection(etPassword);
     }

     @Click void tw2() {
          tw2.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "2");
          setSelection(etPassword);
     }

     @Click void tw3() {
          tw3.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "3");
          setSelection(etPassword);
     }

     @Click void tw4() {
          tw4.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "4");
          setSelection(etPassword);
     }

     @Click void tw5() {
          tw5.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "5");
          setSelection(etPassword);
     }

     @Click void tw6() {
          tw6.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "6");
          setSelection(etPassword);
     }

     @Click void tw7() {
          tw7.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "7");
          setSelection(etPassword);
     }

     @Click void tw8() {
          tw8.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "8");
          setSelection(etPassword);
     }

     @Click void tw9() {
          tw9.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "9");
          setSelection(etPassword);
     }

     @Click void tw0() {
          tw0.startAnimation(AnimationManager.load(R.anim.fade));
          etPassword.setText(etPassword.getText().toString() + "0");
          setSelection(etPassword);
     }

     @Click void twc() {
          if ( !TextUtils.isEmpty(etPassword.getText().toString()) ) {
               etPassword.setText(etPassword.getText().toString().substring(0, etPassword.getText().toString().length() - 1));
               setSelection(etPassword);
          }
     }

     private void setSelection(EditText et) {
          Selection.setSelection(et.getText(), et.getText().toString().length());
     }
}
