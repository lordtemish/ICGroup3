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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AVRAdapter;
import com.studio.dynamica.icgroup.Adapters.ClientControlAdapter;
import com.studio.dynamica.icgroup.Adapters.RateStarsAdapter;
import com.studio.dynamica.icgroup.Forms.AVRForm;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.CheckListForm;
import com.studio.dynamica.icgroup.Forms.MessageWorkForm;
import com.studio.dynamica.icgroup.Forms.OlkForm;
import com.studio.dynamica.icgroup.Forms.svodkaRateForm;
import com.studio.dynamica.icgroup.R;

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
    LinearLayout progressLayout, createNewLayout;
    ConstraintLayout rateLayout;
    ImageView left, right, yearImageView, monthImageView;
    RecyclerView recyclerView, rateRecyclerView;
    Calendar cal;
    String[] data = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    ClientControlAdapter OlkAdapter, svodkaAdapter, checkListAdapter;
    AVRAdapter avrAdapter;
    View.OnClickListener createOlkListener, createCheckListListener, svodkaListener, avrListener;
    String id="", url, city;
    List<Object> olkForms,checkListForms, svodkaRateForms;
    List<AVRForm> avrForms;
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
        view=inflater.inflate(R.layout.fragment_client_control_object_main, container, false);

        createAllViews();
        setFonttypes();
        setAllListeners();
        PickerSettings();

        ((MainActivity)getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        rateRecyclerView.setAdapter(new RateStarsAdapter(true));

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
        rateLayout=(ConstraintLayout) view.findViewById(R.id.rateLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.CCRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
        extraLayout=(FrameLayout) view.findViewById(R.id.extraLayout);

        numberPicker=(NumberPicker) view.findViewById(R.id.numberPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
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
        pageInfo.setText(pages.get(page));
        String cr="Создать ";
        String s="Чек лист";
        switch (page){
            case 0:
                s="ОЛК";
                setProgressLayout();
                recyclerView.setAdapter(OlkAdapter);
                createNewLayout.setOnClickListener(createOlkListener);
                break;
            case 1:
                s="АВР";
                setProgressLayout();
                recyclerView.setAdapter(avrAdapter);
                createNewLayout.setOnClickListener(avrListener);
                break;
            case 2:
                s="Чек лист";
                setProgressLayout();
                recyclerView.setAdapter(checkListAdapter);
                createNewLayout.setOnClickListener(createCheckListListener);
                break;
            case 3:
                s="сводку";
                setSvodkaRate();
                recyclerView.setAdapter(svodkaAdapter);
                createNewLayout.setOnClickListener(svodkaListener);
                break;
        }
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url + "&kind=" + ids.get(page), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
        addNewSetText(cr+s);
    }
    private String getdate(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+"|"+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
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
             if(page==1){
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
             if(page==2){
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
             if(page==3){
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
