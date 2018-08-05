package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewCheckListFragment extends Fragment {

    RecyclerView recyclerView;
    public AddNewCheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_check_list, container, false);

        List<CheckListBoxForm> boxForms=new ArrayList<>();
        List<CheckListBoxRowForm> boxRowForms=new ArrayList<>();

        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));

        boxForms.add(new CheckListBoxForm("",true, boxRowForms));
        boxForms.add(new CheckListBoxForm("",false, boxRowForms));

        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);

        CheckListBoxAdapter adapter=new CheckListBoxAdapter(boxForms);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
