package com.studio.dynamica.icgroup.ObjectFragments;


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
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.DaysAdapter;
import com.studio.dynamica.icgroup.Adapters.TechnoMapAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.DateChooseView;
import com.studio.dynamica.icgroup.Forms.TechnoMapForm;
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
public class TechnoMapFragment extends Fragment {
    List<String> pages, ids;
    ImageView rightPage, leftPage;
    TextView pageInfo, mainObjectTitle,extraText, period, service, category, status;
    RecyclerView  techoMapRec;
    FrameLayout extraLayout;
    DateChooseView dateChooseView;
    int page;
    String id="", date="";
    boolean today=true, soon=false;
    List<TechnoMapForm> forms;
    Calendar cal;
    TechnoMapAdapter adapter1;
    public TechnoMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cal=Calendar.getInstance();cal.setTime(new Date());
        id=getArguments().getString("id");
        page=0;
        pages=new ArrayList<>();
        ids=new ArrayList<>();
        pages.add("Коридоры лифтовые зоны и все такое");
        pages.add("Подоконники и разные у оконныз зоны");
        pages.add("Полы в коридорах");
        View view= inflater.inflate(R.layout.fragment_techno_map_fragment, container, false);
        createViews(view);
        setFonttype();
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(true);
            }
        });
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPagePlus(false);
            }
        });







        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity());
        forms=new ArrayList<>();
        adapter1=new TechnoMapAdapter(forms);
        techoMapRec.setLayoutManager(layoutManager1);
        techoMapRec.setItemAnimator(new DefaultItemAnimator());
        techoMapRec.setAdapter(adapter1);
        today=dateChooseView.today();
        date=dateChooseView.dateDivided();
        getRequest();
        return view;
    }

    private void getRequest(){
        extraLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"plangroups/?point="+id;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                extraLayout.setVisibility(View.GONE);
                setPlanGroups(response);
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
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setPlanGroups(JSONArray array){
        try {
            pages.clear();
            ids.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                pages.add(object.getString("name"));
                ids.add(object.getString("id"));
            }
            checkPage();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setPlans(JSONArray array){
        try {
            extraLayout.setVisibility(View.VISIBLE);
            forms.clear();
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
                    if(!today){
                        if(soon){
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
                    forms.add(form);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            adapter1.notifyDataSetChanged();
            if(!((MainActivity)getActivity()).client)
            resultRequest();
            else{
                extraLayout.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void resultRequest(){
        String url = ((MainActivity) getActivity()).MAIN_URL + "results/?plan__group__point=" + id;
        if(date.length()>0){
            url+="&date="+date;
        }
        Log.d("resultsURL",url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setResults(response);
                Log.d("resulLength",response.length()+"");
                extraLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                extraLayout.setVisibility(View.GONE);
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
                for(TechnoMapForm f:forms){
                    if(f.getId().equals(plan+"")){
                        f.setId(object.getString("id"));
                        f.setStat(stat1);
                        f.setResult(true);
                        break;
                    }
                }
            }
            adapter1.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createViews(View view){
        rightPage=(ImageView) view.findViewById(R.id.rightPageImageView);
        leftPage=(ImageView) view.findViewById(R.id.leftPageImageView);
        pageInfo=(TextView) view.findViewById(R.id.pageInfoTextView);
        extraText=(TextView) view.findViewById(R.id.extraText);
        period=(TextView) view.findViewById(R.id.period);
        category=(TextView) view.findViewById(R.id.category);
        service=(TextView) view.findViewById(R.id.service);
        status=(TextView) view.findViewById(R.id.status);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        techoMapRec=(RecyclerView) view.findViewById(R.id.technoMapRecyclerView);
        extraLayout=(FrameLayout) view.findViewById(R.id.extraLayout);
        dateChooseView=(DateChooseView) view.findViewById(R.id.dateChooseView);
        dateChooseView.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = dateChooseView.dateDivided();
                today=dateChooseView.today();
                soon=dateChooseView.soon();
                Log.d("todayCHeckTechno",today+" "+date+" "+soon);
                checkPage();
            }
        });
    }

    public void setDate(String date) {
        this.date = date;
    }

    private void setFonttype(){
        setTypeFace("demibold", pageInfo);
        setTypeFace("light", extraText, period, status, category, service);
        setTypeFace("it", mainObjectTitle);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
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
        try {
        extraLayout.setVisibility(View.VISIBLE);
        pageInfo.setText(pages.get(page));
        String url = ((MainActivity) getActivity()).MAIN_URL + "plans/?group=" + ids.get(page);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setPlans(response);
                extraLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                extraLayout.setVisibility(View.GONE);
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
    catch (IndexOutOfBoundsException e){
        e.printStackTrace();
        Toast.makeText(getActivity(), "Нет списка групп технологической карты", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).onBackPressed();
    }
    }
}
