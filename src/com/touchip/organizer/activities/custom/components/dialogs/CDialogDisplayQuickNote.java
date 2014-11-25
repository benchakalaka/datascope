package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.Utils.AnimationManager;

@EViewGroup ( R.layout.dialog_show_quick_note ) public class CDialogDisplayQuickNote extends RelativeLayout {

     // ===================== views
     @ViewById public TextView  tvText;
     @ViewById public ImageView ivClose;

     // ====================== variable

     public CDialogDisplayQuickNote ( SuperActivity context ) {
          super(context);
     }

     /**
      * Handle close note button
      */
     @Click void ivClose() {
          Animation anim = AnimationManager.load(R.anim.slide_out_from_top);
          anim.setAnimationListener(new AnimationListener() {

               @Override public void onAnimationStart(Animation animation) {

               }

               @Override public void onAnimationRepeat(Animation animation) {

               }

               @Override public void onAnimationEnd(Animation animation) {
                    CDialogDisplayQuickNote.this.setVisibility(View.GONE);
               }
          });
          startAnimation(anim);

     }

}