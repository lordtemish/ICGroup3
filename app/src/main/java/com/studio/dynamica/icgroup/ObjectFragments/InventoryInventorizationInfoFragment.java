package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.InventoryStatusAdapter;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.Forms.InventoryStatusFactoryForm;
import com.studio.dynamica.icgroup.Forms.InventoryStatusForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryInventorizationInfoFragment extends Fragment {

    RecyclerView inventoryStatusRecyclerView;
    public InventoryInventorizationInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_inventorization_info, container, false);
        createViews(view);

        List<InventoryStatusForm> statusForms=new ArrayList<>();
        InventoryStatusForm statusForm=new InventoryStatusForm("Timur Syzdykov","OK05454545",4);
        statusForm.setStatus(false);
        CommentaryForm commentaryForm=new CommentaryForm("02.07.2017","Tima TIma",getActivity().getResources().getString(R.string.bigtext));
        List<CommentaryForm> commentaryForms=new ArrayList<CommentaryForm>();commentaryForms.add(commentaryForm);
        statusForm.setCommentaryForms(commentaryForms);
        List<RemontForms> remontForms=new ArrayList<>();remontForms.add(new RemontForms("Ремонт всего",5));
        statusForm.setRemontForms(remontForms);
        InventoryStatusForm ff=new InventoryStatusForm("Temirlan Almassov","OK04546464",5);
        ff.setStatus(false);
        statusForms.add(ff);
        statusForms.add(statusForm);
        statusForms.add(new InventoryStatusForm("Temirlan Almassov","OK04546464",5));
        statusForms.add(new InventoryStatusForm("Nadira Ryskulova","OK546406846",5));
        List<InventoryStatusFactoryForm> factoryForms=new ArrayList<>();
        factoryForms.add(new InventoryStatusFactoryForm("Оборудование",statusForms));factoryForms.add(new InventoryStatusFactoryForm("NAME",statusForms));factoryForms.add(new InventoryStatusFactoryForm("NAME",statusForms));
        InventoryStatusAdapter statusAdapter=new InventoryStatusAdapter(factoryForms);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(inventoryStatusRecyclerView, LinearLayoutManager.VERTICAL);
        inventoryStatusRecyclerView.setAdapter(statusAdapter);
        return view;
    }

    private void createViews(View view){
        inventoryStatusRecyclerView=(RecyclerView) view.findViewById(R.id.inventoryStatusReyclerView);
    }
}
