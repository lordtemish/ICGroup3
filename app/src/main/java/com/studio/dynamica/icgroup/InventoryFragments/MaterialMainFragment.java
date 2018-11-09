package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MaterialAdapter;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialMainFragment extends Fragment {
    RecyclerView recyclerView;
    MaterialAdapter adapter;
    List<MaterialForm> forms;
    public MaterialMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_material_main, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        forms.add(new MaterialForm("","",2,5));forms.add(new MaterialForm("","",2,5, orderForms));forms.add(new MaterialForm("","",2,5, orderForms));
         adapter=new MaterialAdapter(forms);
         recyclerView.setAdapter(adapter);
        return view;
    }
    private void createViews(View view){
        forms=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
    }
}
