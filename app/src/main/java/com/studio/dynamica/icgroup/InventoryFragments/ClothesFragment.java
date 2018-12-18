package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentMainAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.dynamica.icgroup.Adapters.MaterialAdapter;
import com.studio.dynamica.icgroup.Adapters.MaterialMainAdapter;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.EquipmentMainForm;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.MaterialMainForm;
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
public class ClothesFragment extends Fragment {
    RecyclerView recyclerView, reqRecycler;
    EquipmentAdapter adapter;
    EquipmentMainAdapter mainAdapter;
    EquipmentReqAdapter reqAdapter;
    List<EquipmentForm> forms;
    ConstraintLayout addLayout;
    LinearLayout createNewLayout;
    List<EquipmentMainForm> mainForms, allMainForms;
    String id="";
    List<String> reqs;
    FrameLayout progressLayout;
    boolean object;
    public ClothesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        object=getArguments().getBoolean("object",false);
        View view=inflater.inflate(R.layout.fragment_clothes, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(reqRecycler, LinearLayoutManager.HORIZONTAL);
        forms=new ArrayList<>();
        mainForms=new ArrayList<>();
        allMainForms=new ArrayList<>();
        adapter=new EquipmentAdapter(forms);
        mainAdapter=new EquipmentMainAdapter(mainForms);
        if(object) {
            addLayout.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(adapter);
        }
        else {
            addLayout.setVisibility(View.GONE);
            recyclerView.setAdapter(mainAdapter);
        }

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
        addLayout=(ConstraintLayout) view.findViewById(R.id.addLayout);
        createNewLayout=(LinearLayout) view.findViewById(R.id.createNewLayout);

        reqs=new ArrayList<>();
        reqs.add("Все позииции");
        reqs.add("На замену");
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL;
        if(object)
            url+="consumptions/?inventory__kind="+"CLOTHES&point="+id;
        else{
            url+="inventories/?kind="+"CLOTHES";
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
    private void setInfo(final JSONArray array){
        try{
            if(object) {
                forms.clear();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject inventory = object.getJSONObject("inventory");
                 //   JSONObject company = inventory.getJSONObject("company");
                    String name = inventory.getString("name"), id = object.getString("id"), unit = inventory.getJSONObject("unit").getString("name"), vendor_code = inventory.getString("vendor_code");
                    String num = object.getString("quantity") + " " + unit;
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
                createNewLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddNewMassFragment fragment=new AddNewMassFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("array",array.toString());
                        bundle.putString("kind","CLOTHES");
                        bundle.putString("point",id);
                        fragment.setArguments(bundle);
                        ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
                    }
                });
            }
            else{
                mainForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String vendor=object.getString("vendor_code");
                    String name=object.getString("name"), uni=object.getJSONObject("unit").getString("name"), id=object.getString("id");
                    int qua=object.getInt("quantity");
                    String unit=uni;
                    EquipmentMainForm mainForm=new EquipmentMainForm(id, name, unit, qua);
                    mainForm.setVendor(vendor);
                    allMainForms.add(mainForm);
                }
                reqAdapter.setClicked(0);
                mainForms.addAll(allMainForms);
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
        int page=reqAdapter.getClicked();
        mainForms.clear();
        switch (page){
            case 1:
                for(EquipmentMainForm form:allMainForms){
                    if(form.getReplace()>0){
                        mainForms.add(form);
                        Log.d("Replace","+1");
                    }
                }
                mainAdapter.setWhole("На замене:");
                break;
            default:
                mainForms.addAll(allMainForms);
                mainAdapter.setWhole("Общее количество:");
        }

        mainAdapter.notifyDataSetChanged();
    }
}
