package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AVRAdapter;
import com.studio.crm.icgroup.Adapters.ClientControlAdapter;
import com.studio.crm.icgroup.Adapters.RateStarsAdapter;
import com.studio.crm.icgroup.ExtraFragments.YearMonthView;
import com.studio.crm.icgroup.Forms.AVRForm;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.Forms.CheckListBoxForm;
import com.studio.crm.icgroup.Forms.CheckListBoxRowForm;
import com.studio.crm.icgroup.Forms.CheckListForm;
import com.studio.crm.icgroup.Forms.MessageWorkForm;
import com.studio.crm.icgroup.Forms.OlkForm;
import com.studio.crm.icgroup.Forms.OneCallBack;
import com.studio.crm.icgroup.Forms.svodkaRateForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientControlObjectMainFragment extends Fragment {
    int page=0, location=0;
    List<String> pages, ids;
    View view;
    FrameLayout extraLayout;
    NumberPicker numberPicker, yearPicker;
    TextView pageInfo, addNewTextView,mainObjectTitle, clientControlInfoTextView, yearTextView, monthTextView,PercentageTextView;

    TextView calendarTextView;
    YearMonthView yearMonthView;

    LinearLayout progressLayout, createNewLayout;
    ConstraintLayout rateLayout, addLayout;
    ImageView left, right, yearImageView, monthImageView,
                addNewImageView;
    RecyclerView recyclerView, rateRecyclerView;
    Calendar cal;
    String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    ClientControlAdapter OlkAdapter, svodkaAdapter, checkListAdapter;
    AVRAdapter avrAdapter;
    RateStarsAdapter rateStarsAdapter;
    View.OnClickListener createOlkListener, createCheckListListener, svodkaListener, avrListener;
    String id="", url, city;
    List<Object> olkForms,checkListForms, svodkaRateForms;
    List<AVRForm> avrForms;
    ProgressBar ProgressBar;
    boolean client=false;
    private boolean timeChange=false;

    public ClientControlObjectMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        city=getArguments().getString("city");
        location=getArguments().getInt("location");
        url=((MainActivity)getActivity()).MAIN_URL+"controls/?point="+id;
        cal=Calendar.getInstance();
        cal.setTime(new Date());
        client=((MainActivity)getActivity()).client;
        view=inflater.inflate(R.layout.fragment_client_control_object_main, container, false);
        createAllViews();
        setFonttypes();
        setAllListeners();
        PickerSettings();

        ((MainActivity)getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        rateStarsAdapter=new RateStarsAdapter(true);
        rateRecyclerView.setAdapter(rateStarsAdapter);

        olkForms=new ArrayList<>();
        checkListForms=new ArrayList<>();
        svodkaRateForms=new ArrayList<>();

        avrForms=new ArrayList<>();
        List<MessageWorkForm> workForms=new ArrayList<>();
        List<AcceptForm> acceptForms=new ArrayList<>();


        OlkAdapter=new ClientControlAdapter(olkForms, 0);
        avrAdapter=new AVRAdapter(avrForms);
        checkListAdapter=new ClientControlAdapter(checkListForms,1);
        svodkaAdapter=new ClientControlAdapter(svodkaRateForms,2);
        svodkaAdapter.setCity(city);svodkaAdapter.setLocation(location);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(OlkAdapter);


        pages=new ArrayList<>();
        ids=new ArrayList<>();
        pages.add("Опросной лист клиента");
        ids.add("QUESTIONNAIRE");
        pages.add("Акт выполненых работ");
        ids.add("PERFORMANCE");
        pages.add("Чек-лист проверки качества работы");
        ids.add("CHECKLIST");
        pages.add("Сводка по результатам обзвона");
        ids.add("RINGING");

        View.OnClickListener click=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click();
            }
        };
        yearImageView.setOnClickListener(click);
        monthImageView.setOnClickListener(click);
        setDate();

        checkPage();

        if(client){
           // addLayout.setVisibility(View.GONE);
            addLayout.setVisibility(View.GONE);
        }
        else{
            addLayout.setVisibility(View.GONE);
        }
        return view;
    }
    private void PickerSettings(){
        numberPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        numberPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getContext().getResources().getColor( android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(cal.get(Calendar.MONTH)+1);
        int year=cal.get(Calendar.YEAR);
        yearPicker.setMinValue(year-2);
        yearPicker.setMaxValue(year+1);
        yearPicker.setValue(year);
    }
    private void setDate(){
        monthTextView.setText(data[cal.get(Calendar.MONTH)]);
        yearTextView.setText(cal.get(Calendar.YEAR)+"");
    }
    private void click(){
        if(yearPicker.getVisibility()==View.GONE){
            yearImageView.setImageResource(R.drawable.ic_arrowup_green);
            monthImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearPicker.setVisibility(View.VISIBLE);
            numberPicker.setVisibility(View.VISIBLE);
            yearTextView.setVisibility(View.GONE);
            monthTextView.setVisibility(View.GONE);
        }
        else{
            yearImageView.setImageResource(R.drawable.ic_arrowdown_green);
            monthImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearPicker.setVisibility(View.GONE);
            numberPicker.setVisibility(View.GONE);
            yearTextView.setVisibility(View.VISIBLE);
            monthTextView.setVisibility(View.VISIBLE);
            cal.set(yearPicker.getValue(),numberPicker.getValue()-1,1);
            setDate();
        }
    }
    private void setFonttypes(){
        setTypeFace("demibold", pageInfo, addNewTextView, yearTextView, monthTextView,PercentageTextView);
        setTypeFace("light", clientControlInfoTextView);
        setTypeFace("it", mainObjectTitle);
        numberPicker.setTypeface(((MainActivity) getActivity()).getTypeFace("demibold"));
        yearPicker.setTypeface(((MainActivity) getActivity()).getTypeFace("demibold"));
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }
    private void createAllViews(){
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);

        calendarTextView=(TextView) view.findViewById(R.id.calendarTextView);
        calendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrowClick();
            }
        });
        yearMonthView=(YearMonthView) view.findViewById(R.id.yearMonthView);
        yearMonthView.setPickers(cal);
        yearMonthView.setCallBack(new OneCallBack() {
            @Override
            public void callBackCal() {
                arrowClick();
            }
        });

        clientControlInfoTextView=(TextView) view.findViewById(R.id.clientControlInfoTextView);
        addNewTextView=(TextView) view.findViewById(R.id.createNewTextView);
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        left=(ImageView) view.findViewById(R.id.ImageLeftView);
        right=(ImageView) view.findViewById(R.id.ImageRightView);
        yearImageView=(ImageView) view.findViewById(R.id.yearImageView);
        monthImageView=(ImageView) view.findViewById(R.id.monthImageView);
        progressLayout=(LinearLayout) view.findViewById(R.id.progressLayout);
        createNewLayout=(LinearLayout) view.findViewById(R.id.createNewLayout);
        addNewImageView=(ImageView) view.findViewById(R.id.addNewImageView);
        rateLayout=(ConstraintLayout) view.findViewById(R.id.rateLayout);
        addLayout=(ConstraintLayout) view.findViewById(R.id.addLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.CCRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
        extraLayout=(FrameLayout) view.findViewById(R.id.extraLayout);

        numberPicker=(NumberPicker) view.findViewById(R.id.numberPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
    }

    private void  setAllListeners(){
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(false);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(true);
            }
        });

        createOlkListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddNewOlkFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("author","11");
                fragment.setArguments(bundle);
                ((MainActivity)view.getContext()).setFragment(R.id.content_frame,fragment);
            }
        };
        createNewLayout.setOnClickListener(createOlkListener);


        createCheckListListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddNewCheckListFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("author","11");
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,fragment);
            }
        };
        svodkaListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddNewSvodkaFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("author","11");
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,fragment);
            }
        };
        avrListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new AddNewAVRFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                bundle.putString("author","11");
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
            }
        };
    }


    public void setPagePlus(boolean plus){
        if(plus)
            page=(page+1)%pages.size();
        else
            page=(page-1)%pages.size();
        if(page==-1){
            page=pages.size()-1;
        }
        checkPage();
    }
    public void checkPage(){
        extraLayout.setVisibility(View.VISIBLE);
        calendarTextView.setText(data[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR));

        pageInfo.setText(pages.get(page));
        String statUrl=((MainActivity)getActivity()).MAIN_URL;
        String cr="Создать ";
        String s="Чек лист";
        boolean array=false;
        switch (page){
            case 0:
                s="ОЛК";
                setProgressLayout();
                statUrl+="positioncontrols/questionnaire_stats/";
                recyclerView.setAdapter(OlkAdapter);
                array=true;
                addNewImageView.setOnClickListener(createOlkListener);
                break;
            case 1:
                s="АВР";
                setProgressLayout();
                statUrl+="positioncontrols/performance_stats/";
                recyclerView.setAdapter(avrAdapter);
                addNewImageView.setOnClickListener(avrListener);

                break;
            case 2:
                s="Чек лист";
                setProgressLayout();
                recyclerView.setAdapter(checkListAdapter);
                addNewImageView.setOnClickListener(createCheckListListener);
                break;
            case 3:
                s="сводку";
                setSvodkaRate();
                recyclerView.setAdapter(svodkaAdapter);
                addNewImageView.setOnClickListener(svodkaListener);
                break;
        }


        Calendar cali=Calendar.getInstance();
        cali.setTime(cal.getTime());
        cali.set(Calendar.DAY_OF_MONTH,1);
        cali.set(Calendar.HOUR_OF_DAY,0);
        cali.set(Calendar.MINUTE,0);
        cali.set(Calendar.SECOND,0);
        //cali.add(Calendar.HOUR_OF_DAY,-6);
        String s1=((MainActivity)getActivity()).inputFormat.format(cali.getTime());
        cali.setTime(cal.getTime());
        cali.set(Calendar.DAY_OF_MONTH,cali.getActualMaximum(Calendar.DAY_OF_MONTH));
        cali.set(Calendar.HOUR_OF_DAY,23);
        cali.set(Calendar.MINUTE,59);
        cali.set(Calendar.SECOND,59);
        //  cali.add(Calendar.HOUR_OF_DAY,-6);
        String e=((MainActivity)getActivity()).inputFormat.format(cali.getTime());
        try {

            s1 = s1.substring(0, 10);
            e = e.substring(0,10);
            Log.d("TIMES 1 1 1", s1+" | "+e);
        }catch (Exception e2){e2.printStackTrace();}


        String urlReq=url + "&kind=" + ids.get(page)+"&created_at__lte="+e+"&created_at__gte="+s1;

        Log.d("URL CONTROLS", urlReq);
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, urlReq, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("URL RESPONSE",response.toString());
                    setInfo(response);
                    extraLayout.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
    extraLayout.setVisibility(View.GONE);
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
            ((MainActivity)getActivity()).requestQueue.add(jsonArrayRequest);

        statUrl+="?control__point="+id;
       getStatistics(statUrl, array);
        addNewSetText(cr+s);
    }


    private void arrowClick(){
        if(timeChange) {
            timeChange = false;
            yearMonthView.setVisibility(View.GONE);
            cal.set(yearMonthView.yearPicker.getValue(),yearMonthView.monthPicker.getValue(),1);
            checkPage();
        }
        else{
            timeChange=true;
            yearMonthView.setVisibility(View.VISIBLE);
        }
    }



    private void getStatistics(String url, boolean a){
        if(url.contains("positioncontrols")) {
            if(a){
                JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        setStatistic(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
                };((MainActivity)getActivity()).requestQueue.add(arrayRequest);
            }
            else {
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        extraLayout.setVisibility(View.GONE);
                        setStatistic(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        extraLayout.setVisibility(View.GONE);
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
                ((MainActivity) getActivity()).requestQueue.add(objectRequest);
            }
        }
    }
    private void setStatistic(JSONObject object){
        try {
            int it=0;
            if(object.isNull("rate_avg")){
                it=0;
            }
            else {
                Log.d("response", object.toString() + "");
                double rate = object.getDouble("rate_avg");
                Log.d("rate_avg", rate + "");
                it = Integer.parseInt("" + Math.round(rate * 20));
            }
            setStatistics(it);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setStatistic(JSONArray object){
        try {
            Log.d("response",object.toString()+"");
            int n=object.length();
            double a=0;
            for(int i=0;i<n;i++) {
                JSONObject ob=object.getJSONObject(i);
                int it=0;
                if (ob.isNull("rate_avg")) {
                    it=0;
                }
                else {
                    double rate = ob.getDouble("rate_avg");
                    Log.d("rate_avg", rate + "");
                    it = Integer.parseInt("" + Math.round(rate * 20));
                }
                a+=it;
            }
            a=a/n;
            int it=Integer.parseInt(Math.round(a)+"");
            setStatistics(it);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setStatistics(int it){
        ProgressBar.setProgress(it);
        PercentageTextView.setText(it+"%");
    }
    private String getdate(Calendar calendar){
        String min=calendar.get(Calendar.MINUTE)+"";
        if(min.length()==1){
            min="0"+min;
        }
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+"|"+calendar.get(Calendar.HOUR_OF_DAY)+":"+min;
    }
    private String getdate(Calendar calendar, int a){
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR);
    }

    private void setInfo(JSONArray array){
        try {
             if(page==0){
                 olkForms.clear();
                 for(int i=0;i<array.length();i++){
                     JSONObject object=array.getJSONObject(i);
                     JSONObject author=object.getJSONObject("author");
                     String created_at=object.getString("created_at");
                     Date date=((MainActivity)getActivity()).getNowDate(created_at);
                     Calendar calendar=Calendar.getInstance();
                     calendar.setTime(date);
                     String dateTime=((MainActivity)getActivity()).getdate(calendar);
                     String name=author.getString("fullname");
                     int sum=0;
                     JSONArray pos=object.getJSONArray("positions");
                     for(int j=0;j<pos.length();j++){
                         JSONObject posit=pos.getJSONObject(j);
                         sum+=posit.getInt("rate");
                     }
                     String role=author.getString("role");
                     String position=((MainActivity)getActivity()).positions.get(role);
                     if(pos.length()>0)
                     sum=sum/(pos.length());
                     else{
                         sum=0;
                     }
                     OlkForm olkForm=new OlkForm(dateTime,name,position,sum);
                     olkForm.setId(object.getString("id"));
                     olkForms.add(olkForm);
                 }
                 OlkAdapter.notifyDataSetChanged();
             }
             if(page==1){
                avrForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONObject author=object.getJSONObject("author");
                    String date1=object.getString("created_at");
                    Date date=((MainActivity)getActivity()).getNowDate(date1);
                    Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                    String thatdate=getdate(calendar);
                    int sum=0;
                    JSONArray pos=object.getJSONArray("positions");
                    for(int j=0;j<pos.length();j++){
                        JSONObject posit=pos.getJSONObject(j);
                        sum+=posit.getInt("rate");
                    }
                    if(pos.length()>0)
                        sum=sum/(pos.length());
                    else{
                        sum=0;
                    }
                    String name=author.getString("fullname"), role=author.getString("role");
                    String posi=((MainActivity)getActivity()).positions.get(role);

                    AVRForm avrForm=new AVRForm(thatdate,sum, "",name,posi);
                    avrForm.setId(object.getString("id"));
                    avrForms.add(avrForm);
                }
                avrAdapter.notifyDataSetChanged();
             }
             if(page==2){
                checkListForms.clear();
                 for(int i=0;i<array.length();i++){
                     JSONObject object=array.getJSONObject(i);
                     String date1=object.getString("created_at");
                     Date date=((MainActivity)getActivity()).getNowDate(date1);
                     Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                     String thatdate=getdate(calendar);
                     int sum=0;
                     JSONArray pos=object.getJSONArray("positions");
                     for(int j=0;j<pos.length();j++){
                         JSONObject posit=pos.getJSONObject(j);
                         sum+=posit.getInt("rate");
                     }
                     if(pos.length()>0)
                         sum=sum/(pos.length());
                     else{
                         sum=0;
                     }
                     JSONObject author=object.getJSONObject("author");
                     String fullname=author.getString("fullname");
                     String role=author.getString("role");
                     String position=((MainActivity)getActivity()).positions.get(role);
                     CheckListForm checkListForm=new CheckListForm(thatdate, fullname, sum);
                     checkListForm.setId(object.getString("id"));
                     checkListForms.add(checkListForm);
                 }
                checkListAdapter.notifyDataSetChanged();
             }
             if(page==3){
                svodkaRateForms.clear();
                 for(int i=0;i<array.length();i++){
                     JSONObject object=array.getJSONObject(i);
                     String date1=object.getString("created_at");
                     Date date=((MainActivity)getActivity()).getNowDate(date1);
                     Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                     String thatdate=getdate(calendar);
                     int sum=0;
                     JSONArray pos=object.getJSONArray("positions");
                     for(int j=0;j<pos.length();j++){
                         JSONObject posit=pos.getJSONObject(j);
                         sum+=posit.getInt("rate");
                     }
                     if(pos.length()>0)
                         sum=sum/(pos.length());
                     else{
                         sum=0;
                     }
                     JSONObject author=object.getJSONObject("author");
                     String fullname=author.getString("fullname");
                     String role=author.getString("role");
                     String position=((MainActivity)getActivity()).positions.get(role);
                     svodkaRateForm svodkaForm=new svodkaRateForm(thatdate,position,fullname,sum);
                     svodkaForm.setId(object.getString("id"));
                     svodkaRateForms.add(svodkaForm);
                 }
                svodkaAdapter.notifyDataSetChanged();
             }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void addNewSetText(String s){
        addNewTextView.setText(s);
    }
    public void setSvodkaRate(){
        progressLayout.setVisibility(View.GONE);
        rateLayout.setVisibility(View.VISIBLE);
    }
    public void setProgressLayout(){
        progressLayout.setVisibility(View.VISIBLE);
        rateLayout.setVisibility(View.GONE);
    }

}
