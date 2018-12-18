package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListInfoFragment extends Fragment {
    RecyclerView recyclerView, messagesInfoRecyclerView, infoAcceptRecyclerView;
    int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
    FrameLayout progressLayout;
    Spinner spinner;
    String id, rate, point;
    List<CheckListBoxForm> boxForms;
    MessageAdapter messageAdapter;
    CheckListBoxAdapter adapter;
    List<AcceptForm> acceptForms;
    AcceptAdapter acceptAdapter;
    List<String[]> strings;
    TextView mainObjectTitle,infoDateTextView, totalMarkLabelTextView, rateTextView, revisorLabelTextView, nameInfoLayout,tookLabel;
    public CheckListInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");rate=getArguments().getString("rate");
        View view=inflater.inflate(R.layout.fragment_check_list_info, container, false);
        createViews(view);
        setFonttype();

        rateTextView.setText(rate);

        boxForms=new ArrayList<>();
        List<CheckListBoxRowForm> boxRowForms=new ArrayList<>();

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(messagesInfoRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(infoAcceptRecyclerView, LinearLayoutManager.HORIZONTAL);

         adapter=new CheckListBoxAdapter(boxForms, true);
        recyclerView.setAdapter(adapter);

        messageAdapter=new MessageAdapter(new MessageForm(getActivity().getResources().getString(R.string.bigtext)));
        messagesInfoRecyclerView.setAdapter(messageAdapter);

        acceptForms=new ArrayList<>();
        acceptAdapter=new AcceptAdapter(acceptForms);
        infoAcceptRecyclerView.setAdapter(acceptAdapter);
        getPositions();
        return view;
    }

    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", infoDateTextView, rateTextView, nameInfoLayout,tookLabel);
        setTypeFace("light", totalMarkLabelTextView, revisorLabelTextView);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
    private void createViews(View view){
        strings=new ArrayList<>();strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        infoDateTextView=(TextView) view.findViewById(R.id.infoDateTextView);
        totalMarkLabelTextView=(TextView) view.findViewById(R.id.totalMarkLabelTextView);
        rateTextView=(TextView) view.findViewById(R.id.rateTextView);
        revisorLabelTextView=(TextView) view.findViewById(R.id.revisorLabelTextView);
        tookLabel=(TextView) view.findViewById(R.id.tookLabel);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        nameInfoLayout=(TextView) view.findViewById(R.id.nameInfoLayout);

        messagesInfoRecyclerView=(RecyclerView) view.findViewById(R.id.messagesInfoRecyclerView);
        infoAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.infoAcceptRecyclerView);
    }

    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity) getActivity()).MAIN_URL+"controls/"+id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);

            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(request);
    }
    private void setInfo(JSONObject object){
        try{
            JSONObject poin=object.getJSONObject("point");
            point=poin.getString("id");
            JSONObject author=object.getJSONObject("author");
            infoDateTextView.setText(((MainActivity)getActivity()).getDateText(object.getString("created_at")));
            nameInfoLayout.setText(author.getString("fullname"));
                Log.d("TagTag",author.getString("fullname"));
            String comment=object.getString("comment");
            List<MessageForm> messageForms=messageAdapter.getList();
            messageForms.clear();if(comment.length()>0){
                messageForms.add(new MessageForm(comment));
            }
            messageAdapter.notifyDataSetChanged();
            List<CheckListBoxForm> forms=new ArrayList<>();forms.addAll(boxForms);
            boxForms.clear();
            JSONArray array=object.getJSONArray("positions");
            List<CheckListBoxRowForm> rowForms=new ArrayList<>();
            int l=0;
            for(int i=0;i<array.length();i++){
                JSONObject object1=array.getJSONObject(i);
                JSONObject position=object1.getJSONObject("position");
                for(CheckListBoxForm form:forms){
                    boolean updated=false;
                    if(forms.indexOf(form)>l){
                        boxForms.add(new CheckListBoxForm(forms.get(l).getName(),l%2==0,rowForms));
                        l=forms.indexOf(form);
                        rowForms=new ArrayList<>();
                    }
                    for(CheckListBoxRowForm rowForm:form.getList()){
                        if(rowForm.getId().equals(position.getString("id"))){
                            rowForms.add(new CheckListBoxRowForm(position.getString("name"),object1.getString("comment"),object1.getInt("rate")));
                            updated=true;
                            break;
                        }
                    }
                    if(updated){
                        break;
                    }
                }
            }
            boxForms.clear();
            boxForms.add(new CheckListBoxForm(forms.get(l).getName(),l%2==0,rowForms));
            adapter.notifyDataSetChanged();

            if(object.isNull("is_executive_permitted")){ is_executive_permitted=-1; }
            else{is_executive_permitted=0;
                if(object.getBoolean("is_executive_permitted")){
                    is_executive_permitted=1;
                }
            }
            if(object.isNull("is_technical_permitted")){ is_technical_permitted=-1; }
            else{is_technical_permitted=0;
                if(object.getBoolean("is_technical_permitted")){
                    is_technical_permitted=1;
                }

            }
            if(object.isNull("is_producer_permitted")){ is_producer_permitted=-1; }
            else{
                is_producer_permitted=0;
                if(object.getBoolean("is_producer_permitted")){
                    is_producer_permitted=1;
                }
            }
            if(object.isNull("is_curator_permitted")){ is_curator_permitted=-1; }
            else{
                is_curator_permitted=0;
                if(object.getBoolean("is_curator_permitted")){
                    is_curator_permitted=1;
                }
            }
            if(object.isNull("is_contactor_permitted")){ is_contactor_permitted=-1; }
            else{
                is_contactor_permitted=0;
                if(object.getBoolean("is_contactor_permitted")){
                    is_contactor_permitted=1;
                }
            }
            getAccepts();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getPositions(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity) getActivity()).MAIN_URL+"positions/?key=CHECKLIST";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setPositions(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_LONG).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setPositions(JSONArray array){
        try{
            boxForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONArray positions=object.getJSONArray("positions");
                List<CheckListBoxRowForm> rowForms=new ArrayList<>();
                for(int j=0;j<positions.length();j++){
                    JSONObject pos=positions.getJSONObject(j);
                    CheckListBoxRowForm form=new CheckListBoxRowForm(pos.getString("name"),"",1);
                    form.setId(pos.getString("id"));
                    rowForms.add(form);
                }
                boolean galo=i%2==0;
                String name=object.getString("name");
                Log.d("thisname",name);
                boxForms.add(new CheckListBoxForm(name,galo,rowForms));
                Log.d("length",positions.length()+" "+positions.toString());
            }
            adapter.notifyDataSetChanged();
            getRequest();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getAccepts(){
        String url=((MainActivity)getActivity()).MAIN_URL;
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
                Toast.makeText(getActivity(), "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
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
                Toast.makeText(getActivity(), "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, url+"points/"+point, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                try {
                    JSONObject contactor=response.getJSONObject("contactor");
                    JSONObject producer=response.getJSONObject("producer");
//                    JSONObject curator=response.getJSONObject("curator");
                    if(is_producer_permitted>-1)
                        strings.set(2,new String[]{producer.getString("fullname"),producer.getString("role"), "-1"});
  /*                  if(is_curator_permitted>-1)
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
                Toast.makeText(getActivity(), "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(admin_execRequest);
        ((MainActivity)getActivity()).requestQueue.add(admin_techRequest);
        ((MainActivity)getActivity()).requestQueue.add(admin_contRequest);
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
        int l=0;
        for(String[] a:strings){
            if(a.length==0){
                l++;
                continue;
            }
            Log.d("Strings"+l,a[0]+" "+a[1]+" "+a[2]);
            String name=a[0];
            String role=((MainActivity)getActivity()).positions.get(a[1]);
            String dep="";
            if(!a[2].equals("-1") && ((MainActivity)getActivity()).dpids.indexOf(a[2])!=-1) {
                dep= ((MainActivity) getActivity()).departments.get(((MainActivity) getActivity()).dpids.indexOf(a[2]));
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
