package com.studio.crm.icgroup.StartFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.crm.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartPage extends Fragment {


    public StartPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_start_page, container, false);
        TextView corp=(TextView)view.findViewById(R.id.corp);
        corp.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-UltLt.ttf"));
        TextView welc=(TextView) view.findViewById(R.id.welc);
        welc.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir-light.ttf"));
        TextView icg=(TextView) view.findViewById(R.id.icg);
        icg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
        return view;
    }

}
