package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
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
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AttendanceAdapter;
import com.studio.crm.icgroup.ExtraFragments.AttendanceChooseView;
import com.studio.crm.icgroup.Forms.AttendanceRowForm;
import com.studio.crm.icgroup.Forms.AttendanceRowItemForm;
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
public class AttendanceMainFragment extends Fragment {
    List<AttendanceRowForm> rowForms;
    FrameLayout todayFrame, progressLayout;
    RecyclerView recyclerView;
    AttendanceChooseView attendanceChooseView;
    FrameLayout chooseLayout;
    View.OnClickListener emptyL, postL;
    NumberPicker datePicker, yearPicker;
    TextView mainObjectTitle, yearTextView, monthTextView, smenasTextView, smenaCountTextView, totalDays,allWorkers, absents, heres;
    ImageView dateArrowImageView, yearArrowImageView, leftCalArrow, rightCalArrow, ImageRightView, ImageLeftView;
    Calendar cal, cal2;
    List<String> strings;
    List<TextView> calTextViews;
    boolean swiped=false, today=true, working=false;
    String id="", choseworker="";
    List<JSONObject> atts;
    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"}
        , weeks={"Пн","Вт","Ср","Чт","Пт","Сб","Вс"}
    ;
    int shift_count=0,shift=1, location=1;
    AttendanceAdapter attendanceAdapter;
    public AttendanceMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        shift_count=getArguments().getInt("shift_count");
        location=getArguments().getInt("location",1);
        atts=new ArrayList<>();
        cal=Calendar.getInstance();
        cal2=Calendar.getInstance();
        cal.setTime(new Date());
        View view = inflater.inflate(R.layout.fragment_attendance_main, container, false);
        createViews(view);
        chooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLayout.setVisibility(View.GONE);
            }
        });

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        rowForms = new ArrayList<>();
        List<AttendanceRowItemForm> itemForms = new ArrayList<>();
        final int d=25;
        AttendanceRowItemForm itemForm=new AttendanceRowItemForm("",true, false, false);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setChooseLayout("");
            }
        };
        itemForms.add(itemForm);
        itemForms.add(new AttendanceRowItemForm("",true, false, false));
        itemForms.add(new AttendanceRowItemForm("",false, true, true));
        itemForms.add(new AttendanceRowItemForm("",false, true, false));
        itemForms.add(new AttendanceRowItemForm(""));
        AttendanceRowForm attendanceRowForm=new AttendanceRowForm("1","Темирлан", itemForms, 5, 7);
        attendanceRowForm.setListener(listener);
        rowForms.add(attendanceRowForm);
        rowForms.add(new AttendanceRowForm("2","Темирлан Алмасов", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("3","Темирлан Алмасов Даулетович", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("4","Надира Рыскулова", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("5","Рыскулова Надира", itemForms, 5, 7));


        attendanceAdapter = new AttendanceAdapter(rowForms);
        recyclerView.setAdapter(attendanceAdapter);

        pickerSettings();
        setDate();
        setFonttypes();
        setListeners();

        chooseLayout.setVisibility(View.GONE);

        ImageLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(-1);
            }
        });
        ImageRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(+1);
            }
        });
        checkPage();
        getPoint();

        attendanceChooseView.setLocation(location);
        return view;
    }
    private void changePage(int a){
        shift+=a;
        if(shift==0){
            shift=shift_count;
        }
        if(shift==shift_count+1){
            shift=1;
        }
        checkPage();
    }
    private void checkPage(){
        smenasTextView.setText("Cмена "+shift);
        getRequest("workers/?point="+id+"&shift="+shift);
        // shifts- month year point
        if(today){
            getShifts();
        }
    }
    public void setChooseLayout(String id){
        chooseLayout.setVisibility(View.VISIBLE);
        choseworker=id;
        attendanceChooseView.setId(id);
      //  Log.d("worker",choseworker);
    }
    private void postRequest(){
        if(attendanceChooseView.getChose().equals("-1") && attendanceChooseView.getCheckedPosition()==3){
            Toast.makeText(getActivity(), "Выберите заменяющего", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = ((MainActivity) getActivity()).MAIN_URL + "visits/";
            try {
                progressLayout.setVisibility(View.VISIBLE);
                JSONObject params = new JSONObject();
                int i = attendanceChooseView.getCheckedPosition();
                String s = "PRESENT";
                switch (i) {
                    case 1:
                        s = "ABSENT";
                        break;
                    case 2:
                        s = "ILL";
                        break;
                    case 3:
                        s = "REPLACE";
                        String st = attendanceChooseView.getChose();
                        if (!st.equals("-1"))
                            params.put("replacer", st);

                        break;
                    case 4:
                        s = "HALF";
                        break;
                    case 5:
                        s = "THIRD";
                        break;
                    default:
                        s = "FULL";
                }
                params.put("worker", choseworker);
                params.put("kind", s);
                params.put("salary",0);
                Log.d("parametes", params.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        chooseLayout.setVisibility(View.GONE);
                        checkPage();
                        Log.d("resp", response.toString());
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

                ((MainActivity) getActivity()).requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {

            }
        }
    }

    private void getRequest(String s){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+s;
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
                Toast.makeText(getActivity(), "Проблемы подключения", Toast.LENGTH_LONG).show();
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
    private List<AttendanceRowItemForm> getEmptyList(){
        List<AttendanceRowItemForm> itemForms=new ArrayList<>();
        Calendar cal=Calendar.getInstance();
                cal.setTime(this.cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR,-4);
        SimpleDateFormat dateFormat=((MainActivity)getActivity()).visitFormat;
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,-1);
        return itemForms;
    }
    private void setInfo(JSONArray array){
        progressLayout.setVisibility(View.VISIBLE);
        rowForms.clear();strings=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();calendar.setTime(new Date());
        int count=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        smenaCountTextView.setText( shift+"/"+shift_count);
       // totalDays.setText(count+"");
        Log.d("COUNTCOUNT",count+" ");

        for(int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                String status=object.getString("status");
                JSONObject user=object.getJSONObject("user");
                if(status.equals("STABLE")) {
                    AttendanceRowForm rowForm = new AttendanceRowForm(object.getString("id"), user.getString("fullname"), getEmptyList(), 0, 0);
                    rowForms.add(rowForm);
                  //      strings.add(rowForm.getId() + " " + rowForm.getName());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
     //   attendanceChooseView.setZamena(strings);
        attendanceAdapter.notifyDataSetChanged();
        String url=((MainActivity)getActivity()).MAIN_URL+"visits/?point="+id;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setAttReq(response);
                progressLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы подключения", Toast.LENGTH_LONG).show();
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
    private void dateAttCheck(){
        for(AttendanceRowForm form:rowForms){
            Log.d("Booleans",working +" "+ today +" "+ form.getRowForms().get(3).isNothing());
            if(/*working && */today ) {
                final String id=form.getId();
                form.setListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setChooseLayout(id);
                   //     attendanceChooseView.setZamena(strings);
                    }
                });
            }
           /* else{
                //form.setFucked(true);
                //form.setListener(emptyL);
            }*/
            form.setRowForms(getEmptyList());

        }
        checkItemForms();
    }
    private void setAttReq(JSONArray array){
        try {
            int abs=0, acc=0;
            Calendar calendar=Calendar.getInstance();
            calendar.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                atts.add(object);
                String kind=object.getString("kind");
                String date=object.getString("date"),month=cal.get(Calendar.MONTH)+1+"";
                if (month.length()==1) month="0"+month;

                if(date.substring(0,4).equals(""+cal.get(Calendar.YEAR)) && date.substring(5,7).equals(""+(month))) {
                    if (kind.contains("ABS") || kind.equals("ILL")) {
                        abs++;
                    } else {
                        acc++;
                    }
                }
            }
            Log.d("Attendance", "Total: "+array.length()+"  absents: "+abs+"  here: "+acc);
            absents.setText(abs+"");
            heres.setText(acc+"");
            dateAttCheck();
        }
        catch (Exception e){
            progressLayout.setVisibility(View.GONE);
        }
    }
    private void checkItemForms(){
        try {
            for (JSONObject object : atts) {
                String date=object.getString("date");
                String kind=object.getString("kind");

                JSONObject worker=object.getJSONObject("worker");
                String id=worker.getString("id");
                for(AttendanceRowForm ro:rowForms){
                    if(ro.getId().equals(id)){
                        for(AttendanceRowItemForm itemForm:ro.getRowForms()){
                            if(itemForm.getDay().equals(date)){
                                ro.setListener(emptyL);
                                Log.d("attendancefound",id+" "+date+" "+kind);
                                switch (kind){
                                    case "REPLACE":
                                        itemForm.setReplace(true);
                                        break;
                                    case "ABSENT":
                                        itemForm.setAbsent(true);
                                        break;
                                    case "ILL":
                                        itemForm.setIll(true);
                                        break;
                                    case "HALF":
                                        itemForm.setHalf(true);
                                        break;
                                    case "THIRD":
                                        itemForm.setThird(true);
                                        break;
                                        default:
                                            itemForm.setPlus(true);
                                            break;
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            attendanceAdapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }
    }
    private void onSwipe(){
        swiped=true;
    }
    private void pickerSettings() {
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(9);

        yearPicker.setMinValue(cal.get(Calendar.YEAR)-2);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+1);
        yearPicker.setValue(2018);
    }
    private void dateChange(boolean a){
        if(a){
            cal.add(Calendar.DAY_OF_YEAR,+5);
        }
        else {
            cal.add(Calendar.DAY_OF_YEAR,-5);
        }
        setDate();
    }
    private void setDate(){

        cal2.setTime(new Date());
        if(cal.getTimeInMillis()>cal2.getTimeInMillis()){
            cal.setTime(cal2.getTime());
        }
        if(cal2.get(Calendar.DAY_OF_YEAR)==cal.get((Calendar.DAY_OF_YEAR))){
            setToday(true);
            attendanceAdapter.setToday(true);
            attendanceAdapter.notifyDataSetChanged();
        }
        else{
            setToday(false);
            attendanceAdapter.setToday(false);
            attendanceAdapter.notifyDataSetChanged();
        }
        datePicker.setValue(cal.get(Calendar.MONTH)+1);
        yearPicker.setValue(cal.get(Calendar.YEAR));
        monthTextView.setText(months[datePicker.getValue()-1]);
        yearTextView.setText(yearPicker.getValue()+"");

        cal.add(Calendar.DAY_OF_YEAR,1);
        for(int i=4;i>=0;i--){
            int day=cal.get(Calendar.DAY_OF_WEEK)-2;
            if(day==-1){
                day=6;
            }
            calTextViews.get(i).setText(cal.get(Calendar.DAY_OF_MONTH)+"\n"+(weeks[(day)%7]));
//            Log.d("day"+i,((cal.get(Calendar.DAY_OF_WEEK)-2)%7)+" ");
            cal.add(Calendar.DAY_OF_YEAR,-1);
        }
        cal.add(Calendar.DAY_OF_YEAR,+4);
        dateAttCheck();
    }
    private void dateClick(){
        if(datePicker.getVisibility()!=View.VISIBLE){
            dateArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
                monthTextView.setVisibility(View.GONE);
                yearTextView.setVisibility(View.GONE);
        }
        else {
            dateArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            monthTextView.setVisibility(View.VISIBLE);
            yearTextView.setVisibility(View.VISIBLE);
            if(swiped){
                cal.set(yearPicker.getValue(),datePicker.getValue()-1,1);
                Log.d("Calendar info",yearPicker.getValue()+" "+datePicker.getValue()+" "+cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(yearPicker.getValue(),datePicker.getValue()-1,cal.getMaximum(Calendar.DAY_OF_MONTH));
                setDate();
                swiped=false;
            }
        }
    }

    private void setListeners(){
        View.OnClickListener dateLi=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dateClick();
            }
        };
        yearArrowImageView.setOnClickListener(dateLi);
        dateArrowImageView.setOnClickListener(dateLi);
        monthTextView.setOnClickListener(dateLi);
        yearTextView.setOnClickListener(dateLi);

        leftCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(false);
            }
        });
        rightCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(true);
            }
        });

        NumberPicker.OnValueChangeListener swipe=new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onSwipe();
            }
        };
        datePicker.setOnValueChangedListener(swipe);
        yearPicker.setOnValueChangedListener(swipe);
    }
    private void setToday(boolean f){
        today=f;
        if(f){
            calTextViews.get(3).setBackgroundResource(R.drawable.green_circle);
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.white));
            todayFrame.setVisibility(View.VISIBLE);
        }
        else {
            calTextViews.get(3).setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.black));
            todayFrame.setVisibility(View.GONE);
        }
    }
    private void createViews(View view) {
        chooseLayout=(FrameLayout) view.findViewById(R.id.chooseLayout);
        datePicker = (NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker = (NumberPicker) view.findViewById(R.id.yearPicker);
        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);
        mainObjectTitle = (TextView) view.findViewById(R.id.mainObjectTitle);
        dateArrowImageView = (ImageView) view.findViewById(R.id.dateArrowImageView);
        yearArrowImageView = (ImageView) view.findViewById(R.id.yearArrowImageView);
        leftCalArrow = (ImageView) view.findViewById(R.id.leftCalArrow);
        rightCalArrow = (ImageView) view.findViewById(R.id.rightCalArrow);
        ImageLeftView = (ImageView) view.findViewById(R.id.ImageLeftView);
        ImageRightView = (ImageView) view.findViewById(R.id.ImageRightView);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);
        smenasTextView=(TextView) view.findViewById(R.id.smenasTextView);
        smenaCountTextView=(TextView) view.findViewById(R.id.smenaCountTextView);
        allWorkers=(TextView) view.findViewById(R.id.allWorkers);
        absents=(TextView) view.findViewById(R.id.absents);
        heres=(TextView) view.findViewById(R.id.heres);
        totalDays=(TextView) view.findViewById(R.id.totalDays);
        attendanceChooseView=(AttendanceChooseView) view.findViewById(R.id.attendanceChooseView);

        calTextViews=new ArrayList<>();
        calTextViews.add((TextView) view.findViewById(R.id.f1CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f2CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f3CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f4CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f5CalTextView));
        todayFrame=(FrameLayout) view.findViewById(R.id.todayFrame);

        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);

        emptyL=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        postL=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        };
        attendanceChooseView.setListener(postL);
    }
    private void setFonttypes(){
        setTypeFace("demibold", monthTextView, yearTextView);
        setTypeFace("light");
        setTypeFace("it", mainObjectTitle);
    }
    private void setTypeFace(String s, TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }

    private void getPoint(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"points/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            progressLayout.setVisibility(View.GONE);
            setPoint(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setPoint(JSONObject object){
        try {
            int worker=object.getInt("workers_count");
            allWorkers.setText(worker+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getShifts(){
        try{
            progressLayout.setVisibility(View.VISIBLE);
            String url=((MainActivity)getActivity()).MAIN_URL+"shifts/?point="+id+"&month="+(cal.get(Calendar.MONTH)+1)+"&year="+cal.get(Calendar.YEAR)+"&shift="+shift;
            Log.d("url it is", url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setShifts(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
            ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setShifts(JSONArray array){
        try{
            if(array.length()>0) {
                JSONObject object = array.getJSONObject(0);
                JSONArray days=object.getJSONArray("days");
                totalDays.setText(days.length()+"");
                setCheck(days);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCheck(JSONArray array){
        try {
            int day=cal.get(Calendar.DAY_OF_MONTH);
            Log.d("Today day",day+" ");
           // boolean has=false;
            boolean has=true;
           /* for(int i=0;i<array.length();i++){
                int d=array.getInt(i);
                if (d==day){
                    has=true;
                    break;
                }
            }*/
            working=has;
            dateAttCheck();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}