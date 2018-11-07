package com.studio.dynamica.icgroup.ExtraFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Activities.MapActivity;
import com.studio.dynamica.icgroup.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_map, container, false);
        createVIews(view);
        //((MapActivity)getActivity()).setMapView(mapView);
        return view;
    }

    private void createVIews(View view){
    }


}
