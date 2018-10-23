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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.DrawerAdapter;
import com.studio.dynamica.icgroup.Forms.RowFormPTN;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends Fragment {
    RecyclerView drawerRV;
    TextView nameText;
    TextView positionText;
    ArrayList<RowFormPTN> rowFormPTNS;
    ArrayList<RowFormPTN> formPTNS;
    ArrayList<RowFormPTN> clientRowFormPTNS;
    DrawerAdapter  drawerAdapter;
    FrameLayout wholeLayout;
    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_drawer, container, false);;
        drawerRV=(RecyclerView) view.findViewById(R.id.drawerRecycle);
        wholeLayout=(FrameLayout) view.findViewById(R.id.wholeLayout);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        drawerRV.setLayoutManager(mLayoutManager);
        drawerRV.setItemAnimator(new DefaultItemAnimator());
        rowFormPTNS=new ArrayList<>();
        formPTNS=new ArrayList<>();
        clientRowFormPTNS=new ArrayList<>();
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_box_c,R.drawable.ic_box_c,"Объекты",-1));
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_solvves_c,R.drawable.ic_solvves_c,"Задачи",-1));
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_bell_c,R.drawable.ic_bell_c,"Уведомления",22));
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_settings_c,R.drawable.ic_settings_c,"Настройки",-1));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_box,R.drawable.ic_box_u,"Объекты",34));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_solvves,R.drawable.ic_solvves_u,"Задачи",47));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_people,R.drawable.ic_people_u,"Сотрудники",-1));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_clients,R.drawable.ic_clients_u,"Клиенты",-1));
        rowFormPTNS.add(new RowFormPTN(R.drawable.ic_inventory,R.drawable.ic_inventory_u,"Инвентарь",47));
        formPTNS.addAll(rowFormPTNS);
        drawerAdapter=new DrawerAdapter(formPTNS,getActivity());
        drawerRV.setAdapter(drawerAdapter);

        return view;
    }
     public void setClient(boolean a){
        drawerAdapter.setClient(a);
         formPTNS.clear();
        if(a){
            formPTNS.addAll(clientRowFormPTNS);
            wholeLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.icgGreen));
        }
        else{
            formPTNS.addAll(rowFormPTNS);
            wholeLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        }
        drawerAdapter.notifyDataSetChanged();
     }
}
