//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities.custom.components;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;


/**
 * We use @SuppressWarning here because our java code
 * generator doesn't know that there is no need
 * to import OnXXXListeners from View as we already
 * are in a View.
 * 
 */
@SuppressWarnings("unused")
public final class CDialogEnterPin_
    extends CDialogEnterPin
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public CDialogEnterPin_(Context context, Activity act) {
        super(context, act);
        init_();
    }

    public static CDialogEnterPin build(Context context, Activity act) {
        CDialogEnterPin_ instance = new CDialogEnterPin_(context, act);
        instance.onFinishInflate();
        return instance;
    }

    /**
     * The mAlreadyInflated_ hack is needed because of an Android bug
     * which leads to infinite calls of onFinishInflate()
     * when inflating a layout with a parent and using
     * the <merge /> tag.
     * 
     */
    @Override
    public void onFinishInflate() {
        if (!alreadyInflated_) {
            alreadyInflated_ = true;
            inflate(getContext(), layout.dialog_user_password, this);
            onViewChangedNotifier_.notifyViewChanged(this);
        }
        super.onFinishInflate();
    }

    private void init_() {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        OnViewChangedNotifier.registerOnViewChangedListener(this);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        tw8 = ((TextView) hasViews.findViewById(id.tw8));
        tw0 = ((TextView) hasViews.findViewById(id.tw0));
        tw9 = ((TextView) hasViews.findViewById(id.tw9));
        tw1 = ((TextView) hasViews.findViewById(id.tw1));
        twLogin = ((TextView) hasViews.findViewById(id.twLogin));
        twc = ((TextView) hasViews.findViewById(id.twc));
        tw5 = ((TextView) hasViews.findViewById(id.tw5));
        etPassword = ((EditText) hasViews.findViewById(id.etPassword));
        tw6 = ((TextView) hasViews.findViewById(id.tw6));
        tw4 = ((TextView) hasViews.findViewById(id.tw4));
        tw3 = ((TextView) hasViews.findViewById(id.tw3));
        tw7 = ((TextView) hasViews.findViewById(id.tw7));
        tw2 = ((TextView) hasViews.findViewById(id.tw2));
        {
            View view = hasViews.findViewById(id.tw5);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw5();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.twc);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.twc();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw8);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw8();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw0);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw0();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw1);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw1();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw3);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw3();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw4);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw4();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw7);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw7();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw6);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw6();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw2);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw2();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.tw9);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.tw9();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.twLogin);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogEnterPin_.this.twLogin();
                    }

                }
                );
            }
        }
        afterViews();
    }

}
