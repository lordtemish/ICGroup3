package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.MainObjectAdapter;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectFragment extends Fragment {
    MainObjectAdapter mainObjectAdapter;
    RecyclerView mainObjectRecycle;
    ConstraintLayout RegionLayout;
    TextView RegionTextView;
    ConstraintLayout ObjectTypeLayout;
    TextView ObjectTypeTextView;

    TextView mainObjectTitle;
    public MainObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        mainObjectTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-It.ttf"));

        mainObjectRecycle=(RecyclerView) view.findViewById(R.id.mainObjectRecycle);
        ArrayList<MainObjectRowForm> list=new ArrayList<>();
        list.add(new MainObjectRowForm("125","ТРЦ Moskva metropoliten",55,4,25,21));
        list.add(new MainObjectRowForm("002","Mega SILKWAY",2,1,5,21));
        list.add(new MainObjectRowForm("022","Mega SILKWAY",100,2,15,2));
        list.add(new MainObjectRowForm("042","Mega SILKWAY",88,3,35,23));
        list.add(new MainObjectRowForm("005","Mega SILKWAY",14,4,15,2));
        mainObjectAdapter=new MainObjectAdapter(list,getActivity());
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        mainObjectRecycle.setLayoutManager(mLayoutManager);
        mainObjectRecycle.setItemAnimator(new DefaultItemAnimator());
        mainObjectRecycle.setAdapter(mainObjectAdapter);

        RegionLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRegionLayout);
        RegionTextView=(TextView) view.findViewById(R.id.mainObjectRegionTextView);
        RegionTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));

        ObjectTypeLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectTypeLayout);
        ObjectTypeTextView=(TextView) view.findViewById(R.id.mainObjectTypeTextView);
        ObjectTypeTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
        return view;
    }

}
