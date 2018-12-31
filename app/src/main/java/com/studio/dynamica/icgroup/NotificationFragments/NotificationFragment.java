package com.studio.dynamica.icgroup.NotificationFragments;


import android.app.Notification;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AVRAdapter;
import com.studio.dynamica.icgroup.Adapters.AddOrderAdapter;
import com.studio.dynamica.icgroup.Adapters.ClientControlAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.dynamica.icgroup.Adapters.InventorizationAdapter;
import com.studio.dynamica.icgroup.Adapters.JalobaAdapter;
import com.studio.dynamica.icgroup.Adapters.NotificationAdapter;
import com.studio.dynamica.icgroup.Adapters.PagesAdapter;
import com.studio.dynamica.icgroup.Adapters.ServicesAdapter;
import com.studio.dynamica.icgroup.Adapters.TechnoMapAdapter;
import com.studio.dynamica.icgroup.Forms.AVRForm;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.Forms.CheckListForm;
import com.studio.dynamica.icgroup.Forms.InventorizationForm;
import com.studio.dynamica.icgroup.Forms.JalobaForm;
import com.studio.dynamica.icgroup.Forms.NotificationForm;
import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.Forms.ServiceForm;
import com.studio.dynamica.icgroup.Forms.TechnoMapForm;
import com.studio.dynamica.icgroup.Forms.svodkaRateForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    ConstraintLayout topLayout,botLayout,controlsLayout;
    RecyclerView recyclerView, reqRecycler,pagesRecyclerBot, pagesRecyclerUp;
    PagesAdapter pagesAdapter;
    EquipmentReqAdapter reqAdapter;

    ServicesAdapter servicesAdapter;
    List<ServiceForm> serviceForms;
    JalobaAdapter jalobaAdapter;
    List<JalobaForm> jalobaForms;
    InventorizationAdapter inventorizationAdapter;
    List<InventorizationForm> inventorizationForms;
    AddOrderAdapter addOrderAdapter;
    List<AddOrderForm> addOrderForms;
    TechnoMapAdapter technoMapAdapter;
    List<TechnoMapForm> technoMapForms;

    ClientControlAdapter OlkAdapter, svodkaAdapter, checkListAdapter;
    AVRAdapter avrAdapter;
    List<Object> olkForms,checkListForms, svodkaRateForms;
    List<AVRForm> avrForms;

    List<String> pages, tPages, cPages, cIds;
    TextView mainObjectTitle,pageInfoTextView;
    FrameLayout progressLayout;
    ImageView leftImageBot, leftImageTop, rightImageBot, rightImageTop, ImageLeftView, ImageRightView;
    int page=0, tPage=1,cPage=0,page_count=0;
    String next="", previous="";
    Calendar cal;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal=Calendar.getInstance();cal.setTime(new Date());
        View view=inflater.inflate(R.layout.fragment_notification, container, false);

        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        servicesAdapter=new ServicesAdapter(serviceForms);
        jalobaAdapter=new JalobaAdapter(jalobaForms, false);
        OlkAdapter=new ClientControlAdapter(olkForms, 0);
        avrAdapter=new AVRAdapter(avrForms);
        checkListAdapter=new ClientControlAdapter(checkListForms,1);
        svodkaAdapter=new ClientControlAdapter(svodkaRateForms,2);
        inventorizationAdapter=new InventorizationAdapter(inventorizationForms);
        addOrderAdapter=new AddOrderAdapter(addOrderForms);
        technoMapAdapter=new TechnoMapAdapter(technoMapForms);

        pagesAdapter=new PagesAdapter(tPages);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(pagesRecyclerBot, LinearLayoutManager.HORIZONTAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(pagesRecyclerUp, LinearLayoutManager.HORIZONTAL);
        pagesRecyclerBot.setAdapter(pagesAdapter);pagesRecyclerUp.setAdapter(pagesAdapter);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(reqRecycler, LinearLayoutManager.HORIZONTAL);
        reqAdapter=new EquipmentReqAdapter(pages);
        reqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage();
            }
        });
        reqRecycler.setAdapter(reqAdapter);

        ImageLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPagePlus(false);
            }
        });
        ImageRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPagePlus(true);
            }
        });

        checkPage();
        return view;
    }
    private void getRequest(){

    }

    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        reqRecycler=(RecyclerView) view.findViewById(R.id.reqRecycler);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        topLayout=(ConstraintLayout) view.findViewById(R.id.topLayout);
        botLayout=(ConstraintLayout) view.findViewById(R.id.botLayout);
        controlsLayout=(ConstraintLayout) view.findViewById(R.id.controlsLayout);

        pageInfoTextView=(TextView) view.findViewById(R.id.pageInfoTextView);

        cPages=new ArrayList<>();
        cIds=new ArrayList<>();
        cPages.add("Опросной лист клиента");
        cIds.add("QUESTIONNAIRE");
        cPages.add("Акт выполненых работ");
        cIds.add("PERFORMANCE");
        cPages.add("Чек-лист проверки качества работы");
        cIds.add("CHECKLIST");
        cPages.add("Сводка по результатам обзвона");
        cIds.add("RINGING");
        tPages=new ArrayList<>();
        pages=new ArrayList<>();
        pages.add("Задачи");
        pages.add("Жалобы");
        pages.add("Контроль качества");
        pages.add("Заявки инв.");
        pages.add("Инвентаризация");
        pages.add("Тех. карта");

        serviceForms=new ArrayList<>();
        jalobaForms=new ArrayList<>();
        inventorizationForms=new ArrayList<>();
        addOrderForms=new ArrayList<>();
        technoMapForms=new ArrayList<>();

        olkForms=new ArrayList<>();
        checkListForms=new ArrayList<>();
        svodkaRateForms=new ArrayList<>();
        avrForms=new ArrayList<>();


        leftImageBot=(ImageView)view.findViewById(R.id.leftImageBot);
        leftImageTop=(ImageView)view.findViewById(R.id.leftImageTop);
        rightImageBot=(ImageView)view.findViewById(R.id.rightImageBot);
        rightImageTop=(ImageView)view.findViewById(R.id.rightImageTop);
        ImageLeftView=(ImageView)view.findViewById(R.id.ImageLeftView);
        ImageRightView=(ImageView)view.findViewById(R.id.ImageRightView);
        pagesRecyclerBot=(RecyclerView) view.findViewById(R.id.pagesRecyclerBot);
        pagesRecyclerUp=(RecyclerView) view.findViewById(R.id.pagesRecyclerUp);
    }
    private void checkPage(){
        setPage(reqAdapter.getClicked());
    }
    private void setPage(int a){
        page=a;
        topLayout.setVisibility(View.GONE);
        botLayout.setVisibility(View.GONE);
        controlsLayout.setVisibility(View.GONE);
        switch (a){
            case 1:
                recyclerView.setAdapter(jalobaAdapter);
                getComplaints();
                break;
            case 2:
                controlsLayout.setVisibility(View.VISIBLE);
                cPage=0;
                controlsPages();
                break;
            case 3:
                recyclerView.setAdapter(addOrderAdapter);
                getReplenishments();
                break;
            case 4:
                recyclerView.setAdapter(inventorizationAdapter);
                getCheckGroups();
                break;
            case 5:
                recyclerView.setAdapter(technoMapAdapter);
                getTechno();
                break;
                default:
                    topLayout.setVisibility(View.VISIBLE);
                    botLayout.setVisibility(View.VISIBLE);
                    tPage=1;
                    recyclerView.setAdapter(servicesAdapter);
                    getTasks();
        }
    }
    private void getNext(){
        tPage++;
        getTasks();
    }
    private void getPrevious(){
        tPage--;
        getTasks();
    }
    private void getTasks(){
        String url="tasks/?";
        if(tPage>1){
            url+="&page="+tPage;
        }
        progressLayout.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;
        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                try{
                    if(!response.isNull("count") && page<=1) {
                        int cc = response.getInt("count");
                        page_count=cc/10;
                        if(cc%10>0){
                            page_count++;
                        }
                        if(page_count>0)
                            pagesPage();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                setTasks(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setTasks(JSONObject ob){
        try {
            serviceForms.clear();
            servicesAdapter.notifyDataSetChanged();
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
                JSONObject respondent = object.getJSONObject("respondent");
                JSONObject user = respondent.getJSONObject("user");
                JSONObject department = respondent.getJSONObject("department");
                String fname = user.getString("fullname");
                String[] a = fname.split(" ");
                if (fname.length() > 20) {
                    if (a.length > 1) {
                        fname = a[0] + " " + a[1];
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
                ServiceForm serviceForm = new ServiceForm(kindText, fname, department.getString("name"), status, priority, nowdays, days);
                serviceForm.setId(object.getString("id"));
                serviceForms.add(serviceForm);
            }
            Log.d("res", serviceForms.size() + "");
            servicesAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getComplaints(){
            progressLayout.setVisibility(View.VISIBLE);
            String url=((MainActivity)getActivity()).MAIN_URL+"complaints/";
            JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setComplaints(response);
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
    private void setComplaints(JSONArray array){
        try {
            jalobaForms.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONObject author = object.getJSONObject("author");
                if (!object.isNull("defendant")) {
                    JSONObject defendant = object.getJSONObject("defendant");
                }
                String content = object.getString("content");
                String id = object.getString("id");
                String authorrole = ((MainActivity) getActivity()).positions.get(author.getString("role"));
                String name = author.getString("fullname");
                String created_at = object.getString("created_at");
                String created = ((MainActivity) getActivity()).getdate(created_at);
                created = created.substring(0, created.length() - 6);
                boolean arch = !object.isNull("reply_comment");
                JalobaForm jalobaForm = new JalobaForm(created, authorrole, name, authorrole, content);
                jalobaForm.setId(id);
                jalobaForm.setAnswered(arch);
                jalobaForms.add(jalobaForm);
            }
            jalobaAdapter.notifyDataSetChanged();
        }
        catch (Exception e){e.printStackTrace();}
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
    private void pagesPage(){
        tPages.clear();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=1;i<=page_count;i++){
            tPages.add(i+"");
            final int j=i;
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tPage=j;
                    getTasks();
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
        pagesAdapter.setPage(tPage);
        pagesAdapter.setListeners(listeners);

        if(tPage==page_count){
            rightImageTop.setVisibility(View.GONE);
            rightImageBot.setVisibility(View.GONE);
        }
        else{
            rightImageTop.setVisibility(View.VISIBLE);
            rightImageBot.setVisibility(View.VISIBLE);
        }
        if(tPage==1){
            leftImageTop.setVisibility(View.GONE);
            leftImageBot.setVisibility(View.GONE);
        }
        else{
            leftImageTop.setVisibility(View.VISIBLE);
            leftImageBot.setVisibility(View.VISIBLE);
        }
        pagesAdapter.notifyDataSetChanged();
        if(page>0){
            rightImageTop.setVisibility(View.GONE);
            rightImageBot.setVisibility(View.GONE);
            leftImageTop.setVisibility(View.GONE);
            leftImageBot.setVisibility(View.GONE);
        }
    }
    public void setPagePlus(boolean plus){
        if(plus)
            cPage=(cPage+1)%cPages.size();
        else
            cPage=(cPage-1)%cPages.size();
        if(cPage==-1){
            cPage=cPages.size()-1;
        }
        controlsPages();
    }
    private void controlsPages(){
        progressLayout.setVisibility(View.VISIBLE);
        pageInfoTextView.setText(cPages.get(cPage));
        String url=((MainActivity)getActivity()).MAIN_URL+"controls/?";
        String cr="Создать ";
        String s="Чек лист";
        switch (cPage){
            case 0:
                s="ОЛК";
                recyclerView.setAdapter(OlkAdapter);
                break;
            case 1:
                s="АВР";
                recyclerView.setAdapter(avrAdapter);
                break;
            case 2:
                s="Чек лист";
                recyclerView.setAdapter(checkListAdapter);
                break;
            case 3:
                s="сводку";
                recyclerView.setAdapter(svodkaAdapter);
                break;
        }
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url + "&kind=" + cIds.get(page), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setControls(response);
                progressLayout.setVisibility(View.GONE);
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
        ((MainActivity)getActivity()).requestQueue.add(jsonArrayRequest);
    }
    private void setControls(JSONArray array){
        try {
            if(cPage==0){
                olkForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONObject author=object.getJSONObject("author");
                    String created_at=object.getString("created_at");
                    Date date=((MainActivity)getActivity()).inputFormat.parse(created_at);
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
            if(cPage==1){
                avrForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String date1=object.getString("created_at");
                    Date date=((MainActivity)getActivity()).inputFormat.parse(date1);
                    Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                    String thatdate=getdate(calendar,0);
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

                    AVRForm avrForm=new AVRForm(thatdate,sum, "","","");
                    avrForm.setId(object.getString("id"));
                    avrForms.add(avrForm);
                }
                avrAdapter.notifyDataSetChanged();
            }
            if(cPage==2){
                checkListForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String date1=object.getString("created_at");
                    Date date=((MainActivity)getActivity()).inputFormat.parse(date1);
                    Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                    String thatdate=getdate(calendar,0);
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
            if(cPage==3){
                svodkaRateForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    String date1=object.getString("created_at");
                    Date date=((MainActivity)getActivity()).inputFormat.parse(date1);
                    Calendar calendar=Calendar.getInstance();calendar.setTime(date);
                    String thatdate=getdate(calendar,0);
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
    private String getdate(Calendar calendar, int a){
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR);
    }

    private void getCheckGroups(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"checkgroups/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setCGroups(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеднения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setCGroups(JSONArray array){
        try{
            inventorizationForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject receiving=object.getJSONObject("receiving");
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-6);
                String name=receiving.getString("fullname"), role=receiving.getString("role");
                double match_rate=object.getDouble("match_rate");String position=((MainActivity)getActivity()).positions.get(role);
                int rate=Integer.parseInt(""+Math.round(match_rate*100));
                InventorizationForm form=new InventorizationForm(created,name, position, rate );
                form.setId(object.getString("id"));
                inventorizationForms.add(form);
            }
            inventorizationAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getReplenishments(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"replenishments/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setRepls(response);
                progressLayout.setVisibility(View.GONE);
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
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setRepls(JSONArray array){
        try {

            addOrderForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String
                        created_at=object.getString("created_at"),
                        deadline=object.getString("deadline"),
                        description=object.getString("description"),
                        id=object.getString("id"),
                        kind=object.getString("kind"), status=object.getString("status");
                int pri=object.getInt("priority");
                String rKind=((MainActivity)getActivity()).replKinds.get(kind);
                String date=((MainActivity)getActivity()).getdate(created_at);
                date=date.substring(0,date.length()-6);
                String priority="Низкий";
                switch (pri){
                    case 3:
                        priority="Высокий";
                        break;
                    case 2:
                        priority="Средний";
                        break;
                }
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
                if(wDays<nDays){
                    days=0;
                    nowdays=0;
                }
                if (days - nowdays > 0) {
                    Log.d("nowdays", days + " " + nowdays);
                    nowdays = days - nowdays;
                    Log.d("nowdays", (days - nowdays > 0) + "");
                } else {
                    nowdays = 0;
                }
                AddOrderForm orderForm=new AddOrderForm(date,id,"", priority, status, rKind, nowdays, days);
                orderForm.setMass(object.isNull("count"));
                addOrderForms.add(orderForm);
            }
            addOrderAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getTechno(){
        progressLayout.setVisibility(View.VISIBLE);
        String url = ((MainActivity) getActivity()).MAIN_URL + "plans/";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setTechno(response);
                progressLayout.setVisibility(View.GONE);
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
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }
        };
        ((MainActivity) getActivity()).requestQueue.add(jsonArrayRequest);
    }
    private void setTechno(JSONArray array){
        try {
            progressLayout.setVisibility(View.VISIBLE);
            technoMapForms.clear();
            try {
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject plan = object;
                    String begin = plan.getString("begin");
                    String end = plan.getString("end");
                    String[] beg = begin.split(":"), en = end.split(":");
                    JSONObject sec = null, meth = null;
                    String sector = "",
                            method = "";
                    if(!plan.isNull("sector")){
                        sec=plan.getJSONObject("sector");
                        sector=sec.getString("name");
                    }
                    if(!plan.isNull("method")){
                        meth=plan.getJSONObject("method");
                        method=meth.getString("name");
                    }
                    int duration = plan.getInt("duration");

                    int stat1 = 3;
                    if(!true){
                        if(true){
                            stat1=5;
                        }
                        else
                            stat1=1;
                    }
                    else{
                        int h1=Integer.parseInt(beg[0]),h2=Integer.parseInt(en[0]),m1=Integer.parseInt(beg[1]),m2=Integer.parseInt(en[1]);
                        int nowH=cal.get(Calendar.HOUR_OF_DAY), nowM=cal.get(Calendar.MINUTE);
                        boolean before=(nowH<h1 || (nowH==h1 && nowM<m1)), between=(((nowH>h1 ||(nowH==h1 && nowM>m1)))&& (nowH<h2 || (nowH==h2 && nowM<m2)));
                        Log.d("check_when_it_was "+h1+":"+m1+"   "+h2+":"+m2, before+" "+between);
                        if(before){
                            stat1=5;
                        }
                        else if(between){
                            stat1=3;
                        }
                        else{
                            stat1=1;
                        }
                    }
                    TechnoMapForm form = new TechnoMapForm(beg[0] + ":" + beg[1] + "-" + en[0] + ":" + en[1], sector,  method, duration + " мин", stat1);
                    form.setId(plan.getString("id"));
                    form.setResult(false);
                    technoMapForms.add(form);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            technoMapAdapter.notifyDataSetChanged();
            if(!((MainActivity)getActivity()).client)
                resultRequest();
            else{
                progressLayout.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void resultRequest(){
        String url = ((MainActivity) getActivity()).MAIN_URL + "results/";
        Log.d("resultsURL",url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setResults(response);
                Log.d("resulLength",response.length()+"");
                progressLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }) {  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }
        };
        ((MainActivity) getActivity()).requestQueue.add(jsonArrayRequest);
    }
    private void setResults(JSONArray array){
        try{
            for(int i=0;i<array.length();i++) {
                JSONObject object = array.getJSONObject(i);
                /*JSONObject plan = object.getJSONObject("plan");
                String begin = plan.getString("begin");
                String end = plan.getString("end");
                String[] beg = begin.split(":"), en = end.split(":");
                JSONObject sec = plan.getJSONObject("sector"), cat = plan.getJSONObject("category"), meth = plan.getJSONObject("method");
                String sector = sec.getString("name"), category = cat.getString("id"), method = meth.getString("name");
                int duration = plan.getInt("duration");*/
                int plan=object.getInt("plan");
                String status = object.getString("status");
                int stat1 = 0;
                switch (status) {
                    case "PRAISED":
                        stat1 = 1;
                        break;
                    case "FAILED":
                        stat1 = 0;
                        break;
                    case "WARNED":
                        stat1 = 1;
                        break;
                    default:
                        stat1 = 3;
                }
                for(TechnoMapForm f:technoMapForms){
                    if(f.getId().equals(plan+"")){
                        f.setId(object.getString("id"));
                        f.setStat(stat1);
                        f.setResult(true);
                        break;
                    }
                }
            }
            technoMapAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);

        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(notificationRecyclerView, LinearLayoutManager.VERTICAL);
       forms=new ArrayList<>();main=new ArrayList<>();archive=new ArrayList<>();
        forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));
        adapter=new NotificationAdapter(forms);
        notificationRecyclerView.setAdapter(adapter);

        newNotificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPage(0);
            }
        });
        archiveNotificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPage(1);
            }
        });
        getRequest();
        return view;
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"notices/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONArray array){
        try{
            forms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String objectid=object.getString("object_id");
                JSONObject user=object.getJSONObject("user");
                JSONObject content_type=object.getJSONObject("content_type");
                String title=object.getString("title");

                String model=content_type.getString("model");
                String authorName=object.getString("author_fullname");

                String role=object.getString("author_role");
                String position=((MainActivity)getActivity()).positions.get(role);
                Boolean arc=object.getBoolean("is_archived");
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-5);

                String type="Задача";

                NotificationForm form=new NotificationForm(type,created,title,authorName, position);
                form.setId(objectid);
                form.setObjectType(model);
                form.setIs_archive(arc);
                if(arc){
                    archive.add(form);
                }
                else{
                    main.add(form);
                }
            }
            checkPage();
        }
        catch (JSONException e){

        }
    }
    private void createViews(View view){
        notificationRecyclerView=(RecyclerView) view.findViewById(R.id.notificationsRecyclerView);
        archiveNotificationsTextView=(TextView) view.findViewById(R.id.archiveNotificationsTextView);
        newNotificationsTextView=(TextView) view.findViewById(R.id.newNotificationsTextView);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
    }
    private void checkPage(){
        forms.clear();
        if(page==0){
            forms.addAll(main);
            newNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            archiveNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
        else{
            forms.addAll(archive);
            archiveNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
        adapter.notifyDataSetChanged();
    }
    private void setPage(int a){
        page=a;
        checkPage();
    }*/