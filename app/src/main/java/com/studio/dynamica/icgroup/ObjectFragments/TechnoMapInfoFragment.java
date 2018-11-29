package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Adapters.RateStarsAdapter;
import com.studio.dynamica.icgroup.Adapters.WashAdapter;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.Forms.WashForm;
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
public class TechnoMapInfoFragment extends Fragment {
    ImageView imageLate;
    RecyclerView technicView,washView, statusComment, commentsRecycler, rateRecyclerView, rateLateRecyclerView;
    TextView mainObjectTitle, period, service, category, status, timeInside, placeInside, categoryInside, statusInside, methodLabel, method, periodInside, worksLabel, worksInfo,
            technicLabel, washLabel, statusLate, statusExtraInfo, employeeLabel, employeeName, employeePosition, commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView
       ,category1, facilitiesText, equipmentText,dateExtraTextView;
    LinearLayout statusButtonsLayout,commentsLayout,addNewCommentLayout, wholeLayout, statusLayout;
    RadioButton goodJobRadio,answerRadioButton0, answerRadioButton1, answerRadioButton2;
    RadioGroup answerRadioGroup;
    FrameLayout progressLayout;
    String id, role;
    int stat;
    List<WashForm> washForms;
    RateStarsAdapter starsAdapter, rateStarsAdapter;
    MessageAdapter messageAdapter, messageAdapter1;
    WashAdapter washAdapter;
    EditText commentEditText;
    boolean result;
    List<MessageForm> forms, forms1;
    public TechnoMapInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id=getArguments().getString("id");
        result=getArguments().getBoolean("is_result");
        role=((MainActivity)getActivity()).role;
        View view=inflater.inflate(R.layout.fragment_techno_map_info, container, false);
        createViews(view);
        setFonttype();

        washForms=new ArrayList<>();
        washForms.add(new WashForm("","",6));

       washAdapter=new WashAdapter(washForms);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        technicView.setLayoutManager(layoutManager);
        technicView.setItemAnimator(new DefaultItemAnimator());
        technicView.setAdapter(washAdapter);


        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        washView.setLayoutManager(layoutManager1);
        washView.setItemAnimator(new DefaultItemAnimator());


       forms=new ArrayList<>();
        forms1=new ArrayList<>();
       messageAdapter=new MessageAdapter(forms);
       messageAdapter1=new MessageAdapter(forms1);

        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        statusComment.setLayoutManager(layoutManager2);
        statusComment.setItemAnimator(new DefaultItemAnimator());
        statusComment.setAdapter(messageAdapter);

        RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentsRecycler.setLayoutManager(layoutManager3);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        commentsRecycler.setAdapter(messageAdapter1);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(rateLateRecyclerView,LinearLayoutManager.HORIZONTAL);
        starsAdapter=new RateStarsAdapter(true);
        rateRecyclerView.setAdapter(starsAdapter);
        rateStarsAdapter=new RateStarsAdapter(4);
        rateLateRecyclerView.setAdapter(rateStarsAdapter);

        stat=getArguments().getInt("status",0);
        setStatus(stat);
        getRequest();
        if(!result){
            if(role.equals("SUPERADMIN") || role.contains("PRODUCTION") || role.contains("ADMIN_")) {
                statusButtonsLayout.setVisibility(View.VISIBLE);
                addNewCommentLayout.setVisibility(View.VISIBLE);
            }
        }
        else if(!((MainActivity)getActivity()).client){
            statusButtonsLayout.setVisibility(View.GONE);
            commentsLayout.setVisibility(View.VISIBLE);
            statusLayout.setVisibility(View.VISIBLE);
        }
        statusButtonFailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    saveFailed("FAILED");
            }
        });
        statusButtonCloseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status="WARNED";
                if(answerRadioButton0.isChecked()){
                    status="PRAISED";
                }
                saveFailed(status);
            }
        });
      //  Log.d("QWEQWEQW",(addNewCommentLayout.getVisibility()==View.VISIBLE)+" "+(statusButtonsLayout.getVisibility()==View.VISIBLE));
        return view;
    }
    private void getRequest(){
        String url=((MainActivity)getActivity()).MAIN_URL;
        if(!result){
            progressLayout.setVisibility(View.VISIBLE);
//            ((MainActivity)getActivity()).onBackPressed();
            url+="plans/"+id;
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    setPlan(response);
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
            ((MainActivity)getActivity()).requestQueue.add(jsonObjectRequest);
        }
        else{
            progressLayout.setVisibility(View.VISIBLE);
            url+="results/"+id;
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    setInfo(response);
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
    }
    private void setInfo(JSONObject object){
        try {
            washForms.clear();
            String plan=object.getString("plan");
            result=false;
            id=plan;getRequest();
            JSONObject author=object.getJSONObject("author");
            String status=object.getString("status"),created_at=object.getString("created_at"), name=author.getString("fullname"), role=author.getString("role");
            int score=object.getInt("score");
            String comment=object.getString("comment"), created=((MainActivity)getActivity()).getdate(created_at), position=((MainActivity)getActivity()).positions.get(role);

            rateStarsAdapter.setRate(score);
            rateStarsAdapter.notifyDataSetChanged();


            dateExtraTextView.setText(created);
            employeeName.setText(name);employeePosition.setText(position);
            forms1.add(new MessageForm(comment));
            messageAdapter1.notifyDataSetChanged();
            if(status.equals("FAILED")){
                statusExtraInfo.setText("Задача провалена");
                statusLate.setBackgroundResource(R.drawable.failed_verydarkgreen);
                statusLate.setText("Провалено");
                goodJobRadio.setVisibility(View.GONE);
            }
            else{
                statusExtraInfo.setText("Задача выполнена");
                statusLate.setBackgroundResource(R.drawable.greyrow_page);
                statusLate.setText("Выполнено");
                goodJobRadio.setVisibility(View.VISIBLE);
                if(status.equals("WARNED")){
                    goodJobRadio.setText("Предупреждение за несоответствие");
                }
                else{
                    goodJobRadio.setText("Похвалено за отличную работу");
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setPlan(JSONObject object){
        try {

            washForms.clear();
            JSONObject plan = object;
            JSONObject category= object.getJSONObject("category");
            JSONObject sector=null;
            JSONObject method=null;
            String sec= "", meth="";

            if(!plan.isNull("sector")){
                sector=plan.getJSONObject("sector");
                sec=sector.getString("name");
            }
            if(!plan.isNull("method")){
                method=plan.getJSONObject("method");
                meth=method.getString("name");
            }
            int duration=plan.getInt("duration");
            String description=plan.getString("description");

            String[] begin=plan.getString("begin").split(":"), end=plan.getString("end").split(":");
            String time=begin[0]+":"+begin[1]+"-"+end[0]+":"+end[1];
            timeInside.setText(time);
            placeInside.setText(sec);
            this.method.setText(meth);
            category1.setText(category.getString("name"));
            periodInside.setText(duration+" мин");
            worksInfo.setText(description);
            washView.setVisibility(View.GONE);technicView.setVisibility(View.GONE);
            String facilities=object.getString("facilities"), equipments=object.getString("equipments");
            equipmentText.setText(equipments);facilitiesText.setText(facilities);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setFonttype(){
        setTypeFace("demibold", timeInside, categoryInside, placeInside, statusInside, method, worksLabel, statusExtraInfo, statusLate, employeeName, employeePosition,goodJobRadio,answerRadioButton0,answerRadioButton1);
        setTypeFace("light", period, service, category ,status, methodLabel, worksInfo, washLabel, technicLabel, employeeLabel,commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView, facilitiesText, equipmentText);
        setTypeFace("it",mainObjectTitle);
        setTypeFace("medium",periodInside);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }
    private void createViews(View view){
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        technicView=(RecyclerView) view.findViewById(R.id.technicRecyclerView);
        washView=(RecyclerView) view.findViewById(R.id.washRecyclerView);
        statusComment=(RecyclerView) view.findViewById(R.id.statusCommentsRecyclerView);
        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
        rateLateRecyclerView=(RecyclerView) view.findViewById(R.id.rateLateRecyclerView);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        dateExtraTextView=(TextView) view.findViewById(R.id.dateExtraTextView);
        period=(TextView) view.findViewById(R.id.period);
        category=(TextView) view.findViewById(R.id.category);
        service=(TextView) view.findViewById(R.id.service);
        status=(TextView) view.findViewById(R.id.status);
        timeInside=(TextView) view.findViewById(R.id.time);
        placeInside=(TextView) view.findViewById(R.id.place);
        categoryInside=(TextView) view.findViewById(R.id.categoryInside);
        category1=(TextView) view.findViewById(R.id.category1);
        statusInside=(TextView) view.findViewById(R.id.statusInside);
        periodInside=(TextView) view.findViewById(R.id.periodInside);
        methodLabel=(TextView) view.findViewById(R.id.methodLabel);
        method=(TextView) view.findViewById(R.id.method);
        worksLabel=(TextView) view.findViewById(R.id.worksLabel);
        worksInfo=(TextView) view.findViewById(R.id.worksInfo);
        technicLabel=(TextView) view.findViewById(R.id.technicLabel);
        washLabel=(TextView) view.findViewById(R.id.washLabel);
        statusExtraInfo=(TextView) view.findViewById(R.id.statusExtraInfo);
        statusLate=(TextView) view.findViewById(R.id.statusLate);
        employeePosition=(TextView) view.findViewById(R.id.employeePosition);
        employeeName=(TextView) view.findViewById(R.id.employeeName);
        employeeLabel=(TextView) view.findViewById(R.id.employeeLabel);
        commentsLabel=(TextView) view.findViewById(R.id.commentsLabel);
        equipmentText=(TextView) view.findViewById(R.id.equipmentText);
        facilitiesText=(TextView) view.findViewById(R.id.facilitiesText);
        extraText=(TextView) view.findViewById(R.id.extraText);
        commentButtonTextView=(TextView) view.findViewById(R.id.commentButtonTextView);
        statusButtonFailTextView=(TextView) view.findViewById(R.id.statusButtonFailTextView);
        statusButtonCloseTextView=(TextView) view.findViewById(R.id.statusButtonCloseTextView);

        goodJobRadio=(RadioButton) view.findViewById(R.id.goodJobRadio);
        answerRadioButton0=(RadioButton) view.findViewById(R.id.answerRadioButton0);
        answerRadioButton1=(RadioButton) view.findViewById(R.id.answerRadioButton1);
//        answerRadioButton2=(RadioButton) view.findViewById(R.id.answerRadioButton2);
        answerRadioGroup=(RadioGroup) view.findViewById(R.id.answerRadioGroup);

        wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
        statusLayout=(LinearLayout) view.findViewById(R.id.statusLayout);
        statusButtonsLayout=(LinearLayout) view.findViewById(R.id.statusButtonsLayout);
        commentsLayout=(LinearLayout) view.findViewById(R.id.commentsLayout);
        addNewCommentLayout=(LinearLayout) view.findViewById(R.id.addNewCommentLayout);

        commentEditText=(EditText)view.findViewById(R.id.commentEditText);
    }

    private void setFailed(){
        wholeLayout.setBackgroundResource(R.drawable.failed_lowdarkgreen);
        timeInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        placeInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        categoryInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        statusInside.setBackgroundResource(R.drawable.failed_verydarkgreen);
        methodLabel.setTextColor(getActivity().getResources().getColor(R.color.white));
        method.setTextColor(getActivity().getResources().getColor(R.color.white));
        periodInside.setTextColor(getActivity().getResources().getColor(R.color.white));
        statusInside.setText("Провалено");
    }
    private void setFinished(){
        statusInside.setBackgroundResource(R.drawable.greyrow_page);
        statusInside.setText("Выполнено");
    }
    public void setinProcess(){
        statusInside.setBackgroundResource(R.drawable.inwait_yellowpage);
        statusInside.setText("В процессе");
    }
    public void setActual(){
        statusInside.setBackgroundResource(R.drawable.inprocess_green_page);
        statusInside.setText("Актуально");
    }
    public void setRelate(){
     //   statusInside.setBackgroundResource(R.drawable.related_darkgreen_page);
        statusInside.setBackgroundResource(R.drawable.inwait_yellowpage);
       // statusInside.setText("На просрочке");
        statusInside.setText("В ожидании");
    }
    private void setStatus(int a){
        switch (a){
            case 0:
                setFailed();
                break;
            case 1:
                setFinished();
                break;
            case 2:
                setinProcess();
                break;
            case 3:
                setActual();
                break;
            case 5:
                setRelate();
                break;
        }
    }

    private void saveFailed(String status) {
        if (commentEditText.getText().length()<=1) {
            Toast.makeText(getActivity(), "Напишите комментарий", Toast.LENGTH_SHORT).show();
        } else {

            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "results/";
            JSONObject object = new JSONObject();
            try {
                object.put("status", status);
                object.put("comment", commentEditText.getText()+"");
                object.put("score", 1);
                object.put("plan", Integer.parseInt(id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Статус изменен", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
            ((MainActivity) getActivity()).requestQueue.add(request);
        }
    }
}
