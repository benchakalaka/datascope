//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities.custom.components.dialogs;

import java.util.Map;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import com.touchip.organizer.activities.SuperActivity;
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
public final class CDialogCreateQuickNote_
    extends CDialogCreateQuickNote
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public CDialogCreateQuickNote_(SuperActivity context, Map requestParams, Dialog d) {
        super(context, requestParams, d);
        init_();
    }

    public static CDialogCreateQuickNote build(SuperActivity context, Map requestParams, Dialog d) {
        CDialogCreateQuickNote_ instance = new CDialogCreateQuickNote_(context, requestParams, d);
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
            inflate(getContext(), layout.dialog_create_quick_note, this);
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
        etQuickNoteText = ((EditText) hasViews.findViewById(id.etQuickNoteText));
        twValidTo = ((TextView) hasViews.findViewById(id.twValidTo));
        ibValidFrom = ((ImageButton) hasViews.findViewById(id.ibValidFrom));
        btnOk = ((Button) hasViews.findViewById(id.btnOk));
        ibValidTo = ((ImageButton) hasViews.findViewById(id.ibValidTo));
        twValidFrom = ((TextView) hasViews.findViewById(id.twValidFrom));
        {
            View view = hasViews.findViewById(id.ibValidFrom);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogCreateQuickNote_.this.ibValidFrom();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.btnOk);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogCreateQuickNote_.this.btnOk();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibValidTo);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogCreateQuickNote_.this.ibValidTo();
                    }

                }
                );
            }
        }
        afterViews();
    }

}