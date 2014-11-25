package com.touchip.organizer.activities.custom.components.dialogs;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.ADrawingCompanies;
import com.touchip.organizer.activities.SuperActivity;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;
import com.touchip.organizer.utils.seekbarhint.SeekBarHint;

@EViewGroup ( R.layout.dialog_type_of_shape ) public class CDialogTypeOfShape extends RelativeLayout {

     // ===================== views
     @ViewById SeekBarHint       sbBrushSize;
     @ViewById ImageView         ivChangeBrushSize , ivSimpleSelected , ivSimple , ivLineSelected , ivLine , ivRectangleSelected , ivRectangle , ivTriangleSelected , ivTriangle , ivCircleSelected , ivCircle , ivArrowSelected , ivArrow;
     // ====================== variable
     private final SuperActivity activity;
     private final Dialog        dialog;
     private final int           animationId = R.anim.grow_from_top;
     private int                 currentShapeType;

     public CDialogTypeOfShape ( SuperActivity act , Dialog hostDialog ) {
          super(act);
          this.activity = act;
          this.dialog = hostDialog;
     }

     @AfterViews void afterViews() {
          currentShapeType = ADrawingCompanies.DRAW_VIEW.getDrawingShape();
          selectViewByTypesOfShape();
          // set current brush size
          sbBrushSize.setProgress(ADrawingCompanies.DRAW_VIEW.getBrushSize());
          sbBrushSize.setOnProgressChangeListener(new SeekBarHint.OnSeekBarHintProgressChangeListener() {
               @Override public String onHintTextChanged(SeekBarHint seekBarHint, int progress) {
                    return String.format("%s px", progress);
               }
          });
     }

     @Click void ivSimple() {
          playAnimationOnView(ivSimple);
          ivSimpleSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.STANDART_DRAWING;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     @Click void ivLine() {
          playAnimationOnView(ivLine);
          ivLineSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.LINE;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     @Click void ivRectangle() {
          playAnimationOnView(ivRectangle);
          ivRectangleSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.RECTANGLE;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     @Click void ivTriangle() {
          playAnimationOnView(ivTriangle);
          ivTriangleSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.TRIANGLE;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     @Click void ivCircle() {
          playAnimationOnView(ivCircle);
          ivCircleSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.CIRCLE;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     @Click void ivArrow() {
          playAnimationOnView(ivArrow);
          ivArrowSelected.setVisibility(View.VISIBLE);
          currentShapeType = ShapesType.ARROW;
          ADrawingCompanies.DRAW_VIEW.setDrawingShape(currentShapeType);
          Utils.showCustomToast(activity, getStringMessageByTypesOfShape() + "\n" + String.format(Utils.getResources(R.string.brush_size_is_set_to_pixels), sbBrushSize.getProgress()), R.drawable.different_shapes);
          ADrawingCompanies.getDrawView().setBrushSize(sbBrushSize.getProgress());
          dialog.dismiss();
     }

     private void playAnimationOnView(View target) {
          target.startAnimation(AnimationManager.load(animationId));
          unselectAllTypesOfShapes();
     }

     private void selectViewByTypesOfShape() {
          switch (currentShapeType) {
               case ShapesType.ARROW:
                    ivArrowSelected.setVisibility(View.VISIBLE);
                    break;

               case ShapesType.CIRCLE:
                    ivCircleSelected.setVisibility(View.VISIBLE);
                    break;

               case ShapesType.LINE:
                    ivLineSelected.setVisibility(View.VISIBLE);
                    break;

               case ShapesType.RECTANGLE:
                    ivRectangleSelected.setVisibility(View.VISIBLE);
                    break;

               case ShapesType.STANDART_DRAWING:
                    ivSimpleSelected.setVisibility(View.VISIBLE);
                    break;

               case ShapesType.TRIANGLE:
                    ivTriangleSelected.setVisibility(View.VISIBLE);
                    break;
          }
     }

     private String getStringMessageByTypesOfShape() {
          switch (currentShapeType) {
               case ShapesType.ARROW:
                    return "Draw an arrow";

               case ShapesType.CIRCLE:
                    return "Draw circle";

               case ShapesType.LINE:
                    return "Draw line";

               case ShapesType.RECTANGLE:
                    return "Draw rectangle";

               case ShapesType.STANDART_DRAWING:
                    return "Free drawing";

               case ShapesType.TRIANGLE:
                    return "Draw triangle";
          }

          return "";
     }

     private void unselectAllTypesOfShapes() {
          ivSimpleSelected.setVisibility(View.INVISIBLE);
          ivLineSelected.setVisibility(View.INVISIBLE);
          ivRectangleSelected.setVisibility(View.INVISIBLE);
          ivTriangleSelected.setVisibility(View.INVISIBLE);
          ivCircleSelected.setVisibility(View.INVISIBLE);
          ivArrowSelected.setVisibility(View.INVISIBLE);;
     }
}