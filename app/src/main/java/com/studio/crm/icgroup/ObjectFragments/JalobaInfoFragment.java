package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AcceptAdapter;
import com.studio.crm.icgroup.Adapters.MessageAdapter;
import com.studio.crm.icgroup.Adapters.RadioAdapter;
import com.studio.crm.icgroup.Adapters.UserRowAdapter;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.Forms.MessageForm;
import com.studio.crm.icgroup.Forms.RadioForm;
import com.studio.crm.icgroup.Forms.UserRowForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class JalobaInfoFragment extends Fragment {
    RecyclerView userRecyclerView, messageRecyclerView, radioRecyclerView, acceptRecyclerView;
    ConstraintLayout makeAnswerLayout;
    LinearLayout answerLayout, makeAnswerLinear;
    EditText nameEditText;
    FrameLayout progressLayout;
    TextView mainObjectTitle, dateTextView,consultationTextView, jalobaAnswerLabelTextView, jalobaDateTextView, answerNameTextView, answerMessageTextView, answerPositionTextView,makeAnswerTextView;
    boolean answerable;
    String id, point="", role, usrid;
    List<RadioForm> radioForms;
    List<AcceptForm> acceptForms;
    UserRowAdapter userRowAdapter;
    MessageAdapter messageAdapter;
    RadioAdapter radioAdapter;
    AcceptAdapter acceptAdapter;
    List<UserRowForm> rowForms;
    boolean is_producer_seen=false, is_curator_seen=false, is_executive_seen=false;
    public JalobaInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        usrid=((MainActivity)getActivity()).userid;
        role=((MainActivity)getActivity()).role;

        answerable=getArguments().getBoolean("answerable",false);
        id=getArguments().getString("id","");
        Log.d("ROLE",role+" "+id);
        View view=inflater.inflate(R.layout.fragment_jaloba_info, container, false);
        createViews(view);
        setFonttype();
        ((MainActivity)getActivity()).setRecyclerViewOrientation(userRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(messageRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(radioRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView, LinearLayoutManager.HORIZONTAL);

         rowForms=new ArrayList<>();
        UserRowForm form=new UserRowForm("Temirlan","начальник отдела",getActivity().getResources().getString(R.string.datestring), "Отправитель","Представитель клиента");
        rowForms.add(new UserRowForm( "Жалоба","Качество услуг"));
        rowForms.add(form);
        radioForms=new ArrayList<>();
        radioForms.add(new RadioForm(true,"Халатное отношение"));
        radioForms.add(new RadioForm(false,"Не соответствующий внешний вид"));
        radioForms.add(new RadioForm(true,"Хамит на работе"));
         acceptForms=new ArrayList<>();

        userRowAdapter=new UserRowAdapter(rowForms);
        messageAdapter=new MessageAdapter(new MessageForm(getActivity().getResources().getString(R.string.bigtext)));
         radioAdapter=new RadioAdapter(radioForms);
        acceptAdapter=new AcceptAdapter(acceptForms);

        userRecyclerView.setAdapter(userRowAdapter);
        messageRecyclerView.setAdapter(messageAdapter);
        radioRecyclerView.setAdapter(radioAdapter);
        acceptRecyclerView.setAdapter(acceptAdapter);
        makeAnswerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRepy();
            }
        });
        setAnswerable(answerable);
        getRequest();
        return view;
    }

    private void createViews(View view){
        userRecyclerView=(RecyclerView) view.findViewById(R.id.userRecyclerView);
        messageRecyclerView=(RecyclerView) view.findViewById(R.id.messageRecyclerView);
        radioRecyclerView=(RecyclerView) view.findViewById(R.id.radioRecyclerView);
        acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);

        makeAnswerLayout=(ConstraintLayout) view.findViewById(R.id.makeAnswerLayout);
        nameEditText=(EditText) view.findViewById(R.id.nameEditText);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        makeAnswerLinear=(LinearLayout) view.findViewById(R.id.makeAnswerLinear);
        answerLayout=(LinearLayout) view.findViewById(R.id.answerLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        consultationTextView=(TextView) view.findViewById(R.id.consultationTextView);
        jalobaAnswerLabelTextView=(TextView) view.findViewById(R.id.jalobaAnswerLabelTextView);
        jalobaDateTextView=(TextView) view.findViewById(R.id.jalobaDateTextView);
        answerNameTextView=(TextView) view.findViewById(R.id.answerNameTextView);
        answerMessageTextView=(TextView) view.findViewById(R.id.answerMessageTextView);
        answerPositionTextView=(TextView) view.findViewById(R.id.answerPositionTextView);
        makeAnswerTextView=(TextView) view.findViewById(R.id.makeAnswerTextView);
    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", dateTextView, jalobaAnswerLabelTextView, answerNameTextView);
        setTypeFace("light",consultationTextView, jalobaDateTextView, answerPositionTextView, answerMessageTextView);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }
    public void setAnswerable(boolean a){
        if(a){
            makeAnswerLinear.setVisibility(View.VISIBLE);
            answerLayout.setVisibility(View.GONE);
        }
        else{
            makeAnswerLinear.setVisibility(View.GONE);
            answerLayout.setVisibility(View.VISIBLE);
        }
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"complaints/"+id;
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
        };
        ((MainActivity)getActivity()).requestQueue.add(request);
    }
    private void setInfo(JSONObject object){
        try{
            rowForms.clear();
            rowForms.add(new UserRowForm( "Жалоба","Качество услуг"));
            if(!object.isNull("is_curator_seen"))
            is_curator_seen=object.getBoolean("is_curator_seen");
            if(!object.isNull("is_executive_seen"))
            is_executive_seen=object.getBoolean("is_executive_seen");
            if(!object.isNull("is_producer_seen"))
            is_producer_seen=object.getBoolean("is_producer_seen");
            JSONArray array=object.getJSONArray("reasons");
            JSONObject defendant=null, user=object.getJSONObject("author"), point=object.getJSONObject("point"), reply_user=null;
            if(!object.isNull("reply_user")){
                reply_user=object.getJSONObject("reply_user");
            }
            if(!object.isNull("defendant")){
                defendant=object.getJSONObject("defendant");
                String name=defendant.getString("fullname"), role=defendant.getString("role");
                String pos=((MainActivity)getActivity()).positions.get(role);
                UserRowForm form=new UserRowForm(name, pos, "","Жалоба","На сотрудника");
                if(!defendant.isNull("avatar")){
                    // need change
                }
                rowForms.set(0,form);
            }
            String usName=user.getString("fullname"), usRole=user.getString("role"), role=((MainActivity)getActivity()).positions.get(usRole);
            String created_at=object.getString("created_at");
            String created=((MainActivity)getActivity()).getdate(created_at);
            created=created.substring(0,created.length()-6);
            UserRowForm form=new UserRowForm(usName,role ,"","Отправитель",role);
            rowForms.add(form);
            userRowAdapter.notifyDataSetChanged();
            List<MessageForm> forms=new ArrayList<>();
            forms.add(new MessageForm(object.getString("content")));
            messageAdapter.setList(forms);
            messageAdapter.notifyDataSetChanged();
            radioForms.clear();
            this.point=point.getString("id");
            for(int i=0;i<array.length();i++){
                radioForms.add(new RadioForm(true,array.getJSONObject(i).getString("name")));
            }
            radioAdapter.notifyDataSetChanged();
            boolean a=object.isNull("reply_comment");
            if(a){
                setAnswerable(true);
                makeAnswerLinear.setVisibility(View.GONE);
                if(this.role.equals("SUPERADMIN")){
                    makeAnswerLinear.setVisibility(View.VISIBLE);
                }
            }
            else{
                setAnswerable(false);
                answerNameTextView.setText(reply_user.getString("fullname"));
                answerPositionTextView.setText(((MainActivity)getActivity()).positions.get(reply_user.getString("role")));
                answerMessageTextView.setText("Комментарий:\n\n"+object.getString("reply_comment"));
                String cret=object.getString("reply_time");
                String cr=((MainActivity)getActivity()).getdate(cret);cr=cr.substring(0,cr.length()-6);
                jalobaDateTextView.setText(cr);
            }
            acceptForms.clear();
            JSONArray permits=object.getJSONArray("permits");
            Log.d("perm",permits.toString());
            for(int i=0;i<permits.length();i++){
                JSONObject jsonObject=permits.getJSONObject(i);
                JSONObject jsonUser=jsonObject.getJSONObject("user");
                String name=jsonUser.getString("fullname"), jsonRole=jsonUser.getString("role");
                String position=((MainActivity)getActivity()).positions.get(jsonRole);
                String status="Не просмотренно";
                boolean st=jsonObject.getBoolean("permitted");
                if(st){
                    status="Просмотренно";
                }
                AcceptForm acceptForm=new AcceptForm(name,"",position, status, st);
                acceptForms.add(acceptForm);
            }
            acceptAdapter.notifyDataSetChanged();
            //getExec();
            //getPoint();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getPoint(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"points/"+point;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setPoints(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(request);
    }
    private void setPoints(JSONObject object){
        try {
            String cStatus="Не просмотренно", pStatus="Не просмотренно";
            if(!object.isNull("curator")){
                JSONObject contactor=object.getJSONObject("curator");
                if(usrid.equals(contactor.getString("id"))){

                    makeAnswerLinear.setVisibility(View.VISIBLE);
                }
                String cName=contactor.getString("fullname"), cRole=contactor.getString("role");
                String cPos=((MainActivity)getActivity()).positions.get(cRole);
                if(is_curator_seen){
                    cStatus="Просмотренно";
                }
                AcceptForm cForm=new AcceptForm(cName,"", cPos,cStatus,is_curator_seen);
                acceptForms.add(cForm);
            }
            if(!object.isNull("producer")) {
                JSONObject producer = object.getJSONObject("producer");
                if(usrid.equals(producer.getString("id"))){

                    makeAnswerLinear.setVisibility(View.VISIBLE);
                }
                String pName = producer.getString("fullname"), pRole = producer.getString("role");
                String pPos = ((MainActivity) getActivity()).positions.get(pRole);
                if (is_producer_seen) {
                    pStatus = "Просмотренно";
                }
                AcceptForm pForm = new AcceptForm(pName, "", pPos, pStatus, is_producer_seen);
                acceptForms.add(pForm);
            }
            acceptAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getExec(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"employees";
        JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, url + "?user__role=ADMIN_EXECUTIVE", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                if(response.length()>0){
                    try {
                        setExec(response.getJSONObject(0));
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(admin_execRequest);
    }
    private void setExec(JSONObject o){
        try{
            String dep="";
            String depid=o.getString("department");
            int l=0;
            for(String i:((MainActivity)getActivity()).dpids){
                if(depid.equals(i)){
                    dep=((MainActivity)getActivity()).departments.get(l);
                }
                l++;
            }
            JSONObject user=o.getJSONObject("user");
            if(usrid.equals(user.getString("id"))){
                makeAnswerLinear.setVisibility(View.VISIBLE);
            }
            String name=user.getString("fullname"), role=user.getString("role");
            String position=((MainActivity)getActivity()).positions.get(role);
            String status="Не просмотренно";
            if(is_executive_seen){
                status="Просмотренно";
            }
            AcceptForm acceptForm=new AcceptForm(name, dep, position, status,is_executive_seen);
            acceptForms.add(acceptForm);
            acceptAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void sendRepy(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"complaints/"+id+"/reply/";
        JSONObject params=new JSONObject();
        try {
            params.put("reply_comment", nameEditText.getText() + "");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Ответ успешно добавлен", Toast.LENGTH_SHORT).show();
                getRequest();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
}
