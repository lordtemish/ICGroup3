package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.InventorizationPassingSetAdapter;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.Forms.InventorizationPassingSetForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryPassInventorizationSetFragment extends Fragment {
    RecyclerView recyclerView;

    public InventoryPassInventorizationSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_pass_inventorization_set, container, false);
        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        List<InventorizationPassingSetForm> setForms=new ArrayList<>();
        setForms.add(new InventorizationPassingSetForm("","",2));setForms.add(new InventorizationPassingSetForm("","",2));setForms.add(new InventorizationPassingSetForm("","",2));
        setForms.add(new InventorizationPassingSetForm("","",2,5,3));
        InventorizationPassingSetAdapter setAdapter=new InventorizationPassingSetAdapter(setForms);
        recyclerView.setAdapter(setAdapter);
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
    }
}
