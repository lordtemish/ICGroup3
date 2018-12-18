package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptServiceAdapter;
import com.studio.dynamica.icgroup.Adapters.RadioAdapter;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.Forms.RadioForm;
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
public class AddNewJalobaFragment extends Fragment {

    TextView workerTextView, qualityTextView, departmentLabelTextView, employeeLabelTextView, answerTextView, acceptLabelTextView, commentLabelTextView;
    Spinner departmentChangeSpinner, employeeChangeSpinner;
    FrameLayout spinnerDepartFrameImage, spinnerEmplFrameImage, progressLayout;
    ConstraintLayout answerButton;
    EditText nameEditText;
    LinearLayout employeeChooseLayout;
    RecyclerView radioRecycler, acceptRecycler;
    RadioAdapter adapter;
    List<RadioForm> radioFormList, defList, comList;
    List<String> employees, departments, emids, dpids, emplSpin, depaSpin, userIds;
    ArrayAdapter<String> depaAdapter, emplAdapter;
    boolean empls=false;
    String id="", emid="", dpid="";
    int location=0;

    List<String[] > strings;
    List<ChooseAcceptForm> acceptForms;
    ChooseAcceptServiceAdapter serviceAdapter;
    int is_executive_seen=-1,is_curator_seen=-1, is_producer_seen=-1;
    public AddNewJalobaFragment() {
        // Required empty public constructor
    }
    private void SpinnerSets(){
        emplAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,emplSpin){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};
        spinnerEmplFrameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeChangeSpinner.performClick();
            }
        });
        employeeChangeSpinner.setAdapter(emplAdapter);

        depaAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,depaSpin){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};
        spinnerDepartFrameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                departmentChangeSpinner.performClick();
            }
        });
        departmentChangeSpinner.setAdapter(depaAdapter);
    }
    private void createViews(View view){
        workerTextView=(TextView)view.findViewById(R.id.workerTextView);
        qualityTextView=(TextView)view.findViewById(R.id.qualityTextView);
        answerTextView=(TextView)view.findViewById(R.id.answerTextView);
        commentLabelTextView=(TextView)view.findViewById(R.id.commentLabelTextView);
        acceptLabelTextView=(TextView)view.findViewById(R.id.acceptLabelTextView);
        departmentLabelTextView=(TextView)view.findViewById(R.id.departmentLabelTextView);
        employeeLabelTextView=(TextView)view.findViewById(R.id.employeeLabelTextView);
        nameEditText=(EditText)view.findViewById(R.id.nameEditText);
        answerButton=(ConstraintLayout)view.findViewById(R.id.answerButton);
        departmentChangeSpinner=(Spinner)view.findViewById(R.id.departmentChangeSpinner);
        employeeChangeSpinner=(Spinner)view.findViewById(R.id.employeeChangeSpinner);
        spinnerDepartFrameImage=(FrameLayout)view.findViewById(R.id.spinnerDepartFrameImage);
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        spinnerEmplFrameImage=(FrameLayout)view.findViewById(R.id.spinnerEmplFrameImage);
        employeeChooseLayout=(LinearLayout)view.findViewById(R.id.employeeChooseLayout);
        radioRecycler=(RecyclerView) view.findViewById(R.id.radioRecycler);
        acceptRecycler=(RecyclerView) view.findViewById(R.id.acceptRecycler);

        strings=new ArrayList<>();
        strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});

        acceptForms=new ArrayList<>();
        userIds=new ArrayList<>();

        radioFormList=new ArrayList<>();
        defList=new ArrayList<>();
        comList=new ArrayList<>();
        emplSpin=new ArrayList<>();
        depaSpin=new ArrayList<>();depaSpin.add("Работники объекта");
        employees=new ArrayList<>();
        emids=new ArrayList<>();
        departments=new ArrayList<>();
        dpids=new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        location=getArguments().getInt("location");
        id=getArguments().getString("id");
        View view = inflater.inflate(R.layout.fragment_add_new_jaloba, container, false);
        createViews(view);
        SpinnerSets();
        ((MainActivity)getActivity()).setType("demibold", workerTextView, qualityTextView, answerTextView);
        ((MainActivity)getActivity()).setType("light", departmentLabelTextView, employeeLabelTextView, commentLabelTextView, acceptLabelTextView);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(radioRecycler,LinearLayoutManager.VERTICAL);
        adapter=new RadioAdapter(radioFormList);
        adapter.setCheckable(true);
        radioRecycler.setAdapter(adapter);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecycler,LinearLayoutManager.VERTICAL);
        serviceAdapter=new ChooseAcceptServiceAdapter(acceptForms);
        acceptRecycler.setAdapter(serviceAdapter);

        workerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empls=true;
                setShown();
            }
        });
        qualityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empls=false;
                setShown();
            }
        });
        setShown();
        getReasons();
        getDepartments();
        getEmployees();
        getData();
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIt();
            }
        });
        return view;
    }

    private void setShown(){
        radioFormList.clear();
        if(empls){
            qualityTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
            workerTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            employeeChooseLayout.setVisibility(View.VISIBLE);
            radioFormList.addAll(defList);
        }
        else{
            qualityTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            workerTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
            employeeChooseLayout.setVisibility(View.GONE);
            radioFormList.addAll(comList);
        }
        adapter.notifyDataSetChanged();
    }

    private void getReasons() {
        progressLayout.setVisibility(View.VISIBLE);
        String url = ((MainActivity) getActivity()).MAIN_URL + "reasons/";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setResults(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "роблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }) {@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setResults(JSONArray array){
        try{
            defList.clear();
            comList.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                RadioForm form=new RadioForm(false,object.getString("name"));
                form.setId(object.getString("id"));
                if(object.getString("kind").contains("COM")){
                    comList.add(form);
                }
                else{
                    defList.add(form);
                }
            }
          setShown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getDepartments(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"departments/?location="+location;
        JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setDepartments(response);
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
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setDepartments(JSONArray array){
        try{
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                departments.add(object.getString("name"));
                dpids.add(object.getString("id"));
                depaSpin.add(object.getString("name"));
            }
            depaAdapter.notifyDataSetChanged();
            ((MainActivity)getActivity()).setDepartments(departments);
            ((MainActivity)getActivity()).setDpids(dpids);
            departmentChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i>0) {
                        dpid = dpids.get(i - 1);
                    }
                    else{
                        dpid="";

                    }
                    getEmployees();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getEmployees(){
        emid="";
        progressLayout.setVisibility(View.VISIBLE);
        if(dpid.length()>0){
            String url=((MainActivity)getActivity()).MAIN_URL+"employees/?department="+dpid;
            JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setEmployees(response);
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
            ((MainActivity)getActivity()).requestQueue.add(objectRequest);
        }
        else{
            String url=((MainActivity)getActivity()).MAIN_URL+"workers/?point="+id;
            JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setWorkers(response);
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
            ((MainActivity)getActivity()).requestQueue.add(objectRequest);
        }
    }
    private void setEmployees(JSONArray array){
        try {
            emplSpin.clear();
            userIds.clear();
            emplSpin.add("Выберите сотрудника");
            employeeChangeSpinner.setSelection(0);
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject user=object.getJSONObject("user");

                String na=user.getString("fullname")+"\n"+((MainActivity)getActivity()).positions.get(user.getString("role"));
                employees.add(na);
                emids.add(object.getString("id"));
                userIds.add(user.getString("id"));
                emplSpin.add(na);
                employeeChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i>0){
                            emid=emids.get(i-1);
                        }
                        else{
                            progressLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        progressLayout.setVisibility(View.GONE);
                    }
                });
            }
            emplAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setWorkers(JSONArray array){
        try{
            emplSpin.clear();
            userIds.clear();
            emplSpin.add("Выберите сотрудника");
            employeeChangeSpinner.setSelection(0);
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject user=object.getJSONObject("user");
                String id=object.getString("id"), userId=user.getString("id"), name=user.getString("fullname")+" НПО";
                employees.add(name);
                emids.add(id);userIds.add(userId);
                emplSpin.add(name);
                employeeChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i>0){
                            emid=emids.get(i-1);
                        }
                        else{
                            progressLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        progressLayout.setVisibility(View.GONE);
                    }
                });
            }
            emplAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getData(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL;
        String pU=url+"points/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, pU, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                try {
                    if (!response.isNull("curator")) {
                        strings.set(0, new String[]{});
                        JSONObject cura = response.getJSONObject("curator");
                        String name=cura.getString("fullname");
                        String role=cura.getString("role");
                        strings.set(0, new String[]{name, role});
                    } else {
                        strings.set(0, new String[]{});
                    }
                    if (!response.isNull("producer")) {
                        JSONObject cura = response.getJSONObject("producer");
                        String name=cura.getString("fullname");
                        String role=cura.getString("role");
                        strings.set(1, new String[]{name, role});
                    }
                    else{
                        strings.set(1, new String[]{});
                    }
                    checkAccepts();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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

        JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, url + "employees/?user__role=ADMIN_EXECUTIVE", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                if(response.length()>0){
                    try {
                        JSONObject object=response.getJSONObject(0);
                        JSONObject cura=object.getJSONObject("user");
                        String name=cura.getString("fullname");
                        String role=cura.getString("role");
                        strings.set(2, new String[]{name, role});
                        checkAccepts();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
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
    }

    private void checkAccepts(){
        acceptForms.clear();
        for(int i=0;i<strings.size();i++){
            final String[] string=strings.get(i);
            if(string.length>0){
                ChooseAcceptForm acceptForm=new ChooseAcceptForm("",string[0],((MainActivity)getActivity()).positions.get(string[1]),false);
                acceptForm.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setChecked(strings.indexOf(string));
                    }
                });
                acceptForms.add(acceptForm);
            }
        }
        Log.d("Strings "+acceptForms.size(),acceptForms.toString());
        serviceAdapter.notifyDataSetChanged();
    }
    private void setChecked(int a){
        switch(a){
            case 0:
                is_curator_seen*=(-1);
                break;
            case 1:
                is_producer_seen*=(-1);
                break;
            case 2:
                is_executive_seen*=(-1);
                break;
        }
        Log.d("permission","curator: "+is_curator_seen+" , producer: "+is_producer_seen+" , exec: "+is_executive_seen);
    }

    private void saveIt(){
        try {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "complaints/";
            if (nameEditText.getText().length() < 1) {
                Toast.makeText(getActivity(), "Напишите комментарий", Toast.LENGTH_SHORT).show();
                progressLayout.setVisibility(View.GONE);
            } else {
                if(empls && (emid.equals(""))){
                    Toast.makeText(getActivity(), "Выберите все пункты", Toast.LENGTH_SHORT).show();
                    progressLayout.setVisibility(View.GONE);
                }
                else {
                    JSONObject params = new JSONObject();
                    JSONArray reasons=new JSONArray();
                    params.put("point",Integer.parseInt( id));
                    params.put("content", nameEditText.getText().toString());
                    if(empls){
                        params.put("defendant", Integer.parseInt(userIds.get(emids.indexOf(emid))));
                    }
                    else{
                        params.put("defendant", null);
                    }
                    for(RadioForm radioForm:radioFormList){
                        if(radioForm.isStatus()){
                            reasons.put(Integer.parseInt(radioForm.getId()));
                        }
                    }
                    params.put("reasons", reasons);
                    if(is_executive_seen>-1){
                        params.put("is_executive_seen", false);
                    }
                    if(is_producer_seen>-1){params.put("is_producer_seen", false);}
                    if(is_curator_seen>-1){params.put("is_curator_seen", false);}
                    Log.d("parameters", params.toString());

                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressLayout.setVisibility(View.GONE);
                            Log.d("responseAnswer", response.toString());
                            Toast.makeText(getActivity(), "Жалоба отправлена", Toast.LENGTH_SHORT).show();
                            ((MainActivity)getActivity()).onBackPressed();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                                progressLayout.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Проблемы соеденения ", Toast.LENGTH_SHORT).show();
                        }
                    }){@Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                        return headers;
                    }};
                    ((MainActivity)getActivity()).requestQueue.add(request);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            progressLayout.setVisibility(View.GONE);
        }
    }
}
