package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceInfoFragment extends Fragment {
    String[] as;
    TextView cancelButText,acceptButText,mainObjectTitle, orderNumTextView, orderNumIdTextView, dayLeftLabelTextView, dayLeftTextView, priorityLabelTextView, priorityTextView, dateLabelTextView, dateTextView,
            stopDateLabelTextView, stopDateTextView, statusLabelTextView, statusTextView, objectNameLabelTextView, objectNameTextView, serviceTypeLabelTextView, serviceTypeTextView,placeLabelTextView, placeTextView, employeeLabelTextView, employeeNameTextView, employeePositionTextView, autorLabelTextView, autorNameTextView,
            autorPositionTextView, serviceInfoLabelTextView, serviceInfoTextView,messagesLabelTextView,mediaLabelTextView,acceptLabelTextView,serviceStatusTextView,
            positionLabelTextView,nameTextView,positionTextView,commentsLabelTextView,positionLabelTextView1,nameTextView1, positionTextView1, notAcceptedButton,acceptedButton,needMarkPlease,mediaFileOpenTextView, commentMediaTextView, SAMessagesLabelTextView, serviceAcceptedLabelTextView
            ,timeLeftTextView,failedTextView, failedExtraTextView, serviceStatusesdateTextView;
    ImageView serviceStatusImageView, userPhotoImageView1, clockImageView;
    RadioButton acceptedRadio;
    FrameLayout progressLayout,ffff;
    boolean accepted=false;
    RecyclerView messagesRecyclerView, acceptRecyclerView,commentsRecyclerView;
    LinearLayout butLayout,messagesLinearLayout,wholeLayout,serviceStatusesLayout, extraLayout, failedLayout, serviceAcceptedLayout,commentsLinearLayout,lastCommentLayout,serviceStatusMediaFileLayout;
    ConstraintLayout serviceStatusLayout,serviceStatusUserInfoLayout;
    ProgressBar progressBar;
    LinearLayout acceptLayout;
    String id="", dpid="", next="", point="", usrid="", stat="", meType="";
    List<AcceptForm> acceptForms;
    AcceptAdapter acceptAdapter;
    MessageAdapter messageAdapter, messageAdapter1;
    List<String[]> strings;
    int superadmin=-1,is_executive_permitted=-1, is_technical_permitted=-1, is_chief_permitted=-1, is_contactor_permitted=-1, is_respondent_permitted=0;
    boolean me=false;

    public ServiceInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        usrid=((MainActivity)getActivity()).userid;
        View view=inflater.inflate(R.layout.fragment_service_info, container, false);
        createViews(view);
        butLayout.setVisibility(View.GONE);
        setFonttypes();
        setAdapters();
       // setFailed();
      //  setFailedRetake();
      //  autorClosed();
        //inProcess();
      //  serviceFinished();
     //   itFinished();

        stat=getArguments().getString("status","");id=getArguments().getString("id");
        as = getArguments().getString("startend", " ").split(" ");
        if(as.length==2) {
            int d1=Integer.parseInt(as[0]), d2=Integer.parseInt(as[1]);
            dayLeftTextView.setText("Осталось дней: "+d1+"/"+d2);
            if (d2 > 0) {
                progressBar.setProgress((d2 - d1) / d2 * 100);
            } else {
                progressBar.setProgress(100);
            }
        }
        else{
            as=new String[]{};
        }
        Log.d("status",stat);
        switch (stat){
            case "FINISHED":
                setAccepted();
                itFinished();
                break;
            case "TIMEOVER":
                setFailedS();
                break;
            case "PROLONGING":
                setReLated();setFailedRetake();
                break;
            case "PROLONGED":
                setReLated();
                break;
            case "PROCESSING":
                setInProcess();
                break;
            case "WAITING":
                setInWait();
                break;
            case "CLOSED":
                setClosed();
                autorClosed();
                break;
        }
        getRequest();
        return view;
    }
    private void setFonttypes(){
        mainObjectTitle.setTypeface(((MainActivity)getActivity()).getTypeFace("it"));

        setType("demibold",orderNumIdTextView,dayLeftTextView,priorityTextView,dateTextView,stopDateTextView, statusTextView,objectNameTextView, serviceTypeTextView, placeTextView, employeeNameTextView, autorNameTextView, serviceInfoTextView,mediaLabelTextView,acceptLabelTextView,serviceStatusTextView,nameTextView,positionTextView,commentsLabelTextView, positionTextView1, nameTextView1, notAcceptedButton,commentMediaTextView, SAMessagesLabelTextView, serviceAcceptedLabelTextView,acceptedRadio,timeLeftTextView, failedTextView, acceptButText, cancelButText);
        setType("light", statusLabelTextView,orderNumTextView,dayLeftLabelTextView, priorityLabelTextView, dateLabelTextView, stopDateLabelTextView, objectNameLabelTextView, serviceTypeLabelTextView , placeLabelTextView, employeeLabelTextView, autorLabelTextView, serviceInfoLabelTextView, employeePositionTextView, autorPositionTextView, messagesLabelTextView,positionLabelTextView,positionLabelTextView1,acceptedButton,needMarkPlease, mediaFileOpenTextView, failedExtraTextView);
        //setType("regular");
    }
    private void setType(String type, TextView... a){
            for(int i=0;i<a.length;i++){
                a[i].setTypeface(((MainActivity)getActivity()).getTypeFace(type));
            }

    }
    private void setAdapters(){
        ((MainActivity) getActivity()).setRecyclerViewOrientation(messagesRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(acceptRecyclerView, LinearLayoutManager.HORIZONTAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(commentsRecyclerView, LinearLayoutManager.VERTICAL);

        MessageForm messageForm=new MessageForm(getActivity().getResources().getString(R.string.bigtext));
        messageAdapter=new MessageAdapter(messageForm);
        messagesRecyclerView.setAdapter(messageAdapter);

       acceptForms=new ArrayList<>();
       acceptAdapter=new AcceptAdapter(acceptForms);
        acceptRecyclerView.setAdapter(acceptAdapter);

        MessageForm messageForm1=new MessageForm(getActivity().getResources().getString(R.string.bigtext));
        messageAdapter1=new MessageAdapter(messageForm1);
        commentsRecyclerView.setAdapter(messageAdapter1);
    }
    private void setFailed(){
        setWhite(orderNumTextView,orderNumIdTextView, dayLeftLabelTextView, dayLeftTextView, priorityTextView, priorityLabelTextView, dateTextView,dateLabelTextView,stopDateTextView,stopDateLabelTextView,statusLabelTextView,statusTextView);
        wholeLayout.setBackgroundResource(R.drawable.failed_green_page);
        statusTextView.setBackgroundResource(R.drawable.failed_verydarkgreen);
    }
    private void setWhite(TextView... textView){
        for(int i=0;i<textView.length;i++)
        textView[i].setTextColor(getActivity().getResources().getColor(R.color.white));
    }
    private void createViews(View view){
        strings=new ArrayList<>();
        strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});

        serviceStatusLayout=(ConstraintLayout) view.findViewById(R.id.serviceStatusLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        ffff=(FrameLayout) view.findViewById(R.id.ffff);
        acceptLayout=(LinearLayout) view.findViewById(R.id.acceptLayout);
        acceptButText=(TextView) view.findViewById(R.id.acceptButText);
        cancelButText=(TextView) view.findViewById(R.id.cancelButText);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        orderNumTextView=(TextView) view.findViewById(R.id.orderNumTextView);
        orderNumIdTextView=(TextView) view.findViewById(R.id.orderNumIdTextView);
        dayLeftLabelTextView=(TextView) view.findViewById(R.id.dayLeftLabelTextView);
        dayLeftTextView=(TextView) view.findViewById(R.id.dayLeftTextView);
        priorityTextView=(TextView) view.findViewById(R.id.priorityTextView);
        priorityLabelTextView=(TextView) view.findViewById(R.id.priorityLabelTextView);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        dateLabelTextView=(TextView) view.findViewById(R.id.dateLabelTextView);
        stopDateTextView=(TextView) view.findViewById(R.id.stopDateTextView);
        stopDateLabelTextView=(TextView) view.findViewById(R.id.stopDateLabelTextView);
        statusLabelTextView=(TextView) view.findViewById(R.id.statusLabelTextView);
        statusTextView=(TextView) view.findViewById(R.id.statusTextView);
        objectNameTextView=(TextView) view.findViewById(R.id.objectNameTextView);
        objectNameLabelTextView=(TextView) view.findViewById(R.id.objectNameLabelTextView);
        serviceTypeTextView=(TextView) view.findViewById(R.id.serviceTypeTextView);
        serviceTypeLabelTextView=(TextView) view.findViewById(R.id.serviceTypeLabelTextView);
        placeTextView=(TextView) view.findViewById(R.id.placeTextView);
        placeLabelTextView=(TextView) view.findViewById(R.id.placeLabelTextView);
        employeeNameTextView=(TextView) view.findViewById(R.id.employeeNameTextView);
        employeeLabelTextView=(TextView) view.findViewById(R.id.employeeLabelTextView);
        employeePositionTextView=(TextView) view.findViewById(R.id.employeePositionTextView);
        autorNameTextView=(TextView) view.findViewById(R.id.autorNameTextView);
       autorLabelTextView=(TextView) view.findViewById(R.id.autorLabelTextView);
        autorPositionTextView=(TextView) view.findViewById(R.id.autorPositionTextView);
        serviceInfoTextView=(TextView) view.findViewById(R.id.serviceInfoTextView);
        serviceInfoLabelTextView=(TextView) view.findViewById(R.id.serviceInfoLabelTextView);
        messagesLabelTextView=(TextView) view.findViewById(R.id.messagesLabelTextView);
        mediaLabelTextView=(TextView) view.findViewById(R.id.mediaLabelTextView);
        acceptLabelTextView=(TextView) view.findViewById(R.id.acceptLabelTextView);
        serviceStatusTextView=(TextView) view.findViewById(R.id.serviceStatusTextView);
        positionLabelTextView=(TextView) view.findViewById(R.id.positionLabelTextView);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        commentsLabelTextView=(TextView) view.findViewById(R.id.commentsLabelTextView);
        positionTextView1=(TextView) view.findViewById(R.id.positionTextView1);
        positionLabelTextView1=(TextView) view.findViewById(R.id.positionLabelTextView1);
        nameTextView1=(TextView) view.findViewById(R.id.nameTextView1);
        acceptedButton=(TextView) view.findViewById(R.id.acceptedButton);
        notAcceptedButton=(TextView) view.findViewById(R.id.notAcceptedButton);
        needMarkPlease=(TextView) view.findViewById(R.id.needMarkPlease);
        mediaFileOpenTextView=(TextView) view.findViewById(R.id.mediaFileOpenTextView);
        commentMediaTextView=(TextView) view.findViewById(R.id.commentMediaTextView);
        SAMessagesLabelTextView=(TextView) view.findViewById(R.id.SAMessagesLabelTextView);
        serviceAcceptedLabelTextView=(TextView) view.findViewById(R.id.serviceAcceptedLabelTextView);
        timeLeftTextView=(TextView) view.findViewById(R.id.timeLeftTextView);
        failedTextView=(TextView) view.findViewById(R.id.failedTextView);
        failedExtraTextView=(TextView) view.findViewById(R.id.failedExtraTextView);
        serviceStatusesdateTextView=(TextView) view.findViewById(R.id.serviceStatusesdateTextView);

        messagesRecyclerView=(RecyclerView) view.findViewById(R.id.messagesRecyclerView);
        acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecycler);
        commentsRecyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);

        butLayout=(LinearLayout) view.findViewById(R.id.butLayout);
        wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
        messagesLinearLayout=(LinearLayout) view.findViewById(R.id.messagesLinearLayout);
        serviceStatusesLayout=(LinearLayout) view.findViewById(R.id.serviceStatusesLayout);
        commentsLinearLayout=(LinearLayout) view.findViewById(R.id.commentsLinearLayout);
        extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
        failedLayout=(LinearLayout) view.findViewById(R.id.failedLayout);
        serviceAcceptedLayout=(LinearLayout) view.findViewById(R.id.serviceAcceptedLayout);
        lastCommentLayout=(LinearLayout) view.findViewById(R.id.lastCommentLayout);
        serviceStatusMediaFileLayout=(LinearLayout) view.findViewById(R.id.serviceStatusMediaFileLayout);

        acceptedRadio=(RadioButton) view.findViewById(R.id.acceptedRadio);
        progressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);

        clockImageView=(ImageView) view.findViewById(R.id.clockImageView);
        serviceStatusImageView=(ImageView) view.findViewById(R.id.serviceStatusImageView);
        serviceStatusUserInfoLayout=(ConstraintLayout) view.findViewById(R.id.serviceStatusUserInfoLayout);


    }
    private void setFailedTime(){
        extraLayout.setVisibility(View.VISIBLE);
        failedLayout.setVisibility(View.VISIBLE);
    }
    private void setFailedRetake(){
        acceptedRadio.setVisibility(View.VISIBLE);
        extraLayout.setVisibility(View.VISIBLE);
        failedLayout.setVisibility(View.VISIBLE);
        clockImageView.setVisibility(View.GONE);
        timeLeftTextView.setText("Запрос на повторное возобновление задач");
    }
    private void autorClosed(){
        serviceStatusesLayout.setVisibility(View.VISIBLE);
        serviceStatusLayout.setVisibility(View.VISIBLE);
     //   commentsLinearLayout.setVisibility(View.VISIBLE);
    }
    private void inProcess(){
        serviceStatusesLayout.setVisibility(View.VISIBLE);
   //     commentsLinearLayout.setVisibility(View.VISIBLE);
    //    serviceStatusUserInfoLayout.setVisibility(View.VISIBLE);
    //    serviceStatusMediaFileLayout.setVisibility(View.VISIBLE);
     //   serviceStatusImageView.setImageResource(R.drawable.ic_books);
      //  serviceStatusTextView.setText("В процессе");
    }
    private void itFinished(){
        serviceStatusesdateTextView.setVisibility(View.VISIBLE);
       // serviceStatusesLayout.setVisibility(View.VISIBLE);
        serviceStatusLayout.setVisibility(View.VISIBLE);
      //  commentsLinearLayout.setVisibility(View.VISIBLE);
      //  serviceStatusUserInfoLayout.setVisibility(View.VISIBLE);
      //  serviceStatusMediaFileLayout.setVisibility(View.VISIBLE);
      //  serviceStatusImageView.setImageResource(R.drawable.ic_solvves);
        serviceStatusTextView.setText("Задача выполнена");
    }
    private void serviceFinished(){
        acceptedRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkedCheck();
            }
        });
        serviceAcceptedLayout.setVisibility(View.VISIBLE);
    }
    private void checkedCheck(){
        acceptedRadio.setChecked(!accepted);
        accepted=!accepted;
        if(accepted){
            acceptedRadio.setTextColor(getActivity().getResources().getColor(R.color.black));
        }
        else{
            acceptedRadio.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
    }


    public void setInProcess(){
        statusTextView.setBackgroundResource((R.drawable.inprocess_green_page));
        statusTextView.setText("В процессе");
        inProcess();
    }
    public void setReLated(){
        statusTextView.setBackgroundResource((R.drawable.related_darkgreen_page));
        progressBar.setProgressDrawable(getActivity().getResources().getDrawable(R.drawable.wronged_progress_row));
        statusTextView.setText("На просрочке");
    }
    public void setFailedS(){
        setAllColor(getActivity().getResources().getColor(R.color.white));
        wholeLayout.setBackgroundResource((R.drawable.failed_green_page));
        statusTextView.setBackgroundResource((R.drawable.failed_verydarkgreen));
        progressBar.setProgressDrawable(getActivity().getResources().getDrawable(R.drawable.failedprogress_perc));
        progressBar.setProgress(100);
        statusTextView.setText("Провалено");
        setFailedTime();
    }
    public void setInWait(){
        statusTextView.setBackgroundResource((R.drawable.inwait_yellowpage));
        progressBar.setProgress(100);
        statusTextView.setText("Ожидает подтверждения");
    }
    public void setAccepted(){
        progressBar.setProgress(0);
        statusTextView.setBackgroundResource((R.drawable.greyrow_page));
        statusTextView.setText("Выполнено");
    }
    public void setClosed(){
        statusTextView.setBackgroundResource(R.drawable.closed_page);
        serviceStatusLayout.setVisibility(View.VISIBLE);
        statusTextView.setText("Закрыта");
        progressBar.setProgress(0);
    }
    private void setAllColor(int color){
        orderNumTextView.setTextColor(color);
        orderNumIdTextView.setTextColor(color);
        serviceTypeLabelTextView.setTextColor(color);
        serviceTypeTextView.setTextColor(color);
        dayLeftLabelTextView.setTextColor(color);
        dayLeftTextView.setTextColor(color);
        priorityLabelTextView.setTextColor(color);
        priorityTextView.setTextColor(color);
        dateLabelTextView.setTextColor(color);
        dateTextView.setTextColor(color);
        stopDateLabelTextView.setTextColor(color);
        stopDateTextView.setTextColor(color);
        statusLabelTextView.setTextColor(color);
    }

    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity) getActivity()).MAIN_URL+"tasks/"+id;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(jsonObjectRequest);
    }
    private void setInfo(JSONObject object){
        try{
            if(object.isNull("is_executive_permitted"))
                is_executive_permitted=-1;
            else{
                Boolean b=object.getBoolean("is_executive_permitted");
                if(b) is_executive_permitted=1;else is_executive_permitted=0;
            }
            if(object.isNull("is_contactor_permitted"))
                is_contactor_permitted=-1;
            else{ Boolean b=object.getBoolean("is_contactor_permitted");
                if(b) is_contactor_permitted=1;else is_contactor_permitted=0; }
            if(object.isNull("is_technical_permitted"))
                is_technical_permitted=-1;
            else{ Boolean b=object.getBoolean("is_technical_permitted");
                if(b) is_technical_permitted=1;else is_technical_permitted=0; }
            if(object.isNull("is_chief_permitted"))
                is_chief_permitted=-1;
            else{ Boolean b=object.getBoolean("is_executive_permitted");
                if(b) is_chief_permitted=1;else is_chief_permitted=0; }
                if(true){
                    Boolean b=object.getBoolean("is_respondent_permitted");
                    if(b) is_respondent_permitted=1;else is_respondent_permitted=0;
                }
                if(((MainActivity)getActivity()).role.equals("SUPERADMIN")){
                      superadmin=0;

                }
            Log.d("responsInfo",object.toString());
            orderNumIdTextView.setText("IC"+id);
            String priority="низкий";
            switch (object.getInt("priority")){
                case 1:
                    priority="Низкий";
                    break;
                case 2:
                    priority="Средний";
                    break;
                case 3:
                    priority="Высокий";
                    break;
            }
            priorityTextView.setText(priority);
            String created_at=object.getString("created_at");
            String deadline=object.getString("deadline");
            String date1=((MainActivity)getActivity()).getdate(created_at);
            String dead1=((MainActivity)getActivity()).getdate(deadline);
            dateTextView.setText(date1);stopDateTextView.setText(dead1);
            serviceTypeTextView.setText(object.getJSONObject("kind").getString("name"));

            JSONObject point=object.getJSONObject("point"), author=object.getJSONObject("author");
            if(!object.isNull("respondent")) {
                JSONObject respondent = object.getJSONObject("respondent");
                JSONObject user = respondent.getJSONObject("user");

                this.point = point.getString("id");
                dpid = respondent.getJSONObject("department").getString("id");
                if (user.getString("id").equals(usrid)) {
                    me = true;
                    meType = "respondent";
                }
                strings.set(4, new String[]{user.getString("fullname"), user.getString("role"), dpid});
                objectNameTextView.setText(point.getString("name"));
                placeTextView.setText(respondent.getJSONObject("department").getString("name"));
                employeeNameTextView.setText(user.getString("fullname"));
                employeePositionTextView.setText(((MainActivity) getActivity()).positions.get(user.getString("role")));
            }
            else{

            }
            autorNameTextView.setText(author.getString("fullname"));
            autorPositionTextView.setText(((MainActivity)getActivity()).positions.get(author.getString("role")));
            serviceInfoTextView.setText(object.getString("description"));
            String comment_create="";
            if(!object.isNull("comment_create")){
                comment_create=object.getString("comment_create");
                messagesLinearLayout.setVisibility(View.VISIBLE);
                List<MessageForm>forms=new ArrayList<>();
                forms.add(new MessageForm(comment_create));
                messageAdapter.setList(forms);
                messageAdapter.notifyDataSetChanged();
            }
            if(created_at.length()==20){
                created_at=created_at.substring(0,created_at.length()-1)+".0Z";
                }
            if(deadline.length()==20){
                deadline=created_at.substring(0,deadline.length()-1)+".0Z";
            }
            Date created = ((MainActivity) getActivity()).inputFormat.parse(created_at), dead = ((MainActivity) getActivity()).inputFormat.parse(deadline);
            Date now = new Date();
            long wDays = dead.getTime() - created.getTime(), nDays = now.getTime() - created.getTime();
            int days = Integer.parseInt(TimeUnit.DAYS.convert(wDays, TimeUnit.MILLISECONDS) + "");
            int nowdays = Integer.parseInt(TimeUnit.DAYS.convert(nDays, TimeUnit.MILLISECONDS) + "");
            if (days - nowdays > 0) {
                Log.d("nowdays", days + " " + nowdays);
                nowdays = days - nowdays;
                Log.d("nowdays", (days - nowdays > 0) + "");
            } else {
                nowdays = 0;
            }
           /*     dayLeftTextView.setText("Осталось дней: "+nowdays+"/"+days);
                if (days > 0 && days>nowdays) {
                    progressBar.setProgress((nowdays) / days * 100);
                } else {
                    progressBar.setProgress(100);
                }*/
            checkAccepts();
            getData();
            getChief();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }




    private void getData(){
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
        JsonArrayRequest admin_techRequest=new JsonArrayRequest(Request.Method.GET, url + "?user__role=ADMIN_TECHNICAL", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                if(response.length()>0){
                    try {
                        setTech(response.getJSONObject(0));
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
        JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, ((MainActivity) getActivity()).MAIN_URL+"points/"+point, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                if(response.length()>0){
                    try {
                        JSONObject contactor=response.getJSONObject("contactor");
                        String id=contactor.getString("id");
                        if(id.equals(usrid)){
                            me=true;
                            meType="contactor";
                        }
                        strings.set(3,new String[]{contactor.getString("fullname"),contactor.getString("role"), "-1"});
                        checkAccepts();
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
        if(is_executive_permitted!=-1)
        ((MainActivity) getActivity()).requestQueue.add(admin_execRequest);
        if(is_technical_permitted!=-1)
        ((MainActivity) getActivity()).requestQueue.add(admin_techRequest);
        if(is_contactor_permitted!=-1)
        ((MainActivity) getActivity()).requestQueue.add(admin_contRequest);
    }
    private void setExec(JSONObject re){
        try {
            JSONObject object=re.getJSONObject("user");
            if(object.getString("id").equals(usrid)){
                me=true;
                meType="executive";
            }
            strings.set(0, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
            checkAccepts();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setTech(JSONObject re){
        try {
            JSONObject object=re.getJSONObject("user");
            Log.d("THISID",object.getString("id")+" "+object.getString("fullname"));
            if(object.getString("id").equals(usrid)){
                me=true;
                meType="technical";
            }
            strings.set(1, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
            checkAccepts();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getChief(){
        if(dpid.length()>0) {

            String url = ((MainActivity) getActivity()).MAIN_URL + "employees/?is_chief=true&department="+dpid;
            JsonArrayRequest chiefRequest = new JsonArrayRequest(Request.Method.GET, url , null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    Log.d("FUCKING DP",dpid+" "+response.length()+" "+response);
                    if (response.length() > 0) {
                        try {
                            setChief(response.getJSONObject(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Проблемы соеднения", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                    return headers;
                }
            };
            if(is_chief_permitted!=-1)
            ((MainActivity)getActivity()).requestQueue.add(chiefRequest);
        }
    }
    private void setChief(JSONObject re){
        try{
            JSONObject object=re.getJSONObject("user");
            if(object.getString("id").equals(usrid)){
                me=true;
                meType="chief";
            }
            strings.set(2, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
            checkAccepts();
        }
        catch (Exception e){

        }
    }
    private void checkAccepts(){
        try {
            acceptForms.clear();
            for (int j = 0; j < strings.size(); j++) {
                String[] i = strings.get(j);
                Log.d("stringCheck", i.length + " " + i.toString());
                if (i.length > 1) {
                    String status = "Ожидает ответа";
                    boolean a = false;
                    switch (j) {
                        case 0:
                            a = is_executive_permitted == 1;
                            break;
                        case 1:
                            a = is_technical_permitted == 1;
                            break;
                        case 2:
                            a = is_chief_permitted == 1;
                            break;
                        case 3:
                            a = is_contactor_permitted == 1;
                            break;
                        default:
                            a = is_respondent_permitted == 1;
                    }
                    if (a) {
                        status = "Подтвержден";
                    }
                    int index = ((MainActivity) getActivity()).dpids.indexOf(i[2]);
                    String dd = "";
                    if (index > -1) dd = ((MainActivity) getActivity()).departments.get(index);
                    AcceptForm acceptForm = new AcceptForm(i[0]
                            , dd
                            , ((MainActivity) getActivity()).positions.get(i[1])
                            , status
                            , a);
                    acceptForms.add(acceptForm);
                }
                acceptAdapter.notifyDataSetChanged();
                Log.d("sadadsasdasdas",superadmin + " "+acceptForms.size());
                if (acceptForms.size() > 0) {
                    acceptLayout.setVisibility(View.VISIBLE);
                }
                checkForAccept();
            }
            if (me) {
                checkForAccept();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void checkForAccept(){
        boolean a=false;
        switch (meType){
            case "respondent":
                a=is_respondent_permitted==1;
                break;
            case "contactor":
                a=is_contactor_permitted==1;
                break;
            case "executive":
                a=is_executive_permitted==1;
                break;
            case "technical":
                a=is_technical_permitted==1;
                break;
            case "chief":
                a=is_chief_permitted==1;
                break;
        }
        if(superadmin==0){
            a=false;
        }
        if(!a){
            butLayout.setVisibility(View.VISIBLE);
            switch (stat){
                case "WAITING":
                        cancelButText.setText("Закрыть задачу");
                        cancelButText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                close();
                            }
                        });
                        acceptButText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                permit();
                            }
                        });
                    break;
                case "PROCESSING":
                    cancelButText.setText("Закрыть задачу");
                    cancelButText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            close();
                        }
                    });
                    acceptButText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            permit();
                        }
                    });
                    break;
                case "TIMEOVER":
                    cancelButText.setText("Закрыть задачу");
                    cancelButText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            close();
                        }
                    });
                    acceptButText.setVisibility(View.GONE);
                    break;
                case "FAILED":
                    cancelButText.setText("Закрыть задачу");
                    cancelButText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            close();
                        }
                    });
                    acceptButText.setText("Подвердить продление");
                    acceptButText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            permit();
                        }
                    });
                    break;
                case "FINISHED":
                    butLayout.setVisibility(View.GONE);
                    break;

            }
        }
    }
    private void permit(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"tasks/"+id+"/permit/";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                Log.d("RESPpermit", response.length()+" "+response);
                Toast.makeText(getActivity(), "Задача подтверждена", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onBackPressed();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                NetworkResponse response=error.networkResponse;
                if(response.statusCode==400 || response.statusCode==500)
                    Toast.makeText(getActivity(), "Вы не можете подтвердить задачу", Toast.LENGTH_SHORT).show();
                else
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
    private void close(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"tasks/"+id+"/close/";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressLayout.setVisibility(View.GONE);
                Log.d("RESPpermit", response.length()+" "+response);
                Toast.makeText(getActivity(), "Задача закрыта", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                NetworkResponse response=error.networkResponse;
                if(response.statusCode==400)
                    Toast.makeText(getActivity(), "Вы не можете закрыть задачу", Toast.LENGTH_SHORT).show();
                    else
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
}
