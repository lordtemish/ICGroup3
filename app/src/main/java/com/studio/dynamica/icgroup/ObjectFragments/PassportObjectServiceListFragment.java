package com.studio.dynamica.icgroup.ObjectFragments;


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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ServiceListAdapter;
import com.studio.dynamica.icgroup.Forms.ServiceListForm;
import com.studio.dynamica.icgroup.Forms.ServicePeriodForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectServiceListFragment extends Fragment {

    List<ServiceListForm> listForms;
    FrameLayout progressLayout;
    RecyclerView recyclerView;
    ServiceListAdapter adapter;
    String id;
    public PassportObjectServiceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_passport_object_service_list, container, false);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
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

        listForms=new ArrayList<>();
        periodFormList.add(new ServicePeriodForm("Весенне-летне-осенний период",strings));
        periodFormList.add(new ServicePeriodForm("Зимний период",strings));
        listForms.add(new ServiceListForm("Плановая текущая уборка объекта ежедневно",periodFormList));
        listForms.add(new ServiceListForm("Мойка ветражей (внутри снаружи) и фасада здания",periodFormList));
        adapter=new ServiceListAdapter(listForms);
        recyclerView.setAdapter(adapter);
        id=getArguments().getString("id");
            getRequest("servicegroups/?point="+id);
        return view;
    }

    private void getRequest(String url){
        progressLayout.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;

        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    setInfo(response);
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        ((MainActivity)getActivity()).requestQueue.add(request);
    }
    private void setInfo(JSONArray array) throws JSONException{
        progressLayout.setVisibility(View.GONE);
        listForms.clear();
        for(int i=0;i<array.length();i++){
            List<ServicePeriodForm> periodForms=new ArrayList<>();
            JSONObject object=array.getJSONObject(i);
            JSONArray services=object.getJSONArray("services");
            for(int j=0;j<services.length();j++){
                List<String> clauses=new ArrayList<>();
                JSONObject service=services.getJSONObject(j);
                JSONArray rray=service.getJSONArray("clauses");
                for(int k=0;k<rray.length();k++){
                    clauses.add(rray.getString(k));
                }
                String name=service.getString("name");
                periodForms.add(new ServicePeriodForm(name,clauses));
                Log.d("  asdsadasd",name+" "+periodForms);
            }
            listForms.add(new ServiceListForm(object.getString("name"),periodForms));
        }
        adapter.notifyDataSetChanged();
    }
}
