package com.touchip.organizer.activities.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.timessquare.sample.R;

public class FragmentDrawing extends Fragment {

     @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          return inflater.inflate(R.layout.fragment_detail, null);
     }
}
