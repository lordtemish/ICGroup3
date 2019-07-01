package com.studio.crm.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.MassCreationAdapter;
import com.studio.crm.icgroup.Adapters.RateStarsAdapter;
import com.studio.crm.icgroup.Forms.MassCreationForm;
import com.studio.crm.icgroup.Forms.RemontForms;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MassCreationInfoFragment extends Fragment {

    FrameLayout progressFrame;
    RecyclerView recyclerView, startsRecyclerView;
    RateStarsAdapter starsAdapter;
    MassCreationAdapter adapter;
    TextView typeTextView, dateTextView,fullnameTextView, positionTextView, statusTextView, idTextView, senderInfoTextView;
    List<MassCreationForm> forms;
    LinearLayout butLayout, statusLayout, serviceAcceptedLayout;
    TextView depLabelTextView, orderNameTextView, depTextView, justText, cancelOrderTextView, acceptOrderTextView;
    String id, kind, array, role;
    String status="";
    EditText commentEditText;
    public MassCreationInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role=((MainActivity)getActivity()).role;
        id=getArguments().getString("id");
        View view=inflater.inflate(R.layout.fragment_mass_creation_info, container, false);
        createViews(view);
        adapter=new MassCreationAdapter(forms);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(startsRecyclerView, LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);
        starsAdapter=new RateStarsAdapter(true);
        startsRecyclerView.setAdapter(starsAdapter);

        try {
//            setInfo(new JSONArray(array));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        cancelOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close("FAILED");
            }
        });
        acceptOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
        getReq();
        return view;
    }
    private void createViews(View view){
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        startsRecyclerView=(RecyclerView) view.findViewById(R.id.starsRecyclerView);

        cancelOrderTextView=(TextView)view.findViewById(R.id.cancelOrderTextView);
        acceptOrderTextView=(TextView)view.findViewById(R.id.acceptOrderTextView);
        typeTextView=(TextView)view.findViewById(R.id.typeTextView);
        positionTextView=(TextView)view.findViewById(R.id.positionTextView);
        fullnameTextView=(TextView)view.findViewById(R.id.fullnameTextView);
        dateTextView=(TextView)view.findViewById(R.id.dateTextView);
        statusTextView=(TextView)view.findViewById(R.id.statusTextView);
        idTextView=(TextView)view.findViewById(R.id.idTextView);
        senderInfoTextView=(TextView)view.findViewById(R.id.senderInfoTextView);
        butLayout=(LinearLayout)view.findViewById(R.id.butLayout);
        serviceAcceptedLayout=(LinearLayout) view.findViewById(R.id.serviceAcceptedLayout);
        statusLayout=(LinearLayout)view.findViewById(R.id.statusLayout);
        commentEditText=(EditText)view.findViewById(R.id.commentEditText);
        forms=new ArrayList<>();
    }
    private void confirm(){
        if(status.equals("WAITING")){
            JSONObject object=new JSONObject();
            postReq("replenishments/"+id+"/confirm/",object);
        }
        if(status.equals("PROCESSING")){
            JSONObject object=new JSONObject();
            try {
              /*  object.put("status","FINISHED");
                object.put("score",5);
                object.put("comment_close","No message");*/
            }
            catch (Exception e){
                e.printStackTrace();
            }
            postReq("replenishments/"+id+"/finish/",object);
        }
        if(status.equals("FINISHED")){

            close("CLOSED");
        }
    }
    private void close(String status){
        if(commentEditText.getText().length()>0) {
            JSONObject object = new JSONObject();
            try {
                object.put("status", status);
                object.put("score", starsAdapter.getRate()  );
                object.put("comment_close", commentEditText.getText()+"");
            } catch (Exception e) {

            }
            postReq("replenishments/" + id + "/close/", object);
        }
        else {
            Toast.makeText(getActivity(), "Напишите комментарий", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkRoles(){
        serviceAcceptedLayout.setVisibility(View.GONE);
        cancelOrderTextView.setVisibility(View.VISIBLE);
        butLayout.setVisibility(View.GONE);
        if(role.equals("SUPERADMIN") || role.contains("SUPPLY") || role.contains("ADMIN_")){
            if( status.equals("FAILED") || status.equals("CLOSED")){
                    butLayout.setVisibility(View.GONE);
            }
            else{
                butLayout.setVisibility(View.VISIBLE);
                if(status.equals("PROCESSING")){
                    cancelOrderTextView.setVisibility(View.GONE);
                    acceptOrderTextView.setText("Завершить заявку");
                }
                else if(status.equals("WAITING")){
                    cancelOrderTextView.setVisibility(View.GONE);
                    acceptOrderTextView.setText("Принять заявку");
                }
                else if(status.equals("FINISHED")){
                    serviceAcceptedLayout.setVisibility(View.VISIBLE);
                    cancelOrderTextView.setText("Задача провалена");
                    acceptOrderTextView.setText("Задача завершена");
                }
            }
        }
    }
    private void postReq(String url, JSONObject params){
        progressFrame.setVisibility(View.VISIBLE);
        url=((MainActivity)getActivity()).MAIN_URL+url;
        Log.d("LOGlog",url+"\n"+params.toString());
        JsonObjectRequest objectRequest =new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Заявка обновлена", Toast.LENGTH_SHORT).show();
                getReq();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void getReq(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"replenishments/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                setReq(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setReq(JSONObject object){
        try {
            String id=object.getString("id"), status=object.getString("status"), created_at=object.getString("created_at");
            int priority=object.getInt("priority");
            String prio="Низкий", created=((MainActivity)getActivity()).getdate(created_at);
            JSONArray array=object.getJSONArray("orders");
            JSONObject author=object.getJSONObject("author"), respondent=object.getJSONObject("respondent");
            fullnameTextView.setText(respondent.getString("fullname"));positionTextView.setText(((MainActivity)getActivity()).positions.get(respondent.getString("role")));
            dateTextView.setText(created);
            senderInfoTextView.setText(author.getString("fullname")+" ("+((MainActivity)getActivity()).positions.get(author.getString("role"))+")");
            idTextView.setText("IC"+id);
            this.status=status;
            setStatus(status);
            checkRoles();
            setInfo(array);
        }
        catch (Exception e){

        }
    }
    private void setStatus(String s){

        switch (s){
            case "WAITING":
                statusLayout.setBackgroundResource(R.drawable.inwait_yellowpage);
                statusTextView.setText("Ожидает");
                break;
            case "PROCESSING":
                statusLayout.setBackgroundResource(R.drawable.icgreen_page);
                statusTextView.setText("Актуально");
                break;
            case "FAILED":
                statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                statusTextView.setText("Провалено");
                break;
            case "FINISHED":
                statusLayout.setBackgroundResource(R.drawable.greyrow_page);
                statusTextView.setText("Завершенно");
                break;
            case "CLOSED":
                statusLayout.setBackgroundResource(R.drawable.closed_page);
                statusTextView.setText("Закрыто");
                break;
        }
    }
    private void setInfo(JSONArray array){
        try{
            forms.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject order=array.getJSONObject(i);
                JSONObject object = order.getJSONObject("consumption");
                JSONObject inventory = object.getJSONObject("inventory");
                // JSONObject company = inventory.getJSONObject("company");
                String name = inventory.getString("name"), id = object.getString("id"), unit =inventory.getJSONObject("unit").getString("name"), vendor_code = inventory.getString("vendor_code");
                int num = object.getInt("remainder"), lim=object.getInt("limit");
                int repair = object.getInt("repair"), replace = object.getInt("replace");
                List<RemontForms> remontForms = new ArrayList<>();
                if (repair > 0) {
                    RemontForms form = new RemontForms("На ремонте", repair);
                    remontForms.add(form);
                }
                if (replace > 0) {
                    RemontForms form = new RemontForms("На замене", replace);
                    remontForms.add(form);
                }
                MassCreationForm creationForm=new MassCreationForm(name,vendor_code,unit,order.getInt("count"),num, lim);
                creationForm.setId(id);
                creationForm.setSet(true);
                forms.add(creationForm);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
