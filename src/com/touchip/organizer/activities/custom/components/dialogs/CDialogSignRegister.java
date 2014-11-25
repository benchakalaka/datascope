package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QCollection;
import android.app.Dialog;
import android.text.InputType;
import android.text.Selection;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.fragments.FragmentCompaniesList;
import com.touchip.organizer.communication.rest.model.ModelUserList;
import com.touchip.organizer.communication.rest.model.ModelUserList.ModelUser;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseRegisterOperatives;
import com.touchip.organizer.communication.rest.request.listener.ResponseSignInUser;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.dialog_sign_register ) public class CDialogSignRegister extends RelativeLayout {

     // ===================== views
     @ViewById public TextView        tw1 , tw2 , tw3 , tw4 , tw5 , tw6 , tw7 , tw8 , tw9 , tw0 , twc , twLogin , tvTitle;
     @ViewById EditText               etPassword;
     @ViewById Button                 btnOk;
     @ViewById public static ListView lvSignedIn;
     @ViewById public RelativeLayout  llPinPad;

     // ====================== variable
     private final SuperActivity      activity;
     private final ModelUserList      users;
     private final Dialog             dialog;
     private final String             date;

     public CDialogSignRegister ( SuperActivity act , ModelUserList list , Dialog hostDialog , String date ) {
          super(act);
          this.activity = act;
          this.users = list;
          this.dialog = hostDialog;
          this.date = date;
     }

     @AfterViews void afterViews() {
          etPassword.setInputType(InputType.TYPE_NULL);
          etPassword.setTransformationMethod(new PasswordTransformationMethod());
          if ( null != users && !users.isEmpty() ) {
               lvSignedIn.setAdapter(new ListViewSignedUsersAdapter(activity, users));
               llPinPad.setVisibility(View.GONE);
               tvTitle.setText(date);
          }
     }

     @Click void btnOk() {
          if ( llPinPad.getVisibility() == View.GONE ) {
               dialog.dismiss();
               return;
          }

          if ( lvSignedIn.getAdapter() != null && ((ListViewSignedUsersAdapter) lvSignedIn.getAdapter()).getUsers() != null ) {
               HashMap <String, Object> params = QCollection.newHashMap();
               params.put(HTTP_PARAMS.OPERATIVES, ((ListViewSignedUsersAdapter) lvSignedIn.getAdapter()).getUsers());
               params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
               SuperRequest <ModelUser> request = new SuperRequest <ModelUser>(this.activity, ModelUser.class, RestAddresses.REGISTER_OPERATIVES, null, params);
               this.activity.execute(request, new ResponseRegisterOperatives(this.activity));
          }

          dialog.dismiss();
     }

     @Click void twLogin() {
          HashMap <String, String> params = QCollection.newHashMap();
          params.put(HTTP_PARAMS.PIN, etPassword.getText().toString());
          params.put(HTTP_PARAMS.DATE_OF_MEETING, GlobalConstants.SITE_PLAN_IMAGE_NAME);
          SuperRequest <ModelUser> request = new SuperRequest <ModelUser>(this.activity, ModelUser.class, RestAddresses.SIGN_IN_USER, null, params);
          this.activity.execute(request, new ResponseSignInUser(this.activity));
          etPassword.getText().clear();
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

     // --------------------------------- COMPANIES LIST ADAPTER
     /**
      * Description: adapter, which represents list of companies and colours for list view
      * 
      * @author Ihor Karpachev
      *         Copyright (c) 2013-2014 Datascope Ltd. All Rights Reserved.
      *         Date: 24 Sep 2014
      *         Time: 15:05:27
      */
     public static class ListViewSignedUsersAdapter extends ArrayAdapter <String> {

          private final SuperActivity activity;
          private ModelUserList       users;

          public ListViewSignedUsersAdapter ( SuperActivity act , ModelUserList list ) {
               super(act, R.layout.listview_company_list_item);
               activity = act;
               this.users = list;
               notifyDataSetChanged();
          }

          public ModelUserList getUsers() {
               return this.users;
          }

          @Override public int getCount() {
               return this.users.size();
          }

          @Override public String getItem(int position) {
               return this.users.get(position).firstName;
          }

          @Override public View getView(final int position, View view, ViewGroup parent) {
               final View rowView = activity.getLayoutInflater().inflate(R.layout.listview_filter_company_list_item, null, true);
               TextView txtTitle = (TextView) rowView.findViewById(R.id.twCompanyTitle);
               ImageView imageView = (ImageView) rowView.findViewById(R.id.ivCompanyColor);
               final CheckBox cbChecked = (CheckBox) rowView.findViewById(R.id.cbChecked);

               cbChecked.setChecked(true);
               cbChecked.setEnabled(false);

               txtTitle.setText(this.users.get(position).firstName + " " + this.users.get(position).lastName);
               imageView.setImageBitmap(FragmentCompaniesList.createRoundImage(this.users.get(position).companyColour, 30, 30));

               return rowView;
          }

          @Override public long getItemId(int position) {
               return 0;
          }

          public void addSignedUser(ModelUser user) {
               if ( null == users ) {
                    users = new ModelUserList();
               }

               // check if this user exists
               for ( ModelUser userSingle : users ) {
                    if ( userSingle.pin.equals(user.pin) ) { return; }
               }

               users.add(user);
               notifyDataSetChanged();
          }
     }
}
