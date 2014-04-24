package com.touchip.organizer.activities.custom.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.utils.Utils;

/**
 * @author Ihor Karpachev
 */
public class QuickAction extends Utils.PopupWindows {
     private Animation                 mTrackAnim;
     private LayoutInflater            inflater;
     private ViewGroup                 mTrack;
     private OnActionItemClickListener mListener;

     private int                       mChildPos;
     private boolean                   animateTrack;
     private int                       animStyle;

     public static final int           ANIM_GROW_FROM_LEFT   = 1;
     public static final int           ANIM_GROW_FROM_RIGHT  = 2;
     public static final int           ANIM_GROW_FROM_CENTER = 3;
     public static final int           ANIM_AUTO             = 4;

     public QuickAction ( Context context ) {
          super(context);
          inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

          mTrackAnim = AnimationUtils.loadAnimation(context, R.anim.rail);

          mTrackAnim.setInterpolator(new Interpolator() {
               @Override public float getInterpolation(float t) {
                    final float inner = (t * 1.55f) - 1.1f;

                    return 1.2f - inner * inner;
               }
          });

          setRootViewId(R.layout.quickaction);

          animateTrack = true;
          mChildPos = 0;
     }

     public void setRootViewId(int id) {
          mRootView = inflater.inflate(id, null);
          mTrack = (ViewGroup) mRootView.findViewById(R.id.tracks);

          // mArrowDown = (ImageView) mRootView.findViewById(R.id.arrow_down);
          // mArrowUp = (ImageView) mRootView.findViewById(R.id.arrow_up);

          setContentView(mRootView);
     }

     public void animateTrack(boolean animateTrack) {
          this.animateTrack = animateTrack;
     }

     public void setAnimStyle(int animStyle) {
     }

     public void addActionItem(QuickAction.ActionItem action) {

          String title = action.getTitle();
          Drawable icon = action.getIcon();

          View container = inflater.inflate(R.layout.quickaction_single_item, null);

          ImageView img = (ImageView) container.findViewById(R.id.iv_icon);
          TextView text = (TextView) container.findViewById(R.id.tv_title);

          if ( icon != null ) {
               img.setImageDrawable(icon);
          } else {
               img.setVisibility(View.GONE);
          }

          if ( title != null ) {
               text.setText(title);
          } else {
               text.setVisibility(View.GONE);
          }

          final int pos = mChildPos;

          container.setOnClickListener(new OnClickListener() {
               @Override public void onClick(View v) {
                    if ( mListener != null ) {
                         mListener.onItemClick(pos);
                    }

                    dismiss();
               }
          });

          container.setFocusable(true);
          container.setClickable(true);

          mTrack.addView(container, mChildPos + 1);

          mChildPos++;
     }

     public void setOnActionItemClickListener(OnActionItemClickListener listener) {
          mListener = listener;
     }

     public void show(View anchor, int[] location) {
          preShow();

          Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());

          mRootView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
          mRootView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

          int rootHeight = mRootView.getMeasuredHeight();

          @SuppressWarnings ( "deprecation" )
          int screenWidth = mWindowManager.getDefaultDisplay().getWidth();

          boolean onTop = true;

          if ( rootHeight > anchor.getTop() ) {
               onTop = false;
          }

          // /showArrow(R.id.arrow_up, location[0]);

          setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

          mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, location[0], location[1] + anchor.getHeight());

          if ( animateTrack ) {
               mTrack.startAnimation(mTrackAnim);
          }

          /*
           * preShow();
           * int screenWidth = mWindowManager.getDefaultDisplay().getWidth();
           * showArrow(R.id.arrow_down, screenWidth/2);
           * mWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, screenWidth/2, mWindowManager.getDefaultDisplay().getHeight()/2);
           * if (animateTrack) mTrack.startAnimation(mTrackAnim);
           */
     }

     private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {

          switch (animStyle) {
               case ANIM_GROW_FROM_LEFT:
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
                    break;

               case ANIM_GROW_FROM_RIGHT:
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
                    break;

               case ANIM_GROW_FROM_CENTER:
                    mWindow.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
                    break;
               default:
                    return;
          }
     }

     public interface OnActionItemClickListener {
          public abstract void onItemClick(int pos);
     }

     /**
      * Action Item class
      * 
      * @author Karpachev Ihor
      */
     public static class ActionItem {
          private Drawable icon;
          private Bitmap   thumb;
          private String   title;
          private boolean  selected;

          public ActionItem () {
          }

          public ActionItem ( Drawable icon ) {
               this.icon = icon;
          }

          public void setTitle(String title) {
               this.title = title;
          }

          public String getTitle() {
               return this.title;
          }

          public void setIcon(Drawable icon) {
               this.icon = icon;
          }

          public Drawable getIcon() {
               return this.icon;
          }

          public void setSelected(boolean selected) {
               this.selected = selected;
          }

          public boolean isSelected() {
               return this.selected;
          }

          public void setThumb(Bitmap thumb) {
               this.thumb = thumb;
          }

          public Bitmap getThumb() {
               return this.thumb;
          }
     }

}