package com.alanesuhr.booleantoolbox;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lyndis on 10/3/14.
 */
public static class TabObjectFragment extends Fragment {
    public  static final String ARG_OBJECT = "object ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_object,container,false);
        Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt((ARG_OBJECT)));
        return  rootView;
    }
}
