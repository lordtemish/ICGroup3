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
import com.studio.crm.icgroup.Adapters.AcceptAdapter;
import com.studio.crm.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.crm.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.Forms.CheckListBoxForm;
import com.studio.crm.icgroup.Forms.CheckListBoxRowForm;
import com.studio.crm.icgroup.Forms.ChooseAcceptForm;
import com.studio.crm.icgroup.Forms.SpinnerForm;
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
public class AddNewCheckListFragment extends Fragment {

    RecyclerView recyclerView, acceptRecyclerView;
    TextView mainObjectTitle, addTextView,acceptLabel;
    boolean media=false;
    RadioButton mediaRadio;
    EditText commentEditText;
    ConstraintLayout addNewLayout;
    FrameLayout commentLayout, progressLayout;
    String id, author;
    List<CheckListBoxForm> boxForms;
    CheckListBoxAdapter adapter;
    List<String[]> strings;
    List<ChooseAcceptForm> acceptForms;
    ChooseAcceptAdapter acceptAdapter;
    int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
    public AddNewCheckListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        author=getArguments().getString("author");
        View view=inflater.inflate(R.layout.fragment_add_new_check_list, container, false);
        createViews(view);
        setFonttype();

        mediaRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaClicked();
            }
        });

        acceptForms=new ArrayList<ChooseAcceptForm>();
         acceptAdapter=new ChooseAcceptAdapter(acceptForms);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView,LinearLayoutManager.VERTICAL);
        acceptRecyclerView.setAdapter(acceptAdapter);

        boxForms=new ArrayList<>();
        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        adapter=new CheckListBoxAdapter(boxForms);
        recyclerView.setAdapter(adapter);
        getPositions();


        addNewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePositions();
            }
        });
        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
        acceptLabel=(TextView) view.findViewById(R.id.acceptLabel);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        mediaRadio=(RadioButton) view.findViewById(R.id.mediaRadio);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        commentLayout=(FrameLayout) view.findViewById(R.id.commentLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        addNewLayout=(ConstraintLayout) view.findViewById(R.id.addNewLayout);
        strings=new ArrayList<>();
        strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", mediaRadio, addTextView);
        setTypeFace("light", acceptLabel);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }

    public void mediaClicked(){
        if(media){
            mediaRadio.setChecked(false);
            mediaRadio.setTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setFocusableInTouchMode(false);
            commentEditText.setFocusable(false);
            commentEditText.setText("");
            commentLayout.setBackgroundResource((R.drawable.grey_line));
            media=false;
        }
        else{
            mediaRadio.setChecked(true);
            mediaRadio.setTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setFocusableInTouchMode(true);
            commentEditText.setFocusable(true);
            commentLayout.setBackgroundResource((R.drawable.black_line));
            media=true;
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
                String id=object.getString("id");
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
                CheckListBoxForm forma1=new CheckListBoxForm(name,galo,rowForms);
                forma1.setId(id);
                boxForms.add(forma1);
                Log.d("length",positions.length()+" "+positions.toString());
            }
            adapter.notifyDataSetChanged();
            getAccepts();
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
                    catch (Exception e){

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
    private void savePositions(){
        try {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "controls/";
            CheckListBoxForm form1=boxForms.get(adapter.getOpen());
            JSONObject params = new JSONObject();
            JSONArray permits=new JSONArray();
            params.put("author", author);
            params.put("kind", "CHECKLIST");
            params.put("checklist", Integer.parseInt(form1.getId()));
            params.put("point", id);
            String comm=commentEditText.getText()+"";
            if(comm.length()>0){
                params.put("comment",comm);
            }
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
            JSONArray array=new JSONArray();

                for(CheckListBoxRowForm form:form1.getList()) {
                    JSONObject object = new JSONObject();
                    object.put("position", form.getId());
                    object.put("rate", form.getRate());
                    array.put(object);
                }


            params.put("positions",array);
            Log.d("params",params.toString());
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Чек-лист добавлен", Toast.LENGTH_LONG).show();
                    ((MainActivity)getActivity()).onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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
            e.printStackTrace();
        }
    }
}
