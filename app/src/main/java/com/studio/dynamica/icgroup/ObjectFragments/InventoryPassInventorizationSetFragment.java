package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.InventorizationPassingSetAdapter;
import com.studio.dynamica.icgroup.Forms.CheckApiForm;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.Forms.InventorizationPassingSetForm;
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
public class InventoryPassInventorizationSetFragment extends Fragment {
    RecyclerView recyclerView;
    String id="", name="", kind="", point;
    InventorizationPassingSetAdapter setAdapter;
    List<InventorizationPassingSetForm> setForms;
    FrameLayout progressFrame;
    TextView nameTextView;
    int total=0;
    public InventoryPassInventorizationSetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        point=getArguments().getString("point");
        name=getArguments().getString("name");
        kind=getArguments().getString("kind");
        View view=inflater.inflate(R.layout.fragment_inventory_pass_inventorization_set, container, false);
        createViews(view);

        nameTextView.setText(name);

        ((MainActivity)getActivity()).setPressable(true, null);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
         setForms=new ArrayList<>();
       setAdapter=new InventorizationPassingSetAdapter(setForms);
        recyclerView.setAdapter(setAdapter);
        getCons();
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);
        nameTextView=(TextView)view.findViewById(R.id.nameTextView);


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
            setForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject inventory=object.getJSONObject("inventory");
                String kind=inventory.getString("kind");
                String id=object.getString("id");
                String name=inventory.getString("name");
                String vendor=inventory.getString("vendor_code");
                int quantity=object.getInt("quantity");
                if(this.kind.equals(kind)){
                    InventorizationPassingSetForm setForm=new InventorizationPassingSetForm(name, this.id, quantity);
                    setForm.setVendor(vendor);
                    setForm.setConsumption(id);
                    setForms.add(setForm);
                }
            }
            setAdapter.notifyDataSetChanged();
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
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setChecks(JSONArray array){
        try{
            Log.d("array.length",array.length()+"");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                for(InventorizationPassingSetForm setForm:setForms){
                    if(setForm.getConsumption().equals(object.getString("consumption"))) {
                        setForm.setStatus(false);
                        setForm.setCreated(true);
                        setForm.setCheck(object.getString("id"));
                        setForm.setRepa(object.getInt("repair"));
                        setForm.setRepl(object.getInt("replace"));
                        setForm.setMiss(object.getInt("missing"));
                        setForm.setRema(object.getInt("remainder"));
                        setForm.setComment(object.getString("comment"));
                        break;
                    }
                }
            }
            setAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void checkAll(){
        for(int i=0;i<setForms.size();i++){

            int l=0;
            int to=0;

        }
    }
}
