package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ServicesAdapter;
import com.studio.dynamica.icgroup.Forms.ServiceForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceObjectMainFragment extends Fragment {
    LinearLayout graphLinear;
    ConstraintLayout allServicesLayout;
    GraphViewXML graphViewXML;
    List<ServiceForm> forms;
    ServicesAdapter adapter;
    RecyclerView allServicesRecycler;
    ImageView allServicesImage, arrowCalendarImageView;
    FrameLayout addNewServiceFrame, progressLayout;
    TextView ObjectTitle, allOtdelsTextView, allEmployeesTextView,calendarTextView, PercentageTextView, allServicesTextView;
    NumberPicker monthPicker, yearPicker;
    String[] months={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    Calendar cal;
    boolean timeChange=false;
    public ServiceObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_service_object_main, container, false);

        createViews(view);
        setFonttype();
        PickerSettings();

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
        arrowCalendarImageView.setOnClickListener(dateClick);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        allServicesRecycler.setLayoutManager(layoutManager);
        allServicesRecycler.setItemAnimator(new DefaultItemAnimator());
        setServicesAdapter();


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 30),
                new DataPoint(1, 30),
                new DataPoint(2, 60),
                new DataPoint(3, 20),
                new DataPoint(4, 50)
        }); LineGraphSeries<DataPoint> series23 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 33),
                new DataPoint(1, 15),
                new DataPoint(2, 40),
                new DataPoint(3, 29),
                new DataPoint(4, 11),
                new DataPoint(5, 22)
        });

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 20),
                new DataPoint(1, 14),
                new DataPoint(2, 49),
                new DataPoint(3, 79),
                new DataPoint(4, 21),
                new DataPoint(5, 11)
        });

        graphViewXML.getSecondScale().addSeries(series2);
        graphViewXML.getSecondScale().addSeries(series23);
        graphViewXML.getSecondScale().addSeries(series4);
        graphViewXML.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphViewXML.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

        });
        graphViewXML.getSecondScale().setMinY(0);
        graphViewXML.getSecondScale().setMaxY(100);

        series2.setColor(Color.RED);
        setDate();
        getRequest("tasks");
        return view;
    }

    private void getRequest(String url){
        progressLayout.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;

        JsonObjectRequest arrayRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    setInfo(response);
                }
                catch (JSONException e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        ((MainActivity) getActivity()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONObject ob) throws JSONException{
        forms.clear();
        progressLayout.setVisibility(View.GONE);
        JSONArray array=ob.getJSONArray("results");
        for (int i=0;i<array.length();i++){
            JSONObject object=array.getJSONObject(i);
            String priority=object.getString("priority");priority=getPriority(priority);
            String status=object.getString("status");
            String kind=object.getString("kind");
            ServiceForm serviceForm=new ServiceForm(kind,"","",status,priority,5,6);
            forms.add(serviceForm);
        }
        adapter.notifyDataSetChanged();
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
    private String getPriority(String s){
        switch (s){
        case "NORMAL":
            s="Нормальный";
            break;
            case "MEDIUM":
                s="Средний";
                break;
            case "HIGH":
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
        yearPicker.setMinValue(2017);
        yearPicker.setMaxValue(2020);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    private void createViews(View view){
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);

        monthPicker=(NumberPicker) view.findViewById(R.id.monthPicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        cal=Calendar.getInstance();
        cal.setTime(new Date());

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
        graphViewXML=(GraphViewXML) view.findViewById(R.id.graphView);
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
       /* forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","TIMEOVER","Обычный", 4,6));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","PROCESSING","Обычный", 4,8));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","WAITING","Обычный", 10,10));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","PROLONGING","Обычный", 1,6));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","FINISHED","Обычный", 7,7));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","Куратор","CLOSED","Обычный", 7,7));*/
        adapter=new ServicesAdapter(forms);
        allServicesRecycler.setAdapter(adapter);
    }
    public void addNewServiceOpen(){
        ((MainActivity)getActivity()).setFragment(R.id.content_frame,new AddNewServiceFragment());
    }
}
