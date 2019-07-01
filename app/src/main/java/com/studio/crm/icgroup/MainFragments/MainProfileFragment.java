package com.studio.crm.icgroup.MainFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
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
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.ExtraFragments.PasswordChangeView;
import com.studio.crm.icgroup.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainProfileFragment extends Fragment {
FrameLayout progressLayout;
    ImageView greenImageDrawer;
    TextView nameTextView, positionUpTextView, positionLabelTextView, positionTextView, periodLabelTextView, periodTextView, addressLabelTextView, addressTextView, personidLabelTextView, personidTextView, phoneLabelTextView, phoneTextView
            ,mobilePhoneLabelTextView,mobilePhoneTextView , emailLabelTextView , emailTextView;
    String id, role;
    ConstraintLayout passwordChangeLayout;
    PasswordChangeView passwordChangeView;
    public MainProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=((MainActivity)getActivity()).userid;
        role=((MainActivity)getActivity()).role;
        View view=inflater.inflate(R.layout.fragment_main_profile, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setType("light",positionUpTextView, addressLabelTextView,emailLabelTextView,mobilePhoneLabelTextView,periodLabelTextView,personidLabelTextView,phoneLabelTextView,positionLabelTextView);
        ((MainActivity)getActivity()).setType("demibold",nameTextView, addressTextView,emailTextView,mobilePhoneTextView,periodTextView,personidTextView,phoneTextView,positionTextView);

//        ((MainActivity) getActivity()).setPhoto("http://www.coveralls.co.uk/young-lad-road-worker_6.jpg",greenImageDrawer);
        getProfile();

        passwordChangeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setPressable(false, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        passwordChangeView.setVisibility(View.GONE);
                        ((MainActivity)getActivity()).setPressable(true,null);
                    }
                });
                passwordChangeView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }
    private void createViews(View view){
        greenImageDrawer=(ImageView)view.findViewById(R.id.greenImageDrawer);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        positionUpTextView=(TextView) view.findViewById(R.id.positionUpTextView);
        positionLabelTextView=(TextView) view.findViewById(R.id.positionLabelTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        periodLabelTextView=(TextView) view.findViewById(R.id.periodLabelTextView);
        periodTextView=(TextView) view.findViewById(R.id.periodTextView);
        addressLabelTextView=(TextView) view.findViewById(R.id.addressLabelTextView);
        addressTextView=(TextView) view.findViewById(R.id.addressTextView);
        personidLabelTextView=(TextView) view.findViewById(R.id.personidLabelTextView);
        personidTextView=(TextView) view.findViewById(R.id.personidTextView);
        phoneLabelTextView=(TextView) view.findViewById(R.id.phoneLabelTextView);
        phoneTextView=(TextView) view.findViewById(R.id.phoneTextView);
        mobilePhoneLabelTextView=(TextView) view.findViewById(R.id.mobilePhoneLabelTextView);
        mobilePhoneTextView=(TextView) view.findViewById(R.id.mobilePhoneTextView);
        emailLabelTextView=(TextView) view.findViewById(R.id.emailLabelTextView);
        emailTextView=(TextView) view.findViewById(R.id.emailTextView);

        passwordChangeLayout=(ConstraintLayout)view.findViewById(R.id.passwordChangeLayout);
        passwordChangeView=(PasswordChangeView)view.findViewById(R.id.passwordChangeView);
    }
    private void getProfile(){
        progressLayout.setVisibility(View.VISIBLE);
        if(role.equals("POINT") || role.equals("CLIENT") || role.equals("SUPERADMIN")){
            String url = ((MainActivity) getActivity()).MAIN_URL + "user/";
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    setUser(response);
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
            ((MainActivity) getActivity()).requestQueue.add(objectRequest);
        }
        else {
            String url = ((MainActivity) getActivity()).MAIN_URL + "employee/";
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
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
            ((MainActivity) getActivity()).requestQueue.add(objectRequest);
        }
    }
    private void setInfo(JSONObject object){
        try{
            JSONObject user=object.getJSONObject("user");
            JSONObject department=object.getJSONObject("department");
            nameTextView.setText(user.getString("fullname"));
            if(!user.isNull("avatar")) {
                JSONObject avatar = user.getJSONObject("avatar");
                String ava=((MainActivity)getActivity()).SERVER_URL+avatar.getString("file");
                ((MainActivity)getActivity()).setPhoto(ava,this.greenImageDrawer);
            }
            String pos=user.getString("role"), depN=department.getString("name");
            positionUpTextView.setText(depN);
            positionTextView.setText(((MainActivity)getActivity()).positions.get(pos));
            emailTextView.setText(user.getString("email"));
            phoneTextView.setText(user.getString("phone"));
            if(user.isNull("mobile"))
                mobilePhoneTextView.setText("");
            else
                mobilePhoneTextView.setText(user.getString("mobile"));
            addressTextView.setText(object.getString("address"));
            periodTextView.setText(object.getString("period"));
            personidTextView.setText(object.getString("iin"));

        }
        catch (Exception e){

        }
    }
    private void setUser(JSONObject object){
        try{
            JSONObject user=object;
            nameTextView.setText(user.getString("fullname"));
            if(!user.isNull("avatar")) {
                JSONObject avatar = user.getJSONObject("avatar");
                String ava=((MainActivity)getActivity()).SERVER_URL+avatar.getString("file");
                ((MainActivity)getActivity()).setPhoto(ava,this.greenImageDrawer);
            }
            String pos=user.getString("role");
            positionTextView.setText(((MainActivity)getActivity()).positions.get(pos));
            emailTextView.setText(user.getString("email"));
            phoneTextView.setText(user.getString("phone"));
            if(user.isNull("mobile"))
                mobilePhoneTextView.setText("");
            else
                mobilePhoneTextView.setText(user.getString("mobile"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
