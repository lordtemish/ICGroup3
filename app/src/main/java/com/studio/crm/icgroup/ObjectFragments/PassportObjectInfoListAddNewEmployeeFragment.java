package com.studio.crm.icgroup.ObjectFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;


public class PassportObjectInfoListAddNewEmployeeFragment extends Fragment {
    EditText nametext, nametext1,salaryEditText;
    FrameLayout frontLayout;
    TextView addTextView;
    ConstraintLayout addLayout;
    RadioButton firstB, secondB, thirdB;
    RadioGroup radioGroup;
    int shifts;
    String point;
    boolean is_trainee, check=false;
    RadioButton contractRadio;
    View.OnClickListener clickListener;
    public PassportObjectInfoListAddNewEmployeeFragment() {
        // Required empty public constructor
    }
    FrameLayout backLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        shifts=bundle.getInt("shifts",0);
        point=bundle.getString("point");
        is_trainee=bundle.getBoolean("is_trainee",false);
        View view=inflater.inflate(R.layout.fragment_passport_object_info_list_add_new_employee, container, false);
        createViews(view);

        frontLayout.setVisibility(View.GONE);
        addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest("workers/");
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
        return view;
    }
    private void setContract(){
        contractRadio.setChecked(check);
    }
    private void createViews(View view){
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
    private void setInfo(JSONObject ov){
        frontLayout.setVisibility(View.GONE);
        Toast.makeText(getActivity(),"Сотрудник добавлен",Toast.LENGTH_LONG).show();
        //SystemClock.sleep(5000);
        clickListener.onClick(getView());
        Log.d("created","somesome "+ov.toString());
        ((MainActivity)getActivity()).onBackPressed();
    }
    private void postRequest(String url){
        if(nametext1.getText().length()>1  && nametext.getText().length()>0) {
            url = ((MainActivity) getActivity()).MAIN_URL + url;
            JSONObject params = new JSONObject();
            frontLayout.setVisibility(View.VISIBLE);
            try {
                JSONObject user=new JSONObject();
                user.put("fullname", nametext.getText() + "");
                user.put("phone", "" + nametext1.getText());
                user.put("role", "WORKER");
                user.put("password", "anypassword");
                params.put("user",user);
                params.put("shift",  radioCheck());
                params.put("point",  Integer.parseInt(point));
                params.put("is_trainee",  is_trainee);
                params.put("is_contract",  check);
                params.put("salary", Integer.parseInt(salaryEditText.getText()+""));
            } catch (Exception e) {

            }
            Log.d("params",params.toString());
            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
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
