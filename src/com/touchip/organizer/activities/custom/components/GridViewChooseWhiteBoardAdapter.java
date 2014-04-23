package com.touchip.organizer.activities.custom.components;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.GeneralWhiteBoardActivity_;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList;
import com.touchip.organizer.utils.GlobalConstants;

public class GridViewChooseWhiteBoardAdapter extends ArrayAdapter <Object> {
     Context                             context;
     private final PathsCreationTimeList data;

     public GridViewChooseWhiteBoardAdapter ( Context context , PathsCreationTimeList data ) {
          super(context, 0);
          this.context = context;
          this.data = data;

     }

     @Override public Object getItem(int position) {
          return super.getItem(position - 1);
     }

     @Override public int getCount() {
          return (null == data) ? 0 : data.size() + 1;
     }

     public PathsCreationTimeList getData() {
          return data;
     }

     @Override public View getView(final int position, View convertView, ViewGroup parent) {
          View row = convertView;

          LayoutInflater inflater = ((Activity) context).getLayoutInflater();
          row = inflater.inflate(R.layout.row_grid_choose_whiteboard, parent, false);

          TextView textViewTitle = (TextView) row.findViewById(R.id.item_text);
          ImageView imageViewIte = (ImageView) row.findViewById(R.id.item_image);

          if ( position == 0 ) {
               textViewTitle.setText("Create new");
               imageViewIte.setImageResource(R.drawable.add_whiteboard);
               row.setOnClickListener(new OnClickListener() {

                    @Override public void onClick(View v) {
                         GeneralWhiteBoardActivity_.IS_WHITEBOARD_NEW = true;
                         GeneralWhiteBoardActivity_.WHITEBOARD_TYPE = GlobalConstants.DrawingType.GENERAL_WHITEBOARD_DRAWING;
                         context.startActivity(new Intent(context, GeneralWhiteBoardActivity_.class));
                    }
               });
          } else {
               textViewTitle.setText(data.get(position - 1).time);
               imageViewIte.setImageResource(R.drawable.whiteboard);
          }
          return row;

     }

}