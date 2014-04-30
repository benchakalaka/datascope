//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities.custom.components;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import org.androidannotations.api.BackgroundExecutor;
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
public final class ActionBarDrawingCompanies_
    extends ActionBarDrawingCompanies
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    public ActionBarDrawingCompanies_(Context context) {
        super(context);
        init_();
    }

    public static ActionBarDrawingCompanies build(Context context) {
        ActionBarDrawingCompanies_ instance = new ActionBarDrawingCompanies_(context);
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
            inflate(getContext(), layout.ab_company_drawing, this);
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
        tvCurrentCompanyName = ((TextView) hasViews.findViewById(id.tvCurrentCompanyName));
        whiteboardLayout = ((LinearLayout) hasViews.findViewById(id.whiteboardLayout));
        llTv = ((LinearLayout) hasViews.findViewById(id.llTv));
        tbShowTrades = ((ToggleButton) hasViews.findViewById(id.tbShowTrades));
        tbShowFilters = ((ToggleButton) hasViews.findViewById(id.tbShowFilters));
        ivCurrentCompanyColor = ((ImageView) hasViews.findViewById(id.ivCurrentCompanyColor));
        {
            View view = hasViews.findViewById(id.llTv);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActionBarDrawingCompanies_.this.llTv();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.whiteboardLayout);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        ActionBarDrawingCompanies_.this.whiteboardLayout();
                    }

                }
                );
            }
        }
        {
            CompoundButton view = ((CompoundButton) hasViews.findViewById(id.tbShowTrades));
            if (view!= null) {
                view.setOnCheckedChangeListener(new OnCheckedChangeListener() {


                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ActionBarDrawingCompanies_.this.tbShowTrades(buttonView, isChecked);
                    }

                }
                );
            }
        }
        {
            CompoundButton view = ((CompoundButton) hasViews.findViewById(id.tbShowFilters));
            if (view!= null) {
                view.setOnCheckedChangeListener(new OnCheckedChangeListener() {


                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        ActionBarDrawingCompanies_.this.tbShowFilters(buttonView, isChecked);
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void reloadBrowser() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                ActionBarDrawingCompanies_.super.reloadBrowser();
            }

        }
        );
    }

    @Override
    public void refreshBrowser() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    ActionBarDrawingCompanies_.super.refreshBrowser();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

}
