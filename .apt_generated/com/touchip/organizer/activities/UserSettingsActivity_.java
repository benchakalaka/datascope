//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import com.touchip.organizer.utils.AppSharedPreferences_;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class UserSettingsActivity_
    extends UserSettingsActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.activity_application_settings);
    }

    private void init_(Bundle savedInstanceState) {
        appPreff = new AppSharedPreferences_(this);
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

    public static UserSettingsActivity_.IntentBuilder_ intent(Context context) {
        return new UserSettingsActivity_.IntentBuilder_(context);
    }

    public static UserSettingsActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new UserSettingsActivity_.IntentBuilder_(fragment);
    }

    public static UserSettingsActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new UserSettingsActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
<<<<<<< HEAD
        etPort = ((EditText) hasViews.findViewById(id.etPort));
        buttonOk = ((Button) hasViews.findViewById(id.buttonOk));
        etIp = ((EditText) hasViews.findViewById(id.etIp));
        buttonCancel = ((Button) hasViews.findViewById(id.buttonCancel));
=======
        etIp = ((EditText) hasViews.findViewById(id.etIp));
        etPort = ((EditText) hasViews.findViewById(id.etPort));
        buttonCancel = ((Button) hasViews.findViewById(id.buttonCancel));
        buttonOk = ((Button) hasViews.findViewById(id.buttonOk));
>>>>>>> 78b3b7a5c2e64f02d5ae56556b9490c6f58c1ad9
        {
            View view = hasViews.findViewById(id.buttonCancel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserSettingsActivity_.this.buttonCancel();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.buttonOk);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        UserSettingsActivity_.this.buttonOk();
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
            intent_ = new Intent(context, UserSettingsActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, UserSettingsActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, UserSettingsActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public UserSettingsActivity_.IntentBuilder_ flags(int flags) {
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
