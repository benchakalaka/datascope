//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities.custom.components.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
public final class CDialogCompleteHotspot_
    extends CDialogCompleteHotspot
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public CDialogCompleteHotspot_(SuperActivity act, Dialog hostDialog, int hotspotId, Dialog hotspotDetailHostDialog) {
        super(act, hostDialog, hotspotId, hotspotDetailHostDialog);
        init_();
    }

    public static CDialogCompleteHotspot build(SuperActivity act, Dialog hostDialog, int hotspotId, Dialog hotspotDetailHostDialog) {
        CDialogCompleteHotspot_ instance = new CDialogCompleteHotspot_(act, hostDialog, hotspotId, hotspotDetailHostDialog);
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
            inflate(getContext(), layout.dialog_complete_hotspot, this);
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
        btnOk = ((Button) hasViews.findViewById(id.btnOk));
        btnCancel = ((Button) hasViews.findViewById(id.btnCancel));
        {
            View view = hasViews.findViewById(id.btnCancel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogCompleteHotspot_.this.btnCancel();
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
                        CDialogCompleteHotspot_.this.btnOk();
                    }

                }
                );
            }
        }
    }

}
