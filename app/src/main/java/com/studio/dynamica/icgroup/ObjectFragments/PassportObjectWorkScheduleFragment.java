package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Adapters.WorkScheduleAdapter;
import com.studio.dynamica.icgroup.Forms.WorkScheduleForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectWorkScheduleFragment extends Fragment {
    WorkScheduleAdapter adapter;
    RecyclerView recyclerView;
    public PassportObjectWorkScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_passport_object_work_schedule, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.PassportObjectSchRecycler);
        List<WorkScheduleForm> list=new ArrayList<>();
        list.add(new WorkScheduleForm("","","",""));
        list.add(new WorkScheduleForm("","","",""));
        WorkScheduleAdapter adapter=new WorkScheduleAdapter(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
