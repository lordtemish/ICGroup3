package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptServiceAdapter;
import com.studio.dynamica.icgroup.Adapters.FilesAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.CalendarView;
import com.studio.dynamica.icgroup.ExtraFragments.TimePickView;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewServiceFragment extends Fragment {
    RecyclerView filesRecyclerView;
    CalendarView calendarView;
    TimePickView timePickView;
    FrameLayout commentLayout, progressLayout;
    EditText nameEditText, commentEditText;
    RadioGroup radioGroup;
    RadioButton todayRadio, dateRadio, mediaRadio, photoRadio;
    RadioButton[] priorityRadios={null,null,null};
    TextView ObjectTitle,priorityLabelTextView, placeLabelTextView, serviceTypeLabeLTextView, otvLabelTextView, dateLabelTextView, opisanieLabelTextView, cancelTextView, addTextView, filesTextView, needAcceptTextView;
    List<Spinner> spinners;
    List<FrameLayout> spinnerButtonFrames;
    List<ArrayList<String>> spinnerLists;
    RecyclerView answerUserRecyclerView;
    boolean media=false, photo=false;
    View.OnClickListener photoListener;
    int[][] choseindexes={{0,0},{0,0}};
     int click=0, clickx=0, clicky=0, xmax=0, ymax=0;
    Calendar cal;
    List<ArrayAdapter<String>> adapters;
    List<String> employees, departments, emids, dpids, taskkinds, tkids;
    String emid="", dpid="", id, tkid="";
    int location=1;
    List<String[] > strings;
    List<ChooseAcceptForm> acceptForms;
    ChooseAcceptServiceAdapter serviceAdapter;
    int is_executive_permitted=-1,is_technical_permitted=-1,is_chief_permitted=-1,is_contactor_permitted=-1,is_respondent_permitted=-1;
    public AddNewServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        location=getArguments().getInt("location");
        View view=inflater.inflate(R.layout.fragment_add_new_service, container, false);

        click=0;
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        this.cal=new GregorianCalendar(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
        createViews(view);
        setFonttype();
        setSpinners();
        mediaRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaClicked();
            }
        });
        photoListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                String[] mimeTypes = {"image/*", "video/*"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                ((MainActivity)getActivity()).startActivityForResult(intent,1);

            }
        };
        photoRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoclicked();
            }
        });
        todayRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                todayRadio.setChecked(true);
                timePickView.setVisibility(View.VISIBLE);
            }
        });

        dateRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRadioButtons();
                dateRadio.setChecked(true);
                calendarView.setVisibility(View.VISIBLE);
            }
        });


        ((MainActivity)getActivity()).setRecyclerViewOrientation(filesRecyclerView,LinearLayoutManager.HORIZONTAL);
        List<Bitmap> bitmaps=new ArrayList<Bitmap>();
        FilesAdapter filesAdapter=new FilesAdapter(bitmaps);
        filesRecyclerView.setAdapter(filesAdapter);
        ((MainActivity)getActivity()).setFileAdapterOs(bitmaps,filesAdapter);


        ((MainActivity) getActivity()).setRecyclerViewOrientation(answerUserRecyclerView, LinearLayoutManager.VERTICAL);
       acceptForms=new ArrayList<>();
        acceptForms.add(new ChooseAcceptForm("Отдел производства","Темирлан Алмасович","ОПУ",false));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","1Темирлан Алмасович","ОПУ",false));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","2Темирлан Алмасович","ОПУ",true));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","3Темирлан Алмасович","ОПУ",true));
        acceptForms.add(new ChooseAcceptForm("Отдел производства","4Темирлан Алмасович","ОПУ",false));
        serviceAdapter=new ChooseAcceptServiceAdapter(acceptForms);
        answerUserRecyclerView.setAdapter(serviceAdapter);
        progressLayout.setVisibility(View.GONE);
        getDepartments();
        getTaskKinds();
        getData();

        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveThis();
            }
        });
        return view;
    }
    private void setSpinners(){
        for(int i=0;i<3;i++){
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,spinnerLists.get(i)){
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
            spinnerButtonFrames.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinners.get(spinnerButtonFrames.indexOf(view)).performClick();
                }
            });
            spinners.get(i).setAdapter(adapter);
            adapters.add(adapter);
        }
    }
    private void setFonttype(){
        ObjectTitle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));
        priorityLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        nameEditText.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        commentEditText.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        placeLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        serviceTypeLabeLTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        otvLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        cancelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        addTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        dateLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        todayRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        dateRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        opisanieLabelTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        mediaRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        photoRadio.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        needAcceptTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        for(RadioButton i:priorityRadios){
            i.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        }
    }
    private void createViews(View view){
        spinners=new ArrayList<>();
        adapters=new ArrayList<>();employees=new ArrayList<>();departments=new ArrayList<>();emids=new ArrayList<>();dpids=new ArrayList<>();taskkinds=new ArrayList<>();tkids=new ArrayList<>();

        spinnerButtonFrames=new ArrayList<>();
        spinnerLists=new ArrayList<>();for(int i=0;i<3;i++){
            spinnerLists.add(new ArrayList<String>());
            spinnerLists.get(i).add("Выберите объект");
        }
        spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner1));spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner2));spinners.add((Spinner) view.findViewById(R.id.employeeChangeSpinner3));
        spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage1));spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage2));spinnerButtonFrames.add((FrameLayout) view.findViewById(R.id.spinnerFrameImage3));
        filesRecyclerView=(RecyclerView) view.findViewById(R.id.filesRecyclerView);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        commentLayout=(FrameLayout) view.findViewById(R.id.commentLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        nameEditText=(EditText) view.findViewById(R.id.nameEditText);

        radioGroup=(RadioGroup) view.findViewById(R.id.radioGroup);
        mediaRadio=(RadioButton) view.findViewById(R.id.mediaRadioButton);
        photoRadio=(RadioButton) view.findViewById(R.id.photoRadioButton);
        todayRadio=(RadioButton) view.findViewById(R.id.radioButton);
        dateRadio=(RadioButton) view.findViewById(R.id.radioButton1);

        calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        timePickView=(TimePickView) view.findViewById(R.id.timePickView);
        ObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
        placeLabelTextView=(TextView) view.findViewById(R.id.placeLabelTextView);
        serviceTypeLabeLTextView=(TextView) view.findViewById(R.id.serviceTypeLabelTextView);
        otvLabelTextView=(TextView) view.findViewById(R.id.otvLabelTextView);
        dateLabelTextView=(TextView) view.findViewById(R.id.dateLabelTextView);
        opisanieLabelTextView=(TextView) view.findViewById(R.id.opisanieLabelTextView);
        cancelTextView=(TextView) view.findViewById(R.id.cancelTextView);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        needAcceptTextView=(TextView) view.findViewById(R.id.needAcceptTextView);
        filesTextView=(TextView) view.findViewById(R.id.filesTextView);
        answerUserRecyclerView=(RecyclerView) view.findViewById(R.id.answerUserRecyclerView);

        priorityRadios[0]=(RadioButton) view.findViewById(R.id.priority1);
        priorityRadios[1]=(RadioButton) view.findViewById(R.id.priority2);
        priorityRadios[2]=(RadioButton) view.findViewById(R.id.priority3);

        strings=new ArrayList<>();
        strings.add(new String[]{});
        strings.add(new String[]{});
        strings.add(new String[]{});
        strings.add(new String[]{});
    }
    public void clearRadioButtons(){
        dateRadio.setChecked(false);
        todayRadio.setChecked(false);
        calendarView.setVisibility(View.GONE);
        timePickView.setVisibility(View.GONE);
    }
    private void photoclicked()
    {
        if(photo){
           photo=false;
           filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
           filesTextView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
        }
        else{
            photo=true;
            filesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
            filesTextView.setOnClickListener(photoListener);
        }
        photoRadio.setChecked(photo);
    }
    public void mediaClicked(){
        if(media){
            mediaRadio.setChecked(false);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setFocusableInTouchMode(false);
            commentEditText.setFocusable(false);
            commentEditText.setText("");
            commentLayout.setBackgroundResource((R.drawable.grey_line));
            media=false;
        }
        else{
            mediaRadio.setChecked(true);
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setFocusableInTouchMode(true);
            commentEditText.setFocusable(true);
            commentLayout.setBackgroundResource((R.drawable.black_line));
            media=true;
        }
    }

    private void sendFile(){
        String url=((MainActivity) getActivity()).MAIN_URL+"files/";
        SimpleMultiPartRequest multiPartRequest=new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fileRe",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }
        };



        ((MainActivity)getActivity()).requestQueue.add(multiPartRequest);
    }
    private void getEmployees(){
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
            progressLayout.setVisibility(View.GONE);
        }
    }
    private void setEmployees(JSONArray array){
        try {
            spinnerLists.get(2).clear();
            spinnerLists.get(2).add("Выберите объект");
            spinners.get(2).setSelection(0);
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject user=object.getJSONObject("user");
                String na=user.getString("fullname")+"\n"+((MainActivity)getActivity()).positions.get(user.getString("role"));
                employees.add(na);
                emids.add(object.getString("id"));
                spinnerLists.get(2).add(na);
                spinners.get(2).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getTaskKinds(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"taskkinds/";
        JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setTaskKinds(response);
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
    private void setTaskKinds(JSONArray array){
        try{
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                taskkinds.add(object.getString("name"));
                tkids.add(object.getString("id"));
                spinnerLists.get(1).add(object.getString("name"));
                adapters.get(1).notifyDataSetChanged();
            }
            spinners.get(1).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i>0) {
                        tkid = tkids.get(i - 1);
                    }
                    else{
                        tkid="";
                    }
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
                spinnerLists.get(0).add(object.getString("name"));
                adapters.get(0).notifyDataSetChanged();
            }
            ((MainActivity)getActivity()).setDepartments(departments);
            ((MainActivity)getActivity()).setDpids(dpids);
            spinners.get(0).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if(i>0) {
                        dpid = dpids.get(i - 1);
                        getChief();
                    }
                    else{
                        dpid="";
                        strings.set(2,new String[]{});
                        checkAccepts();
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

    private void saveThis(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"tasks/";
        if(tkid.length()==0 || emid.length()==0 || nameEditText.getText().length()==0 ){
            Toast.makeText(getActivity(), "Введите все данные", Toast.LENGTH_SHORT).show();
            progressLayout.setVisibility(View.GONE);
        }
        else if(!todayRadio.isChecked() && calendarView.getClick()!=2){
            Toast.makeText(getActivity(), "Выставьте дату", Toast.LENGTH_SHORT).show();
            progressLayout.setVisibility(View.GONE);
        }
        else if(todayRadio.isChecked() && timePickView.getChose().getTimeInMillis()<new Date().getTime()){
            Toast.makeText(getActivity(), "Данное время уже прошло", Toast.LENGTH_SHORT).show();
            progressLayout.setVisibility(View.GONE);
        }
        else {
            JSONObject params = new JSONObject();
            try {
                int s = 1;
                if (priorityRadios[0].isChecked()) s = 1;
                else if (priorityRadios[1].isChecked()) s = 2;
                else s = 3;
                JSONArray files=new JSONArray();
                params.put("files",files);
                params.put("priority", s);
                params.put("point", Integer.parseInt(id));
                params.put("respondent", Integer.parseInt(emid));
                params.put("kind", Integer.parseInt(tkid));
                params.put("description", nameEditText.getText() + "");
                if(true){
                    if(is_executive_permitted==1){
                        params.put("is_executive_permitted",false);
                    }
                    if(is_technical_permitted==1){
                        params.put("is_technical_permitted",false);
                    }
                    if(is_contactor_permitted==1){
                        params.put("is_contactor_permitted",false);
                    }
                    if(is_chief_permitted==1){
                        params.put("is_chief_permitted",false);
                    }
                }
                if (commentEditText.getText().length() > 0) {
                    params.put("comment_create", commentEditText.getText() + "");
                }
                Calendar today;
                if (todayRadio.isChecked()) {
                    today = timePickView.getChose();
                } else {
                    today = calendarView.getChose();
                }

                if (today != null) {
                    params.put("deadline", ((MainActivity) getActivity()).inputFormat.format(today.getTime()) + "");
                } else {
                    params.put("deadline", ((MainActivity) getActivity()).inputFormat.format(today.getTime()) + "");
                }
                Log.d("params",params.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    Log.d("sentPOST",response.toString());
                    Toast.makeText(getActivity(), "Задача добавлена", Toast.LENGTH_SHORT).show();
                    ((MainActivity)getActivity()).onBackPressed();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    int code=error.networkResponse.statusCode;
                    Log.d("code",code+"");
                    if(code==400){
                        Toast.makeText(getActivity(), "Данный работник не может быть ответственным", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                    error.printStackTrace();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                    return headers;
                }
            };
            ((MainActivity) getActivity()).requestQueue.add(jsonObjectRequest);
        }
    }



    private void getData(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"employees/";
        JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, url + "?user__role=ADMIN_EXECUTIVE", null, new Response.Listener<JSONArray>() {
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
        JsonArrayRequest admin_techRequest=new JsonArrayRequest(Request.Method.GET, url + "?user__role=ADMIN_TECHNICAL", null, new Response.Listener<JSONArray>() {
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
        JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, ((MainActivity) getActivity()).MAIN_URL+"points/"+id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                    try {
                        JSONObject contactor=response.getJSONObject("contactor");
                        strings.set(3,new String[]{contactor.getString("fullname"),contactor.getString("role"), "-1"});
                        checkAccepts(); }
                    catch (JSONException e){
                        e.printStackTrace(); }
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
        ((MainActivity) getActivity()).requestQueue.add(admin_execRequest);
        ((MainActivity) getActivity()).requestQueue.add(admin_techRequest);
        ((MainActivity) getActivity()).requestQueue.add(admin_contRequest);
    }
    private void getChief(){
        if(dpid.length()>0) {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "employees/?is_chief=true&department="+dpid;
            JsonArrayRequest chiefRequest = new JsonArrayRequest(Request.Method.GET, url , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    Log.d("FUCKING DP",dpid+" "+response.length()+" "+response);
                    if (response.length() > 0) {
                        try {
                            setChief(response.getJSONObject(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Проблемы соеднения", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                    return headers;
                }
            };
            ((MainActivity)getActivity()).requestQueue.add(chiefRequest);
        }
    }
    private void setChief(JSONObject re){
        try{
            JSONObject object=re.getJSONObject("user");
            strings.set(2, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
            checkAccepts();
        }
        catch (Exception e){

        }
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
        for(int i=0;i<strings.size();i++){
            final String[] string=strings.get(i);
            if(string.length>0){
                String department="";
                if(i==3){
                    department="Представитель клиента";
                }
                else{ int ind=dpids.indexOf(string[2]);
                    if(ind!=-1)
                    department=departments.get(ind);
                }
                ChooseAcceptForm acceptForm=new ChooseAcceptForm(department,string[0],((MainActivity)getActivity()).positions.get(string[1]),i==3);
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
                is_executive_permitted*=(-1);
                break;
            case 1:
                is_technical_permitted*=(-1);
                break;
            case 2:
                is_chief_permitted*=(-1);
                break;
            case 3:
                is_contactor_permitted*=(-1);
                break;
        }
        Log.d("permission","exec: "+is_executive_permitted+" , technic: "+is_technical_permitted+" , chief: "+is_chief_permitted+" , contactor: "+is_contactor_permitted);
    }
}
