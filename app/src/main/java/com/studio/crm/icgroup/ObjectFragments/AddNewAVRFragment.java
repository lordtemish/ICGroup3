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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.crm.icgroup.Adapters.ClientControlSpinnersAdapter;
import com.studio.crm.icgroup.Adapters.RadioUserAdapter;
import com.studio.crm.icgroup.Adapters.ServicesAdapter;
import com.studio.crm.icgroup.Forms.ChooseAcceptForm;
import com.studio.crm.icgroup.Forms.RadioUserForm;
import com.studio.crm.icgroup.Forms.ServiceForm;
import com.studio.crm.icgroup.Forms.SpinnerForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewAVRFragment extends Fragment {
    RecyclerView setAcceptRecyclerView, spinnersRecycler, recyclerView, taskRecyclerView;
    List<Spinner> spinners;
    List<TextView> rateTexts;
    TextView taskButton,acceptLabelTextView, createNewTextView, commentsLabelTextView, mainObjectTitle, addTextView, deleteTextView;
    FrameLayout progressLayout;
    LinearLayout buttonsLayout, taskLayout;
    List<RadioUserForm> radioUserForms;
    ServicesAdapter servicesAdapter;
    List<ServiceForm> serviceForms;
    RadioUserAdapter userAdapter;
    RadioButton standartRadio,arbitaryRadio,taskRadio;
    List<ChooseAcceptForm> acceptForms;
    ConstraintLayout createNewLayout;
    ChooseAcceptAdapter adapter;
    EditText commentEditText, taskEditText;
    List<SpinnerForm> spinnerForms, arbitaryForms;
    ClientControlSpinnersAdapter spinnersAdapter, arbitaryAdapter;
    String id, author;
    int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1, taskid=-1;
    List<String[]> strings;
    public AddNewAVRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        author=getArguments().getString("author");
        View view=inflater.inflate(R.layout.fragment_add_new_avr, container, false);
        createViews(view);
        //   spinnerSet();
        setFonttype();
        ((MainActivity) getActivity()).setRecyclerViewOrientation(setAcceptRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(taskRecyclerView, LinearLayoutManager.VERTICAL);
        acceptForms=new ArrayList<>();
        adapter=new ChooseAcceptAdapter(acceptForms);
        setAcceptRecyclerView.setAdapter(adapter);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(spinnersRecycler,LinearLayoutManager.VERTICAL);
        serviceForms=new ArrayList<>();
        spinnerForms=new ArrayList<>();
        arbitaryForms=new ArrayList<>();arbitaryForms.add(new SpinnerForm());
        spinnersAdapter=new ClientControlSpinnersAdapter(spinnerForms);
        arbitaryAdapter=new ClientControlSpinnersAdapter(arbitaryForms);arbitaryAdapter.setChangeable(true);
        servicesAdapter=new ServicesAdapter(serviceForms);
        taskRecyclerView.setAdapter(servicesAdapter);
        recyclerView.setAdapter(arbitaryAdapter );

        spinnersRecycler.setAdapter(spinnersAdapter);

        getPositions();
        standartRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnersRecycler.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                buttonsLayout.setVisibility(View.GONE);
                taskLayout.setVisibility(View.GONE);
            }
        });
        arbitaryRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnersRecycler.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                buttonsLayout.setVisibility(View.VISIBLE);
                taskLayout.setVisibility(View.GONE);
            }
        });
        taskRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnersRecycler.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                buttonsLayout.setVisibility(View.VISIBLE);
                taskLayout.setVisibility(View.VISIBLE);
            }
        });

        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arbitaryForms.add(new SpinnerForm());
                arbitaryAdapter.notifyDataSetChanged();
            }
        });
        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arbitaryForms.size()>1) {
                    arbitaryForms.remove(arbitaryForms.size() - 1);
                    arbitaryAdapter.notifyDataSetChanged();
                }
            }
        });

        taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTask();
            }
        });
        createNewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveThis();
            }
        });
        return view;
    }
    /*private void spinnerSet(){
        for(int i=0;i<3;i++){

            String[] numbers={"1","2","3","4","5"};
            ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.rate_spinner_item,numbers){
                @Override
                public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view=null;
                    view=super.getDropDownView(position, convertView, parent);
                    try {

                        setTypeface("demibold", ((TextView) view));
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    return view;
                }

                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view= super.getView(position, convertView, parent);
                    view.setBackgroundResource(R.drawable.green_circle_line);
                    setTypeface("demibold",((TextView)view));
                    return view;
                }
            };
            spinners.get(i).setAdapter(spinnerAdapter);
        }
    }*/
    private void getPositions(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity) getActivity()).MAIN_URL+"positions/?key=PERFORMANCE";
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
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONArray positions=object.getJSONArray("positions");
                for(int j=0;j<positions.length();j++){
                    JSONObject pos=positions.getJSONObject(j);
                    spinnerForms.add(new SpinnerForm(pos.getString("id"),pos.getString("name")));

                }
                Log.d("length",positions.length()+" "+positions.toString());
            }
            spinnersAdapter.notifyDataSetChanged();
            getAccepts();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setFonttype(){
        setTypeface("demibold", createNewTextView, taskButton);
        setTypeface("it", mainObjectTitle);
        setTypeface("light",acceptLabelTextView, commentsLabelTextView, commentEditText, taskEditText);
    }
    private void getTask(){
        if(taskEditText.getText().length()==0){
            Toast.makeText(getActivity(), "Укажите id задачи", Toast.LENGTH_SHORT).show();
        }
        else {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "tasks/" + taskEditText.getText()+"/avr/";
            JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    setTask(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode==404){
                        serviceForms.clear();servicesAdapter.notifyDataSetChanged();
                        taskid=-1;
                        Toast.makeText(getActivity(), "Задача не найдена", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    progressLayout.setVisibility(View.GONE);
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
        }
    }
    private void setTask(JSONObject object){
        try {
            int as = object.getInt("priority");
            String priority = getPriority(as);
            String status = object.getString("status");
            JSONObject kind = object.getJSONObject("kind");
            String kindText = kind.getString("name");
            String fname="", dpStr="";
            if(!object.isNull("respondent")) {
                JSONObject respondent = object.getJSONObject("respondent");
                JSONObject user = respondent.getJSONObject("user");
                JSONObject department = respondent.getJSONObject("department");
                dpStr=department.getString("name");
                fname = user.getString("fullname");
                String[] a = fname.split(" ");
                if (fname.length() > 20) {
                    if (a.length > 1) {
                        fname = a[0] + " " + a[1];
                    }
                }
            }
            String created_at = object.getString("created_at"), deadline = object.getString("deadline");

            if(deadline.length()==20 && deadline.length()>1){
                deadline=deadline.substring(0,deadline.length()-1)+".0Z";
            }
            if(created_at.length()==20){
                created_at=created_at.substring(0,created_at.length()-1)+".0Z";
            }
            Date created = ((MainActivity) getActivity()).getNowDate(created_at), dead = ((MainActivity) getActivity()).getNowDate(deadline);
            Date now = new Date();
            long wDays = dead.getTime() - created.getTime(), nDays = now.getTime() - created.getTime();
            int days = Integer.parseInt(TimeUnit.DAYS.convert(wDays, TimeUnit.MILLISECONDS) + "");
            int nowdays = Integer.parseInt(TimeUnit.DAYS.convert(nDays, TimeUnit.MILLISECONDS) + "");
            if (days - nowdays > 0) {
                Log.d("nowdays", days + " " + nowdays);
                nowdays = days - nowdays;
                Log.d("nowdays", (days - nowdays > 0) + "");
            } else {
                nowdays = 0;
            }
            ServiceForm serviceForm = new ServiceForm(kindText, fname, dpStr, status, priority, nowdays, days);
            serviceForm.setId(object.getString("id"));
            taskid=object.getInt("id");
            serviceForms.clear();
            serviceForms.add(serviceForm);
            servicesAdapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }
    }
    private String getPriority(int ss){
        String s="";
        switch (ss){
            case 1:
                s="Низкий";
                break;
            case 2:
                s="Средний";
                break;
            case 3:
                s="Высокий";
                break;
            default:
        }
        return s;
    }
    private void saveThis(){
        try {
            if(taskid==-1 && taskRadio.isChecked()){
                Toast.makeText(getActivity(), "Укажите id задачи", Toast.LENGTH_SHORT).show();
            }
            else {
                progressLayout.setVisibility(View.VISIBLE);
                String url = ((MainActivity) getActivity()).MAIN_URL + "controls/";
                JSONObject params = new JSONObject();
                JSONArray permits=new JSONArray();
                params.put("author", author);
                params.put("kind", "PERFORMANCE");
                params.put("point", id);
                String avr_type = "STANDART";
                if (arbitaryRadio.isChecked()) {
                    avr_type = "ARBITRARY";
                }
                if (taskRadio.isChecked()) {
                    avr_type = "TASK";
                    params.put("task", taskid);
                }
                params.put("avr_type", avr_type);

                if (is_executive_permitted == 0) {
                    params.put("is_executive_permitted", false);
                }
                if (is_contactor_permitted == 0) {
                    params.put("is_contactor_permitted", false);
                }
                if (is_curator_permitted == 0) {
                    params.put("is_curator_permitted", false);
                }
                if (is_producer_permitted == 0) {
                    params.put("is_producer_permitted", false);
                }
                if (is_technical_permitted == 0) {
                    params.put("is_technical_permitted", false);
                }

                params.put("permits",permits);

                String comm = commentEditText.getText() + "";
                if (comm.length() > 0) {
                    params.put("comment", comm);
                }
                JSONArray array = new JSONArray();
                if (standartRadio.isChecked())
                    for (SpinnerForm form : spinnerForms) {
                        JSONObject object = new JSONObject();

                        object.put("position", form.getId());
                        object.put("rate", form.getNum());
                        if (form.getText().length() > 0)
                            object.put("comment", form.getText());
                        array.put(object);
                    }
                else {
                    for (SpinnerForm form : arbitaryForms) {
                        JSONObject object = new JSONObject();
                        if(form.getName().length()==0){
                            continue;
                        }
                        object.put("name", form.getName());
                        object.put("rate", form.getNum());
                        if (form.getText().length() > 0)
                            object.put("comment", form.getText());
                        array.put(object);
                    }
                }
                params.put("positions", array);
                Log.d("saving " + author, url + "\n" + params.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        Log.d("SAVED", response.toString());
                        ((MainActivity) getActivity()).onBackPressed();
                        Toast.makeText(getActivity(), "Акт выполненных работ добавлек", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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
                ((MainActivity) getActivity()).requestQueue.add(jsonObjectRequest);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setTypeface(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity)getActivity()).getTypeFace(s));
        }
    }
    private void createViews(View view){
        strings=new ArrayList<>();strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
        setAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.setAcceptRecyclerView);
        spinnersRecycler=(RecyclerView) view.findViewById(R.id.spinnersRecycler);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        taskRecyclerView=(RecyclerView) view.findViewById(R.id.taskRecyclerView);
        acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
        taskButton=(TextView) view.findViewById(R.id.taskButton);
        createNewTextView=(TextView) view.findViewById(R.id.createNewTextView);
        deleteTextView=(TextView) view.findViewById(R.id.deleteTextView);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        taskEditText=(EditText) view.findViewById(R.id.taskEditText);
        commentsLabelTextView=(TextView) view.findViewById(R.id.commentsLabelTextView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        buttonsLayout=(LinearLayout) view.findViewById(R.id.buttonsLayout);
        createNewLayout=(ConstraintLayout) view.findViewById(R.id.createNewLayout);
        taskLayout=(LinearLayout) view.findViewById(R.id.taskLayout);

        standartRadio=(RadioButton)view.findViewById(R.id.standartRadio);
        arbitaryRadio=(RadioButton)view.findViewById(R.id.arbitaryRadio);
        taskRadio=(RadioButton)view.findViewById(R.id.taskRadio);
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
        adapter.notifyDataSetChanged();
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
        adapter.notifyDataSetChanged();
    }
}
