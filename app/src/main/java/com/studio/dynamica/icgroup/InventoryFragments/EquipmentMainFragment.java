package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentMainFragment extends Fragment {
    RecyclerView recyclerView;
    EquipmentAdapter adapter;
    List<EquipmentForm> forms;
    public EquipmentMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_equipment_main, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        forms=new ArrayList<>();
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт",5));
        forms.add(new EquipmentForm("name","someid",4, remontForms,orderForms));forms.add(new EquipmentForm("name","someid",4,new ArrayList<RemontForms>(),orderForms));forms.add(new EquipmentForm("name","someid",4, remontForms, orderForms));forms.add(new EquipmentForm("name","someid",4));
         adapter=new EquipmentAdapter(forms);
         recyclerView.setAdapter(adapter);
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
    }

}
