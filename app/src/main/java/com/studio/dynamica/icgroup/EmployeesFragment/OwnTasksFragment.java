package com.studio.dynamica.icgroup.EmployeesFragment;


import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.PagesAdapter;
import com.studio.dynamica.icgroup.Adapters.ServicesAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.GraphicDateFrame;
import com.studio.dynamica.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.dynamica.icgroup.Forms.GraphicDateForm;
import com.studio.dynamica.icgroup.Forms.ServiceForm;
import com.studio.dynamica.icgroup.ObjectFragments.AddNewServiceFragment;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnTasksFragment extends Fragment {

    LinearLayout graphLinear;
    ConstraintLayout allServicesLayout;
    GraphicDateFrame graphicDateFrame;
    List<ServiceForm> forms;
    ServicesAdapter adapter;PagesAdapter pagesAdapter;
    RecyclerView allServicesRecycler, pagesRecyclerBot, pagesRecyclerUp;
    ImageView allServicesImage, arrowCalendarImageView, rightImageTop, leftImageTop, rightImageBot, leftImageBot;
    FrameLayout  progressLayout;
    //ConstraintLayout allOtdelsLayout, allEmployeesLayout;
    TextView ObjectTitle, calendarTextView, PercentageTextView, allServicesTextView;
    NumberPicker monthPicker, yearPicker;
    String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    Calendar cal, cal2;
    String id="", location, next="", previous="";
    int page=1, page_count=0;
    MainObjectSetInfoFragment setFragment;
    boolean timeChange=false;
    List<String> pages;
    public OwnTasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            id="";getArguments().getString("id");
            location=getArguments().getString("location");
        }
        catch (Exception e){

        }
        View view=inflater.inflate(R.layout.fragment_own_tasks, container, false);

        createViews(view);
        setFonttype();
        PickerSettings();

        allServicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServices();
            }
        });


        View.OnClickListener dateClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrowClick();
            }
        };
        calendarTextView.setOnClickListener(dateClick);
        arrowCalendarImageView.setOnClickListener(dateClick);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        allServicesRecycler.setLayoutManager(layoutManager);
        allServicesRecycler.setItemAnimator(new DefaultItemAnimator());
        setServicesAdapter();

        pages=new ArrayList<String>();
        ((MainActivity)getActivity()).setRecyclerViewOrientation(pagesRecyclerBot,LinearLayoutManager.HORIZONTAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(pagesRecyclerUp,LinearLayoutManager.HORIZONTAL);
         pagesAdapter=new PagesAdapter(pages);
         pagesRecyclerUp.setAdapter(pagesAdapter);
         pagesRecyclerBot.setAdapter(pagesAdapter);

        setDate();
        setFragment.setVisibility(View.GONE);
        setFragment.setWholeLayoutList(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetFragment();
            }
        });
        getRequest();
        return view;
    }

    public void showSetFragment() {
        int visibility=setFragment.getVisibility();
        if(visibility==View.VISIBLE){
            setFragment.setVisibility(View.GONE);
        }
        else{
            setFragment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        page=1;
        getRequest();
    }

    private void getRequest(){
        String url="tasks/?point="+id;
        if(page>1){
            url+="&page="+page;
        }
        Log.d("urdl",url);
        progressLayout.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;
        final String uu=url;
        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
              //      Log.d(uu, response.toString());
                    if(!response.isNull("count") && page<=1) {
                        int cc = response.getInt("count");
                        page_count=cc/10;
                        if(cc%10>0){
                            page_count++;
                        }
                        if(page_count>0)
                        checkPage();
                    }
                    setInfo(response);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
            }};
        ((MainActivity) getActivity()).requestQueue.add(arrayRequest);
    }
    private void getNext(){
        String url=next;
        progressLayout.setVisibility(View.VISIBLE);

        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.isNull("count") && page<=1) {
                        int cc = response.getInt("count");
                        page_count=cc/10;
                        if(cc%10>0){
                            page_count++;
                        }
                        if(page>0)
                            checkPage();
                    }
                    page++;
                    pagesAdapter.setPage(page);
                    pagesAdapter.notifyDataSetChanged();
                    setInfo(response);
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                progressLayout.setVisibility(View.GONE);
            }
        }){   @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity) getActivity()).requestQueue.add(arrayRequest);
    }
    private void getPrevious(){
        String url=previous;
        progressLayout.setVisibility(View.VISIBLE);

        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(!response.isNull("count") && page<=1) {
                        int cc = response.getInt("count");
                        page_count=cc/10;
                        if(cc%10>0){
                            page_count++;
                        }
                        if(page>0)
                            checkPage();
                    }
                    page--;
                    pagesAdapter.setPage(page);
                    pagesAdapter.notifyDataSetChanged();
                    setInfo(response);
                }
                catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                progressLayout.setVisibility(View.GONE);
            }
        }){   @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity) getActivity()).requestQueue.add(arrayRequest);
    }
    private void checkPage(){
        pages.clear();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=1;i<=page_count;i++){
            pages.add(i+"");
            final int j=i;
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page=j;
                    getRequest();
                }
            });
        }
        if(page_count>1){
            pagesRecyclerBot.setVisibility(View.VISIBLE);
            pagesRecyclerUp.setVisibility(View.VISIBLE);
        }
        else{
            pagesRecyclerBot.setVisibility(View.GONE);
            pagesRecyclerUp.setVisibility(View.GONE);
        }
        pagesAdapter.setPage(page);
        pagesAdapter.setListeners(listeners);
        if(page==page_count){
            rightImageTop.setVisibility(View.GONE);
            rightImageBot.setVisibility(View.GONE);
        }
        else{
            rightImageTop.setVisibility(View.VISIBLE);
            rightImageBot.setVisibility(View.VISIBLE);
        }
        if(page==1){
            leftImageTop.setVisibility(View.GONE);
            leftImageBot.setVisibility(View.GONE);
        }
        else{
            leftImageTop.setVisibility(View.VISIBLE);
            leftImageBot.setVisibility(View.VISIBLE);
        }
        pagesAdapter.notifyDataSetChanged();

    }
    private void setInfo(JSONObject ob){
        try {
            forms.clear();
            progressLayout.setVisibility(View.GONE);

            JSONArray array = ob.getJSONArray("results");
            if(ob.isNull("next")){
                next="";
                int visi=View.GONE;
                View.OnClickListener listener=new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                };
                rightImageTop.setVisibility(visi);rightImageTop.setOnClickListener(listener);
                rightImageBot.setVisibility(visi);rightImageBot.setOnClickListener(listener);
            }
            else{
                String url=ob.getString("next");
                Log.d("url",url);
                next=url;
                int visi=View.VISIBLE;
                View.OnClickListener listener=new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getNext();
                    }
                };
                rightImageTop.setVisibility(visi);rightImageTop.setOnClickListener(listener);
                rightImageBot.setVisibility(visi);rightImageBot.setOnClickListener(listener);
            }
            if(ob.isNull("previous")){
                previous="";
                int visi=View.GONE;
                View.OnClickListener listener=new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                };
                leftImageTop.setVisibility(visi);leftImageTop.setOnClickListener(listener);
                leftImageBot.setVisibility(visi);leftImageBot.setOnClickListener(listener);
            }
            else{
                String url=ob.getString("previous");
                previous=url;
                int visi=View.VISIBLE;
                View.OnClickListener listener=new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getPrevious();
                    }
                };
                leftImageTop.setVisibility(visi);leftImageTop.setOnClickListener(listener);
                leftImageBot.setVisibility(visi);leftImageBot.setOnClickListener(listener);
            }
            Log.d("length",array.length()+"");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
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
                Date created = ((MainActivity) getActivity()).inputFormat.parse(created_at), dead = ((MainActivity) getActivity()).inputFormat.parse(deadline);
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
                forms.add(serviceForm);
            }
            Log.d("res", forms.size() + "");
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private String getStatus(String s){
        switch (s){
            case "WAITING":
                s="Ожидает";
                break;
            case "CLOSED":
                break;
            case "PROCESSING":
                s="В процессе";
                break;
            case "PROLONGING":
                s="";
                break;
            case "PROLONGED":
                s="";
                break;
            case "FINISHED":
                s="";
                break;
            default:
                s="";
        }
        return s;
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
    private void arrowClick(){
        if(timeChange) {
            timeChange = false;
            calendarTextView.setVisibility(View.VISIBLE);
            monthPicker.setVisibility(View.GONE);
            yearPicker.setVisibility(View.GONE);
            cal.set(yearPicker.getValue(),monthPicker.getValue(),1);
            setDate();
            arrowCalendarImageView.setImageResource(R.drawable.ic_arrowdown_green);
        }
        else{
            timeChange=true;
            calendarTextView.setVisibility(View.GONE);
            monthPicker.setVisibility(View.VISIBLE);
            yearPicker.setVisibility(View.VISIBLE);
            arrowCalendarImageView.setImageResource(R.drawable.ic_arrowup_green);
        }
    }

    private void setDate(){
        calendarTextView.setText(months[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR));
        setGraphic();
    }
    private void setFonttype(){
        ObjectTitle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));

        calendarTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        PercentageTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        allServicesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));

        monthPicker.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
        yearPicker.setTypeface(((MainActivity)getActivity()).getTypeFace("demibold"));
    }
    private void PickerSettings(){
        monthPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        monthPicker.setDividerColorResource(android.R.color.transparent);
        yearPicker.setDividerColor(getResources().getColor(android.R.color.transparent));
        yearPicker.setDividerColorResource(android.R.color.transparent);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(months.length-1);
        monthPicker.setDisplayedValues(months);
        monthPicker.setValue(cal.get(Calendar.MONTH));
        yearPicker.setMinValue(2017);
        yearPicker.setMaxValue(2020);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    private void createViews(View view){


        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        setFragment=(MainObjectSetInfoFragment) view.findViewById(R.id.setFragment);
        monthPicker=(NumberPicker) view.findViewById(R.id.monthPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        cal=Calendar.getInstance();
        cal2=Calendar.getInstance();
        cal.setTime(new Date());
        cal2.setTime(new Date());

        ObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        allServicesTextView=(TextView) view.findViewById(R.id.allServicesTextView);
        calendarTextView=(TextView) view.findViewById(R.id.calendarTextView);

        graphLinear=(LinearLayout) view.findViewById(R.id.graphLinearLayout);
        allServicesLayout=(ConstraintLayout) view.findViewById(R.id.allServicesLayout);
        allServicesImage=(ImageView) view.findViewById(R.id.allServicesImageView);
        leftImageBot=(ImageView) view.findViewById(R.id.leftImageBot);
        leftImageTop=(ImageView) view.findViewById(R.id.leftImageTop);
        rightImageBot=(ImageView) view.findViewById(R.id.rightImageBot);
        rightImageTop=(ImageView) view.findViewById(R.id.rightImageTop);
        arrowCalendarImageView=(ImageView) view.findViewById(R.id.arrowCalendarImageView);
        allServicesRecycler=(RecyclerView) view.findViewById(R.id.allServicesRecyclerView);
        pagesRecyclerBot=(RecyclerView) view.findViewById(R.id.pagesRecyclerBot);
        pagesRecyclerUp=(RecyclerView) view.findViewById(R.id.pagesRecyclerUp);


        graphicDateFrame=(GraphicDateFrame) view.findViewById(R.id.graphicDateFrame);


    }
    public void openServices(){
        if(graphLinear.getVisibility()==View.GONE) {
            graphLinear.setVisibility(View.VISIBLE);
            allServicesImage.setImageResource(R.drawable.ic_arrowup);
        }
        else{
            graphLinear.setVisibility(View.GONE);
            allServicesImage.setImageResource(R.drawable.ic_arrowdown);
        }
    }
    public void setServicesAdapter(){
        forms=new ArrayList<>();

        adapter=new ServicesAdapter(forms);
        allServicesRecycler.setAdapter(adapter);
    }
    public void addNewServiceOpen(){
        Fragment fragment=new AddNewServiceFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putInt("location",Integer.parseInt(location));
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
    }
    private void setGraphic(){
        Boolean thismonth=(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH) && Math.abs(cal2.getTimeInMillis()-cal.getTimeInMillis())>1000*60*60*24*33);
        int start=1, end=cal.getActualMaximum(Calendar.DAY_OF_MONTH), end1=cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
        int today=-1;
            if(thismonth)
            today=cal2.get(Calendar.DAY_OF_MONTH);
        List<GraphicDateForm> graphicDateForms=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=start;i<=end;i++){
            Boolean sego=i==today, clicked=false;
            if(today==-1){
                if(i==end){
                    clicked=true;
                }
            }
            else{
                clicked=sego;
            }
            Random random =new Random();
            GraphicDateForm dateForm=new GraphicDateForm(clicked,sego,random.nextInt(50)+50 ,20+random.nextInt(20),0);
            cal.set(Calendar.DAY_OF_MONTH,i);
            dateForm.setCalendar(cal);
            dateForm.setDay(i);
            int day=cal.get(Calendar.DAY_OF_WEEK)-2;
            if(day==-1){
                day=6;
            }
            int weel=(day)%7;
            dateForm.setWeek(((MainActivity)getActivity()).weeks[weel]);
            graphicDateForms.add(dateForm);
        }
        graphicDateFrame.setList(graphicDateForms);
        if(today==-1){
            graphicDateFrame.onScroll(end-1);
        }
        else{
            graphicDateFrame.onScroll(today-1);
        }
    }
}
