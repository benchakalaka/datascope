//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities.custom.components.dialogs;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.utils.seekbarhint.SeekBarHint;
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
public final class CDialogTypeOfShape_
    extends CDialogTypeOfShape
    implements HasViews, OnViewChangedListener
{

    private boolean alreadyInflated_ = false;
    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();

    public CDialogTypeOfShape_(SuperActivity act, Dialog hostDialog) {
        super(act, hostDialog);
        init_();
    }

    public static CDialogTypeOfShape build(SuperActivity act, Dialog hostDialog) {
        CDialogTypeOfShape_ instance = new CDialogTypeOfShape_(act, hostDialog);
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
            inflate(getContext(), layout.dialog_type_of_shape, this);
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
        ivCircle = ((ImageView) hasViews.findViewById(id.ivCircle));
        ivRectangle = ((ImageView) hasViews.findViewById(id.ivRectangle));
        ivTriangle = ((ImageView) hasViews.findViewById(id.ivTriangle));
        ivTriangleSelected = ((ImageView) hasViews.findViewById(id.ivTriangleSelected));
        ivLine = ((ImageView) hasViews.findViewById(id.ivLine));
        ivArrow = ((ImageView) hasViews.findViewById(id.ivArrow));
        ivRectangleSelected = ((ImageView) hasViews.findViewById(id.ivRectangleSelected));
        ivCircleSelected = ((ImageView) hasViews.findViewById(id.ivCircleSelected));
        ivLineSelected = ((ImageView) hasViews.findViewById(id.ivLineSelected));
        ivSimpleSelected = ((ImageView) hasViews.findViewById(id.ivSimpleSelected));
        ivChangeBrushSize = ((ImageView) hasViews.findViewById(id.ivChangeBrushSize));
        ivSimple = ((ImageView) hasViews.findViewById(id.ivSimple));
        ivArrowSelected = ((ImageView) hasViews.findViewById(id.ivArrowSelected));
        sbBrushSize = ((SeekBarHint) hasViews.findViewById(id.sbBrushSize));
        {
            View view = hasViews.findViewById(id.ivRectangle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivRectangle();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivSimple);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivSimple();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivArrow);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivArrow();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivCircle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivCircle();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivLine);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivLine();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ivTriangle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        CDialogTypeOfShape_.this.ivTriangle();
                    }

                }
                );
            }
        }
        afterViews();
    }

}
