package com.studio.crm.icgroup.ObjectFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.PagesAdapter;
import com.studio.crm.icgroup.Adapters.ServicesAdapter;
import com.studio.crm.icgroup.ExtraFragments.GraphicDateFrame;
import com.studio.crm.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.crm.icgroup.ExtraFragments.ServiceSortView;
import com.studio.crm.icgroup.ExtraFragments.YearMonthView;
import com.studio.crm.icgroup.Forms.GraphicDateForm;
import com.studio.crm.icgroup.Forms.OneCallBack;
import com.studio.crm.icgroup.Forms.ServiceForm;
import com.studio.crm.icgroup.Listeners.LoadListener;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceObjectMainFragment extends Fragment {
    LinearLayout graphLinear;
    ConstraintLayout allServicesLayout;
    List<ServiceForm> forms;
    int max=0;
    ServicesAdapter adapter;PagesAdapter pagesAdapter;
    RecyclerView allServicesRecycler, pagesRecyclerBot, pagesRecyclerUp;
    ImageView allServicesImage, arrowCalendarImageView, leftImageBot, leftImageTop, rightImageBot, rightImageTop;
    FrameLayout addNewServiceFrame, progressLayout;
    ConstraintLayout allOtdelsLayout, allEmployeesLayout;
    TextView ObjectTitle, allOtdelsTextView, allEmployeesTextView,calendarTextView, PercentageTextView, allServicesTextView;
    NumberPicker monthPicker, yearPicker;
        String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    Calendar cal, cal2;
    String id="", dpid="", emid="", previous="",next="";
    int location,page=1, page_count=0;
    List<String> departments, dpids, employees, emids, pages;
    MainObjectSetInfoFragment setFragment;
    boolean timeChange=false;
    LinearLayoutManager layoutManager;
    GraphicDateFrame graphicDateFrame;
    YearMonthView yearMonthView;

    ServiceSortView serviceSortView;

    OneCallBack callBack;
    public ServiceObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            id=getArguments().getString("id");
            location=getArguments().getInt("location");
        }
        catch (Exception e){

        }
        View view=inflater.inflate(R.layout.fragment_service_object_main, container, false);

        createViews(view);
        setFonttype();
        //PickerSettings();

        allServicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServices();
            }
        });

        addNewServiceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewServiceOpen();
            }
        });
        View.OnClickListener dateClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrowClick();
            }
        };
        calendarTextView.setOnClickListener(dateClick);
//        arrowCalendarImageView.setOnClickListener(dateClick);

        layoutManager=new LinearLayoutManager(getActivity());
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
       // getRequest();
        getDepartments();
        allOtdelsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetFragment();
                if (departments.size()>0){
                    setFragment.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dpid="";
                            emid="";
                            showSetFragment();
                            getRequest();
                        }
                    });
                    List<View.OnClickListener> listeners=new ArrayList<>();
                    for(final String dp:dpids){
                        listeners.add(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dpid=dp;
                                showSetFragment();
                                getEmployees();
                                getRequest();
                            }
                        });
                    }
                    setFragment.setLinsteners(listeners);
                    setFragment.setList(departments);
                }
                else {
                    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        allEmployeesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSetFragment();
                if(dpid.length()>0){
                    setFragment.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            emid="";
                            showSetFragment();
                            getRequest();
                        }
                    });
                    List<View.OnClickListener> listeners=new ArrayList<>();
                    for(final String em:emids){
                       listeners.add(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               emid=em;
                               showSetFragment();
                               getRequest();
                           }
                       });
                    }
                    setFragment.setLinsteners(listeners);
                    setFragment.setList(employees);
                }
                else{
                    Toast.makeText(getActivity(), "Выберите Отдел", Toast.LENGTH_SHORT).show();
                }
            }
        });
        allServicesRecycler.addOnScrollListener(new LoadListener(layoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Log.d("page"," "+current_page+" "+forms.size());
            }
        });

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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                    Log.d("token",headers.toString());
                    return headers;
                }
            };
            ((MainActivity)getActivity()).requestQueue.add(objectRequest);
        }
    }
    private void setEmployees(JSONArray array){
        try {
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject user=object.getJSONObject("user");
                employees.add(user.getString("fullname")+"\n"+((MainActivity)getActivity()).positions.get(user.getString("role")));
                emids.add(object.getString("id"));
            }
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
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setDepartments(JSONArray array){
        try{
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                departments.add(object.getString("name"));
                dpids.add(object.getString("id"));
            }
            ((MainActivity)getActivity()).setDpids(dpids);
            ((MainActivity)getActivity()).setDepartments(departments);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        page=1;
        setDate();
    }
    private void getRequest(){
        Log.d("BOOLEANS"," "+(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH)) +" "+(cal2.get(Calendar.YEAR)==cal.get(Calendar.YEAR)));
        if(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH) && cal2.get(Calendar.YEAR)==cal.get(Calendar.YEAR)){
           // cal.setTime(cal2.getTime());
        }
        Calendar cali=Calendar.getInstance();
        cali.setTime(cal.getTime());
        cali.set(Calendar.DAY_OF_MONTH,1);
        cali.set(Calendar.HOUR_OF_DAY,0);
        cali.set(Calendar.MINUTE,0);
        cali.set(Calendar.SECOND,0);
        //cali.add(Calendar.HOUR_OF_DAY,-6);
        String s=((MainActivity)getActivity()).inputFormat.format(cali.getTime());
        cali.setTime(cal.getTime());
        cali.set(Calendar.DAY_OF_MONTH,cali.getActualMaximum(Calendar.DAY_OF_MONTH));
        cali.set(Calendar.HOUR_OF_DAY,23);
        cali.set(Calendar.MINUTE,59);
        cali.set(Calendar.SECOND,59);
      //  cali.add(Calendar.HOUR_OF_DAY,-6);
        String e=((MainActivity)getActivity()).inputFormat.format(cali.getTime());
        s=s.substring(0,s.indexOf("T"))+" "+s.substring(s.indexOf("T")+1,s.length()-5);
        e=e.substring(0,e.indexOf("T"))+" "+e.substring(e.indexOf("T")+1,e.length()-5);
        String url="tasks/?point="+id+"&created_at__lte="+e+"&created_at__gte="+s;

        if(graphLinear.getVisibility()==View.VISIBLE){
            String ur="&";
            ur+=serviceSortView.getUrl();
            url+=ur;
        }

        setDateText(cal);

        Log.d("url",url);
        if(page>1){
            url+="&page="+page;
        }
        progressLayout.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;
        if(dpid.length()>0){
        }
        if(emid.length()>0){

        }
        Log.d("TASK SE", url);
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
                        if(page_count>0)
                            checkPage();
                    }
                    Log.d("RESP",response.toString());

                    setInfo(response);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)  {
                try {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    progressLayout.setVisibility(View.GONE);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
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
    private void getNext(){
        String url=next;
        progressLayout.setVisibility(View.VISIBLE);
        if(dpid.length()>0){
        }
        if(emid.length()>0){

        }
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
                        if(page_count>0)
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
                error.printStackTrace();
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
        if(dpid.length()>0){
        }
        if(emid.length()>0){

        }
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
    private void setInfo(JSONObject ob){
        try {
            forms.clear();
            adapter.notifyDataSetChanged();
            progressLayout.setVisibility(View.GONE);

            JSONArray array = ob.getJSONArray("results");
            Log.d("nextprev",ob.isNull("next")+" "+ob.isNull("previous"));
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
                   // JSONObject user = respondent.getJSONObject("user");
                    //JSONObject department = respondent.getJSONObject("department");
                    //dpStr=department.getString("name");
                    fname = respondent.getString("fullname");
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
                Calendar cal=Calendar.getInstance();
                cal.setTime(created);cal.add(Calendar.HOUR,6);

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

                String time1=((MainActivity)getActivity()).inputFormat.format(cal.getTime());
                Log.d("TIME",time1.substring(11,16)+ " "+time1.substring(0,10));
                serviceForm.setTime(time1.substring(11,16),time1.substring(0,10));

                forms.add(serviceForm);
            }
            Log.d("res", forms.size() + "   "+array.length());
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
            yearMonthView.setVisibility(View.GONE);

            cal.set(yearMonthView.yearPicker.getValue(),yearMonthView.monthPicker.getValue(),1);
            if(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH) && Math.abs(cal2.getTimeInMillis()-cal.getTimeInMillis())>1000*60*60*24*33) {
                cal.set(Calendar.DAY_OF_MONTH,cal2.get(Calendar.DAY_OF_MONTH));
            }
            setDate();
        }
        else{
            timeChange=true;

            yearMonthView.setVisibility(View.VISIBLE);

        }
    }
    private void setGraphics(JSONObject object){
        try {
            Log.d("Graphic info",""+object.length()+" ");
            JSONArray array=object.names();
            max=-1;
            String total="__total_count", fin="__finished_count";
            List<List<Integer>> lists=new ArrayList<>();
            for (int i=0;i<array.length();i++){
                String key=array.getString(i);
                String day=(i/2+1)+"";if(day.length()==1) day="0"+day;
                int val=object.getInt(key);
                Log.d("somec ",key+"   "+val);
                if(i%2==0) {
                    lists.add(new ArrayList<Integer>());

                    val=object.getInt(key.substring(0,8)+day+total);
                    lists.get(i/2).add(val);
                    if(val>max)max=val;
                }
                else{
                    val=object.getInt(key.substring(0,8)+day+fin);
                    lists.get(i/2).add(val);
                }
            }
            if(max==0){
                max=0;
            }
            setGraphic(lists);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getGraphic(String extra){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"tasks/dates_stats/?point="+id+"&"+extra;
        Log.d("URL GRAPH", url);
        JSONObject params=new JSONObject();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setGraphics(response);
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
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }
        };((MainActivity)getActivity()).requestQueue.add(jsonObjectRequest);
    }
    private void setDate(){
        calendarTextView.setText(months[cal.get(Calendar.MONTH)]+" "+cal.get(Calendar.YEAR));
        int start=1, end=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);
        cal.set(Calendar.MILLISECOND,0);
        String gte=((MainActivity)getActivity()).inputFormat.format(calendar.getTime()).substring(0,10);
        calendar.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),end,0,0,0);
        String lte=((MainActivity)getActivity()).inputFormat.format(calendar.getTime()).substring(0,10);

        String ex="created_at__lte="+lte+"&created_at__gte="+gte;
        Log.d("EX DATES",ex);
       // getGraphic(ex);
       // setGraphic();
        getRequest();
    }

    private void setFonttype(){
        ObjectTitle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));
        allOtdelsTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("medium"));
        allEmployeesTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("medium"));
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
        yearPicker.setMinValue(cal.get(Calendar.YEAR)-1);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+2);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    private void createViews(View view){
        serviceSortView=(ServiceSortView) view.findViewById(R.id.serviceSortView);
        serviceSortView.radioAdapter.setCallBack(new OneCallBack() {
            @Override
            public void callBackCal() {
                setDate();
            }
        });
        yearMonthView=(YearMonthView)view.findViewById(R.id.yearMonthView);

        departments=new ArrayList<>();dpids=new ArrayList<>();emids=new ArrayList<>();employees=new ArrayList<>();

        leftImageBot=(ImageView)view.findViewById(R.id.leftImageBot);
        leftImageTop=(ImageView)view.findViewById(R.id.leftImageTop);
        rightImageBot=(ImageView)view.findViewById(R.id.rightImageBot);
        rightImageTop=(ImageView)view.findViewById(R.id.rightImageTop);
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        setFragment=(MainObjectSetInfoFragment) view.findViewById(R.id.setFragment);
        monthPicker=(NumberPicker) view.findViewById(R.id.monthPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        cal=Calendar.getInstance();
        cal2=Calendar.getInstance();
        cal2.setTime(new Date());
        cal.setTime(new Date());

        yearMonthView.setPickers(cal);
        yearMonthView.setCallBack(new OneCallBack() {
            @Override
            public void callBackCal() {
                arrowClick();
            }
        });
        yearMonthView.setVisibility(View.GONE);

        ObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        allServicesTextView=(TextView) view.findViewById(R.id.allServicesTextView);
        calendarTextView=(TextView) view.findViewById(R.id.calendarTextView);
        allOtdelsTextView=(TextView) view.findViewById(R.id.allOtdelsTextView);
        allEmployeesTextView=(TextView) view.findViewById(R.id.allEmployeesTextView);
        graphLinear=(LinearLayout) view.findViewById(R.id.graphLinearLayout);
        allServicesLayout=(ConstraintLayout) view.findViewById(R.id.allServicesLayout);
        allServicesImage=(ImageView) view.findViewById(R.id.allServicesImageView);
        arrowCalendarImageView=(ImageView) view.findViewById(R.id.arrowCalendarImageView);
        allServicesRecycler=(RecyclerView) view.findViewById(R.id.allServicesRecyclerView);

        addNewServiceFrame=(FrameLayout) view.findViewById(R.id.addNewServiceFrameLayout);
        graphicDateFrame=(GraphicDateFrame) view.findViewById(R.id.graphicDateFrame);

        allOtdelsLayout=(ConstraintLayout) view.findViewById(R.id.allOtdelsLayout);
        allEmployeesLayout=(ConstraintLayout) view.findViewById(R.id.allEmployeesLayout);


        pagesRecyclerBot=(RecyclerView) view.findViewById(R.id.pagesRecyclerBot);
        pagesRecyclerUp=(RecyclerView) view.findViewById(R.id.pagesRecyclerUp);
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
        bundle.putInt("location",location);
        fragment.setArguments(bundle);
        ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
    }
    private void setGraphic(List<List<Integer>> lists){
        int nnu=1000*60*60*24*33;
        Boolean thismonth=(cal.get(Calendar.MONTH)==cal2.get(Calendar.MONTH) && Math.abs(cal2.getTimeInMillis()-cal.getTimeInMillis())>nnu);
        int start=1, end=cal.getActualMaximum(Calendar.DAY_OF_MONTH), end1=cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
        int today=-1;
        if(thismonth)
        today=cal2.get(Calendar.DAY_OF_MONTH);
        List<GraphicDateForm> graphicDateForms=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        graphicDateFrame.setMaxValue(max+"");
        for(int i=start;i<=lists.size();i++){
            Boolean sego=i==today, clicked=false;
            List<Integer> integers=lists.get(i-1);
            Log.d("days",i+" , "+integers.get(0)+"  "+integers.get(1));
            if(today==-1){
                if(i==end){
                    clicked=true;
                }
            }
            else{
                clicked=sego;
            }
            final int high=Integer.parseInt(""+Math.round((Double.parseDouble(integers.get(0)+"")/max*100))), low=Integer.parseInt(""+Math.round((Double.parseDouble(integers.get(1)+"")/max*100))), ind=i;
            Log.d("kerekrker",""+high+" "+low);
            GraphicDateForm dateForm=new GraphicDateForm(clicked,sego,high,low,0);
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

            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(high>0){
                        cal.set(Calendar.DAY_OF_MONTH,ind);
                        getRequest();
                    }
                    else{
                        cal.set(Calendar.DAY_OF_MONTH,ind);
                        clearRecycler();
                    }
                }
            });
        }
        graphicDateFrame.setListeners(listeners);
        graphicDateFrame.setList(graphicDateForms);
        if(today==-1){
            graphicDateFrame.onScroll(end-1);
        }
        else{
            graphicDateFrame.onScroll(today-1);
        }
       // cal.setTime(cal2.getTime());
        getRequest();
        setDateText(cal2);
    }
    public void clearRecycler(){
        setDateText(cal);

        forms.clear();
        adapter.notifyDataSetChanged();
    }

    public void setDateText(Calendar cal){
        int da=cal.get(Calendar.DAY_OF_MONTH), mo=(cal.get(Calendar.MONTH)+1);
        String date="";
        if(da<10)
            date+="0"+da;
        else
            date+=da;
        date+=".";
        if(mo<10)
            date+="0"+mo;
        else
            date+=mo;

    }
}
