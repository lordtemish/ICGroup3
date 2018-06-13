package com.studio.dynamica.icgroup.MainFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.DrawerAdapter;
import com.studio.dynamica.icgroup.Forms.RowFormPTN;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {
    RecyclerView drawerRV;
    TextView nameText;
    TextView positionText;
    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_drawer, container, false);;
        drawerRV=(RecyclerView) view.findViewById(R.id.drawerRecycle);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        drawerRV.setLayoutManager(mLayoutManager);
        drawerRV.setItemAnimator(new DefaultItemAnimator());
        ArrayList<RowFormPTN> rowFormPTNS=new ArrayList<>();
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_box,"Объекты",34));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_solvves,"Задачи",47));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_people,"Сотрудники",-1));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_clients,"Клиенты",-1));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_inventory,"Инвентарь",47));
        drawerRV.setAdapter(new DrawerAdapter(rowFormPTNS,getActivity()));

        return view;
    }

}
