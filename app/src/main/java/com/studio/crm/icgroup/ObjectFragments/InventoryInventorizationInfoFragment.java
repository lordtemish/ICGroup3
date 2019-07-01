package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.InventoryStatusAdapter;
import com.studio.crm.icgroup.Forms.CheckForm;
import com.studio.crm.icgroup.Forms.CommentaryForm;
import com.studio.crm.icgroup.Forms.InventoryStatusFactoryForm;
import com.studio.crm.icgroup.Forms.InventoryStatusForm;
import com.studio.crm.icgroup.Forms.RemontForms;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryInventorizationInfoFragment extends Fragment {

    TextView dateTextView, PercentageTextView, nameTextView, positionTextView;
    RecyclerView inventoryStatusRecyclerView;
    FrameLayout progressFrame;
    ProgressBar ProgressBar;
    List<InventoryStatusFactoryForm> factoryForms;
    List<String> kinds, kindKeys;
    List<List<InventoryStatusForm>> statusFormsArays;
    String id="";
    InventoryStatusAdapter statusAdapter;
    public InventoryInventorizationInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        View view=inflater.inflate(R.layout.fragment_inventory_inventorization_info, container, false);
        createViews(view);

        List<InventoryStatusForm> statusForms=new ArrayList<>();
        InventoryStatusForm statusForm=new InventoryStatusForm("Timur Syzdykov","OK05454545",4);
        statusForm.setStatus(false);
        CommentaryForm commentaryForm=new CommentaryForm("02.07.2017","Tima TIma",getActivity().getResources().getString(R.string.bigtext));
        List<CommentaryForm> commentaryForms=new ArrayList<CommentaryForm>();commentaryForms.add(commentaryForm);
        statusForm.setCommentaryForms(commentaryForms);
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт всего",5));
        statusForm.setRemontForms(remontForms);
        InventoryStatusForm ff=new InventoryStatusForm("Temirlan Almassov","OK04546464",5);
        ff.setStatus(false);
        statusForms.add(ff);
        statusForms.add(statusForm);
        statusForms.add(new InventoryStatusForm("Temirlan Almassov","OK04546464",5));
        statusForms.add(new InventoryStatusForm("Nadira Ryskulova","OK546406846",5));
        factoryForms=new ArrayList<>();
        for(int i=0;i<6;i++) {
            factoryForms.add(new InventoryStatusFactoryForm(kinds.get(i),statusFormsArays.get(i), kindKeys.get(i)));
        }
       statusAdapter=new InventoryStatusAdapter(factoryForms);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(inventoryStatusRecyclerView, LinearLayoutManager.VERTICAL);

        getReq();
        ((MainActivity)getActivity()).setType("demibold", nameTextView, PercentageTextView);
        ((MainActivity)getActivity()).setType("light",positionTextView, dateTextView);
        return view;
    }

    private void createViews(View view){
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        inventoryStatusRecyclerView=(RecyclerView) view.findViewById(R.id.inventoryStatusReyclerView);
        progressFrame=(FrameLayout) view.findViewById(R.id.progressFrame);
        ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);

        statusFormsArays=new ArrayList<>();
        kindKeys=new ArrayList<>();kinds=new ArrayList<>();
        kinds.add("Оборудование");kindKeys.add("EQUIPMENT");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
        kinds.add("Расходный материал");kindKeys.add("CONSUMABLES");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
        kinds.add("Инвентарь");kindKeys.add("INVENTORY");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
        kinds.add("УМС");kindKeys.add("UMC");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
        kinds.add("Сезонный инвентарь");kindKeys.add("SEASONAL");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
        kinds.add("Спецодежда");kindKeys.add("CLOTHES");statusFormsArays.add(new ArrayList<InventoryStatusForm>());
    }
    private void getReq(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"checkgroups/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                setInfo(response);
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
        }};
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setInfo(JSONObject array){
        try{
           Log.d(id+" Resoa",array.toString());
           JSONObject author=array.getJSONObject("author");
           String name=author.getString("fullname"), role=author.getString("role");
           String position=((MainActivity)getActivity()).positions.get(role);
           String created_at=array.getString("created_at");
           double match_rate=array.getDouble("match_rate");
           int perc=Integer.parseInt(""+Math.round(match_rate*100));
           String created=((MainActivity)getActivity()).getdate(created_at);

           dateTextView.setText(created.substring(0,created.length()-6));
           nameTextView.setText(name);positionTextView.setText(position);
           ProgressBar.setProgress(perc);PercentageTextView.setText(perc+"%");


           JSONObject point=array.getJSONObject("point");
           String pointid=point.getString("id");
           statusAdapter.setPoint(pointid);
            getChecks();
        }catch (Exception e){
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
                setChecksd(response);
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
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setChecksd(JSONArray array){
        try {
            List<CheckForm> checkForms=new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String id=object.getString("id"), complaint=object.getString("consumption");
                CheckForm form=new CheckForm(id,complaint);
                int repair=object.getInt("repair");
                int replac=object.getInt("replace");
                int missing=object.getInt("missing"), moving=object.getInt("moving"),
                        remainder=object.getInt("remainder");

                form.setRepair(repair);
                form.setReplace(replac);
                form.setMissing(missing);
                form.setMoving(moving);
                form.setRemainder(remainder);
                checkForms.add(form);
            }
            statusAdapter.setCheckForms(checkForms);
            inventoryStatusRecyclerView.setAdapter(statusAdapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
