package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasportObjectMainFragment extends Fragment {
    TextView Tittle;

    ConstraintLayout infoListLayout;
    ConstraintLayout scheduleWorkLayout;
    ConstraintLayout serviceLayout;

    FrameLayout infoListFrame;
    FrameLayout scheduleWorkFrame;
    FrameLayout serviceFrame;

    TextView infoListTextView;
    TextView scheduleWorkTextView;
    TextView servicesTextView;

    PassportObjectInfoListFragment infoListFragment;
    PassportObjectWorkScheduleFragment workScheduleFragment;
    PassportObjectServiceListFragment serviceListFragment;

    public PasportObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pasport_object_main, container, false);
        Tittle=(TextView) view.findViewById(R.id.mainObjectTitle);

        infoListLayout=(ConstraintLayout) view.findViewById(R.id.infoListLayout);
        infoListFrame=(FrameLayout) view.findViewById(R.id.infoListGreenFrameLayout);
        infoListTextView=(TextView) view.findViewById(R.id.infoListTextView);
        infoListTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        infoListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(0);
            }
        });
        scheduleWorkLayout=(ConstraintLayout) view.findViewById(R.id.scheduleWorkLayout);
        scheduleWorkFrame=(FrameLayout) view.findViewById(R.id.scheduleWorkGreenFrameLayout);
        scheduleWorkTextView=(TextView) view.findViewById(R.id.scheduleWorkTextView);
        scheduleWorkTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        scheduleWorkLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(1);
            }
        });
        serviceLayout=(ConstraintLayout) view.findViewById(R.id.servicesLayout);
        serviceFrame=(FrameLayout) view.findViewById(R.id.servicesFrameLayout);
        servicesTextView=(TextView) view.findViewById(R.id.servicesTextView);
        servicesTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        serviceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(2);
            }
        });

        infoListFragment=new PassportObjectInfoListFragment();
        workScheduleFragment=new PassportObjectWorkScheduleFragment();
        serviceListFragment=new PassportObjectServiceListFragment();

        setPage(0);
        return view;
    }

    public void clear(){
        infoListFrame.setVisibility(View.GONE);
        infoListTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        scheduleWorkFrame.setVisibility(View.GONE);
        scheduleWorkTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        serviceFrame.setVisibility(View.GONE);
        servicesTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
    }
    public void setPage(int a){
        clear();
        switch (a){
            case 0:
                infoListFrame.setVisibility(View.VISIBLE);
                infoListTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
                SetFragment(infoListFragment);
                break;
            case 1:
                scheduleWorkFrame.setVisibility(View.VISIBLE);
                scheduleWorkTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
                SetFragment(workScheduleFragment);
                break;
            case 2 :
                serviceFrame.setVisibility(View.VISIBLE);
                servicesTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
                SetFragment(serviceListFragment);
                break;
        }
    }

    public void SetFragment(Fragment fragment){
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.PassportObjectFrameLayout, fragment);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        t.commit();
    }
}
