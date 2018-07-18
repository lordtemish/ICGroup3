package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Adapters.ServiceListAdapter;
import com.studio.dynamica.icgroup.Forms.ServiceListForm;
import com.studio.dynamica.icgroup.Forms.ServicePeriodForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectServiceListFragment extends Fragment {

    RecyclerView recyclerView;
    public PassportObjectServiceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_passport_object_service_list, container, false);

        recyclerView=(RecyclerView) view.findViewById(R.id.PassportObjectServiceListRecyclerView);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        List<ServicePeriodForm> periodFormList=new ArrayList<>();
        List<String> strings=new ArrayList<>();
        strings.add("Очистка асфальтированной территории и территории тратуарной плитки от бытового мусора");
        strings.add("Ежедневная мойка места сбора ТБО дезинфицирующими средствами");
        strings.add("Уборка грязи и пыли со сбором в мусоросборники(контейнеры) по месту производственных работ");
        strings.add("Регулярная очистка арыков при наличии в пределах территории");
        strings.add("Ежедневная мойка места сбора ТБО дезинфицирующими средствами");
        strings.add("Уборка грязи и пыли со сбором в мусоросборники(контейнеры) по месту производственных работ");
        strings.add("Регулярная очистка арыков при наличии в пределах территории");

        List<ServiceListForm> listForms=new ArrayList<>();
        periodFormList.add(new ServicePeriodForm("Весенне-летне-осенний период",strings));
        periodFormList.add(new ServicePeriodForm("Зимний период",strings));
        listForms.add(new ServiceListForm("Плановая текущая уборка объекта ежедневно",periodFormList));
        listForms.add(new ServiceListForm("Мойка ветражей (внутри снаружи) и фасада здания",periodFormList));
        ServiceListAdapter adapter=new ServiceListAdapter(listForms);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
