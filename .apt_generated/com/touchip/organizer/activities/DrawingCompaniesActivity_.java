//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.squareup.timessquare.sample.R.layout;
import com.touchip.organizer.activities.custom.components.CompaniesDrawingView;
import org.androidannotations.api.SdkVersionHelper;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class DrawingCompaniesActivity_
    extends DrawingCompaniesActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.fragments_companies_drawing);
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

    public static DrawingCompaniesActivity_.IntentBuilder_ intent(Context context) {
        return new DrawingCompaniesActivity_.IntentBuilder_(context);
    }

    public static DrawingCompaniesActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new DrawingCompaniesActivity_.IntentBuilder_(fragment);
    }

    public static DrawingCompaniesActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new DrawingCompaniesActivity_.IntentBuilder_(supportFragment);
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
        svTrades = ((ScrollView) hasViews.findViewById(com.squareup.timessquare.sample.R.id.svTrades));
        twBottomStatus = ((TextView) hasViews.findViewById(com.squareup.timessquare.sample.R.id.twBottomStatus));
        menuStatusArrow = ((ImageButton) hasViews.findViewById(com.squareup.timessquare.sample.R.id.menuStatusArrow));
        refresh = ((ImageButton) hasViews.findViewById(com.squareup.timessquare.sample.R.id.refresh));
        DRAW_VIEW = ((CompaniesDrawingView) hasViews.findViewById(com.squareup.timessquare.sample.R.id.DRAW_VIEW));
        llTrades = ((LinearLayout) hasViews.findViewById(com.squareup.timessquare.sample.R.id.llTrades));
        llFilters = ((LinearLayout) hasViews.findViewById(com.squareup.timessquare.sample.R.id.llFilters));
        llCompanies = ((LinearLayout) hasViews.findViewById(com.squareup.timessquare.sample.R.id.llCompanies));
        rlHotspotsOnCanvas = ((RelativeLayout) hasViews.findViewById(com.squareup.timessquare.sample.R.id.rlHotspotsOnCanvas));
        {
            View view = hasViews.findViewById(com.squareup.timessquare.sample.R.id.menuStatusArrow);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        DrawingCompaniesActivity_.this.menuStatusArrow();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(com.squareup.timessquare.sample.R.id.refresh);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        DrawingCompaniesActivity_.this.refresh();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == android.R.id.home) {
            homeSelected();
            return true;
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case  100 :
                DrawingCompaniesActivity_.this.onResult(resultCode, data);
                break;
        }
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, DrawingCompaniesActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, DrawingCompaniesActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, DrawingCompaniesActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public DrawingCompaniesActivity_.IntentBuilder_ flags(int flags) {
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
