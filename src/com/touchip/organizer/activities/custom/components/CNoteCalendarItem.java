package com.touchip.organizer.activities.custom.components;

import java.util.ArrayList;
import java.util.Date;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ANotes;
import com.touchip.organizer.communication.rest.model.ModelNote;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.custom_note_calendar_item ) public class CNoteCalendarItem extends RelativeLayout implements OnDragListener , android.view.View.OnClickListener {

     @ViewById public TextView    twDay;
     private final Context        ctx;
     @ViewById LinearLayout       llAttachedColours;
     public ArrayList <ModelNote> notes = new ArrayList <ModelNote>();
     private final Dialog         dialog;

     public CNoteCalendarItem ( Context context , Dialog dialogCreateNote ) {
          super(context);
          this.ctx = context;
          this.dialog = dialogCreateNote;
          setOnDragListener(this);
          setOnClickListener(this);
     }

     @Override public boolean onDrag(View v, DragEvent event) {
          switch (event.getAction()) {
               case DragEvent.ACTION_DROP:
                    displayClearDialog();
                    break;
               case DragEvent.ACTION_DRAG_ENTERED:
                    this.startAnimation(AnimationManager.load(R.anim.fade));
                    break;

               case DragEvent.ACTION_DRAG_EXITED:
                    // this.startAnimation(AnimationManager.load(R.anim.fade));
                    break;
               default:
                    return true;
          }
          return true;
     }

     private void displayClearDialog() {
          // clear fields
          ((EditText) dialog.findViewById(R.id.etComments)).setText("");;
          ((EditText) dialog.findViewById(R.id.etName)).setText("");
          // set up current date (date which recieved dragged event)
          Date selectedDate = ANotes.currentDate;
          selectedDate.setDate(Integer.valueOf(twDay.getText().toString()));
          ((TextView) dialog.findViewById(R.id.twStartDate)).setText(Utils.formatDate(selectedDate));
          ((TextView) dialog.findViewById(R.id.twEndDateL)).setText(Utils.formatDate(selectedDate));
          // set listener on click on start date
          // ((TextView) dialog.findViewById(R.id.twEndDateL)).setOnClickListener(this);
          ((ImageView) dialog.findViewById(R.id.ivOk)).setOnClickListener(this);
          dialog.show();
     }

     private void addNote(String color) {
          ImageView iv = new ImageView(ctx);
          iv.setBackgroundColor(Color.parseColor(color));
          LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(30, 30);
          childParam1.weight = 0.3f;
          childParam1.topMargin = 1;
          iv.setLayoutParams(childParam1);
          this.llAttachedColours.addView(iv);
     }

     private void addNote(int color) {
          ImageView iv = new ImageView(ctx);
          iv.setBackgroundColor(color);
          LinearLayout.LayoutParams childParam1 = new LinearLayout.LayoutParams(30, 30);
          childParam1.weight = 0.3f;
          childParam1.topMargin = 1;
          iv.setLayoutParams(childParam1);
          this.llAttachedColours.addView(iv);
     }

     private Bitmap getThumb(String color) {
          Bitmap bmp = Bitmap.createBitmap(30, 30, Bitmap.Config.RGB_565);
          Canvas canvas = new Canvas(bmp);
          Paint paint = new Paint();

          paint.setColor(Color.parseColor(color));
          paint.setTextSize(24);
          paint.setFlags(Paint.ANTI_ALIAS_FLAG);
          canvas.drawRect(new Rect(0, 0, 150, 150), paint);
          paint.setColor(Color.WHITE);
          paint.setTextAlign(Paint.Align.CENTER);

          return bmp;
     }

     private Bitmap getThumb(int color) {
          Bitmap bmp = Bitmap.createBitmap(30, 30, Bitmap.Config.RGB_565);
          Canvas canvas = new Canvas(bmp);
          Paint paint = new Paint();

          paint.setColor(color);
          paint.setTextSize(24);
          paint.setFlags(Paint.ANTI_ALIAS_FLAG);
          canvas.drawRect(new Rect(0, 0, 150, 150), paint);
          paint.setColor(Color.WHITE);
          paint.setTextAlign(Paint.Align.CENTER);

          return bmp;
     }

     private int getNoteColor(ImageView target) {
          ColorDrawable bg = (ColorDrawable) target.getBackground();
          if ( (null == bg) || (bg.getColor() == Color.TRANSPARENT) ) {
               return Color.WHITE;
          } else {
               return ((ColorDrawable) target.getBackground()).getColor();
          }
     }

     @Override public void onClick(View v) {
          switch (v.getId()) {
               case R.id.ivOk:

                    // get all fields
                    String comments = ((EditText) dialog.findViewById(R.id.etComments)).getText().toString();
                    String name = ((EditText) dialog.findViewById(R.id.etName)).getText().toString();
                    int noteColor = getNoteColor(((ImageView) dialog.findViewById(R.id.ivTypeOfNote)));
                    String startDate = ((TextView) dialog.findViewById(R.id.twStartDate)).getText().toString();
                    String endDate = ((TextView) dialog.findViewById(R.id.twEndDateL)).getText().toString();

                    // construct note
                    ModelNote note = new ModelNote();
                    note.comments = comments;
                    note.name = name;
                    note.endDate = endDate;
                    note.startDate = startDate;
                    note.color = noteColor;
                    note.type = getNoteTypeStringByImageId(CDialogCreateNote.noteTypeId);

                    // add note to array
                    notes.add(note);

                    // update ll
                    fillNotesLl(notes);

                    Utils.logw("Start Date is" + startDate + "\nEnd date is " + endDate + "\nName is " + name + "\nComments are" + comments);
                    addNote(noteColor);
                    dialog.hide();
                    break;

               default:
                    startAnimation(AnimationManager.load(R.anim.fade));
                    fillNotesLl(notes);
                    break;
          }
     }

     private void fillNotesLl(ArrayList <ModelNote> nts) {
          ANotes.llNotes.removeAllViews();
          int i = 0;
          for ( ModelNote n : notes ) {
               CPanelNoteCalendarItem item = CPanelNoteCalendarItem_.build(this.ctx);
               item.setUpNote(n);
               ANotes.llNotes.addView(item);
               Animation anim = AnimationManager.load(R.anim.push_left_in);
               anim.setStartOffset(i);
               i += 150;
               item.startAnimation(anim);
          }
     }

     private String getNoteTypeStringByImageId(int noteImageId) {
          String noteType = "";
          if ( noteImageId == R.drawable.rectangle_1 ) {
               noteType = "rectangle";
          }

          if ( noteImageId == R.drawable.triangle_1 ) {
               noteType = "triangle";
          }

          if ( noteImageId == R.drawable.circle ) {
               noteType = "circle";
          }
          return noteType;
     }

}