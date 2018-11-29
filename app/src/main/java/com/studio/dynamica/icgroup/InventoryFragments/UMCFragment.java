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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MaterialAdapter;
import com.studio.dynamica.icgroup.Adapters.MaterialMainAdapter;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.MaterialMainForm;
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
public class UMCFragment extends Fragment {
    RecyclerView recyclerView;
    MaterialAdapter adapter;
    MaterialMainAdapter mainAdapter;
    List<MaterialForm> forms;
    List<MaterialMainForm> materialMainForms;
    FrameLayout progressLayout;
    String id="";
    boolean object=false;
    public UMCFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        object=getArguments().getBoolean("object");
        View view=inflater.inflate(R.layout.fragment_umc, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);

        adapter=new MaterialAdapter(forms);
        mainAdapter=new MaterialMainAdapter(materialMainForms);
        if (object)
            recyclerView.setAdapter(adapter);
        else{
            recyclerView.setAdapter(mainAdapter);
        }
        getRequest();
        return view;
    }
    private void createViews(View view){
        forms=new ArrayList<>();
        materialMainForms=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL;
        if(object){
            url+="consumptions/?inventory__kind="+"UMC&point="+id;
        }
        else{
            url+="inventories/?kind=UMC";
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
//                    JSONObject company = inventory.getJSONObject("company");
                    String name = inventory.getString("name"), id = object.getString("id"), unit = inventory.getString("unit"), vendor_code = inventory.getString("vendor_code");
                    int quantity = object.getInt("quantity"), limit = object.getInt("limit");

               /* EquipmentForm equipmentForm= new EquipmentForm(name,id,num, remontForms, new ArrayList<OrderForm>());
                equipmentForm.setVendor_code(vendor_code);*/
                    MaterialForm materialForm = new MaterialForm(name, id, quantity, limit);
                    materialForm.setVendor_code(vendor_code);
                    forms.add(materialForm);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                materialMainForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
//                    JSONObject company=object.getJSONObject("company");
                    String vendor_code=object.getString("vendor_code");
                    String id=object.getString("id"), name=object.getString("name"), unit=object.getString("unit");
                    String uni=((MainActivity)getActivity()).inventoryUnits.get(unit);
                    MaterialMainForm form=new MaterialMainForm(id,name,vendor_code,uni,0,0, uni, uni);
                    materialMainForms.add(form);
                }
                mainAdapter.notifyDataSetChanged();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}