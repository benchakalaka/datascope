package com.touchip.organizer.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard_;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.communication.rest.request.DownloadOrCreateNewWhiteboardRequest;
import com.touchip.organizer.communication.rest.request.GetPathsCreationTimeRequest;
import com.touchip.organizer.communication.rest.request.RestAddresses;
import com.touchip.organizer.communication.rest.request.SaveOrCreateNewWhiteboardRequest;
import com.touchip.organizer.communication.rest.request.listener.DownloadOrCreateNewWhiteboardRequestListener;
import com.touchip.organizer.communication.rest.request.listener.GetPathsCreationTimeRequestListener;
import com.touchip.organizer.communication.rest.request.listener.SaveOrCreateNewWhiteboardRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.utils.GlobalConstants;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;
import com.touchip.organizer.utils.colorpicker.ColorPicker;
import com.touchip.organizer.utils.colorpicker.ColorPicker.OnColorChangedListener;
import com.touchip.organizer.utils.colorpicker.OpacityBar;
import com.touchip.organizer.utils.colorpicker.OpacityBar.OnOpacityChangedListener;
import com.touchip.organizer.utils.colorpicker.SaturationBar;
import com.touchip.organizer.utils.colorpicker.SaturationBar.OnSaturationChangedListener;

@EActivity ( R.layout.fragment_white_board ) public class GeneralWhiteBoardActivity extends SpiceActivity implements OnColorChangedListener ,
          View.OnClickListener , OnSaturationChangedListener , OnOpacityChangedListener {
     private Dialog                                dialogChangeColour , dialogInputText;
     private AlertDialog.Builder                   builder;
     private ColorPicker                           picker;
     public static String                          WHITEBOARD_TYPE   = "";
     private static ProgressDialog                 progressDialog;
     private static TextView                       twSaturationBar , twOpacityBar;
     private int                                   colorToSet;
     public static boolean                         IS_WHITEBOARD_NEW = false;

     public static GeneralWhiteBoardActivity       INSTANCE;

     // Load Views
     @ViewById public static WhiteBoardDrawingView WHITE_BOARD_DRAWING;

     @ViewById ImageButton                         ibPencilSettings , ibRedo , ibUndo , ibThick , ibMedium , ibThin , ibEraser;
     @ViewById ImageButton                         ibColorsPanel , ibColourBlack , ibColourBlue , ibColourGreen , ibColourRed , ibColorPicker;
     @ViewById ImageButton                         ibShowShapesPanel , ibShapesRectangle , ibShapesCircle , ibShapesLine , ibShapesTriangle ,
                                                   ibShapesFreeDrawing , ibDrawText;
     @ViewById ImageButton                         ibWhiteboardSettings , ibClearAll , ibLoadWhiteboard , ibSaveDrawing , ibCreateNewWhiteboard ,
                                                   ibRefreshWhiteboard;

     // Layouts which contains all control button (for hide/show feature)
     @ViewById LinearLayout                        llBrushSize , llChangeTextSize , llChangeColour , llDrawShapes;
     public ActionBarGeneralWhiteboard             sideSlideMenu;

     OnTouchListener                               menuTouchListener = new OnTouchListener() {

                                                                          @Override public boolean onTouch(View v, MotionEvent event) {
                                                                               Utils.setScaleFactor(v, ibColorPicker, ibColourBlack, ibColourBlue, ibColourGreen, ibColourRed, ibColourRed, ibShapesCircle, ibShapesFreeDrawing, ibShapesLine, ibShapesRectangle, ibShapesTriangle, ibThick, ibMedium, ibThin);
                                                                               return false;
                                                                          }
                                                                     };

     @AfterViews void afterViews() {
          INSTANCE = this;
          dialogChangeColour = new Dialog(GeneralWhiteBoardActivity.this);
          dialogInputText = new Dialog(GeneralWhiteBoardActivity.this);
          new Dialog(GeneralWhiteBoardActivity.this);
          builder = new AlertDialog.Builder(this);

          dialogChangeColour.setContentView(R.layout.dialog_color_picker);
          dialogInputText.setContentView(R.layout.dialog_input_text_for_drawing);
          dialogChangeColour.setTitle("Choose color/saturation value/opacity/saturation");

          twSaturationBar = ((TextView) dialogChangeColour.findViewById(R.id.twSaturationBar));
          twOpacityBar = ((TextView) dialogChangeColour.findViewById(R.id.twOpacityBar));

          picker = (ColorPicker) dialogChangeColour.findViewById(R.id.picker);
          OpacityBar opacityBar = (OpacityBar) dialogChangeColour.findViewById(R.id.opacitybar);
          SaturationBar saturationBar = (SaturationBar) dialogChangeColour.findViewById(R.id.saturationbar);

          picker.addOpacityBar(opacityBar);
          picker.addSaturationBar(saturationBar);

          // adds listener to the colorpicker which is implemented in the activity
          picker.setOnColorChangedListener(this);
          // to turn of showing the old color
          picker.setShowOldCenterColor(true);
          // set listener when opacity has been changed
          opacityBar.setOnOpacityChangedListener(this);
          // set listener when saturation has been changed
          saturationBar.setOnSaturationChangedListener(this);

          ((Button) dialogChangeColour.findViewById(R.id.buttonOk)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    WHITE_BOARD_DRAWING.disableEraserMode();
                    // color selected by the user.
                    WHITE_BOARD_DRAWING.setColor(colorToSet);
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, "New color has been set", R.drawable.color_picker);
                    dialogChangeColour.hide();
               }
          });

          ((Button) dialogChangeColour.findViewById(R.id.buttonCancel)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    dialogChangeColour.hide();
               }
          });

          sideSlideMenu = ActionBarGeneralWhiteboard_.build(GeneralWhiteBoardActivity.this);

          Utils.configureCustomActionBar(getActionBar(), sideSlideMenu);

          dialogInputText.setTitle("This text will be drawn on the white board");
          Button ok = (Button) dialogInputText.findViewById(R.id.dialog_button_ok);
          Button cancel = (Button) dialogInputText.findViewById(R.id.dialog_button_cancel);

          ok.setOnClickListener(this);
          cancel.setOnClickListener(this);

          progressDialog = new ProgressDialog(GeneralWhiteBoardActivity.this);
          progressDialog.setTitle("Loading ...");
          progressDialog.setCancelable(true);

          ibColourBlack.setOnTouchListener(menuTouchListener);
          ibColourBlue.setOnTouchListener(menuTouchListener);
          ibColourGreen.setOnTouchListener(menuTouchListener);
          ibColourRed.setOnTouchListener(menuTouchListener);
          ibShapesCircle.setOnTouchListener(menuTouchListener);
          ibShapesRectangle.setOnTouchListener(menuTouchListener);
          ibShapesLine.setOnTouchListener(menuTouchListener);
          ibShapesTriangle.setOnTouchListener(menuTouchListener);
          ibShapesFreeDrawing.setOnTouchListener(menuTouchListener);
          ibThin.setOnTouchListener(menuTouchListener);
          ibMedium.setOnTouchListener(menuTouchListener);
          ibThick.setOnTouchListener(menuTouchListener);
          ibEraser.setOnTouchListener(menuTouchListener);
          ibColorPicker.setOnTouchListener(menuTouchListener);

          ibColourBlack.setTag(GlobalConstants.COLOURS_BUTTON);
          ibColourBlue.setTag(GlobalConstants.COLOURS_BUTTON);
          ibColourGreen.setTag(GlobalConstants.COLOURS_BUTTON);
          ibColourRed.setTag(GlobalConstants.COLOURS_BUTTON);
          ibColorPicker.setTag(GlobalConstants.COLOURS_BUTTON);

          ibShapesCircle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesRectangle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesLine.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesTriangle.setTag(GlobalConstants.SHAPES_BUTTON);
          ibShapesFreeDrawing.setTag(GlobalConstants.SHAPES_BUTTON);

          ibThin.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);
          ibMedium.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);
          ibThick.setTag(GlobalConstants.BRUSH_SIZE_BUTTON);

          ibEraser.setTag(GlobalConstants.TEXT_SETTINGS_BUTTON);

          // set default selected settings
          ibColourBlack.setScaleX(0.9f);
          ibColourBlack.setScaleY(0.9f);

          ibShapesFreeDrawing.setScaleX(0.9f);
          ibShapesFreeDrawing.setScaleY(0.9f);

          ibThick.setScaleX(0.9f);
          ibThick.setScaleY(0.9f);

          ibShapesFreeDrawing.setBackgroundResource(R.drawable.background_view_rounded_single);
          ibColourBlack.setBackgroundResource(R.drawable.background_view_rounded_single);
          ibThick.setBackgroundResource(R.drawable.background_view_rounded_single);

          WHITE_BOARD_DRAWING.setColor(Color.BLACK);
          WHITE_BOARD_DRAWING.setBrushSize(5);
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
               case android.R.id.home:
                    WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.STANDART_DRAWING);
                    onBackPressed();
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     /**
      * Load paths array
      */
     public void loadPathes() {
          try {
               // Variable array for POST request
               Map <String, String> params = new HashMap <String, String>();
               if ( WHITEBOARD_TYPE.equals(GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING) || IS_WHITEBOARD_NEW ) {
                    params.put(GlobalConstants.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                    params.put(GlobalConstants.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.put(GlobalConstants.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    params.put(GlobalConstants.TYPE, WHITEBOARD_TYPE);
                    params.put(GlobalConstants.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               } else {
                    params.put(GlobalConstants.PATH_ID, String.valueOf(GlobalConstants.LAST_CLICKED_WHITE_BOARD.id));
               }
               DownloadOrCreateNewWhiteboardRequest request = new DownloadOrCreateNewWhiteboardRequest(params, IS_WHITEBOARD_NEW ? RestAddresses.CREATE_NEW_WHITE_BOARD
                         : RestAddresses.DOWNLOAD_DRAWING_PATHES);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new DownloadOrCreateNewWhiteboardRequestListener());
          } catch (Exception e) {
               Utils.logw(e.getMessage());
               return;
          }
     }

     public void saveAndSendDrawing() {
          try {
               // Variable array for POST request
               MultiValueMap <String, Object> params = new LinkedMultiValueMap <String, Object>();
               File file = new File(getApplicationContext().getCacheDir(), "binary" + System.currentTimeMillis() + ".binary");
               List <PathSerializable> listPathes = WHITE_BOARD_DRAWING.getPaths();
               if ( file.exists() ) {
                    file.delete();
               }
               file.createNewFile();
               ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
               out.writeObject(listPathes);
               out.close();

               FileSystemResource fileSystemResources = new FileSystemResource(file);

               if ( !WHITE_BOARD_DRAWING.getPaths().isEmpty() ) {
                    params.add("drawingData", fileSystemResources);
               }

               if ( WHITEBOARD_TYPE.equals(GlobalConstants.DrawingType.HOTSPOT_WHITEBOARD_DRAWING) ) {
                    params.add(GlobalConstants.TYPE, WHITEBOARD_TYPE);
                    params.add(GlobalConstants.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                    params.add(GlobalConstants.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.add(GlobalConstants.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    params.add(GlobalConstants.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               } else {
                    if ( !IS_WHITEBOARD_NEW ) {
                         params.add(GlobalConstants.PATH_ID, String.valueOf(GlobalConstants.LAST_CLICKED_WHITE_BOARD.id));
                    } else {
                         IS_WHITEBOARD_NEW = !IS_WHITEBOARD_NEW;
                         params.add(GlobalConstants.TYPE, WHITEBOARD_TYPE);
                         params.add(GlobalConstants.MARKER_ID, GlobalConstants.LAST_CLICKED_MARKER_ID);
                         params.add(GlobalConstants.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                         params.add(GlobalConstants.FLOOR, GlobalConstants.CURRENT_FLOOR);
                    }
               }
               SaveOrCreateNewWhiteboardRequest request = new SaveOrCreateNewWhiteboardRequest(params, IS_WHITEBOARD_NEW ? RestAddresses.CREATE_NEW_WHITE_BOARD
                         : RestAddresses.SAVE_DRAWING_PATHES);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveOrCreateNewWhiteboardRequestListener(this));
               if ( IS_WHITEBOARD_NEW ) {
                    IS_WHITEBOARD_NEW = !IS_WHITEBOARD_NEW;
               }
          } catch (Exception e) {
               Utils.logw(e.getMessage());
          }
     }

     @Override public void onClick(final View view) {
          switch (view.getId()) {
          // Draw text dialog - OK button
               case R.id.dialog_button_ok:
                    WHITE_BOARD_DRAWING.disableEraserMode();
                    final EditText ed = (EditText) dialogInputText.findViewById(R.id.dialog_edittext_input_text_to_draw);
                    if ( "".equals(ed.getText().toString()) ) {
                         Utils.showToast(getApplicationContext(), "Field cannot be empty", false);
                    } else {
                         WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.DRAW_TEXT);
                         WHITE_BOARD_DRAWING.setTextToDraw(ed.getText().toString());
                         ed.setText("");
                         dialogInputText.dismiss();
                    }
                    break;
               // Draw text dialog - CANCEL button
               case R.id.dialog_button_cancel:
                    dialogInputText.dismiss();
                    break;
               default:
                    return;
          }
     }

     @Override public void onColorChanged(int color) {
          colorToSet = color;
     }

     // //////////////////////////// CLICK PROCESSOR SECTION //////////////////////////////////////////////////////////////

     @UiThread @Click void ibDrawText() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibDrawText.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Text mode is activated!", R.drawable.text);
          dialogInputText.show();
     }

     @Click void ibColorPicker() {
          ibColorPicker.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Choose custom colour", R.drawable.color_picker);
          try {
               picker.setOldCenterColor(WHITE_BOARD_DRAWING.getCurrentColor());
               picker.setColor(WHITE_BOARD_DRAWING.getCurrentColor());
          } catch (Exception ex) {
               Utils.logw(ex.getMessage());
          }
          dialogChangeColour.show();
     }

     @Click void ibClearAll() {
          builder.setTitle(getResources().getString(R.string.clear_canvas));
          builder.setMessage(getResources().getString(R.string.everything_will_be_clear)).setIcon(R.drawable.clear);
          builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    WHITE_BOARD_DRAWING.disableEraserMode();
                    WHITE_BOARD_DRAWING.clearAll();
                    ibClearAll.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Whiteboard has been cleared!", R.drawable.clear);
                    dialog.dismiss();
               }
          });
          builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });
          builder.create().show();
     }

     @Click void ibColourBlack() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibColourBlack.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setColor(Color.BLACK);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Brush color is set to black", R.drawable.circle_black);
     }

     @Click void ibEraser() {
          ibEraser.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setEraserMode();
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Erase mode is activated", R.drawable.eraser);
     }

     @Click void ibThin() {
          ibThin.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(15);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Thick brush size", R.drawable.brush_pencil128);
     }

     @Click void ibMedium() {
          ibMedium.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(10);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Medium brush size", R.drawable.brush_pencil128);
     }

     @Click void ibShapesRectangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesRectangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.RECTANGLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Draw rectangle", R.drawable.rectangle);
     }

     @Click void ibThick() {
          ibThick.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(5);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Thin brush size", R.drawable.brush_pencil128);
     }

     @Click void ibShapesCircle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesCircle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.CIRCLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Draw circle", R.drawable.circle);
     }

     @Click void ibShapesLine() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesLine.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.LINE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Draw line", R.drawable.line);
     }

     @Click void ibShapesTriangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesTriangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.TRIANGLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Draw triangle", R.drawable.triangle);
     }

     @Click void ibShapesFreeDrawing() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesFreeDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.STANDART_DRAWING);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Free drawing", R.drawable.free_drawing);
     }

     @Click void ibColourBlue() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibColourBlue.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setColor(Color.BLUE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Brush color is set to blue", R.drawable.circle_blue);
     }

     @Click void ibColourGreen() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibColourGreen.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setColor(Color.GREEN);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Brush color is set to green", R.drawable.circle_green);
     }

     @Click void ibColourRed() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibColourRed.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setColor(Color.RED);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Brush color is set to red", R.drawable.circle_red);
     }

     @Click void ibSaveDrawing() {
          ibSaveDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          saveAndSendDrawing();
     }

     @Click void ibUndo() {
          ibUndo.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.redo();

     }

     @Click void ibRedo() {
          ibRedo.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.undo();
     }

     @Click void ibLoadWhiteboard() {
          ibLoadWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));

          GetPathsCreationTimeRequest request = new GetPathsCreationTimeRequest();
          getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new GetPathsCreationTimeRequestListener(this));
     }

     @Click void ibCreateNewWhiteboard() {
          builder.setTitle("Create new whiteboard");
          builder.setMessage("All shapes/lines will be cleared, you can not undo this action! Do you want confirm this action?");
          builder.setIcon(R.drawable.clear);
          builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    ibCreateNewWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
                    IS_WHITEBOARD_NEW = true;
                    WHITE_BOARD_DRAWING.clearAllPaths();
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, "New whiteboard has been created!", R.drawable.add_whiteboard);

                    // twDataAndTimeCreated.setText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " not saved");
                    // twDataAndTimeCreated.startAnimation(AnimationManager.load(R.anim.pump_bottom, 600));
                    dialog.dismiss();
               }
          });
          builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });
          AlertDialog alert = builder.create();
          alert.show();
     }

     @Click void ibRefreshWhiteboard() {
          ibRefreshWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          loadPathes();
     }

     /**
      * Show or hide penicl's settings panel
      */
     @Click void ibPencilSettings() {
          llBrushSize.setVisibility((llBrushSize.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
     }

     /**
      * Show or hide whiteboard panel
      */
     @Click void ibWhiteboardSettings() {
          llChangeTextSize.setVisibility((llChangeTextSize.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
     }

     /**
      * Show or hide colors panel
      */
     @Click void ibColorsPanel() {
          llChangeColour.setVisibility((llChangeColour.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
     }

     /**
      * Show or hide shapes panel
      */
     @Click void ibShowShapesPanel() {
          llDrawShapes.setVisibility((llDrawShapes.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
     }

     @Override public void onSaturationChanged(int saturation) {
          twSaturationBar.setText(saturation + "%");
     }

     @Override public void onOpacityChanged(int opacity) {
          twOpacityBar.setText(Math.abs(Math.round(opacity / 2.55) - 100) + "%");
     }

}
