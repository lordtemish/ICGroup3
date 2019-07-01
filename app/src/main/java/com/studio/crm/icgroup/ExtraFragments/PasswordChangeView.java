package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
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

public class PasswordChangeView extends FrameLayout {
    FrameLayout progressLayout;
    EditText oldEditText, newEditText, new2EditText;
    ConstraintLayout button;
    Context context;
    public PasswordChangeView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public PasswordChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PasswordChangeView(Context context) {
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.password_change_view, null);
        context=view.getContext();
        addView(view);
        createViews(view);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass();
            }
        });
    }
    private void createViews(View view){
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        oldEditText=(EditText)view.findViewById(R.id.oldEditText);
        newEditText=(EditText)view.findViewById(R.id.newEditText);
        new2EditText=(EditText)view.findViewById(R.id.new2EditText);
        button=(ConstraintLayout)view.findViewById(R.id.button);
    }
    private void changePass(){
        if(oldEditText.getText().length()==0 || newEditText.getText().length()==0 || new2EditText.getText().length()==0){
            Toast.makeText(context, "Введите все данные", Toast.LENGTH_SHORT).show();
        }
        else if(!(newEditText.getText()+"").equals(new2EditText.getText()+"")){
            Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
        else {
            progressLayout.setVisibility(VISIBLE);
            String url = ((MainActivity) context).MAIN_URL + "user/password/";
            JSONObject object = new JSONObject();
            try {
                object.put("password",oldEditText.getText()+"");
                object.put("password_new",newEditText.getText()+"");
                object.put("password_confirm",new2EditText.getText()+"");
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Log.d("URL",url+"\n"+object.toString());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(GONE);
                    Toast.makeText(context, "Пароль успешно изменен", Toast.LENGTH_SHORT).show();
                    ((MainActivity) context).onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(GONE);
                    if (error.networkResponse.statusCode == 400) {
                        Toast.makeText(context, "Неправильный пароль", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT " + ((MainActivity) context).token);
                    return headers;
                }
            };
            ((MainActivity) context).requestQueue.add(objectRequest);
        }
    }
}
