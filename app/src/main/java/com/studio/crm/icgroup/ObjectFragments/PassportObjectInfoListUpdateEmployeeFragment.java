package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListUpdateEmployeeFragment extends Fragment {
    EditText nametext, nametext1,salaryEditText;
    FrameLayout frontLayout,spinnerFrameImage, spinnerFrameImage2;
    TextView addTextView, dayT, nightT;
    ConstraintLayout addLayout;
    RadioButton firstB, secondB, thirdB;
    RadioGroup radioGroup;
    Spinner employeeChangeSpinner, employeeChangeSpinner2;
    boolean is_night=false;
    ArrayList<String> kinds, keeps;
    int kind=0, keep=0,janitor_shifts_count=0, gardener_shifts_count=0, plumber_shifts_count=0,electrician_shifts_count=0, salary;
    List<String[]> strings, OPUroles, OPUkeeps;
    int shifts;
    String point, name, id, phone;
    boolean is_trainee, check=false;
    RadioButton contractRadio;
    View.OnClickListener clickListener;

    public PassportObjectInfoListUpdateEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        janitor_shifts_count=getArguments().getInt("janitor_shifts_count");
        gardener_shifts_count=getArguments().getInt("gardener_shifts_count");
        plumber_shifts_count=getArguments().getInt("plumber_shifts_count");
        electrician_shifts_count=getArguments().getInt("electrician_shifts_count");

        id=getArguments().getString("worker");
        name=getArguments().getString("name");
        phone=getArguments().getString("phone");
        salary=getArguments().getInt("salary");

        OPUroles=new ArrayList<>();
        OPUroles.add(new String[]{"Сдельщик","PIECER"});
        OPUroles.add(new String[]{"Стажировщик","INTERN"});
        OPUroles.add(new String[]{"ОПУ","OPU"});
        //   if(janitor_shifts_count>0)
        OPUroles.add(new String[]{"ОПУ ПТ","JANITOR"});
        //   if(gardener_shifts_count>0)
        OPUroles.add(new String[]{"Садовник","GARDENER"});
        //   if(plumber_shifts_count>0)
        OPUroles.add(new String[]{"Сантехник","PLUMBER"});
        //   if(electrician_shifts_count>0)
        OPUroles.add(new String[]{"Электрик","ELECTRICIAN"});
        OPUroles.add(new String[]{"Водитель","DRIVER"});
        strings=new ArrayList<>();
        strings.add(new String[]{});
        strings.add(new String[]{});
        Bundle bundle=getArguments();
        shifts=bundle.getInt("shifts");
        Log.d("ALLINFO", id+" "+name+" "+phone+" "+salary+" "+shifts);
        point=bundle.getString("point");
        is_trainee=bundle.getBoolean("is_trainee",false);
        View view=inflater.inflate(R.layout.fragment_passport_object_info_list_update_employee, container, false);
        createViews(view);
        nametext.setText(name);
        if(!(phone.equals("null")))
        nametext1.setText(phone);
        else{
            nametext1.setText("");
        }
        salaryEditText.setText(salary+"");

        frontLayout.setVisibility(View.GONE);
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest("workers/"+id+"/");
            }
        });
        contractRadio.setChecked(check);
        contractRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=!check;
                setContract();
            }
        });
        setSpinners();
        checkNight();
        dayT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_night=false;
                checkNight();
            }
        });
        nightT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_night=true;
                checkNight();
            }
        });
        return view;
    }
    private void setContract(){
        contractRadio.setChecked(check);
    }
    private void checkNight(){
        if(is_night){
            nightT.setBackgroundResource(R.drawable.green_corners_page);
            nightT.setTextColor(getResources().getColor(R.color.white));
            dayT.setBackgroundResource(R.drawable.greyrow_page);
            dayT.setTextColor(getResources().getColor(R.color.black));
        }
        else{
            dayT.setBackgroundResource(R.drawable.green_corners_page);
            dayT.setTextColor(getResources().getColor(R.color.white));
            nightT.setBackgroundResource(R.drawable.greyrow_page);
            nightT.setTextColor(getResources().getColor(R.color.black));
        }
    }
    private void createViews(View view){
        employeeChangeSpinner=(Spinner)view.findViewById(R.id.employeeChangeSpinner);
        employeeChangeSpinner2=(Spinner)view.findViewById(R.id.employeeChangeSpinner2);
        frontLayout=(FrameLayout) view.findViewById(R.id.frontLayout);
        nametext=(EditText) view.findViewById(R.id.nameEditText);
        nametext1=(EditText) view.findViewById(R.id.nameEditText1);
        salaryEditText=(EditText) view.findViewById(R.id.salaryEditText);
        radioGroup=(RadioGroup)view.findViewById(R.id.radioGroup);
        firstB=(RadioButton) view.findViewById(R.id.firstB);
        secondB=(RadioButton) view.findViewById(R.id.secondB);
        thirdB=(RadioButton) view.findViewById(R.id.thirdB);
        contractRadio=(RadioButton) view.findViewById(R.id.contractRadio);
        addLayout=(ConstraintLayout) view.findViewById(R.id.addLayout);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        dayT=(TextView) view.findViewById(R.id.dayT);
        nightT=(TextView) view.findViewById(R.id.nightT);
        spinnerFrameImage=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        spinnerFrameImage2=(FrameLayout) view.findViewById(R.id.spinnerFrameImage2);

        if(shifts<=2){
            thirdB.setVisibility(View.GONE);
            if(shifts<=1){
                secondB.setVisibility(View.GONE);
                if(shifts==0){
                    firstB.setVisibility(View.GONE);
                }
            }
        }
    }
    private void setSpinners()  {
        kinds=new ArrayList<>();
        keeps=new ArrayList<>();
        for(int i=0;i<OPUroles.size();i++){
            kinds.add(OPUroles.get(i)[0]);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,kinds){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};
        spinnerFrameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeChangeSpinner.performClick();
            }
        });
        employeeChangeSpinner.setAdapter(adapter);
        employeeChangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kind=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,keeps){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};
        spinnerFrameImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeChangeSpinner2.performClick();
            }
        });
        employeeChangeSpinner2.setAdapter(adapter1);
        employeeChangeSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                keep=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setInfo(JSONObject ov){
        frontLayout.setVisibility(View.GONE);
        Toast.makeText(getActivity(),"Сотрудник обновлен",Toast.LENGTH_LONG).show();
        //SystemClock.sleep(5000);
        clickListener.onClick(getView());
        Log.d("created","somesome "+ov.toString());
        ((MainActivity)getActivity()).onBackPressed();
    }
    private void postRequest(String url){
        if( nametext.getText().length()>0) {
            url = ((MainActivity) getActivity()).MAIN_URL + url;
            JSONObject params = new JSONObject();
            frontLayout.setVisibility(View.VISIBLE);
            Log.d("URL",url);
            try {
                JSONObject user=new JSONObject();
                user.put("fullname", nametext.getText() + "");
                if(nametext1.getText().length()>0)
                user.put("phone", "" + nametext1.getText());
                user.put("role", "WORKER");
                user.put("password", "anypassword");
                params.put("user",user);
                params.put("shift",  radioCheck());
                params.put("is_night",  is_night);
                params.put("is_contract",  check);
                if(salaryEditText.getText().length()>0)
                params.put("salary", Integer.parseInt(salaryEditText.getText()+""));
                params.put("kind",OPUroles.get(kind)[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("params",params.toString());
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.PATCH, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    frontLayout.setVisibility(View.GONE);
                    setInfo(response);
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    frontLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                    return headers;
                }
            };
            ((MainActivity) getActivity()).requestQueue.add(postRequest);
        }
        else {
            Toast.makeText(getActivity(),"Введите данные корректно",Toast.LENGTH_LONG).show();
        }
    }
    public void click(View.OnClickListener clickListener){
        this.clickListener=clickListener;
    }
    private int radioCheck(){
        if(firstB.isChecked()){
            return 1;
        }
        else if(secondB.isChecked()){
            return 2;
        }
        else{
            return 3;
        }
    }
}
