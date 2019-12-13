package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.RadioAdapter;
import com.studio.crm.icgroup.Forms.RadioForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceSortView extends FrameLayout {
    RecyclerView radioRecyclerView;
    public RadioAdapter radioAdapter;
    public List<RadioForm> radioForms, statusForms, typesForms;
    FrameLayout statusButton, typesButton, statusFrame, typesFrame;
    TextView statusTextView, typesTextView;
    public boolean type=false;
    public ServiceSortView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public ServiceSortView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public ServiceSortView(Context context){
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.servicesort_view, null);
        addView(view);
        createViews(view);
        checkPage();
        getRequest();
    }
    private void checkPage(){
            statusFrame.setVisibility(GONE);
            typesFrame.setVisibility(GONE);
        statusTextView.setTextColor(getContext().getResources().getColor(R.color.darkgrey));
        typesTextView.setTextColor(getContext().getResources().getColor(R.color.darkgrey));
        radioAdapter.check=0;
        if(!type){
            radioForms.clear();
            radioForms.addAll(statusForms);

            statusFrame.setVisibility(VISIBLE);
            statusTextView.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        else{
            radioForms.clear();
            radioForms.addAll(typesForms);

            typesFrame.setVisibility(VISIBLE);
            typesTextView.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        radioAdapter.notifyDataSetChanged();
    }
    private void createViews(View view) {
        statusButton=(FrameLayout) view.findViewById(R.id.statusButton);
        typesButton=(FrameLayout) view.findViewById(R.id.typesButton);

        statusFrame=(FrameLayout)view.findViewById(R.id.statusFrame);
        typesFrame=(FrameLayout)view.findViewById(R.id.typesFrame);

        typesTextView=(TextView)view.findViewById(R.id.typesTextView);
        statusTextView=(TextView)view.findViewById(R.id.statusTextView);

        statusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type=false;
                checkPage();
            }
        });
        typesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                type=true;
                checkPage();
            }
        });
        radioRecyclerView=(RecyclerView)view.findViewById(R.id.radioRecyclerView);
        ((MainActivity)getContext()).setRecyclerViewOrientation(radioRecyclerView, LinearLayoutManager.VERTICAL);

        radioForms=new ArrayList<>();
        statusForms=new ArrayList<>();
        typesForms=new ArrayList<>();

        radioAdapter=new RadioAdapter(radioForms);
        radioAdapter.setRadioable(true);
        radioRecyclerView.setAdapter(radioAdapter);

        List<RadioForm> radioForms=new ArrayList<>();
        RadioForm t1=new RadioForm(false,"Ожидает подтверждения");
        t1.setId("WAITING");
        statusForms.add(t1);
        RadioForm t2=new RadioForm(false,"В процессе");
        t2.setId("PROCESSING");
        statusForms.add(t2);
        RadioForm t3=new RadioForm(false,"Провалено");
        t3.setId("FAILED");
        statusForms.add(t3);
        RadioForm t4=new RadioForm(false,"Завершено");
        t4.setId("FINISHED");
        statusForms.add(t4);
        RadioForm t5=new RadioForm(false,"Закрыто");
        t5.setId("CLOSED");
        statusForms.add(t5);
        /*
(('WAITING', 'Ожидает подтверждения'),
('PROCESSING', 'В процессе'),
('FAILED', 'Провалено'),
 ('FINISHED', 'Завершено'),
  ('CLOSED', 'Закрыто'))
*/

    }

    private void getRequest(){
        String url=((MainActivity)getContext()).MAIN_URL+"taskkinds/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Looog",response.toString());
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getContext()).token);
                Log.d("token",headers.toString());
                return headers;
            }
        };
        ((MainActivity)getContext()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONArray array){
        try{
            List<RadioForm> forms=new ArrayList<>();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                RadioForm form=new RadioForm(false,object.getString("name"));
                form.setId(object.getString("id"));
                forms.add(form);
            }
            typesForms.clear();
            typesForms.addAll(forms);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getPosition(){
        return radioAdapter.check;
    }
    public String getUrl(){
        if(!type){
            Log.d("RADIO",radioAdapter.check+"  ");
            for(int i=0;i<statusForms.size();i++){
                Log.d("radio i",""+statusForms.get(i).getId()+" "+statusForms.get(i).getText());
            }
            return "status="+statusForms.get(radioAdapter.check).getId();
        }
        else{
            return "kind="+typesForms.get(radioAdapter.check).getId();
        }
    }
}
