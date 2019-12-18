package com.studio.crm.icgroup.MainFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.DrawerAdapter;
import com.studio.crm.icgroup.Forms.RowFormPTN;
import com.studio.crm.icgroup.R;

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
        String role="SUPERADMIN";
        public DrawerFragment() {
            // Required empty public constructor
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role=((MainActivity)getActivity()).role;
        View view=inflater.inflate(R.layout.fragment_drawer, container, false);;
        drawerRV=(RecyclerView) view.findViewById(R.id.drawerRecycle);
        wholeLayout=(FrameLayout) view.findViewById(R.id.wholeLayout);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        drawerRV.setLayoutManager(mLayoutManager);
        drawerRV.setItemAnimator(new DefaultItemAnimator());
        rowFormPTNS=new ArrayList<>();
        formPTNS=new ArrayList<>();
        clientRowFormPTNS=new ArrayList<>();
        setData();
        formPTNS.addAll(rowFormPTNS);
        drawerAdapter=new DrawerAdapter(formPTNS,getActivity());
        drawerRV.setAdapter(drawerAdapter);

        return view;
    }

    public void setRole(String role) {
        this.role = role;
        drawerAdapter.setRole(role);
        setData();
        drawerAdapter.notifyDataSetChanged();
    }
    private void setData(){
        Log.d("THISROLE",role);
        clientRowFormPTNS.clear();
        rowFormPTNS.clear();
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_box,R.drawable.ic_box_u,"Объекты",-1));
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_solvves,R.drawable.ic_solvves_u,"Доп. работы",-1));
        clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_bell,R.drawable.ic_bell_u,"Активности",-1));
      //  clientRowFormPTNS.add(new RowFormPTN(R.drawable.ic_settings,R.drawable.ic_settings_u,"Настройки",-1));
        if (true )
            rowFormPTNS.add(new RowFormPTN(R.drawable.ic_box, R.drawable.ic_box_u, "Объекты", -1));
        if(true)
            rowFormPTNS.add(new RowFormPTN(R.drawable.ic_solvves, R.drawable.ic_solvves_u, "Доп. работы", -1));
        if(role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("CHIEF") || role.contains("PRODUCTION_NPO"))
            rowFormPTNS.add(new RowFormPTN(R.drawable.ic_people, R.drawable.ic_people_u, "Сотрудники", -1));
        if(role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.equals("PRODUCTION_CHIEF"))
            rowFormPTNS.add(new RowFormPTN(R.drawable.ic_clients, R.drawable.ic_clients_u, "Клиенты", -1));
       /* if(role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("SUPPLY"))
            rowFormPTNS.add(new RowFormPTN(R.drawable.ic_inventory, R.drawable.ic_inventory_u, "Инвентарь", -1));*/
    }
    public void setClient(boolean a){
        drawerAdapter.setClient(a);
         formPTNS.clear();
        if(a){
            formPTNS.addAll(clientRowFormPTNS);
            wholeLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        }
        else{
            formPTNS.addAll(rowFormPTNS);
            wholeLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        }
        drawerAdapter.notifyDataSetChanged();
     }
     public  void dataChanged(){
        drawerAdapter.notifyDataSetChanged();
     }
}
