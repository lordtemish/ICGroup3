package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AcceptAdapter;
import com.studio.crm.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.crm.icgroup.Adapters.RateStarsAdapter;
import com.studio.crm.icgroup.Forms.ChooseAcceptForm;
import com.studio.crm.icgroup.R;

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
public class AddNewSvodkaFragment extends Fragment {

    RecyclerView rateRecyclerView, acceptRecyclerView;
    TextView mainObjectTitle,acceptLabelTextView,commentsLabelTextView, addNewTextView;
    List<EditText> editTexts;
    List<TextView> textViews;
    EditText commentEditText2;
    RateStarsAdapter rateStarsAdapter;
    String id, author;
    FrameLayout progressLayout;
    ConstraintLayout addNewLayout;
    int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
    List<String[]> strings;
    List<ChooseAcceptForm> acceptForms;
    ChooseAcceptAdapter acceptAdapter;
    public AddNewSvodkaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        author=getArguments().getString("author");
        View view=inflater.inflate(R.layout.fragment_add_new_svodka, container, false);
        createViews(view);
        setFonttype();
        ((MainActivity)getActivity()).setRecyclerViewOrientation(rateRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView,LinearLayoutManager.VERTICAL);

        acceptForms=new ArrayList<>();
         rateStarsAdapter=new RateStarsAdapter(true);
         acceptAdapter=new ChooseAcceptAdapter(acceptForms);

        rateRecyclerView.setAdapter(rateStarsAdapter);
        acceptRecyclerView.setAdapter(acceptAdapter);

        addNewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveShit();
            }
        });
        getAccepts();
        return view;
    }
    private void createViews(View view){
        strings=new ArrayList<>();strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
            rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
            mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
        commentsLabelTextView=(TextView) view.findViewById(R.id.commentsLabelTextView);
        addNewTextView=(TextView) view.findViewById(R.id.addNewTextView);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        addNewLayout=(ConstraintLayout) view.findViewById(R.id.addNewLayout);

            editTexts=new ArrayList<>();
            textViews=new ArrayList<>();
            editTexts.add((EditText)view.findViewById(R.id.cityEditText));
        commentEditText2=((EditText)view.findViewById(R.id.commentEditText2));
            textViews.add((TextView) view.findViewById(R.id.cityTextView));
              editTexts.add((EditText)view.findViewById(R.id.addressEditText));
              textViews.add((TextView) view.findViewById(R.id.addressTextView));
            editTexts.add((EditText)view.findViewById(R.id.clientEditText));
            textViews.add((TextView) view.findViewById(R.id.clientTextView));
            editTexts.add((EditText)view.findViewById(R.id.positionEditText));
            textViews.add((TextView) view.findViewById(R.id.positionTextView));
    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", addNewTextView);
        setTypeFace("light",acceptLabelTextView,commentsLabelTextView);
        for(TextView i :textViews) {
            setTypeFace("light",i);
        }
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
    private void saveShit(){
        try {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "controls/";
            JSONObject params = new JSONObject();
            JSONArray permits=new JSONArray();
            params.put("author", author);
            params.put("kind", "RINGING");
            params.put("point", id);


            if(is_executive_permitted==0){
                params.put("is_executive_permitted",false);
            }
            if(is_contactor_permitted==0){
                params.put("is_contactor_permitted",false);
            }
            if(is_curator_permitted==0){
                params.put("is_curator_permitted",false);
            }
            if(is_producer_permitted==0){
                params.put("is_producer_permitted",false);
            }
            if(is_technical_permitted==0){
                params.put("is_technical_permitted",false);
            }
            params.put("permits",permits);


            String comm=commentEditText2.getText()+"";
            if(comm.length()>0){
                params.put("comment",comm);
            }
            JSONArray positions=new JSONArray();
            JSONObject position=new JSONObject();
            position.put("position","1");
            position.put("rate",rateStarsAdapter.getRate());
            positions.put(position);
            params.put("positions", positions);
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Сводка добавлена", Toast.LENGTH_LONG).show();
                    Log.d("responseSvodka", response.toString());
                    ((MainActivity)getActivity()).onBackPressed();
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
            ((MainActivity)getActivity()).requestQueue.add(jsonObjectRequest);
        }
        catch (Exception e){

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
        JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, url+"points/"+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                try {
                    JSONObject contactor=null;
                    if(!response.isNull("contactor")){
                        contactor=response.getJSONObject("contactor");
                        strings.set(4,new String[]{contactor.getString("fullname"),contactor.getString("role"), "-1"});
                    }
                    JSONObject producer=null;
                    if(!response.isNull("producer")){
                        producer=response.getJSONObject("producer");
                        strings.set(2,new String[]{producer.getString("fullname"),producer.getString("role"), "-1"});
                    }
                    JSONObject curator=null;
                    if(!response.isNull("curator")){
                        curator=response.getJSONObject("curator");
                        strings.set(3,new String[]{curator.getString("fullname"),curator.getString("role"), "-1"});
                    }
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
            String role=((MainActivity)getActivity()).positions.get(a[1]);
            String dep="";
            if(!a[2].equals("-1") && ((MainActivity)getActivity()).dpids.indexOf(a[2])!=-1) {
                dep= ((MainActivity) getActivity()).departments.get(((MainActivity) getActivity()).dpids.indexOf(a[2]));
            }
            Boolean cl=l==4;
            ChooseAcceptForm acceptForm=new ChooseAcceptForm(dep,name, role,cl);
            final int num=l;
            acceptForm.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //=-1,=-1,=-1,=-1,
                    switch (num){
                        case 0:
                            is_executive_permitted++;
                            is_executive_permitted=is_executive_permitted%2;
                            break;
                        case 1:
                            is_technical_permitted++;
                            is_technical_permitted=is_technical_permitted%2;
                            break;
                        case 2:
                            is_producer_permitted++;
                            is_producer_permitted=is_producer_permitted%2;
                            break;
                        case 3:
                            is_curator_permitted++;
                            is_curator_permitted=is_curator_permitted%2;
                            break;
                        case 4:
                            is_contactor_permitted++;
                            is_contactor_permitted=is_contactor_permitted%2;
                            break;
                    }
                    Log.d("permits",is_executive_permitted+" "+is_technical_permitted+" "+is_producer_permitted+" "+is_curator_permitted+" "+is_contactor_permitted);
                }
            });
            acceptForms.add(acceptForm);
            l++;
        }
        acceptAdapter.notifyDataSetChanged();
    }
}
