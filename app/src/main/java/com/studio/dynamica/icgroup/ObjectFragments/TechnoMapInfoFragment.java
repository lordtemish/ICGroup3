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
import android.widget.FrameLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnoMapInfoFragment extends Fragment {
    RecyclerView technicView,washView, statusComment, commentsRecycler, rateRecyclerView, rateLateRecyclerView;
    TextView mainObjectTitle, period, service, category, status, timeInside, placeInside, categoryInside, statusInside, methodLabel, method, periodInside, worksLabel, worksInfo,
            technicLabel, washLabel, statusLate, statusExtraInfo, employeeLabel, employeeName, employeePosition, commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView
            ;
    LinearLayout statusButtonsLayout,commentLayout, wholeLayout;
    RadioButton goodJobRadio,answerRadioButton0, answerRadioButton1, answerRadioButton2;
    RadioGroup answerRadioGroup;
    FrameLayout progressLayout;
    String id;
    int stat;
    List<WashForm> washForms;
    WashAdapter washAdapter;
    public TechnoMapInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        id=getArguments().getString("id");
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


        List<MessageForm> forms=new ArrayList<>();
        List<MessageForm> forms1=new ArrayList<>();
        forms.add(new MessageForm("some text i am just writing some tex cause i need some big text for my programm, so wgile i was writing whis programm, i was thinking bout a lot of things"));
        forms1.add(new MessageForm(getActivity().getResources().getString(R.string.bigtext),"13 января | 13:55","Алмасов Красавчик",5));
        MessageAdapter messageAdapter=new MessageAdapter(forms);
        MessageAdapter messageAdapter1=new MessageAdapter(forms1);

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
        rateRecyclerView.setAdapter(new RateStarsAdapter(true));
        rateLateRecyclerView.setAdapter(new RateStarsAdapter(4));

        stat=getArguments().getInt("status",0);
        setStatus(stat);
        getRequest();
        return view;
    }
    private void getRequest(){
        String url=((MainActivity)getActivity()).MAIN_URL;
        if(stat==3){
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
            JSONObject plan = object.getJSONObject("plan");
            JSONObject sector=plan.getJSONObject("sector");
            JSONObject category = plan.getJSONObject("category");
            JSONObject method=plan.getJSONObject("method");
            int duration=plan.getInt("duration");
            String description=plan.getString("description");

            String[] begin=plan.getString("begin").split(":"), end=plan.getString("end").split(":");
            String time=begin[0]+":"+begin[1]+"-"+end[0]+":"+end[1];
            timeInside.setText(time);
            placeInside.setText(sector.getString("name"));
            this.method.setText(method.getString("name"));
            periodInside.setText(duration+" мин");
            worksInfo.setText(description);

            JSONArray inventories=plan.getJSONArray("inventories");
            Log.d("inves",inventories.length()+"");
            if(inventories.length()==0){
                technicLabel.setVisibility(View.GONE);
                technicView.setVisibility(View.GONE);
            }
            for(int i=0;i<inventories.length();i++){
                JSONObject obj=inventories.getJSONObject(i);
                JSONObject invent=obj.getJSONObject("inventory");
                String name=invent.getString("name"), unit=invent.getString("unit"),company=invent.getString("company"), vendor=invent.getString("vendor_code");
                int qua=invent.getInt("total_quantity"), quantity=obj.getInt("quantity");
                washForms.add(new WashForm(company+" "+name+" "+qua+" "+unit,"IC"+vendor,quantity));
            }
            washAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setPlan(JSONObject object){
        try {
            washForms.clear();
            JSONObject plan = object;
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
            periodInside.setText(duration+" мин");
            worksInfo.setText(description);

            JSONArray inventories=plan.getJSONArray("inventories");
            Log.d("inves",inventories.length()+"");
            if(inventories.length()==0){
                technicLabel.setVisibility(View.GONE);
                technicView.setVisibility(View.GONE);
            }
            for(int i=0;i<inventories.length();i++){
                JSONObject obj=inventories.getJSONObject(i);
                JSONObject invent=obj.getJSONObject("inventory");
                JSONObject company=invent.getJSONObject("company");
                String name=invent.getString("name"), unit=invent.getString("unit"),companyName=company.getString("name"), vendor=invent.getString("vendor_code");
                int qua=invent.getInt("quantity"), quantity=obj.getInt("quantity");
                washForms.add(new WashForm(companyName+" "+name+" "+qua+" "+unit,"IC"+vendor,quantity));
            }
            washAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setFonttype(){
        setTypeFace("demibold", timeInside, categoryInside, placeInside, statusInside, method, worksLabel, statusExtraInfo, statusLate, employeeName, employeePosition,goodJobRadio,answerRadioButton0,answerRadioButton1,answerRadioButton2);
        setTypeFace("light", period, service, category ,status, methodLabel, worksInfo, washLabel, technicLabel, employeeLabel,commentsLabel,extraText, commentButtonTextView, statusButtonFailTextView, statusButtonCloseTextView);
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
        period=(TextView) view.findViewById(R.id.period);
        category=(TextView) view.findViewById(R.id.category);
        service=(TextView) view.findViewById(R.id.service);
        status=(TextView) view.findViewById(R.id.status);
        timeInside=(TextView) view.findViewById(R.id.time);
        placeInside=(TextView) view.findViewById(R.id.place);
        categoryInside=(TextView) view.findViewById(R.id.categoryInside);
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
        extraText=(TextView) view.findViewById(R.id.extraText);
        commentButtonTextView=(TextView) view.findViewById(R.id.commentButtonTextView);
        statusButtonFailTextView=(TextView) view.findViewById(R.id.statusButtonFailTextView);
        statusButtonCloseTextView=(TextView) view.findViewById(R.id.statusButtonCloseTextView);

        goodJobRadio=(RadioButton) view.findViewById(R.id.goodJobRadio);
        answerRadioButton0=(RadioButton) view.findViewById(R.id.answerRadioButton0);
        answerRadioButton1=(RadioButton) view.findViewById(R.id.answerRadioButton1);
        answerRadioButton2=(RadioButton) view.findViewById(R.id.answerRadioButton2);
        answerRadioGroup=(RadioGroup) view.findViewById(R.id.answerRadioGroup);

        wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);

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
        statusInside.setBackgroundResource(R.drawable.related_darkgreen_page);
        statusInside.setText("На просрочке");
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
            case 4:
                setRelate();
                break;
        }
    }
}
