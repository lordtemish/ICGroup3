package com.studio.dynamica.icgroup.AuthFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.LoginActivity;
import com.studio.dynamica.icgroup.R;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    ConstraintLayout constraintLayout;
    EditText login;
    EditText password;
    TextView loginSystem;
    FrameLayout progressLayout;
    TextView forgotPass;
    TextView loginLogin;
    String token="";
    public LoginFragment() {
        // Required empty public constructor
    }
    public void logIn() throws NullPointerException{
        ((LoginActivity)getActivity()).setInfo();
        getActivity().finish();
    }

    public String getToken() {
        return token;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        login=(EditText) view.findViewById(R.id.logintextLogin);
        login.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir-light.ttf"));
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        loginLogin=(TextView) view.findViewById(R.id.loginlabel);
        password=(EditText) view.findViewById(R.id.passwordLogin);
        password.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir-light.ttf"));

       /* forgotPass=(TextView) view.findViewById(R.id.forgotPass);
        forgotPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRLTSTD-BOOK.otf"));*/

        loginSystem=(TextView) view.findViewById(R.id.loginSystemTextView);
        loginSystem.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        loginLogin.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-MediumCn.ttf"));
        constraintLayout=(ConstraintLayout) view.findViewById(R.id.loginButton);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().length()>0 && password.getText().length()>0){
                    authIt();
                }
                else
                    Toast.makeText(getActivity(), "Введите все данные", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void authIt(){
        progressLayout.setVisibility(View.VISIBLE);
        String email=login.getText()+"";
        String pass=password.getText()+"";
        try{
            String url=((LoginActivity)getActivity()).MAIN_URL+"login/";
            JSONObject object=new JSONObject();
            object.put("phone",email);
            object.put("password",pass);
            JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.has("token")){
                        try {
                            token = response.getString("token");
                            logIn();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Такой пользователь не найден", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Такой пользователь не найден или у вас проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){};
            ((LoginActivity)getActivity()).requestQueue.add(objectRequest);
        }
        catch (Exception e){
            progressLayout.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }
    public String getLogin(){
        return login.getText()+"";
    }
    public String getPassword(){
        return password.getText()+"";
    }
    public boolean isClient(){
        return (login.getText()+"").equals("client");
    }
}
