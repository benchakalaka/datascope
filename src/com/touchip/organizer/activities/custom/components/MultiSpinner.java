package com.touchip.organizer.activities.custom.components;

import quickutils.core.QUFactory.QLog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.touchip.organizer.communication.rest.model.ModelTaskList;

/**
 * Multi select spinner
 * 
 * @author Karpachev Ihor
 */
public class MultiSpinner extends Spinner {

     private static CharSequence[] entries;
     private static boolean[]      selected;
     private MultiSpinnerListener  listener;
     private static ModelTaskList  taskList;
     private static ModelTaskList  allTasks;

     public MultiSpinner ( Context context , AttributeSet attrs ) {
          super(context, attrs);

     }

     public static void setEntries(ModelTaskList tasks) {
          taskList = new ModelTaskList();
          if ( null != tasks ) {
               allTasks = tasks;
               entries = new String[tasks.size()];
               for ( int i = 0; i < tasks.size(); i++ ) {
                    entries[i] = tasks.get(i).description;
               }
               selected = new boolean[entries.length]; // false-filled by default
          }
     }

     private final OnMultiChoiceClickListener      mOnMultiChoiceClickListener = new OnMultiChoiceClickListener() {
                                                                                    @Override public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                                                         if ( null != selected ) {
                                                                                              selected[which] = isChecked;
                                                                                         }
                                                                                    }
                                                                               };

     private final DialogInterface.OnClickListener mOnClickListener            = new DialogInterface.OnClickListener() {
                                                                                    @Override public void onClick(DialogInterface dialog, int which) {
                                                                                         // build new spinner text & delimiter management
                                                                                         StringBuffer spinnerBuffer = new StringBuffer();
                                                                                         taskList.clear();
                                                                                         for ( int i = 0; i < entries.length; i++ ) {
                                                                                              if ( selected[i] ) {
                                                                                                   taskList.add(allTasks.get(i));
                                                                                                   spinnerBuffer.append(entries[i]);
                                                                                                   spinnerBuffer.append(", ");
                                                                                              }
                                                                                         }

                                                                                         // Remove trailing comma
                                                                                         if ( spinnerBuffer.length() > 2 ) {
                                                                                              spinnerBuffer.setLength(spinnerBuffer.length() - 2);
                                                                                         }

                                                                                         // display new text
                                                                                         ArrayAdapter <String> adapter = new ArrayAdapter <String>(getContext(), android.R.layout.simple_spinner_item, new String[] { spinnerBuffer.toString() });
                                                                                         setAdapter(adapter);

                                                                                         if ( listener != null ) {
                                                                                              listener.onItemsSelected(selected);
                                                                                         }

                                                                                         for ( int i = 0; i < taskList.size(); i++ ) {
                                                                                              QLog.debug(taskList.get(i).description);
                                                                                         }

                                                                                         // hide dialog
                                                                                         dialog.dismiss();
                                                                                    }
                                                                               };

     @Override public boolean performClick() {
          new AlertDialog.Builder(getContext()).setMultiChoiceItems(entries, selected, mOnMultiChoiceClickListener).setPositiveButton(android.R.string.ok, mOnClickListener).show();
          return true;
     }

     public void setMultiSpinnerListener(MultiSpinnerListener listener) {
          this.listener = listener;
     }

     public interface MultiSpinnerListener {
          public void onItemsSelected(boolean[] selected);
     }

     public static ModelTaskList getTaskList() {
          return taskList;
     }

     public static void setTaskList(ModelTaskList taskList) {
          MultiSpinner.taskList = taskList;
     }
}