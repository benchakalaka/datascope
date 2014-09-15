//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class AMenuModules_
    extends AMenuModules
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_menu);
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static AMenuModules_.IntentBuilder_ intent(Context context) {
        return new AMenuModules_.IntentBuilder_(context);
    }

    public static AMenuModules_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new AMenuModules_.IntentBuilder_(fragment);
    }

    public static AMenuModules_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new AMenuModules_.IntentBuilder_(supportFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        rlQuilt = ((RelativeLayout) hasViews.findViewById(id.rlQuilt));
        rlNotes = ((RelativeLayout) hasViews.findViewById(id.rlNotes));
        twDate = ((TextView) hasViews.findViewById(id.twDate));
        ivPrevDay = ((ImageView) hasViews.findViewById(id.ivPrevDay));
        rlWboard = ((RelativeLayout) hasViews.findViewById(id.rlWboard));
        ivNextDay = ((ImageView) hasViews.findViewById(id.ivNextDay));
        twUserDetails = ((TextView) hasViews.findViewById(id.twUserDetails));
        rlTv = ((RelativeLayout) hasViews.findViewById(id.rlTv));
        rlDelivery = ((RelativeLayout) hasViews.findViewById(id.rlDelivery));
        rlPrsr = ((RelativeLayout) hasViews.findViewById(id.rlPrsr));
        {
            View view = hasViews.findViewById(id.rlQuilt);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlQuilt();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivPrevDay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.ivPrevDay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rlPrsr);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlPrsr();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rlDelivery);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlDelivery();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rlWboard);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlWboard();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivNextDay);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.ivNextDay();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rlNotes);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlNotes();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.rlTv);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        AMenuModules_.this.rlTv();
                    }

                }
                );
            }
        }
        afterViews();
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, AMenuModules_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, AMenuModules_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, AMenuModules_.class);
        }

        public Intent get() {
            return intent_;
        }

        public AMenuModules_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (fragmentSupport_!= null) {
                fragmentSupport_.startActivityForResult(intent_, requestCode);
            } else {
                if (fragment_!= null) {
                    fragment_.startActivityForResult(intent_, requestCode);
                } else {
                    if (context_ instanceof Activity) {
                        ((Activity) context_).startActivityForResult(intent_, requestCode);
                    } else {
                        context_.startActivity(intent_);
                    }
                }
            }
        }

    }

}
