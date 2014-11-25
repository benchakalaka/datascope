package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import quickutils.core.QUFactory.QCollection;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.communication.rest.model.ModelSignRegisterList;
import com.touchip.organizer.communication.rest.model.ModelUserList;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetSignedInUsers;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.HTTP_PARAMS;
import com.touchip.organizer.utils.Utils;

@EViewGroup ( R.layout.dialog_sign_registers_list ) public class CDialogSignRegisterList extends RelativeLayout {

     // ===================== views
     @ViewById GridView                  gvSignRegisters;

     // ====================== variable
     private final SuperActivity         activity;
     private final ModelSignRegisterList list;
     private final Dialog                dialog;

     public CDialogSignRegisterList ( SuperActivity act , ModelSignRegisterList list , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.list = list;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          gvSignRegisters.setAdapter(new GridViewSignRegisterAdapter(activity, list, this.dialog));
     }

     @Click void btnOk() {
          dialog.dismiss();
     }

     private class GridViewSignRegisterAdapter extends ArrayAdapter <Object> {
          Context                             context;
          private final ModelSignRegisterList data;
          private final Dialog                dialog;

          public GridViewSignRegisterAdapter ( Context context , ModelSignRegisterList data , Dialog dlg ) {
               super(context, 0);
               this.context = context;
               this.data = data;
               this.dialog = dlg;
          }

          @Override public Object getItem(int position) {
               return super.getItem(position - 1);
          }

          @Override public int getCount() {
               return (null == data) ? 0 : data.size() + 1;
          }

          public ModelSignRegisterList getData() {
               return data;
          }

          @Override public View getView(final int position, View convertView, ViewGroup parent) {
               View row = convertView;

               LayoutInflater inflater = ((Activity) context).getLayoutInflater();
               row = inflater.inflate(R.layout.row_grid_choose_whiteboard, parent, false);

               TextView textViewTitle = (TextView) row.findViewById(R.id.item_text);
               ImageView imageViewIte = (ImageView) row.findViewById(R.id.item_image);

               textViewTitle.setMaxLines(2);

               // add button is rendering now, position is 0
               if ( position == 0 ) {
                    textViewTitle.setText("Create new");
                    imageViewIte.setImageResource(R.drawable.sign_register_add);
                    row.setOnClickListener(new OnClickListener() {

                         @Override public void onClick(View v) {
                              Dialog d = Utils.getConfiguredDialog(activity);
                              d.setContentView(CDialogSignRegister_.build(activity, null, d, ""));
                              d.show();
                              dialog.dismiss();
                         }
                    });
               } else {
                    textViewTitle.setText(getData().get(position - 1).time);
                    imageViewIte.setImageResource(R.drawable.sign_register);
                    row.setOnClickListener(new OnClickListener() {

                         @Override public void onClick(View v) {
                              HashMap <String, String> params = QCollection.newHashMap();
                              params.put(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                              params.put(HTTP_PARAMS.TIME, getData().get(position - 1).time);
                              SuperRequest <ModelUserList> request = new SuperRequest <ModelUserList>(activity, ModelUserList.class, RestAddresses.GET_SIGNED_IN_USERS_LIST, null, params);
                              activity.execute(request, new ResponseGetSignedInUsers(activity, getData().get(position - 1).time));
                              dialog.dismiss();
                         }
                    });
               }
               return row;

          }

     }

}
