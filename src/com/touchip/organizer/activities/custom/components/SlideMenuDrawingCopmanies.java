package com.touchip.organizer.activities.custom.components;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.DrawingCompaniesActivity;
import com.touchip.organizer.communication.rest.request.GetDatesToHighlightRequest;
import com.touchip.organizer.communication.rest.request.listener.DatesToHighlightDrawingActivityRequestListener;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.menu_drawing_companies_activity ) public class SlideMenuDrawingCopmanies extends ScrollView implements OnOpenListener , OnCloseListener {

     // Parent activity
     private final DrawingCompaniesActivity activity;
     // Side slide menu
     private SlidingMenu                    menu;
     // Dialog for changing brush size
     private Dialog                         dialog;

     // load views
     @ViewById LinearLayout                 undo;
     @ViewById LinearLayout                 redo;
     @ViewById LinearLayout                 changeBrushSize;
     @ViewById LinearLayout                 meetingPlan;
     @ViewById LinearLayout                 changeArea;
     @ViewById LinearLayout                 changeDate;

     /**
      * Default constructor
      * 
      * @param context
      *             object of parent activity
      */
     public SlideMenuDrawingCopmanies ( Context context ) {
          super(context);
          this.activity = (DrawingCompaniesActivity) context;
     }

     @AfterViews void afterViews() {
          dialog = new Dialog(activity);

          // configure the SlidingMenu
          Point size = new Point();
          activity.getWindowManager().getDefaultDisplay().getSize(size);
          menu = new SlidingMenu(activity);
          menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
          menu.setShadowWidthRes(R.dimen.shadow_width);
          menu.setBehindOffset((int) (size.x * 0.80));
          menu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);
          menu.setMenu(this);
          menu.setAlwaysDrawnWithCacheEnabled(true);
          menu.setMode(SlidingMenu.LEFT);

          menu.setOnOpenListener(this);
          menu.setOnCloseListener(this);
     }

     /**
      * Return menu object
      * 
      * @return SlidingMenu
      */
     public SlidingMenu getMenu() {
          return menu;
     }

     /**
      * Set control button for changing state, depends on menu state (open = show left arrow, closed = show right arrow)
      * 
      * @param control
      *             button to be set
      */
     // public void setMenuControlButton(ImageButton control) {
     // this.controlButton = control;
     // }

     /**
      * Callback which will be invoked when menu is start closing
      */
     // @Override public void onClose() {
     // controlButton.setImageResource(R.drawable.right_arrow);
     // }

     /**
      * Undo operation for drawing
      */
     @Click void undo() {
          undo.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          DrawingCompaniesActivity.getDrawView().undo();
     }

     /**
      * Redo operation for drawing
      */
     @Click void redo() {
          redo.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          DrawingCompaniesActivity.getDrawView().redo();
     }

     /**
      * Change floor/area
      */
     @Click void changeArea() {
          changeArea.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          activity.loadImage(null, null);
     }

     /**
      * Callback which will be invoked when menu is start open
      */
     @Override public void onOpen() {
          // controlButton.setImageResource(R.drawable.left_arrow);
     }

     /**
      * Diaplay change brush size menu
      */
     @Click void changeBrushSize() {
          // Animate menu item
          changeBrushSize.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          // Configure dialog
          dialog.setTitle(R.string.set_brush_size);
          dialog.setContentView(R.layout.dialog_brush_size_chooser);

          Button buttonOk = (Button) dialog.findViewById(R.id.dialog_chooseBrushSize_button_ok);
          Button buttonCancel = (Button) dialog.findViewById(R.id.dialog_chooseBrushSize_button_cancel);

          final SeekBar seekbar = (SeekBar) dialog.findViewById(R.id.dialog_seekbar_brush_chooser);
          buttonOk.setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    Utils.showCustomToast(activity, String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), seekbar.getProgress()), R.drawable.brush);
                    DrawingCompaniesActivity.getDrawView().setBrushSize(seekbar.getProgress());
                    dialog.dismiss();
               }
          });
          buttonCancel.setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    dialog.dismiss();
               }
          });
          // max brush size
          seekbar.setMax(30);
          // set current brush size
          seekbar.setProgress(getResources().getInteger(R.integer.small_size));

          seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
               @Override public void onStopTrackingTouch(SeekBar seekBar) {
               }

               @Override public void onStartTrackingTouch(SeekBar seekBar) {
               }

               @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    dialog.setTitle(String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), seekbar.getProgress()));
               }
          });
          activity.onBackPressed();
          dialog.show();
     }

     private void getDatesToHighliht() {
          DrawingCompaniesActivity.showProgressDialog();
          GetDatesToHighlightRequest request = new GetDatesToHighlightRequest();
          activity.getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DatesToHighlightDrawingActivityRequestListener(activity));
     }

     @Click void changeDate() {
          changeDate.startAnimation(AnimationManager.load(android.R.anim.fade_out));
          /*
           * if ( DrawingCompaniesActivity.getDrawView().isNeedAutoSave() ) {
           * activity.showSaveDrawingDialog(false);
           * }
           */
          getDatesToHighliht();
     }

     @Override public void onClose() {
          // TODO Auto-generated method stub

     }
}
