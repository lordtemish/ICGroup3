package com.studio.crm.icgroup.EmployeesFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Activities.MapActivity;
import com.studio.crm.icgroup.Activities.MapsActivity;
import com.studio.crm.icgroup.Adapters.RadioAdapter;
import com.studio.crm.icgroup.ExtraFragments.MapFragment;
import com.studio.crm.icgroup.Forms.RadioForm;
import com.studio.crm.icgroup.ObjectFragments.CommentsMainFragment;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeeProfileFragment extends Fragment {
RecyclerView jalobaRecyclerView;
LinearLayout perfomanceLayout,objectLayout,jalobasLayout, attendaceLayout;
FrameLayout spinnerFrame, progressLayout;
Spinner spinner;
TextView countJaloba,jalobaName,jalobaLabelTextView, jalobaDateTextView,jalobaPosition, attendaceTextView,PercentageTextView,perfomanceTextView,perfomancePercentageTextView , nameTextView, rateTextView, rateLabelTextView, positionUpTextView, positionLabelTextView, positionTextView, addressLabelTextView, addressTextView, personidLabelTextView, personidTextView, phoneLabelTextView, mobilePhoneLabelTextView, emailLabelTextView, phoneTextView, mobilePhoneTextView, emailTextView ;
ImageView avatar, jalobaAvatar;
ProgressBar ProgressBar, perfomanceProgressBar;
    List<String> categories;
    ArrayAdapter<String> adapter;
    String id="1", userid="", role;
    List<RadioForm> radioForms;
    RadioAdapter radioAdapter;
    public EmployeeProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        View view= inflater.inflate(R.layout.fragment_employee_profile, container, false);
        createViews(view);setSpinner();

        ((MainActivity)getActivity()).setRecyclerViewOrientation(jalobaRecyclerView, LinearLayoutManager.VERTICAL);
        radioForms=new ArrayList<>();
        radioForms.add(new RadioForm(true,"Хамит на работе"));
        radioForms.add(new RadioForm(true,"Халатное отношение"));
        radioForms.add(new RadioForm(true,"Не соответствующий внешний вид"));
        radioAdapter=new RadioAdapter(radioForms);
        jalobaRecyclerView.setAdapter(radioAdapter);
        perfomanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new OwnTasksFragment();
                Bundle bundle=new Bundle();
                bundle.putString("id","2");
                bundle.putString("location","1");
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
            }
        });
        objectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putString("id",userid);
                bundle.putString("role",role);
                Fragment fragment=new MiniObjectFragment();
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
            }
        });
        jalobasLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new CommentsMainFragment();
                Bundle bundle=new Bundle();
                bundle.putString("type","defendant");
                bundle.putString("id","8");
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);
            }
        });
        jalobasLayout.setVisibility(View.GONE);
        attendaceLayout.setVisibility(View.GONE);
        attendaceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Fragment fragment=new MapFragment();
                ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment);*/
                Intent intent=new Intent((MainActivity)getActivity(),MapsActivity.class);
                ((MainActivity)getActivity()).startActivity(intent);
            }
        });
        getRequest();
        return view;
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"employees/"+id;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
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
        };((MainActivity)getActivity()).requestQueue.add(request);
    }
private void setInfo(JSONObject object){
        try {
            JSONObject user=object.getJSONObject("user");
            userid=user.getString("id");
            JSONObject department=object.getJSONObject("department");
            nameTextView.setText(user.getString("fullname"));
            if(!user.isNull("avatar")) {
                JSONObject avatar = user.getJSONObject("avatar");
                String ava=avatar.getString("file");
                ((MainActivity)getActivity()).setPhoto(ava,this.avatar);
            }
            String pos=user.getString("role");
            role=pos;
            positionTextView.setText(((MainActivity)getActivity()).positions.get(pos));
            emailTextView.setText(user.getString("email"));
            phoneTextView.setText(user.getString("phone"));
            if(user.isNull("mobile"))
                mobilePhoneTextView.setText("");
            else
                mobilePhoneTextView.setText(user.getString("mobile"));
            addressTextView.setText(object.getString("address"));
            personidTextView.setText(object.getString("iin"));
            String a=Integer.parseInt(Math.round((object.getDouble("result_rate")*100))+"")+"";
            rateTextView.setText(a);
            int perfo=Integer.parseInt(Math.round(object.getDouble("performance_rate")*100)+"");
            ProgressBar.setProgress(perfo);
            PercentageTextView.setText(perfo+"%");
            perfomanceProgressBar.setProgress(Integer.parseInt(a));
            perfomancePercentageTextView.setText(a+"%");
            getComplaints();
        }
        catch (Exception e){
            e.printStackTrace();
        }
}
    private void getComplaints(){
        String uel=((MainActivity)getActivity()).MAIN_URL+"complaints/?defendant="+userid;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, uel, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setCompls(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
    private void setCompls(JSONArray array){
            try{
                Log.d("array",array.length()+"");
                if(array.length()==0){
                    jalobasLayout.setVisibility(View.GONE);
                }
                else{
                    JSONObject object=array.getJSONObject(array.length()-1);
                    JSONObject author=object.getJSONObject("author");
                    jalobaName.setText(author.getString("fullname"));
                    jalobaPosition.setText(((MainActivity)getActivity()).positions.get(author.getString("role")));
                    JSONArray reasons=object.getJSONArray("reasons");
                    radioForms.clear();
                    for(int i=0;i<reasons.length();i++){
                        JSONObject reason=reasons.getJSONObject(i);
                        radioForms.add(new RadioForm(true,reason.getString("name")));
                    }
                    radioAdapter.notifyDataSetChanged();

                    String created_at=object.getString("created_at");
                    String created=((MainActivity)getActivity()).getdate(created_at);
                    jalobaDateTextView.setText(created.substring(0,created.length()-6));
                    countJaloba.setText(array.length()+"");
                    jalobasLayout.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
    }
    private void createViews(View view){
        attendaceLayout=(LinearLayout)view.findViewById(R.id.attendaceLayout);
        perfomanceLayout=(LinearLayout)view.findViewById(R.id.perfomanceLayout);
        objectLayout=(LinearLayout)view.findViewById(R.id.objectLayout);
        jalobasLayout=(LinearLayout)view.findViewById(R.id.jalobasLayout);
        jalobaRecyclerView=(RecyclerView) view.findViewById(R.id.jalobaRecyclerView);
        categories=new ArrayList<>();
        categories.add("Выберите категорию");
        spinner=(Spinner) view.findViewById(R.id.spinner);
        avatar=(ImageView) view.findViewById(R.id.avatar);
        jalobaAvatar=(ImageView) view.findViewById(R.id.jalobaAvatar);
        spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrame);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        rateTextView=(TextView) view.findViewById(R.id.rateTextView);
        rateLabelTextView=(TextView) view.findViewById(R.id.rateLabelTextView);
        positionUpTextView=(TextView) view.findViewById(R.id.positionUpTextView);
        positionLabelTextView=(TextView) view.findViewById(R.id.positionLabelTextView);
        phoneLabelTextView=(TextView) view.findViewById(R.id.phoneLabelTextView);
        personidTextView=(TextView) view.findViewById(R.id.personidTextView);
        personidLabelTextView=(TextView) view.findViewById(R.id.personidLabelTextView);
        addressTextView=(TextView) view.findViewById(R.id.addressTextView);
        addressLabelTextView=(TextView) view.findViewById(R.id.addressLabelTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        emailTextView=(TextView) view.findViewById(R.id.emailTextView);
        mobilePhoneTextView=(TextView) view.findViewById(R.id.mobilePhoneTextView);
        phoneTextView=(TextView) view.findViewById(R.id.phoneTextView);
        emailLabelTextView=(TextView) view.findViewById(R.id.emailLabelTextView);
        mobilePhoneLabelTextView=(TextView) view.findViewById(R.id.mobilePhoneLabelTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        attendaceTextView=(TextView) view.findViewById(R.id.attendaceTextView);
        perfomancePercentageTextView=(TextView) view.findViewById(R.id.perfomancePercentageTextView);
        perfomanceTextView=(TextView) view.findViewById(R.id.perfomanceTextView);
        jalobaName=(TextView) view.findViewById(R.id.jalobaName);
        jalobaPosition=(TextView) view.findViewById(R.id.jalobaPosition);
        jalobaLabelTextView=(TextView) view.findViewById(R.id.jalobaLabelTextView);
        jalobaDateTextView=(TextView) view.findViewById(R.id.jalobaDateTextView);
        countJaloba=(TextView) view.findViewById(R.id.countJaloba);
        ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
        perfomanceProgressBar=(ProgressBar)view.findViewById(R.id.perfomanceProgressBar);
        ((MainActivity)getActivity()).setType("demibold",countJaloba,jalobaName,jalobaLabelTextView,perfomanceTextView,attendaceTextView,PercentageTextView,perfomancePercentageTextView, nameTextView,positionTextView, addressTextView, personidTextView, mobilePhoneTextView, phoneTextView, emailTextView,rateTextView );
        ((MainActivity)getActivity()).setType("light", jalobaDateTextView, jalobaPosition,positionUpTextView,addressLabelTextView, emailLabelTextView, mobilePhoneLabelTextView, personidLabelTextView, phoneLabelTextView, positionLabelTextView, rateLabelTextView);
    }
    private void setSpinner(){
       adapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,categories){
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
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               spinner.performClick();
            }
        });
        spinner.setAdapter(adapter);
    }
}
