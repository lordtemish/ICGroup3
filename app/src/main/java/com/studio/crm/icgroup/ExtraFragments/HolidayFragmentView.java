
package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HolidayFragmentView extends FrameLayout{
    String id="", name="";
    TextView butText,holidayLabelTextView, positionTextView, nameTextView;
    CalendarView calendarView;
    RadioButton withSod,withoutSod;
    Context context;
    FrameLayout progressFrame;
    public HolidayFragmentView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView(context);
    }
    public HolidayFragmentView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView(context);
    }
    public HolidayFragmentView(Context context){
        super(context);
        initView(context);
    }
    private void initView(Context context) {
        this.context=context;
        View view = inflate(getContext(), R.layout.hoidayfragment_view, null);
        addView(view);
        createViews(view);

        butText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                saveHoliday();
            }
        });
    }
    private void createViews(View view){
        butText=(TextView)view.findViewById(R.id.butText);
        holidayLabelTextView=(TextView)view.findViewById(R.id.holidayLabelTextView);
        nameTextView=(TextView)view.findViewById(R.id.nameTextView);
        positionTextView=(TextView)view.findViewById(R.id.positionTextView);
        calendarView=(CalendarView)view.findViewById(R.id.calendarView);
        withSod=(RadioButton)view.findViewById(R.id.withSod);
        withoutSod=(RadioButton)view.findViewById(R.id.withoutSod);
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);

        ((MainActivity)context).setType("demibold", nameTextView, butText);
        ((MainActivity)context).setType("light", positionTextView, holidayLabelTextView);
        }

    public void setName(String name) {
        this.name = name;
        nameTextView.setText(name);
    }

    public void setId(String id) {
        this.id = id;
    }
    private void saveHoliday(){
        Calendar cal=calendarView.getChose();
        boolean holiday=withSod.isChecked();
        if(cal==null) {
            Toast.makeText(context, "Выберите дату", Toast.LENGTH_SHORT).show();
        }
        else{
            progressFrame.setVisibility(VISIBLE);
            String url = ((MainActivity) context).MAIN_URL + "holidays/";
            JSONObject object = new JSONObject();
            try {
                String s=((MainActivity)context).inputFormat.format(cal.getTime());
                object.put("end", s.substring(0,10));
                object.put("worker", id);
                object.put("payable", holiday);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("responseHoliday", object.toString());
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    saveIt();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressFrame.setVisibility(GONE);
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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
    private void saveIt(){
        progressFrame.setVisibility(VISIBLE);
        String url=((MainActivity)context).MAIN_URL+"workers/"+id+"/";
        JSONObject object=new JSONObject();
        boolean holiday=withSod.isChecked();
        try {
            String s="HOLIDAY";
            if(holiday){
                s+="1";
            }
            else{
                s+="0";
            }
            object.put("status",s);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Log.d("responseHoliday",object.toString());
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.PATCH, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(GONE);
                ((MainActivity)context).onBackPressed();
                Toast.makeText(context, "Сотрудник отправлен в отпуск", Toast.LENGTH_SHORT).show();
                Log.d("RESPAHoliday",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(GONE);
                Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }
        };
        ((MainActivity)context).requestQueue.add(objectRequest);
    }
}
