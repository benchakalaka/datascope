//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.touchip.organizer.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.squareup.timessquare.sample.R.id;
import com.squareup.timessquare.sample.R.layout;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class GeneralWhiteBoardActivity_
    extends GeneralWhiteBoardActivity
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
        setContentView(layout.fragment_white_board);
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

    public static GeneralWhiteBoardActivity_.IntentBuilder_ intent(Context context) {
        return new GeneralWhiteBoardActivity_.IntentBuilder_(context);
    }

    public static GeneralWhiteBoardActivity_.IntentBuilder_ intent(android.app.Fragment fragment) {
        return new GeneralWhiteBoardActivity_.IntentBuilder_(fragment);
    }

    public static GeneralWhiteBoardActivity_.IntentBuilder_ intent(android.support.v4.app.Fragment supportFragment) {
        return new GeneralWhiteBoardActivity_.IntentBuilder_(supportFragment);
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        ibWhiteboardSettings = ((ImageButton) hasViews.findViewById(id.ibWhiteboardSettings));
        ibColorsPanel = ((ImageButton) hasViews.findViewById(id.ibColorsPanel));
        ibClearAll = ((ImageButton) hasViews.findViewById(id.ibClearAll));
        ibShapesTriangle = ((ImageButton) hasViews.findViewById(id.ibShapesTriangle));
        ibRedo = ((ImageButton) hasViews.findViewById(id.ibRedo));
        ibShapesCircle = ((ImageButton) hasViews.findViewById(id.ibShapesCircle));
        llChangeColour = ((LinearLayout) hasViews.findViewById(id.llChangeColour));
        ibEraser = ((ImageButton) hasViews.findViewById(id.ibEraser));
        ibThin = ((ImageButton) hasViews.findViewById(id.ibThin));
        WHITE_BOARD_DRAWING = ((WhiteBoardDrawingView) hasViews.findViewById(id.WHITE_BOARD_DRAWING));
        ibShapesFreeDrawing = ((ImageButton) hasViews.findViewById(id.ibShapesFreeDrawing));
        ibShapesLine = ((ImageButton) hasViews.findViewById(id.ibShapesLine));
        ibColourGreen = ((ImageButton) hasViews.findViewById(id.ibColourGreen));
        llBrushSize = ((LinearLayout) hasViews.findViewById(id.llBrushSize));
        ibColourBlack = ((ImageButton) hasViews.findViewById(id.ibColourBlack));
        ibDrawText = ((ImageButton) hasViews.findViewById(id.ibDrawText));
        ibPencilSettings = ((ImageButton) hasViews.findViewById(id.ibPencilSettings));
        ibColourBlue = ((ImageButton) hasViews.findViewById(id.ibColourBlue));
        ibLoadWhiteboard = ((ImageButton) hasViews.findViewById(id.ibLoadWhiteboard));
        ibUndo = ((ImageButton) hasViews.findViewById(id.ibUndo));
        ibShowShapesPanel = ((ImageButton) hasViews.findViewById(id.ibShowShapesPanel));
        ibShapesRectangle = ((ImageButton) hasViews.findViewById(id.ibShapesRectangle));
        ibRefreshWhiteboard = ((ImageButton) hasViews.findViewById(id.ibRefreshWhiteboard));
        ibMedium = ((ImageButton) hasViews.findViewById(id.ibMedium));
        ibCreateNewWhiteboard = ((ImageButton) hasViews.findViewById(id.ibCreateNewWhiteboard));
        ibThick = ((ImageButton) hasViews.findViewById(id.ibThick));
        ibColourRed = ((ImageButton) hasViews.findViewById(id.ibColourRed));
        llDrawShapes = ((LinearLayout) hasViews.findViewById(id.llDrawShapes));
        ibSaveDrawing = ((ImageButton) hasViews.findViewById(id.ibSaveDrawing));
        ibColorPicker = ((ImageButton) hasViews.findViewById(id.ibColorPicker));
        llChangeTextSize = ((LinearLayout) hasViews.findViewById(id.llChangeTextSize));
        {
            View view = hasViews.findViewById(id.ibRefreshWhiteboard);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibRefreshWhiteboard();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibMedium);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibMedium();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibLoadWhiteboard);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibLoadWhiteboard();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibClearAll);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibClearAll();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColorPicker);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColorPicker();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColourBlue);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColourBlue();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibUndo);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibUndo();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColourBlack);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColourBlack();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibThick);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibThick();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShapesCircle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShapesCircle();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColorsPanel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColorsPanel();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColourRed);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColourRed();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibEraser);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibEraser();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShapesRectangle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShapesRectangle();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibPencilSettings);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibPencilSettings();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShapesLine);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShapesLine();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibRedo);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibRedo();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibDrawText);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibDrawText();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShapesFreeDrawing);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShapesFreeDrawing();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShapesTriangle);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShapesTriangle();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibWhiteboardSettings);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibWhiteboardSettings();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibSaveDrawing);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibSaveDrawing();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibCreateNewWhiteboard);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibCreateNewWhiteboard();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibShowShapesPanel);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibShowShapesPanel();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibColourGreen);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibColourGreen();
                    }

                }
                );
            }
        }
        {
            View view = hasViews.findViewById(id.ibThin);
            if (view!= null) {
                view.setOnClickListener(new OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        GeneralWhiteBoardActivity_.this.ibThin();
                    }

                }
                );
            }
        }
        afterViews();
    }

    @Override
    public void ibDrawText() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                GeneralWhiteBoardActivity_.super.ibDrawText();
            }

        }
        );
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;
        private android.app.Fragment fragment_;
        private android.support.v4.app.Fragment fragmentSupport_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, GeneralWhiteBoardActivity_.class);
        }

        public IntentBuilder_(android.app.Fragment fragment) {
            fragment_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, GeneralWhiteBoardActivity_.class);
        }

        public IntentBuilder_(android.support.v4.app.Fragment fragment) {
            fragmentSupport_ = fragment;
            context_ = fragment.getActivity();
            intent_ = new Intent(context_, GeneralWhiteBoardActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public GeneralWhiteBoardActivity_.IntentBuilder_ flags(int flags) {
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
