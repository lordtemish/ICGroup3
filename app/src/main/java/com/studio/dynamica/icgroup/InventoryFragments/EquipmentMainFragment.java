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
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
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
public class EquipmentMainFragment extends Fragment {
    RecyclerView recyclerView;
    EquipmentAdapter adapter;
    List<EquipmentForm> forms;
    String id="";
    FrameLayout progressLayout;
    public EquipmentMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        View view=inflater.inflate(R.layout.fragment_equipment_main, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        forms=new ArrayList<>();
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт",5));
        forms.add(new EquipmentForm("name","someid","", remontForms,orderForms));
         adapter=new EquipmentAdapter(forms);
         recyclerView.setAdapter(adapter);
         getRequest();
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"consumptions/?kind="+"EQUIPMENT&point="+id;
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
            forms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject inventory=object.getJSONObject("inventory");
                JSONObject company=inventory.getJSONObject("company");
                String name=inventory.getString("name"), id=object.getString("id"), unit=inventory.getString("unit"), vendor_code=inventory.getString("vendor_code");
                String num=object.getString("quantity")+" "+ ((MainActivity)getActivity()).inventoryUnits.get(unit);
                int repair=object.getInt("repair"), replace=object.getInt("replace");
                List<RemontForms> remontForms=new ArrayList<>();
                if(repair>0){
                    RemontForms form=new RemontForms("На ремонте", repair);
                    remontForms.add(form);
                }
                if(replace>0){
                    RemontForms form=new RemontForms("На замене", replace);
                    remontForms.add(form);
                }
                EquipmentForm equipmentForm= new EquipmentForm(name,id,num, remontForms, new ArrayList<OrderForm>());
                equipmentForm.setVendor_code(vendor_code);
                forms.add(equipmentForm);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
