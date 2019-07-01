package com.studio.crm.icgroup.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.studio.crm.icgroup.AuthFragments.LoginFragment;
import com.studio.crm.icgroup.R;

public class LoginActivity extends AppCompatActivity {
    TextView lorem;
   // public final String MAIN_URL="https://icgroup.herokuapp.com/api/v0.1/"
   public final String MAIN_URL="https://crm.ic-group.kz/api/v0.1/", SERVER_URL="https://crm.ic-group.kz";
    TextView ICG;
   public    RequestQueue requestQueue;
    FrameLayout loginFrame;
    LoginFragment fragment;
    public void onClick(View view){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
         fragment=new LoginFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.inputLoginFrame,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

    }
    public void setInfo(){
        Intent output=getIntent();
        output.putExtra("client",fragment.isClient());
        output.putExtra("token",fragment.getToken());
        setResult(RESULT_OK,output);
        Log.d("LOGINFr",output.getBooleanExtra("client",false)+"");
    }
    @Override
    public void onBackPressed(){

    }
}
