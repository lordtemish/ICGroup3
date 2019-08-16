package com.studio.crm.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.crm.icgroup.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PasportObjectMainFragment extends Fragment {
    TextView Tittle;
    RecyclerView recyclerView;
    EquipmentReqAdapter reqAdapter;
    View.OnClickListener listener;
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

    String id;

    public PasportObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pasport_object_main, container, false);
        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.HORIZONTAL);
        List<String> strings=new ArrayList<>();
        reqAdapter=new EquipmentReqAdapter(strings);strings.add("Информационный лист");strings.add("График работы");strings.add("Перечень услуг");
        listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPage(reqAdapter.getClicked());
            }
        };
        reqAdapter.setOnClickListener(listener);
        recyclerView.setAdapter(reqAdapter);

        infoListFragment=new PassportObjectInfoListFragment();
        workScheduleFragment=new PassportObjectWorkScheduleFragment();
        serviceListFragment=new PassportObjectServiceListFragment();

        Tittle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));

        setPage(0);
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        Tittle=(TextView) view.findViewById(R.id.mainObjectTitle);
        id=getArguments().getString("id");
    }
    private void setInfo(JSONObject object){

    }




    public void clear(){
    }
    public void setPage(int a){
        switch (a){
            case 0:
                SetFragment(infoListFragment);
                break;
            case 1:
                SetFragment(workScheduleFragment);
                break;
            case 2 :
                SetFragment(serviceListFragment);
                break;
        }
    }

    public void SetFragment(Fragment fragment){
        Bundle bundle=getArguments();
        fragment.setArguments(bundle);
        FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
        t.replace(R.id.PassportObjectFrameLayout, fragment);
        t.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        t.commit();
    }
}
