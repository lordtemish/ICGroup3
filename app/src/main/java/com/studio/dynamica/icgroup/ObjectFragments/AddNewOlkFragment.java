package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewOlkFragment extends Fragment {
    RecyclerView setAcceptRecyclerView;

    public AddNewOlkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_olk, container, false);

        setAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.setAcceptRecyclerView);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(setAcceptRecyclerView, LinearLayoutManager.VERTICAL);
        List<ChooseAcceptForm> acceptForms=new ArrayList<>();acceptForms.add(new ChooseAcceptForm("","",""));acceptForms.add(new ChooseAcceptForm("","",""));
        acceptForms.add(new ChooseAcceptForm("","",""));
        acceptForms.add(new ChooseAcceptForm("","",""));
        ChooseAcceptAdapter adapter=new ChooseAcceptAdapter(acceptForms);
        setAcceptRecyclerView.setAdapter(adapter);

        return view;
    }

}
