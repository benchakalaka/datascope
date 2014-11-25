package com.touchip.organizer.activities.custom.components;

import quickutils.core.QUFactory.QSystem;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.AGeneralWhiteBoard_;
import com.touchip.organizer.communication.rest.model.ModelPathsCreationTimeList;
import com.touchip.organizer.utils.GlobalConstants;

public class GridViewChooseWhiteBoardAdapter extends ArrayAdapter <Object> {
     Context                                  context;
     private final ModelPathsCreationTimeList data;
     private final Dialog                     dialog;

     public GridViewChooseWhiteBoardAdapter ( Context context , ModelPathsCreationTimeList data , Dialog dlg ) {
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

     public ModelPathsCreationTimeList getData() {
          return data;
     }

     @Override public View getView(final int position, View convertView, ViewGroup parent) {
          View row = convertView;

          LayoutInflater inflater = ((Activity) context).getLayoutInflater();
          row = inflater.inflate(R.layout.row_grid_choose_whiteboard, parent, false);

          TextView textViewTitle = (TextView) row.findViewById(R.id.item_text);
          ImageView imageViewIte = (ImageView) row.findViewById(R.id.item_image);

          textViewTitle.setMaxLines(2);

          if ( position == 0 ) {
               textViewTitle.setText(R.string.create_whiteboard);
               imageViewIte.setImageResource(R.drawable.add);
               row.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {
                         AGeneralWhiteBoard_.IS_WHITEBOARD_NEW = true;
                         AGeneralWhiteBoard_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
                         dialog.dismiss();
                         QSystem.navigateToActivity(context, AGeneralWhiteBoard_.class);
                    }
               });
          } else {
               if ( data.get(position - 1).name == null || data.get(position - 1).name.equals("") ) {
                    textViewTitle.setText(data.get(position - 1).time);
               } else {
                    textViewTitle.setText(data.get(position - 1).name);
               }
               imageViewIte.setImageResource(R.drawable.whiteboard);
          }
          return row;

     }

}