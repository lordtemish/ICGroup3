package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.PassInventorizationAdapter;
import com.studio.dynamica.icgroup.Forms.CheckApiForm;
import com.studio.dynamica.icgroup.Forms.PassInventorizationForm;
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
public class InventoryPassInventorizationFragment extends Fragment {
    RecyclerView passInventoryRecyclerView;
    PassInventorizationAdapter inventorizationAdapter;
    String id, point, prodid;
    List<PassInventorizationForm> inventorizationForms;
    List<Boolean> passed;
    List<List<String>> consids;
    List<String> kinds;
    List<CheckApiForm> checkApiForms;
    FrameLayout progressFrame, ruSureLayout;
    JSONArray conses, checks;
    TextView endTextView, noSure, yesSure;
    public InventoryPassInventorizationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id","");
        point=getArguments().getString("point","");
        prodid=getArguments().getString("producer","1");
        View view=inflater.inflate(R.layout.fragment_inventory_pass_inventorization, container, false);
        createView(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(passInventoryRecyclerView, LinearLayout.VERTICAL);
       inventorizationForms=new ArrayList<>();
        inventorizationForms.add(new PassInventorizationForm("Оборудование",false,100,"EQUIPMENT"));
        inventorizationForms.add(new PassInventorizationForm("Расходный материал",false,100,"CONSUMABLES"));
        inventorizationForms.add(new PassInventorizationForm("Инвентарь",false,100,"INVENTORY"));
        inventorizationForms.add(new PassInventorizationForm("УМС",false,100,"UMC"));
        inventorizationForms.add(new PassInventorizationForm("Сезонный инвентарь",false,100,"SEASONAL"));
        inventorizationForms.add(new PassInventorizationForm("Спецодежда",false,100,"CLOTHES"));
        inventorizationAdapter=new PassInventorizationAdapter(inventorizationForms);
        inventorizationAdapter.setPoint(point);inventorizationAdapter.setId(id);
        passInventoryRecyclerView.setAdapter(inventorizationAdapter);
        ((MainActivity)getActivity()).setPressable(false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Завершите инвентаризацию", Toast.LENGTH_SHORT).show();
            }
        });
        getCons();
        endTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruSureLayout.setVisibility(View.VISIBLE);
            }
        });
        noSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ruSureLayout.setVisibility(View.GONE);
            }
        });
        yesSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finishGroup();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).setPressable(false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Завершите инвентаризацию", Toast.LENGTH_SHORT).show();
            }
        });
        getCons();
        super.onResume();
    }

    private void createView(View view){
        passInventoryRecyclerView=(RecyclerView) view.findViewById(R.id.passInventoryRecyclerView);
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);
        ruSureLayout=(FrameLayout)view.findViewById(R.id.ruSureLayout);
        endTextView=(TextView)view.findViewById(R.id.endTextView);
        yesSure=(TextView)view.findViewById(R.id.yesSure);
        noSure=(TextView)view.findViewById(R.id.noSure);

        passed=new ArrayList<>();
        consids=new ArrayList<>();
        kinds=new ArrayList<>();
        for(int i=0;i<6;i++){
            kinds.add("");
            passed.add(false);
            consids.add(new ArrayList<String>());
        }
        kinds.set(0,"EQUIPMENT");
        kinds.set(1,"CONSUMABLES");
        kinds.set(2,"INVENTORY");
        kinds.set(3,"UMC");
        kinds.set(4,"SEASONAL");
        kinds.set(5,"CLOTHES");
        checkApiForms=new ArrayList<>();
    }
    private void getCons(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"consumptions/?point="+point;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressFrame.setVisibility(View.GONE);
                setCons(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                return headers;
            }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setCons(JSONArray array){
        try{
            conses=array;
            checkApiForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject inventory=object.getJSONObject("inventory");
                String kind=inventory.getString("kind");
                String id=object.getString("id");
                String name=inventory.getString("name");
                int quantity=object.getInt("quantity");
                int index=kinds.indexOf(kind);
                if(index>-1){
                    consids.get(index).add(id);
                }
                checkApiForms.add(new CheckApiForm(id, name, kind, quantity));
            }
            for(int i=0;i<6;i++){
                String s=""+kinds.get(i);

                for(String id:consids.get(i)){
                    s+=" "+id;
                }
                Log.d("index "+i+" consumptions",s);
            }
            inventorizationAdapter.notifyDataSetChanged();
            getChecks();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getChecks(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"checks/?group="+id;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressFrame.setVisibility(View.GONE);
                setChecks(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соденения", Toast.LENGTH_SHORT).show();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setChecks(JSONArray array){
        try{
            checks=array;
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                int total=0;
                String id=object.getString("consumption");
                total+=object.getInt("repair");
                total+=object.getInt("replace");
                total+=object.getInt("missing");
                total+=object.getInt("moving");
                for(CheckApiForm form:checkApiForms){
                    if(form.getId().equals(id)){
                        form.setCheck(true);
                        form.setRemoved(total);
                        break;
                    }
                }
            }
            checkAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void checkAll(){
        for(int i=0;i<6;i++){
            String kind=kinds.get(i);
            int l=0;
            int to=0;
            boolean rema=false;
            for(CheckApiForm form:checkApiForms){
                if(form.getKind().equals(kind)){
                    l++;
                    to+=form.getTotal();
                    if(form.isChanged()){
                        rema=true;
                    }
                }
            }
            if(rema) {
                if(to/l<100){
                    inventorizationForms.get(i).setStatus(true);
                }
                inventorizationForms.get(i).setPerc(to / l);
            }
            Log.d("KINDCHECKALL", kinds.get(i)+" "+consids.get(i).size());
            if(consids.get(i).size()==0){
                inventorizationForms.get(i).setStatus(true);
            }
        }
        inventorizationAdapter.notifyDataSetChanged();
    }

    private void finishGroup(){
        progressFrame.setVisibility(View.VISIBLE);
        String ul=((MainActivity)getActivity()).MAIN_URL+"checkgroups/"+id+"/finish/";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, ul, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                Log.d("respaOnseCG",response.toString());
                Toast.makeText(getActivity(), "Инвентаризация закрыта", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).setPressable(true,null);
                ((MainActivity)getActivity()).onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                return headers;
            }
        };
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }

}
