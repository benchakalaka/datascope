package com.touchip.organizer.activities;

import java.util.Date;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.CDialogCreateNote_;
import com.touchip.organizer.activities.custom.components.CNoteCalendarItem;
import com.touchip.organizer.activities.custom.components.CNoteCalendarItem_;
import com.touchip.organizer.utils.Utils;

@EActivity ( R.layout.activity_notes ) public class ANotes extends SpiceFragmentActivity {
     @ViewById GridLayout                 gridView;
     @ViewById ImageView                  ivAddNote;
     @ViewById public static LinearLayout llNotes;
     private Dialog                       dialogCreateNote;
     public static Date                   currentDate;

     OnTouchListener                      touch                 = new OnTouchListener() {

                                                                     @Override public boolean onTouch(View v, MotionEvent event) {

                                                                          switch (event.getAction()) {
                                                                               case MotionEvent.ACTION_DOWN:
                                                                                    String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                                    ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, new ClipData.Item(v.getTag().toString()));
                                                                                    View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                                    v.startDrag(dragData, shadow, null, 0);
                                                                                    break;
                                                                          }

                                                                          return false;
                                                                     }
                                                                };

     OnClickListener                      longClickDragListener = new android.view.View.OnClickListener() {

                                                                     @Override public void onClick(View v) {
                                                                          String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                                                                          ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, new ClipData.Item(v.getTag().toString()));
                                                                          View.DragShadowBuilder shadow = new DragShadowBuilder(v);
                                                                          v.startDrag(dragData, shadow, null, 0);
                                                                     }

                                                                };

     @AfterViews void afterViews() {
          currentDate = new Date();
          dialogCreateNote = new Dialog(this);
          dialogCreateNote.requestWindowFeature(Window.FEATURE_NO_TITLE);
          dialogCreateNote.setContentView(CDialogCreateNote_.build(this));
          fillGridView(currentDate.getMonth(), currentDate.getYear());
          // ivAddNote.setOnClickListener(longClickDragListener);
          ivAddNote.setOnTouchListener(touch);
     }

     private void fillGridView(int month, int year) {
          int amountOfDaysInMonth = Utils.getDaysInMonth(month, year);
          for ( int i = 0; i < amountOfDaysInMonth; i++ ) {
               CNoteCalendarItem item = CNoteCalendarItem_.build(this, dialogCreateNote);
               item.twDay.setText(String.valueOf(i + 1));
               LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150, 100);
               item.setLayoutParams(lp);
               gridView.addView(item);
          }
     }
}