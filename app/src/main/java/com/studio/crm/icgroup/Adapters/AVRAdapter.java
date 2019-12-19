package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.AVRForm;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.Forms.MessageWorkForm;
import com.studio.crm.icgroup.ObjectFragments.AvrInfoFragment;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AVRAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private class myHolder extends RecyclerView.ViewHolder{
        String id, point;
        int pos;
        TextView dateTextView,wholeMark,markTextView,workDone, worksLabelTextView, markLabelTextView, workerTextView, nameTextView, positionTextView;
        RecyclerView tableRecyclerView, acceptRecyclerView;
        //RadioButton radioButton;
        LinearLayout extraLayout, wholeLayout;
        FrameLayout progressLayout;
        MessageWorkAdapter workAdapter;
        AcceptAdapter acceptAdapter;
        List<MessageWorkForm> workForms;
        List<AcceptForm> acceptForms;
        List<String[]> strings;
        int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
        boolean visible=false, updated=false;
        private myHolder(View view){
            super(view);
            acceptForms=new ArrayList<>();
            workForms=new ArrayList<>();
            strings=new ArrayList<>();strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            wholeMark=(TextView) view.findViewById(R.id.wholeMark);
            markTextView=(TextView) view.findViewById(R.id.averageMarkTextView);
           // workDone=(TextView) view.findViewById(R.id.workDone);
          //  wrapTextView=(TextView) view.findViewById(R.id.wrapTextView);
            worksLabelTextView=(TextView) view.findViewById(R.id.worksLabelTextView);
            markLabelTextView=(TextView) view.findViewById(R.id.markLabelTextView);
            workerTextView=(TextView) view.findViewById(R.id.workerTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            tableRecyclerView=(RecyclerView) view.findViewById(R.id.tableRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
          //  radioButton=(RadioButton) view.findViewById(R.id.radioButton);
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);

            setFonttypes();

          /*  wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setVisible();
                }
            });*/
        }
        private void setFonttypes(){
            setTypeFace("demibold", dateTextView, markTextView,workerTextView, nameTextView  );
            setTypeFace("light", wholeMark, worksLabelTextView, markLabelTextView, positionTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for (int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity) context).getTypeFace(s));
            }
        }
        private void setInfo(AVRForm form, int position){
            id=form.getId();
            final String tId=id;
            pos=position;
            dateTextView.setText(form.getDate());
            markTextView.setText(form.getMark()+"");
          //  radioButton.setText("  "+form.getRadio());
            nameTextView.setText(form.getWorkerName());
            positionTextView.setText(form.getWorkerPosition());

            ((MainActivity)context).setRecyclerViewOrientation(acceptRecyclerView, LinearLayoutManager.HORIZONTAL);
            ((MainActivity)context).setRecyclerViewOrientation(tableRecyclerView, LinearLayoutManager.VERTICAL);

            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AvrInfoFragment fragment=new AvrInfoFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",tId);
                    fragment.setArguments(bundle);
                    ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
        private void setVisible(){
            if(updated) {
                visible = !visible;
                if (visible) {
                    extraLayout.setVisibility(View.VISIBLE);
                  //  wrapTextView.setText("Свернуть");
                } else {
                    extraLayout.setVisibility(View.GONE);
                  //  wrapTextView.setText("Развернуть");
                }
            }
            else{
                progressLayout.setVisibility(View.VISIBLE);
                String url=((MainActivity)context).MAIN_URL+"controls/"+id;
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        try {
                            updated = true;
                            setVisible();
                            acceptForms.clear();
                            workForms.clear();
                            JSONObject poin=response.getJSONObject("point");
                            point=poin.getString("id");
                            JSONObject author=response.getJSONObject("author");
                            String fullname=author.getString("fullname");
                            String role=author.getString("role");
                            String position=((MainActivity)context).positions.get(role);
                            JSONArray array = response.getJSONArray("positions");
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                String name="";
                                if(!object.isNull("position")) {
                                    JSONObject position1 = object.getJSONObject("position");
                                     name= position1.getString("name");
                                }
                                else{
                                    name=object.getString("name");
                                }
                                int rate=object.getInt("rate");
                                String comment="";
                                if(!object.isNull("comment")) comment=object.getString("comment");
                                MessageWorkForm workForm=new MessageWorkForm(name,false,rate);
                                workForm.setInfo(comment);
                                workForms.add(workForm);
                            }
                            nameTextView.setText(fullname);
                            positionTextView.setText(position);
                            workAdapter.notifyItemChanged(pos);

                            /*
                            if(response.isNull("is_executive_permitted")){ is_executive_permitted=-1; }
                            else{is_executive_permitted=0;
                                if(response.getBoolean("is_executive_permitted")){
                                    is_executive_permitted=1;
                                }
                            }
                            if(response.isNull("is_technical_permitted")){ is_technical_permitted=-1; }
                            else{is_technical_permitted=0;
                                if(response.getBoolean("is_technical_permitted")){
                                    is_technical_permitted=1;
                                }

                            }
                            if(response.isNull("is_producer_permitted")){ is_producer_permitted=-1; }
                            else{
                                is_producer_permitted=0;
                                if(response.getBoolean("is_producer_permitted")){
                                    is_producer_permitted=1;
                                }
                            }
                            if(response.isNull("is_curator_permitted")){ is_curator_permitted=-1; }
                            else{
                                is_curator_permitted=0;
                                if(response.getBoolean("is_curator_permitted")){
                                    is_curator_permitted=1;
                                }
                            }
                            if(response.isNull("is_contactor_permitted")){ is_contactor_permitted=-1; }
                            else{
                                is_contactor_permitted=0;
                                if(response.getBoolean("is_contactor_permitted")){
                                    is_contactor_permitted=1;
                                }
                            }*/
                            //getAccepts();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }){@Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)context).token);
                    return headers;
                }};

                ((MainActivity)context).requestQueue.add(jsonObjectRequest);
            }
        }
        private void getAccepts(){
            String url=((MainActivity)context).MAIN_URL;
            String execUrl=url+"employees/"+ "?user__role=ADMIN_EXECUTIVE";
            JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, execUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setExec(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(0,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            String techinUrl=url+"employees/" + "?user__role=ADMIN_TECHNICAL";
            JsonArrayRequest admin_techRequest=new JsonArrayRequest(Request.Method.GET, techinUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setTech(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(1,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, url+"points/"+point, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    try {
                        JSONObject contactor=response.getJSONObject("contactor");
                        JSONObject producer=response.getJSONObject("producer");
                        //curator change
//                        JSONObject curator=response.getJSONObject("curator");
                        if(is_producer_permitted>-1)
                            strings.set(2,new String[]{producer.getString("fullname"),producer.getString("role"), "-1"});
          /*              if(is_curator_permitted>-1)
                            strings.set(3,new String[]{curator.getString("fullname"),curator.getString("role"), "-1"});*/
                        if(is_contactor_permitted>-1)
                            strings.set(4,new String[]{contactor.getString("fullname"),contactor.getString("role"), "-1"});
                        checkAccepts();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(admin_execRequest);
            ((MainActivity)context).requestQueue.add(admin_techRequest);
            ((MainActivity)context).requestQueue.add(admin_contRequest);
        }
        private void setExec(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_executive_permitted>-1)
                    strings.set(0, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void setTech(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_technical_permitted>-1)
                    strings.set(1, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void checkAccepts(){
            acceptForms.clear();
            acceptAdapter.notifyDataSetChanged();
            int l=0;
            for(String[] a:strings){
                if(a.length==0){
                    l++;
                    continue;
                }
                Log.d("Strings"+l,a.toString());
                String name=a[0];
                String role=((MainActivity)context).positions.get(a[1]);
                String dep="";
                if(!a[2].equals("-1") && ((MainActivity)context).dpids.indexOf(a[2])!=-1) {
                    dep= ((MainActivity) context).departments.get(((MainActivity) context).dpids.indexOf(a[2]));
                }
                Boolean cl=l==4;
                String sta="Не подтвержденно";
                Boolean stat=false;
                switch (l){
                    case 0:
                        stat=is_executive_permitted==1;
                        break;
                    case 1:
                        stat=is_technical_permitted==1;
                        break;
                    case 2:
                        stat=is_producer_permitted==1;
                        break;
                    case 3:
                        stat=is_curator_permitted==1;
                        break;
                    case 4:
                        stat=is_contactor_permitted==1;
                        break;

                }
                AcceptForm acceptForm=new AcceptForm(name, dep, role, sta,stat);
                acceptForms.add(acceptForm);
                l++;
            }
            acceptAdapter.notifyDataSetChanged();
        }
    }
    List<AVRForm> list;
    public AVRAdapter(List<AVRForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position), position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.avr_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
