package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CityRadioAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentMainAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.EquipmentMainForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFrament extends Fragment {
    RecyclerView recyclerView, reqRecycler;
    EquipmentAdapter adapter;
    EquipmentMainAdapter mainAdapter;
    EquipmentReqAdapter reqAdapter;
    List<EquipmentForm> forms;
    List<EquipmentMainForm> mainForms, allMainForms;
    String id="";
    List<String> reqs;
    FrameLayout progressLayout;
    boolean object;
    public InventoryFrament() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        object=getArguments().getBoolean("object",false);
        View view=inflater.inflate(R.layout.fragment_inventory_frament, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(reqRecycler, LinearLayoutManager.HORIZONTAL);
        forms=new ArrayList<>();
        mainForms=new ArrayList<>();
        allMainForms=new ArrayList<>();
        adapter=new EquipmentAdapter(forms);
        mainAdapter=new EquipmentMainAdapter(mainForms);
        if(object)
            recyclerView.setAdapter(adapter);
        else
            recyclerView.setAdapter(mainAdapter);

        reqAdapter=new EquipmentReqAdapter(reqs);
        reqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage();
            }
        });
        reqRecycler.setAdapter(reqAdapter);
        if(!object){
            reqRecycler.setVisibility(View.VISIBLE);
        }
        getRequest();
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        reqRecycler=(RecyclerView) view.findViewById(R.id.reqRecycler);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);

        reqs=new ArrayList<>();
        reqs.add("Все позииции");
        reqs.add("На замену");
        reqs.add("На ремонте");
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL;
        if(object)
            url+="consumptions/?inventory__kind="+"INVENTORY&point="+id;
        else{
            url+="inventories/?kind="+"INVENTORY";
        }
        Log.d("urlEq", url);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                error.printStackTrace();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONArray array){
        try{
            if(object) {
                forms.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject inventory = object.getJSONObject("inventory");
                    JSONObject company = inventory.getJSONObject("company");
                    String name = inventory.getString("name"), id = object.getString("id"), unit = inventory.getString("unit"), vendor_code = inventory.getString("vendor_code");
                    String num = object.getString("quantity") + " " + ((MainActivity) getActivity()).inventoryUnits.get(unit);
                    int repair = object.getInt("repair"), replace = object.getInt("replace");
                    List<RemontForms> remontForms = new ArrayList<>();
                    if (repair > 0) {
                        RemontForms form = new RemontForms("На ремонте", repair);
                        remontForms.add(form);
                    }
                    if (replace > 0) {
                        RemontForms form = new RemontForms("На замене", replace);
                        remontForms.add(form);
                    }
                    EquipmentForm equipmentForm = new EquipmentForm(name, id, num, remontForms, new ArrayList<OrderForm>());
                    equipmentForm.setVendor_code(vendor_code);
                    forms.add(equipmentForm);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                mainForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String vendor=object.getString("vendor_code");
                    String name=object.getString("name"), uni=object.getString("unit"), id=object.getString("id");
                    int qua=object.getInt("quantity");
                    String unit=((MainActivity)getActivity()).inventoryUnits.get(uni);
                    EquipmentMainForm mainForm=new EquipmentMainForm(id, name, unit, qua);

                    mainForm.setVendor(vendor);
                    allMainForms.add(mainForm);
                    mainForms.add(mainForm);
                }
                mainAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            progressLayout.setVisibility(View.GONE);
        }
    }
    private void checkPage(){
        Log.d("page",reqAdapter.getClicked()+" clicked");
    }
}
/*
package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CityRadioAdapter;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 public class InventoryFrament extends Fragment {
    RecyclerView pointRecycler, recyclerView;
    CityRadioAdapter radioAdapter;
    List<String> points, ptids;
    int point;
    String ptid;
    ImageView arrowImageView;
    TextView pointTextView;
    FrameLayout progressFrame;
    LinearLayout arrowLayout;
    public InventoryFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_frament, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(pointRecycler, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        radioAdapter=new CityRadioAdapter(points);
        pointRecycler.setAdapter(radioAdapter);
        arrowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrClick();
            }
        });
        getPoints();
        return view;
    }
    private void arrClick(){
        if(pointRecycler.getVisibility()==View.VISIBLE){
            pointRecycler.setVisibility(View.GONE);
            arrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
        }else{
            pointRecycler.setVisibility(View.VISIBLE);
            arrowImageView.setImageResource(R.drawable.ic_arrowup_green);
        }
    }
    private void createViews(View view){
        pointRecycler=(RecyclerView)view.findViewById(R.id.pointRecycler);
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);
        pointTextView=(TextView) view.findViewById(R.id.pointTextView);
        arrowImageView=(ImageView)view.findViewById(R.id.arrowImageView);
        arrowLayout=(LinearLayout)view.findViewById(R.id.arrowLayout);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);

        points=new ArrayList<>();
        ptids=new ArrayList<>();
    }
    private void getPoints(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"points/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressFrame.setVisibility(View.GONE);
                setPoints(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setPoints(JSONArray array){
        try {
            points.clear();
            ptids.clear();
            List<View.OnClickListener> listeners=new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String name=object.getString("name");
                final String id=object.getString("id");
                points.add(name);ptids.add(id);
                listeners.add(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setPtid(id);
                    }
                });
            }
            if(ptids.size()>0){
                setPtid(ptids.get(0));
            }
            radioAdapter.setListeners(listeners);
            radioAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPtid(String ptid) {
        this.ptid = ptid;
        pointTextView.setText(points.get(ptids.indexOf(ptid)));
    }
}

 */