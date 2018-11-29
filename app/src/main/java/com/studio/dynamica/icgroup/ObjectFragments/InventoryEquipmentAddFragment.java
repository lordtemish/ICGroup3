package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.ExtraFragments.BigCounterView;
import com.studio.dynamica.icgroup.ExtraFragments.CalendarView;
import com.studio.dynamica.icgroup.ExtraFragments.CounterView;
import com.studio.dynamica.icgroup.ExtraFragments.RadioFragment;
import com.studio.dynamica.icgroup.ExtraFragments.TimePickView;
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
public class InventoryEquipmentAddFragment extends Fragment {
    BigCounterView bigCounterView;
    CalendarView calendarView;
    RadioButton todayRadioButton, datesRadioButton;
    TimePickView counterView;
    String[] a={"Снабжение", "Уборка"};
    FrameLayout radioFrame, spinnerFrame, typeSpinnerFrame, progressLayout;
    RadioFragment radioFragment;
    Spinner employeeChangeSpinner,typeSpinner;
    String id="",vendor="",num="", name="",kind="SUPPLY";
    EditText commentEditText;
    List<String> employees, emids, emStrings, rKinds, rkKeys, rkStrings;
    ArrayAdapter<String> arrayAdapter, arrayAdapter2;
    String emid="", rkey="";
    boolean cal=false;
    Calendar calendar;
    TextView addTextView, nameTextView, idTextView, numberTextView;
    public InventoryEquipmentAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        num=getArguments().getString("num");
        name=getArguments().getString("name");
        vendor=getArguments().getString("vendor");
        calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        View view=inflater.inflate(R.layout.fragment_inventory_equipment_add, container, false);
        createViews(view);

        nameTextView.setText(name);
        idTextView.setText(vendor);
        numberTextView.setText(num);

        radioFragment=new RadioFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("checked",0);
        radioFragment.setArguments(bundle);
        ((MainActivity)getActivity()).setFragmentNoBackStack(R.id.radioFrameLaoyut,radioFragment);

        todayRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayRadioButton.setChecked(true);
                datesRadioButton.setChecked(false);
                cal=false;
                setCal();
            }
        });
        datesRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todayRadioButton.setChecked(false);
                datesRadioButton.setChecked(true);
                cal=true;
                setCal();
            }
        });

        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIt();
            }
        });
        arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.simple_spinner_item,emStrings);
        employeeChangeSpinner.setAdapter(arrayAdapter);
        employeeChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==0){
                        emid="";
                    }
                    else{
                        emid=emids.get(i-1);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSp();
            }
        });
         arrayAdapter2=new ArrayAdapter<>(getActivity(),R.layout.simple_spinner_item,rkStrings);
        typeSpinner.setAdapter(arrayAdapter2);
        typeSpinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeSpinner.performClick();
            }
        });
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    rkey="";
                }
                else {
                    rkey=rkKeys.get(i-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setCal();
        setRKinds();
        getEmployees();
        return view;
    }

    public void setCal() {
        if(cal){
            calendarView.setVisibility(View.VISIBLE);
            counterView.setVisibility(View.GONE);
        }
        else {
            calendarView.setVisibility(View.GONE);
            counterView.setVisibility(View.VISIBLE);
        }
    }

    private void showSp(){
        employeeChangeSpinner.performClick();
    }
    private void createViews(View view){
        rKinds=new ArrayList<>();rkKeys=new ArrayList<>();
        rkStrings=new ArrayList<>();
        employees=new ArrayList<>();
        emids=new ArrayList<>();emStrings=new ArrayList<>();

        addTextView=(TextView)view.findViewById(R.id.addTextView);
        nameTextView=(TextView)view.findViewById(R.id.nameTextView);
        idTextView=(TextView)view.findViewById(R.id.idTextView);
        numberTextView=(TextView)view.findViewById(R.id.numberTextView);


        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        todayRadioButton=(RadioButton) view.findViewById(R.id.todayRadioButton);
        datesRadioButton=(RadioButton) view.findViewById(R.id.datesRadioButton);
        employeeChangeSpinner=(Spinner) view.findViewById(R.id.employeeChangeSpinner);
        typeSpinner=(Spinner)view.findViewById(R.id.typeChangeSpinner);
        spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        typeSpinnerFrame=(FrameLayout) view.findViewById(R.id.typeSpinnerFrameImage);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        radioFrame=(FrameLayout) view.findViewById(R.id.radioFrameLaoyut);
        bigCounterView=(BigCounterView) view.findViewById(R.id.bigCounterView);
        calendarView=(CalendarView) view.findViewById(R.id.calendarView);
        counterView=(TimePickView) view.findViewById(R.id.counterView);

        ((MainActivity)getActivity()).setType("demibold", nameTextView, numberTextView);
        ((MainActivity)getActivity()).setType("light", idTextView);
    }

    public int getChecked(){
       return radioFragment.getArguments().getInt("checked");
    }

    private void getEmployees(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"employees/?department__kind="+kind+"&department__location="+((MainActivity)getActivity()).location;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(   View.GONE);
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
                return headers;
            }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setEmployees(JSONArray array){
        try {
            emStrings.clear();
            emStrings.add("Выберите ответственного");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject user=object.getJSONObject("user");
                employees.add(user.getString("fullname"));
                emids.add(user.getString("id"));
            }
            emStrings.addAll(employees);
            arrayAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setRKinds(){
        HashMap<String, String> map=((MainActivity)getActivity()).replKinds;
        for(String key:map.keySet()){
            rKinds.add(map.get(key));
            rkKeys.add(key);
        }
        rkStrings.add("Выберите тип заявки");
        rkStrings.addAll(rKinds);
        arrayAdapter2.notifyDataSetChanged();
    }
    private void saveIt(){
        boolean go=false;
        Date date=new Date();
        if(cal){
            go=(calendarView.getChose()==null);
            if(!go){
                date=calendarView.getChose().getTime();
            }
        }
        else{
            date=counterView.getChose().getTime();
        }
        if(commentEditText.getText().length()<1){
            Toast.makeText(getActivity(), "Напишите комментарий к заявке", Toast.LENGTH_SHORT).show();
        }
        else if(emid.length()==0){
            Toast.makeText(getActivity(), "Выберите ответственного", Toast.LENGTH_SHORT).show();
        }
        else if(rkey.length()==0){
            Toast.makeText(getActivity(), "Выберите тип заявки", Toast.LENGTH_SHORT).show();
        }
        else if(go){
            Toast.makeText(getActivity(), "Укажите дату окончания", Toast.LENGTH_SHORT).show();
        }
        else if(date.getTime()<=calendar.getTime().getTime()){
            Toast.makeText(getActivity(), "Это время уже прошло", Toast.LENGTH_SHORT).show();
        }
        else {
            progressLayout.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "replenishments/";
            JSONObject object = new JSONObject();
            try {
                object.put("consumption",Integer.parseInt(id));
                object.put("kind",rkey);
                object.put("respondent",Integer.parseInt(emid));
                object.put("priority",radioFragment.getChecked()+1);
                String deadline=((MainActivity)getActivity()).inputFormat.format(date);
                object.put("deadline", deadline);
                object.put("count", bigCounterView.getPage());

                object.put("description", commentEditText.getText()+"");
                Log.d("params",object.toString());
                JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Заявка успешно созданна", Toast.LENGTH_LONG).show();
                        ((MainActivity)getActivity()).onBackPressed();
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
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                        return headers;
                    }
                };
                ((MainActivity)getActivity()).requestQueue.add(objectRequest);

            } catch (Exception e) {
                progressLayout.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
    }
}
