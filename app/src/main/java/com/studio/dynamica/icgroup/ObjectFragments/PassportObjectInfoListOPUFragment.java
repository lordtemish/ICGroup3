package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.android.volley.request.StringRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CommentAdapter;
import com.studio.dynamica.icgroup.Adapters.ReplacerAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.BigCounterView;
import com.studio.dynamica.icgroup.ExtraFragments.HolidayFragmentView;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.Forms.JalobaForm;
import com.studio.dynamica.icgroup.Forms.UserRowForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListOPUFragment extends Fragment {
    RecyclerView commentsRecycler, archiveCommentsRecyclerView, replacerRecyclerView;
    Spinner spinner;
    BigCounterView counterView;
    boolean all=false;
    List<CommentForm> newCommentsList, allCommentsList;
    List<UserRowForm> userForms;
    CommentAdapter commentAdapter, archCommentAdapter;
    ReplacerAdapter replacerAdapter;
    LinearLayout newComments,allComments;
    TextView newCommentTextView,planWorkTextView,allCommentTextView, mainObjectTitle, nameTextView, positionTextView, dateTextView, PercentageTextView,  employeeChangeTextView, emplChangeButton, emplDropTextView,
            holidayTypeTop, holidayTypeTextView, holidayDate, holidaySetButton,replacedTextView;
    FrameLayout newCommentFrame, allCommentFrame, progressLayout;
    LinearLayout attendanceLayout, commentsLayout, holidayLayout;
    ProgressBar ProgressBar;
    ImageView circlePhoneImageView,arrowPlanImageView;
    int shift, point, planD=0;
    RadioButton checkRadio;
    JSONObject user;
    HolidayFragmentView holidayFragmentView;
    View.OnClickListener holidayListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holidayFragmentView.setVisibility(View.GONE);
            getRequest();
            ((MainActivity)getActivity()).setPressable(true, null);
        }
    }, lista=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            holidayFragmentView.setVisibility(View.VISIBLE);
            ((MainActivity)getActivity()).setPressable(false,holidayListener);
        }
    };
    boolean open=false;
String id, name, phone, userid;
    public PassportObjectInfoListOPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        userid=getArguments().getString("userid");
        name=getArguments().getString("name");
        phone=getArguments().getString("phone");
        View view=inflater.inflate(R.layout.fragment_passport_object_info_list_opu, container, false);
        createViews(view);
        setTypeFace(view.getContext());
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        nameTextView.setText(name);
        circlePhoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).callPhone(phone);
            }
        });
        commentsRecycler.setLayoutManager(mLayoutManager);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        ((MainActivity)getActivity()).setRecyclerViewOrientation(archiveCommentsRecyclerView,LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(replacerRecyclerView,LinearLayoutManager.VERTICAL);
        userForms=new ArrayList<>();
        replacerAdapter=new ReplacerAdapter(userForms);
        replacerRecyclerView.setAdapter(replacerAdapter);

        newCommentsList=new ArrayList<>();
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));

        newComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(true);
            }
        });


        allCommentsList=new ArrayList<>();

        allComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPage(false);
            }
        });

        emplDropTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });



        commentAdapter=new CommentAdapter(newCommentsList);
        commentAdapter.setIs_archive(false);
        archCommentAdapter=new CommentAdapter(allCommentsList);
        archCommentAdapter.setIs_archive(true);
        commentsRecycler.setAdapter(commentAdapter);
        archiveCommentsRecyclerView.setAdapter(archCommentAdapter);
        String[] spinnerList={"Выберите Сотрудника","asdasd","asdasd"};
        spinner=(Spinner) view.findViewById(R.id.employeeChangeSpinner);

        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,spinnerList){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

                return v;
        }
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
            ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

            return v;

            }
        };
        FrameLayout spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinner();
            }
        });
        spinner.setAdapter(spinnerAdapter);
        getRequest();
        getVisits();
        arrowPlanImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrowPlan();
            }
        });
        holidaySetButton.setOnClickListener(lista);
        return view;
    }
    private void arrowPlan(){
        if(counterView.getVisibility()==View.VISIBLE){
            counterView.setVisibility(View.GONE);
            arrowPlanImageView.setImageResource(R.drawable.ic_arrowdown);
            JSONObject object=new JSONObject();
            try{
                object.put("plan_days",counterView.getPage());
            }
            catch (Exception e){e.printStackTrace();}
            update(object);
        }
        else{
            counterView.setVisibility(View.VISIBLE);
            arrowPlanImageView.setImageResource(R.drawable.ic_arrowup);
        }
    }
    private void setTypeFace(Context context){
        mainObjectTitle.setTypeface(((MainActivity) context).getTypeFace("it"));
        nameTextView.setTypeface(((MainActivity) context).getTypeFace("bold"));
        positionTextView.setTypeface(((MainActivity) context).getTypeFace("light"));

        employeeChangeTextView.setTypeface(((MainActivity) context).getTypeFace("regular"));
        dateTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        PercentageTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        allCommentTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        newCommentTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        emplChangeButton.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        emplDropTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
    }
    private void createViews(View view){
        counterView=(BigCounterView)view.findViewById(R.id.counterView);
        counterView.setMax(30);

        replacedTextView=(TextView) view.findViewById(R.id.replacedTextView);
        employeeChangeTextView=(TextView) view.findViewById(R.id.employeeChangeTextView);
        allCommentTextView=(TextView) view.findViewById(R.id.allCommentsTextView);
        newCommentTextView=(TextView) view.findViewById(R.id.newCommentTextView);
        replacerRecyclerView=(RecyclerView) view.findViewById(R.id.replacerRecyclerView);
        archiveCommentsRecyclerView=(RecyclerView) view.findViewById(R.id.archiveCommentsRecyclerView);
        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        holidayLayout=(LinearLayout) view.findViewById(R.id.holidayLayout);
        attendanceLayout=(LinearLayout) view.findViewById(R.id.attendanceLayout);
        commentsLayout=(LinearLayout) view.findViewById(R.id.commentsLayout);
        newComments=(LinearLayout) view.findViewById(R.id.newCommentLinearLayout);
        newCommentFrame=(FrameLayout) view.findViewById(R.id.newCommentFrameLayout);
        allComments=(LinearLayout) view.findViewById(R.id.allCommentsLinearLayout);
        allCommentFrame=(FrameLayout) view.findViewById(R.id.allCommentsFrameLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        holidayTypeTop=(TextView) view.findViewById(R.id.holidayTypeTop);
        holidayDate=(TextView) view.findViewById(R.id.holidayDate);
        holidayTypeTextView=(TextView) view.findViewById(R.id.holidayTypeTextView);
        planWorkTextView=(TextView) view.findViewById(R.id.planWorkTextView);

        checkRadio=(RadioButton) view.findViewById(R.id.checkRadio);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        holidaySetButton=(TextView) view.findViewById(R.id.holidaySetButton);
        emplChangeButton=(TextView) view.findViewById(R.id.employeeChangeButton);
        emplDropTextView=(TextView) view.findViewById(R.id.employeeDropTextView);
        arrowPlanImageView=(ImageView) view.findViewById(R.id.arrowPlanImageView);
        circlePhoneImageView=(ImageView) view.findViewById(R.id.circlePhoneImageView);
        ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);

        holidayFragmentView=(HolidayFragmentView)view.findViewById(R.id.holidayFragmentView);
    }

    private void setPage(boolean att){
        clearComments();
        if(att){
            newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            commentsLayout.setVisibility(View.GONE);
            attendanceLayout.setVisibility(View.VISIBLE);
            newCommentFrame.setVisibility(View.VISIBLE);
        }
        else {
            allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            commentsLayout.setVisibility(View.VISIBLE);
            attendanceLayout.setVisibility(View.GONE);
            allCommentFrame.setVisibility(View.VISIBLE);
        }
    }
    public void setComment(boolean all){
        commentAdapter.notifyDataSetChanged();
        archCommentAdapter.notifyDataSetChanged();
    }
    public void clearComments(){
        newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        newCommentFrame.setVisibility(View.GONE);
        allCommentFrame.setVisibility(View.GONE);

    }
    public void showSpinner(){
        spinner.performClick();
    }
    private void getVisits(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"visits/?replacer="+id;
        JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setVisits(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "CONNECTION_TROUBLE", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setVisits(JSONArray array){
        try {
            int n=array.length();
            replacedTextView.setText(n+"");
            userForms.clear();
            for (int i=0;i<n;i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject worker=object.getJSONObject("worker");
                JSONObject user=worker.getJSONObject("user");
                userForms.add(new UserRowForm(user.getString("fullname"), "ОПУ",object.getString("date")));
            }
            replacerAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"workers/"+id+"/";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "CONNECTION_TROUBLE", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setInfo(JSONObject object){
        try{
            JSONObject point=object.getJSONObject("point");
            holidayFragmentView.setId(id);
            this.point=point.getInt("id");
            user=object.getJSONObject("user");
            holidayFragmentView.setName(user.getString("fullname"));
            shift=object.getInt("shift");
            String status=object.getString("status");
            Double dos=object.getDouble("attendance_rate");
            boolean aa=object.getBoolean("is_contract");
            int perfomance=(int)Math.round(dos*100), salary=object.getInt("salary"), plan_days=object.getInt("plan_days");
            planD=plan_days;
            Calendar calendar=Calendar.getInstance();calendar.setTime(new Date());
            int count=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            planWorkTextView.setText("План работ: "+0+" / "+plan_days);
            counterView.setPage(plan_days);
            checkRadio.setText("  "+salary+"тг");
            ProgressBar.setProgress(perfomance);
            PercentageTextView.setText(perfomance+"%");
            String dateString="";

            if(aa){
                checkRadio.setChecked(true);
            }
            else{
                checkRadio.setChecked(false);
            }

            dateString+=((MainActivity)getActivity()).data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR);
            Log.d(dateString,dateString);
            dateTextView.setText(dateString);
            getReq();
            holidaySetButton.setVisibility(View.VISIBLE);
            holidayLayout.setVisibility(View.GONE);
            if(status.contains("HOLIDAY")){
                holidayLayout.setVisibility(View.VISIBLE);
                holidaySetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject object=new JSONObject();
                        try{
                            object.put("status","STABLE");
                        }catch (Exception e){e.printStackTrace();}
                        update(object);
                    }
                });
                holidaySetButton.setText("ОТМЕНА ОТПУСКА");
                if(status.contains("0")) {
                    holidayTypeTextView.setText("Отпуск без содержания");
                    holidayTypeTop.setText("Отпуск (без содержания)");
                }
                else{
                    holidayTypeTextView.setText("Отпуск с содержанием");
                    holidayTypeTop.setText("Отпуск (с содержанием)");
                }
            }
            else if(status.equals("REMOVE")){
                positionTextView.setText("ОПУ - уволен");
                holidaySetButton.setVisibility(View.GONE);
            }
            else{
                holidaySetButton.setOnClickListener(lista);
                holidaySetButton.setText("ОТПУСК");
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setPlanDays(int a){
        planWorkTextView.setText("План работ: "+a+" / "+planD);
    }
    private void delete(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"workers/"+id+"/";
        JSONObject params=new JSONObject();
        try {
            params.put("status","REMOVE");
        }
        catch (Exception e){e.printStackTrace();}
        Log.d("PARAMSdelete",params.toString());
        JsonObjectRequest ob=new JsonObjectRequest(Request.Method.PATCH, url,params,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "ОПУ уволен", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(ob);
    }
    private void getReq(){
        progressLayout.setVisibility(View.VISIBLE);
        String type="defendant";
        String url=((MainActivity)getActivity()).MAIN_URL+"complaints/?"+type+"="+userid;
        Log.d("this", url);
        JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setInfo1(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                return headers;
            }
        };
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }

    private void setInfo1(JSONArray array){
        try{
            newCommentsList.clear();
            allCommentsList.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject author=object.getJSONObject("author");
                JSONObject defendant=object.getJSONObject("defendant");
                String content=object.getString("content");
                String id=object.getString("id");

                String authorrole=((MainActivity)getActivity()).positions.get(author.getString("role"));
                String name=author.getString("fullname"),role=((MainActivity)getActivity()).positions.get(defendant.getString("role"));
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-6);

                boolean arch=object.getBoolean("is_reply");
                CommentForm jalobaForm=new CommentForm(name,created);
                jalobaForm.setId(id);
                if(arch){
                    allCommentsList.add(jalobaForm);
                }
                else{
                    newCommentsList.add(jalobaForm);
                }
            }
            setComment(all);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void update(JSONObject object){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"workers/"+id+"/";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.PATCH, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                getRequest();
                Toast.makeText(getActivity(), "Сохранено", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(request);
    }

}
