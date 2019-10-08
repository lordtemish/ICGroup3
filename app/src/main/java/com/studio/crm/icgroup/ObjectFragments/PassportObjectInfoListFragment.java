package com.studio.crm.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.PhonesAdapter;
import com.studio.crm.icgroup.Adapters.ProgressPhonesAdapter;
import com.studio.crm.icgroup.Forms.PhonesRowForm;
import com.studio.crm.icgroup.Forms.ProgressPhoneForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListFragment extends Fragment {
    HashMap<String, TextView> mapText;
    RecyclerView phonesRecycler;

    View view;

    PhonesAdapter adapter;
    List<PhonesRowForm> phonesRowFormList;


    int shifts=0, janitor_shifts_count=0, gardener_shifts_count=0, plumber_shifts_count=0,electrician_shifts_count=0;

    ConstraintLayout  progressLayout;

    TextView title;
    boolean is_trainee=false, client=false;
    String id;
    LinearLayout emplLa;
    ImageView arrowTop;
    public PassportObjectInfoListFragment() {
        // Required empty public constructor
    }

    public int getJanitor_shifts_count() {
        return janitor_shifts_count;
    }

    public int getGardener_shifts_count() {
        return gardener_shifts_count;
    }

    public int getPlumber_shifts_count() {
        return plumber_shifts_count;
    }

    public int getElectrician_shifts_count() {
        return electrician_shifts_count;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        client=((MainActivity)getActivity()).client;
        id=getArguments().getString("id","0");
        janitor_shifts_count=getArguments().getInt("janitor_shifts_count");
        gardener_shifts_count=getArguments().getInt("gardener_shifts_count");
        plumber_shifts_count=getArguments().getInt("plumber_shifts_count");
        electrician_shifts_count=getArguments().getInt("electrician_shifts_count");
       view=inflater.inflate(R.layout.fragment_passport_object_info_list, container, false);

       emplLa=(LinearLayout)view.findViewById(R.id.emplLa);
        arrowTop=(ImageView)view.findViewById(R.id.arrowTop);
       title=(TextView) view.findViewById(R.id.title);
       title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));

        mapText=new HashMap<>();
        addAlltoMap();

        for (String i:
             mapText.keySet()) {
            if(i.contains("TextView")) {
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/avenir-light.ttf"));
            }
            else{
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Black.ttf"));
            }
        }
        phonesRecycler=(RecyclerView) view.findViewById(R.id.phonesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
         phonesRowFormList=new ArrayList<>();

        adapter=new PhonesAdapter(phonesRowFormList,getActivity());
        phonesRecycler.setLayoutManager(mLayoutManager);
        phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        phonesRecycler.setAdapter(adapter);


        getRequest("points/"+id);
        View.OnClickListener requestListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest("points/"+id);
            }
        };


        showEmpls();
        arrowTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmpls();
            }
        });


        return view;
    }
    private void showEmpls(){
        if(emplLa.getVisibility()==View.VISIBLE){
            emplLa.setVisibility(View.GONE);
            arrowTop.setImageResource(R.drawable.ic_arrowdown);
        }
        else{
            emplLa.setVisibility(View.VISIBLE);
            arrowTop.setImageResource(R.drawable.ic_arrowup);
        }
    }




    private void setPhonesAdapter(List<PhonesRowForm> forms){
        phonesRowFormList.clear();
        phonesRowFormList.addAll(forms);
        adapter.notifyDataSetChanged();
    }
    private void getRequest(final String url1){
        try {
            progressLayout.setVisibility(View.VISIBLE);
            final String url = ((MainActivity) getActivity()).MAIN_URL + url1;
            if (url1.contains("points")) {
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setInfo(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                        return headers;
                    }
                };
                (((MainActivity) getActivity()).requestQueue).add(objectRequest);
            }
        }
        catch (Exception e){
            e.printStackTrace();progressLayout.setVisibility(View.GONE);
        }
    }
    private void setInfo(JSONObject object){
        progressLayout.setVisibility(View.GONE);
        try {
            String name = object.getString("name");
            String c_at=object.getString("created_at");

            Date cr_at=null, fi_at=null;
            try{
                cr_at=((MainActivity) getActivity()).getNowDate(c_at);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            String address=object.getString("address");
            String area=object.getString("area");
            JSONObject location=getJsObject(object,"location");

            JSONObject contactor=getJsObject(object,"contactor");
            JSONObject client=getJsObject(object,"client");
            JSONObject producer=getJsObject(object,"producer");
            JSONArray curators=object.getJSONArray("curators");
            JSONArray administrators=object.getJSONArray("admins");
            try {

                mapText.get("infoListObjectName").setText(name);
                mapText.get("infoListRegion").setText(location.getString("name"));
                mapText.get("infoListObjectAddress").setText(address);
                mapText.get("infoListWorkStart").setText(((MainActivity)getActivity()).getDateText(cr_at));
                String ff="";
                if(!object.isNull("finished_at"));
                {
                    String f_at = object.getString("finished_at");
                    if(!f_at.equals("null"))
                    ff = ((MainActivity) getActivity()).getdate(f_at);
                }
                if(ff.length()>6)
                mapText.get("infoListWorkStop").setText(ff.substring(0,ff.length()-6));

                mapText.get("infoListObjectArea").setText(area);
                try {
                    phonesRowFormList.clear();
                    List<PhonesRowForm> phonesRowForms=new ArrayList<>();
                    if(producer!=null) {
                        mapText.get("infoListHead").setText(producer.getString("fullname"));
                        phonesRowForms.add(new PhonesRowForm(true, producer.getString("fullname"), "Начальник производства", producer.getString("phone")));
                    }
                    else{
                        mapText.get("infoListHead").setText("");
                    }
                    if(curators.length()>0) {
                        String s="";
                        for(int i=0;i<curators.length();i++){
                            JSONObject curator=curators.getJSONObject(i);
                            phonesRowForms.add(new PhonesRowForm(true, curator.getString("fullname"), "Куратор", curator.getString("phone")));
                            s=s+curator.getString("fullname")+"\n";
                        }

                        mapText.get("infoListAdvisor").setText(s);
                    }
                    else{
                        mapText.get("infoListAdvisor").setText("");
                    }
                    if(administrators.length()>0) {
                        String s="";
                        for(int i=0;i<administrators.length();i++){
                            JSONObject curator=administrators.getJSONObject(i);
                            String po=curator.getString("role");
                            String position=((MainActivity)getActivity()).positions.get(po);
                            phonesRowForms.add(new PhonesRowForm(true, curator.getString("fullname"), position, curator.getString("phone")));
                            s+=""+curator.getString("fullname")+"\n";
                        }

                        mapText.get("infoListAdministrator").setText(s);
                    }
                    else{
                        mapText.get("infoListAdministrator").setText("");
                    }
                    if(client!=null)
                    mapText.get("infoListClient").setText(client.getString("name"));
                    else{
                        mapText.get("infoListClient").setText("");
                    }
                    if(contactor!=null)
                    mapText.get("infoListClientPre").setText(contactor.getString("fullname"));
                    else mapText.get("infoListClientPre").setText("");
                    setPhonesAdapter(phonesRowForms);
                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException e){e.printStackTrace();
                }



            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    private void setShiftInfo(JSONArray array){

    }

    @Override
    public void onResume() {
        //getRequest("points/"+id);
        super.onResume();
    }

    private JSONObject getJsObject(JSONObject object, String s){
        try{
            return object.getJSONObject(s);
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public void addAlltoMap(){
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);

        mapText.put("infoListObjectNameTextView",(TextView) view.findViewById(R.id.infoListObjectNameTextView));
        mapText.put("infoListObjectName",(TextView) view.findViewById(R.id.infoListObjectName));
        mapText.put("infoListRegionTextView",(TextView) view.findViewById(R.id.infoListRegionTextView));
        mapText.put("infoListRegion",(TextView) view.findViewById(R.id.infoListRegion));
        mapText.put("infoListObjectAddressTextView",(TextView) view.findViewById(R.id.infoListObjectAddressTextView));
        mapText.put("infoListObjectAddress",(TextView) view.findViewById(R.id.infoListObjectAddress));

        mapText.put("infoListWorkStartTextView",(TextView) view.findViewById(R.id.infoListWorkStartTextView));
        mapText.put("infoListWorkStart",(TextView) view.findViewById(R.id.infoListWorkStart));
        mapText.put("infoListWorkStopTextView",(TextView) view.findViewById(R.id.infoListWorkStopTextView));
        mapText.put("infoListWorkStop",(TextView) view.findViewById(R.id.infoListWorkStop));
        mapText.put("infoListObjectAreaTextView",(TextView) view.findViewById(R.id.infoListObjectAreaTextView));
        mapText.put("infoListObjectArea",(TextView) view.findViewById(R.id.infoListObjectArea));

        mapText.put("infoListHeadTextView",(TextView) view.findViewById(R.id.infoListHeadTextView));
        mapText.put("infoListHead",(TextView) view.findViewById(R.id.infoListHead));
        mapText.put("infoListAdvisorTextView",(TextView) view.findViewById(R.id.infoListAdvisorTextView));
        mapText.put("infoListAdvisor",(TextView) view.findViewById(R.id.infoListAdvisor));
        mapText.put("infoListAdministratorTextView",(TextView) view.findViewById(R.id.infoListAdministratorTextView));
        mapText.put("infoListAdministrator",(TextView) view.findViewById(R.id.infoListAdministrator));
        mapText.put("infoListClientTextView",(TextView) view.findViewById(R.id.infoListClientTextView));
        mapText.put("infoListClient",(TextView) view.findViewById(R.id.infoListClient));
        mapText.put("infoListClientPreTextView",(TextView) view.findViewById(R.id.infoListClientPreTextView));
        mapText.put("infoListClientPre",(TextView) view.findViewById(R.id.infoListClientPre));

    }
}
