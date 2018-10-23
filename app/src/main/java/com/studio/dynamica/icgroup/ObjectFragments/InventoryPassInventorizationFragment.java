package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.PassInventorizationAdapter;
import com.studio.dynamica.icgroup.Forms.PassInventorizationForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryPassInventorizationFragment extends Fragment {
    RecyclerView passInventoryRecyclerView;
    public InventoryPassInventorizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_pass_inventorization, container, false);
        createView(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(passInventoryRecyclerView, LinearLayout.VERTICAL);
        List<PassInventorizationForm> inventorizationForms=new ArrayList<>();
        inventorizationForms.add(new PassInventorizationForm("Оборудование",true,78));
        inventorizationForms.add(new PassInventorizationForm("Имя цйу",false,78));
        inventorizationForms.add(new PassInventorizationForm("Название",false,0));
        inventorizationForms.add(new PassInventorizationForm("Название",true,0));
        inventorizationForms.add(new PassInventorizationForm("Название",true,0));
        inventorizationForms.add(new PassInventorizationForm("Название",true,0));
        PassInventorizationAdapter inventorizationAdapter=new PassInventorizationAdapter(inventorizationForms);
        passInventoryRecyclerView.setAdapter(inventorizationAdapter);
        return view;
    }
    private void createView(View view){
        passInventoryRecyclerView=(RecyclerView) view.findViewById(R.id.passInventoryRecyclerView);
    }
}
