package com.touchip.organizer.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.squareup.timessquare.sample.R;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard;
import com.touchip.organizer.activities.custom.components.ActionBarGeneralWhiteboard_;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView;
import com.touchip.organizer.activities.custom.components.WhiteBoardDrawingView.ShapesType;
import com.touchip.organizer.communication.rest.model.ModelPathId;
import com.touchip.organizer.communication.rest.model.PathsCreationTimeList.POJORoboPathCreationTime;
import com.touchip.organizer.communication.rest.request.SuperRequest;
import com.touchip.organizer.communication.rest.request.listener.ResponseGetDrawingPathByHotspotId;
import com.touchip.organizer.communication.rest.request.listener.SaveHSWhiteboardRequestListener;
import com.touchip.organizer.communication.rest.serializables.PathSerializable;
import com.touchip.organizer.constants.GlobalConstants;
import com.touchip.organizer.constants.HTTP_PARAMS;
import com.touchip.organizer.constants.RestAddresses;
import com.touchip.organizer.utils.Utils;
import com.touchip.organizer.utils.Utils.AnimationManager;
import com.touchip.organizer.utils.colorpicker.ColorPicker;
import com.touchip.organizer.utils.colorpicker.ColorPicker.OnColorChangedListener;
import com.touchip.organizer.utils.colorpicker.OpacityBar;
import com.touchip.organizer.utils.colorpicker.OpacityBar.OnOpacityChangedListener;
import com.touchip.organizer.utils.colorpicker.SaturationBar;
import com.touchip.organizer.utils.colorpicker.SaturationBar.OnSaturationChangedListener;

@EActivity ( R.layout.fragment_white_board ) public class GeneralWhiteBoardActivity extends SpiceFragmentActivity implements OnColorChangedListener , View.OnClickListener , OnSaturationChangedListener , OnOpacityChangedListener {
     private Dialog                                dialogChangeColour , dialogInputText;
     private AlertDialog.Builder                   builder;
     private ColorPicker                           picker;
     public static int                             WHITEBOARD_TYPE;
     private static TextView                       twSaturationBar , twOpacityBar;
     private int                                   colorToSet;

     public static GeneralWhiteBoardActivity       INSTANCE;

     // Load Views
     @ViewById public static WhiteBoardDrawingView WHITE_BOARD_DRAWING;

     @ViewById ImageButton                         ibRedo , ibUndo , ibThick , ibMedium , ibThin , ibEraser;
     @ViewById ImageButton                         ibColorPicker;
     @ViewById ImageButton                         ibShapesRectangle , ibShapesCircle , ibShapesLine , ibShapesTriangle , ibShapesFreeDrawing , ibDrawText;
     @ViewById ImageButton                         ibClearAll , ibLoadWhiteboard , ibSaveDrawing , ibCreateNewWhiteboard , ibRefreshWhiteboard;

     @ViewById ImageView                           ibColour1 , ibColour2 , ibColour3 , ibColour4 , ibColour5 , ibColour6 , ibColour7 , ibColour8 , ibColour9 , ibColour10 , ibColour11 , ibColour12 , ibColour13 , ibColour14 , ibColour15 , ibColour16 , ibColour17;
     @ViewById ImageView                           ivWhiteboardSettings , ivDrawingSettings , ivColours , ivShapes;

     // Layouts which contains all control button (for hide/show feature)
     @ViewById LinearLayout                        llBrushSize , llChangeTextSize , llDrawShapes;
     @ViewById HorizontalScrollView                svChangeColour;
     public ActionBarGeneralWhiteboard             customActionBar;

     OnTouchListener                               menuTouchListener = new OnTouchListener() {

                                                                          @Override public boolean onTouch(View v, MotionEvent event) {
                                                                               // Utils.setScaleFactor(v, ibColorPicker, ibColourBlack, ibColourBlue,
                                                                               // ibColourGreen, ibColourRed, ibColourRed, ibShapesCircle, ibShapesFreeDrawing,
                                                                               // ibShapesLine, ibShapesRectangle, ibShapesTriangle, ibThick, ibMedium, ibThin);
                                                                               return false;
                                                                          }
                                                                     };

     @AfterViews void afterViews() {
          INSTANCE = this;

          ibColour1.setOnClickListener(this);
          ibColour2.setOnClickListener(this);
          ibColour3.setOnClickListener(this);
          ibColour4.setOnClickListener(this);
          ibColour5.setOnClickListener(this);
          ibColour6.setOnClickListener(this);
          ibColour7.setOnClickListener(this);
          ibColour8.setOnClickListener(this);
          ibColour9.setOnClickListener(this);
          ibColour10.setOnClickListener(this);
          ibColour11.setOnClickListener(this);
          ibColour12.setOnClickListener(this);
          ibColour13.setOnClickListener(this);
          ibColour14.setOnClickListener(this);
          ibColour15.setOnClickListener(this);
          ibColour16.setOnClickListener(this);
          ibColour17.setOnClickListener(this);

          dialogChangeColour = new Dialog(GeneralWhiteBoardActivity.this);
          dialogInputText = new Dialog(GeneralWhiteBoardActivity.this);
          new Dialog(GeneralWhiteBoardActivity.this);
          builder = new AlertDialog.Builder(this);

          dialogChangeColour.setContentView(R.layout.dialog_color_picker);
          dialogInputText.setContentView(R.layout.dialog_input_text_for_drawing);
          dialogChangeColour.setTitle(R.string.choose_color_saturation_opacity);
          final EditText ed = (EditText) dialogInputText.findViewById(R.id.dialog_edittext_input_text_to_draw);
          ed.setLines(1);

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
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.new_color_has_been_set, R.drawable.color_picker);
                    dialogChangeColour.hide();
               }
          });

          ((Button) dialogChangeColour.findViewById(R.id.buttonCancel)).setOnClickListener(new OnClickListener() {

               @Override public void onClick(View v) {
                    dialogChangeColour.hide();
               }
          });

          customActionBar = ActionBarGeneralWhiteboard_.build(GeneralWhiteBoardActivity.this);

          Utils.configureCustomActionBar(getActionBar(), customActionBar);

          getActionBar().setIcon(R.drawable.menu);

          dialogInputText.setTitle(R.string.text_will_be_drawn_on_canvas);
          Button ok = (Button) dialogInputText.findViewById(R.id.dialog_button_ok);
          Button cancel = (Button) dialogInputText.findViewById(R.id.dialog_button_cancel);

          ok.setOnClickListener(this);
          cancel.setOnClickListener(this);

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

          ibShapesFreeDrawing.setBackgroundResource(R.drawable.background_view_rounded_single);
          ibThick.setBackgroundResource(R.drawable.background_view_rounded_single);

          WHITE_BOARD_DRAWING.setColor(Color.BLACK);
          WHITE_BOARD_DRAWING.setBrushSize(5);
     }

     public void loadPaths() {
          // if it's hotspot white board
          if ( WHITEBOARD_TYPE == GlobalConstants.HWD ) {
               HashMap <String, String> httpParams = new HashMap <String, String>();
               httpParams.put(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
               SuperRequest <ModelPathId> request = new SuperRequest <ModelPathId>(ModelPathId.class, RestAddresses.GET_DRAWING_PATH_BY_HOTSPOT_ID, null, httpParams);
               getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponseGetDrawingPathByHotspotId(this));
          } else {
               // else it's general whiteboard

          }
     }

     @Override public boolean onOptionsItemSelected(MenuItem item) {
          switch (item.getItemId()) {
               case android.R.id.home:

                    if ( WHITEBOARD_TYPE == GlobalConstants.HWD ) {
                         saveWhiteBoard(null);
                         return super.onOptionsItemSelected(item);
                    }

                    final EditText input = new EditText(this);
                    builder.setView(input);
                    builder.setTitle(getResources().getString(R.string.changes_hasnt_been_saved));
                    builder.setMessage(getResources().getString(R.string.you_will_loose_whiteboard)).setIcon(R.drawable.whiteboard);
                    builder.setPositiveButton(getResources().getString(R.string.save), new DialogInterface.OnClickListener() {
                         @Override public void onClick(DialogInterface dialog, int which) {
                              saveWhiteBoard(input.getText().toString());
                         }
                    });
                    builder.setNegativeButton(Utils.getResources(R.string.proceed_anyway), new DialogInterface.OnClickListener() {
                         @Override public void onClick(DialogInterface dialog, int which) {
                              WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.STANDART_DRAWING);
                              onBackPressed();
                         }
                    });
                    builder.create().show();
                    return true;
               default:
                    return super.onOptionsItemSelected(item);
          }
     }

     public void saveWhiteBoard(String whiteBoardName) {
          try {

               SuperRequest <POJORoboPathCreationTime> request = null;

               HttpHeaders headers = new HttpHeaders();
               headers.setContentType(MediaType.MULTIPART_FORM_DATA);

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
               params.add(HTTP_PARAMS.DRAWING_DATA, fileSystemResources);

               // attempt to save drawing
               try {
                    WHITE_BOARD_DRAWING.setDrawingCacheEnabled(true);
                    String saveResponseFilePath = Media.insertImage(getContentResolver(), WHITE_BOARD_DRAWING.getDrawingCache(), Utils.getCurrentDate() + ".png", "drawing");
                    // Preparing feedback for user
                    boolean isSuccessfullySaved = saveResponseFilePath != null;
                    String userFeedback = (isSuccessfullySaved) ? Utils.getResources(R.string.drawing_saved_to_gallery) : Utils.getResources(R.string.cannot_save_image);
                    if ( isSuccessfullySaved ) {
                         FileSystemResource snapshotSpringWrapper = new FileSystemResource(new File(Utils.getRealPathFromURI(getApplicationContext(), Uri.parse(saveResponseFilePath))));
                         params.add(HTTP_PARAMS.SNAPSHOT, snapshotSpringWrapper);
                    }
                    WHITE_BOARD_DRAWING.destroyDrawingCache();
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, userFeedback, R.drawable.save);
               } catch (Exception ex) {
                    ex.printStackTrace();
               }

               // Hotspot white board
               if ( GlobalConstants.HWD == WHITEBOARD_TYPE ) {
                    if ( null != GlobalConstants.LAST_CLICKED_HOTSPOT ) {
                         params.add(HTTP_PARAMS.HOTSPOT_ID, String.valueOf(GlobalConstants.LAST_CLICKED_HOTSPOT.id));
                    }
                    request = new SuperRequest <POJORoboPathCreationTime>(POJORoboPathCreationTime.class, RestAddresses.CREATE_HOTSPOT_DRAWING_PATHS, new FormHttpMessageConverter(), params, headers);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveHSWhiteboardRequestListener(this));
                    return;
               }

               if ( GlobalConstants.LAST_CLICKED_WHITE_BOARD != null && GlobalConstants.LAST_CLICKED_WHITE_BOARD.id != 0 ) {
                    params.add(HTTP_PARAMS.PATH_ID, String.valueOf(GlobalConstants.LAST_CLICKED_WHITE_BOARD.id));
               } else {
                    // send WB name
                    if ( null != whiteBoardName ) {
                         params.add(HTTP_PARAMS.NAME, whiteBoardName);
                    }
                    params.add(HTTP_PARAMS.SITE_ID, GlobalConstants.SITE_ID);
                    params.add(HTTP_PARAMS.DATE, GlobalConstants.SITE_PLAN_IMAGE_NAME);
                    params.add(HTTP_PARAMS.AREA_NAME, GlobalConstants.SITE_PLAN_FULL_INFO.currentArea);
               }

               // we have pathid, so instead of sending all date, just send pathid and new drawingdata
               if ( GlobalConstants.LAST_CLICKED_WHITE_BOARD != null && GlobalConstants.LAST_CLICKED_WHITE_BOARD.id != 0 ) {
                    request = new SuperRequest <POJORoboPathCreationTime>(POJORoboPathCreationTime.class, RestAddresses.UPDATE_DRAWING_PATHES, new FormHttpMessageConverter(), params, headers);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveHSWhiteboardRequestListener(this));
               } else {
                    request = new SuperRequest <POJORoboPathCreationTime>(POJORoboPathCreationTime.class, RestAddresses.CREATE_GENERAL_WHITE_BOARD, new FormHttpMessageConverter(), params, headers);
                    getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new SaveHSWhiteboardRequestListener(this));
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
                    ed.setLines(1);

                    if ( "".equals(ed.getText().toString()) ) {
                         Utils.showToast(getApplicationContext(), R.string.field_cannot_be_empty, false);
                    } else {
                         WHITE_BOARD_DRAWING.setDrawingShape(ShapesType.DRAW_TEXT);
                         WHITE_BOARD_DRAWING.setTextToDraw(ed.getText().toString().replace("\n", " "));
                         ed.setText("");
                         dialogInputText.dismiss();
                    }
                    break;
               // Draw text dialog - CANCEL button
               case R.id.dialog_button_cancel:
                    dialogInputText.dismiss();
                    break;
               default:
                    ibColour1.setBackgroundResource(android.R.color.transparent);
                    ibColour2.setBackgroundResource(android.R.color.transparent);
                    ibColour3.setBackgroundResource(android.R.color.transparent);
                    ibColour4.setBackgroundResource(android.R.color.transparent);
                    ibColour5.setBackgroundResource(android.R.color.transparent);
                    ibColour6.setBackgroundResource(android.R.color.transparent);
                    ibColour7.setBackgroundResource(android.R.color.transparent);
                    ibColour8.setBackgroundResource(android.R.color.transparent);
                    ibColour9.setBackgroundResource(android.R.color.transparent);
                    ibColour10.setBackgroundResource(android.R.color.transparent);
                    ibColour11.setBackgroundResource(android.R.color.transparent);
                    ibColour12.setBackgroundResource(android.R.color.transparent);
                    ibColour13.setBackgroundResource(android.R.color.transparent);
                    ibColour14.setBackgroundResource(android.R.color.transparent);
                    ibColour15.setBackgroundResource(android.R.color.transparent);
                    ibColour16.setBackgroundResource(android.R.color.transparent);
                    ibColour17.setBackgroundResource(android.R.color.transparent);
                    view.setBackgroundResource(R.drawable.transparent_inside_and_white_round_border);
                    // set bg color of note
                    int color = Color.parseColor(view.getTag().toString());
                    WHITE_BOARD_DRAWING.disableEraserMode();
                    WHITE_BOARD_DRAWING.setColor(color);
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.colour_has_been_changed, R.drawable.paint_pressed);

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
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.text_mode_is_active, R.drawable.text);
          dialogInputText.show();
     }

     @Click void ibColorPicker() {
          ibColorPicker.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.choose_custom_color, R.drawable.color_picker);
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
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.whiteboard_clear, R.drawable.clear);
                    dialog.dismiss();
               }
          });
          builder.setNegativeButton(Utils.getResources(R.string.no), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });
          builder.create().show();
     }

     @Click void ibEraser() {
          ibEraser.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setEraserMode();
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.erase_mode_is_active, R.drawable.eraser);
     }

     @Click void ibThin() {
          ibThin.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(15);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.thick_brush, R.drawable.brush);
     }

     @Click void ibMedium() {
          ibMedium.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(10);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.medium_brush_size, R.drawable.brush);
     }

     @Click void ibShapesRectangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesRectangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.RECTANGLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.draw_rectangle, R.drawable.rectangle);
     }

     @Click void ibThick() {
          ibThick.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setBrushSize(5);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.thin_brush_size, R.drawable.brush);
     }

     @Click void ibShapesCircle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesCircle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.CIRCLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.draw_circle, R.drawable.circle);
     }

     @Click void ibShapesLine() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesLine.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.LINE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.draw_line, R.drawable.line);
     }

     @Click void ibShapesTriangle() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesTriangle.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.TRIANGLE);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.draw_triangle, R.drawable.triangle);
     }

     @Click void ibShapesFreeDrawing() {
          WHITE_BOARD_DRAWING.disableEraserMode();
          ibShapesFreeDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          WHITE_BOARD_DRAWING.setDrawingShape(WhiteBoardDrawingView.ShapesType.STANDART_DRAWING);
          Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.free_drawing, R.drawable.free_drawing);
     }

     @Click void ibSaveDrawing() {
          ibSaveDrawing.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
          saveWhiteBoard(null);
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
          if ( WHITEBOARD_TYPE == GlobalConstants.HWD ) {
               Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Disabled (whiteboard hotspot)", R.drawable.whiteboard);
               return;
          }
          // GetPathsCreationTimeRequest request = new GetPathsCreationTimeRequest();
          // getSpiceManager().execute(request, request.createCacheKey(), DurationInMillis.ALWAYS_EXPIRED, new ResponsePathsCreationTime(this));
     }

     @Click void ibCreateNewWhiteboard() {
          if ( WHITEBOARD_TYPE == GlobalConstants.HWD ) {
               Utils.showCustomToast(GeneralWhiteBoardActivity.this, "Disabled (whiteboard hotspot)", R.drawable.whiteboard);
               return;
          }

          builder.setTitle(R.string.create_whiteboard);
          builder.setMessage(R.string.everything_will_be_cleared);
          builder.setIcon(R.drawable.clear);
          builder.setPositiveButton(Utils.getResources(R.string.yes), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    ibCreateNewWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
                    WHITE_BOARD_DRAWING.clearAllPaths();
                    Utils.showCustomToast(GeneralWhiteBoardActivity.this, R.string.new_whiteboard_created, R.drawable.add);

                    customActionBar.setTimeCreatedText(GlobalConstants.SITE_PLAN_IMAGE_NAME + " not saved");
                    dialog.dismiss();
               }
          });
          builder.setNegativeButton(Utils.getResources(R.string.no), new DialogInterface.OnClickListener() {
               @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
               }
          });
          AlertDialog alert = builder.create();
          alert.show();
     }

     @Click void ibRefreshWhiteboard() {
          ibRefreshWhiteboard.startAnimation(AnimationManager.load(R.anim.pump_bottom, 500));
     }

     /**
      * Show or hide penicl's settings panel
      */
     @Click void ivDrawingSettings() {
          llBrushSize.setVisibility((llBrushSize.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);

          svChangeColour.setVisibility(View.GONE);
          llChangeTextSize.setVisibility(View.GONE);
          llDrawShapes.setVisibility(View.GONE);
     }

     /**
      * Show or hide whiteboard panel
      */
     @Click void ivWhiteboardSettings() {
          llChangeTextSize.setVisibility((llChangeTextSize.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);

          svChangeColour.setVisibility(View.GONE);
          llBrushSize.setVisibility(View.GONE);
          llDrawShapes.setVisibility(View.GONE);
     }

     /**
      * Show or hide colors panel
      */
     @Click void ivColours() {
          svChangeColour.setVisibility((svChangeColour.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);

          llChangeTextSize.setVisibility(View.GONE);
          llBrushSize.setVisibility(View.GONE);
          llDrawShapes.setVisibility(View.GONE);
     }

     /**
      * Show or hide shapes panel
      */
     @Click void ivShapes() {
          llDrawShapes.setVisibility((llDrawShapes.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);

          llChangeTextSize.setVisibility(View.GONE);
          llBrushSize.setVisibility(View.GONE);
          svChangeColour.setVisibility(View.GONE);
     }

     @Override public void onSaturationChanged(int saturation) {
          twSaturationBar.setText(saturation + "%");
     }

     @Override public void onOpacityChanged(int opacity) {
          twOpacityBar.setText(Math.abs(Math.round(opacity / 2.55) - 100) + "%");
     }

}
