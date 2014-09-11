package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.communication.rest.model.ModelNote;

@EViewGroup ( R.layout.custom_panel_note_calendar_item ) public class CPanelNoteCalendarItem extends RelativeLayout implements android.view.View.OnClickListener {

     @ViewById public TextView twName;
     private final Context     ctx;
     @ViewById ImageView       ivNoteColor , ivNoteType;
     @ViewById LinearLayout    llAttachedColours;
     public ModelNote          note;

     public CPanelNoteCalendarItem ( Context context ) {
          super(context);
          this.ctx = context;
          setOnClickListener(this);
     }

     @Override public void onClick(View v) {

     }

     private String getNoteTypeStringByImageId(int noteImageId) {
          String noteType = "";
          if ( noteImageId == R.drawable.rectangle ) {
               noteType = "rectangle";
          }

          if ( noteImageId == R.drawable.triangle ) {
               noteType = "triangle";
          }

          if ( noteImageId == R.drawable.circle ) {
               noteType = "circle";
          }
          return noteType;
     }

     public static int getNoteImageIdByStringName(String type) {
          int noteTypeImageId = 0;
          if ( type.equals("rectangle") ) {
               noteTypeImageId = R.drawable.rectangle;
          }

          if ( type.equals("triangle") ) {
               noteTypeImageId = R.drawable.triangle;
          }

          if ( type.equals("circle") ) {
               noteTypeImageId = R.drawable.circle;
          }
          return noteTypeImageId;
     }

     public void setUpNote(ModelNote n) {
          note = n;
          ivNoteColor.setBackgroundColor(note.color);
          twName.setText(note.name);
          ivNoteType.setBackgroundResource(getNoteImageIdByStringName(note.type));

     }
}