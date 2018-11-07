package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CommentAdapter;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListOPUFragment extends Fragment {
    RecyclerView commentsRecycler;
    Spinner spinner;
    List<CommentForm> commentForms, newCommentsList, allCommentsList;
    CommentAdapter commentAdapter;
    LinearLayout newComments,allComments;
    TextView newCommentTextView,allCommentTextView, mainObjectTitle, nameTextView, positionTextView, attendanceTextView,dateTextView, PercentageTextView, jalobaTextView, employeeChangeTextView, emplChangeButton, emplDropTextView;
    FrameLayout newCommentFrame, allCommentFrame, progressLayout;
    ProgressBar ProgressBar;
    ImageView circlePhoneImageView;
String id, name, phone;
    public PassportObjectInfoListOPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        name=getArguments().getString("name");
        phone=getArguments().getString("phone");
        View view=inflater.inflate(R.layout.fragment_passport_object_info_list_opu, container, false);
        createViews(view);
        setTypeFace(view.getContext());
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        nameTextView.setText(name);
        circlePhoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).callPhone(phone);
            }
        });
        commentsRecycler.setLayoutManager(mLayoutManager);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        commentForms=new ArrayList<>();

        newCommentsList=new ArrayList<>();
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));

        newComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setComment(false);
            }
        });


        allCommentsList=new ArrayList<>();

        commentForms.addAll(newCommentsList);

        allComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setComment(true);
            }
        });

        emplDropTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        commentAdapter=new CommentAdapter(commentForms);
        commentsRecycler.setAdapter(commentAdapter);
        String[] spinnerList={"Выберите Сотрудника","asdasd","asdasd"};
        spinner=(Spinner) view.findViewById(R.id.employeeChangeSpinner);

        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,spinnerList){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

                return v;
        }
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
            ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));

            return v;

            }
        };
        FrameLayout spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinner();
            }
        });
        spinner.setAdapter(spinnerAdapter);
        getRequest();
        return view;
    }
    private void setTypeFace(Context context){
        mainObjectTitle.setTypeface(((MainActivity) context).getTypeFace("it"));
        nameTextView.setTypeface(((MainActivity) context).getTypeFace("bold"));
        positionTextView.setTypeface(((MainActivity) context).getTypeFace("light"));
        attendanceTextView.setTypeface(((MainActivity) context).getTypeFace("regular"));
        jalobaTextView.setTypeface(((MainActivity) context).getTypeFace("regular"));
        employeeChangeTextView.setTypeface(((MainActivity) context).getTypeFace("regular"));
        dateTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        PercentageTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        allCommentTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        newCommentTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        emplChangeButton.setTypeface(((MainActivity) context).getTypeFace("demibold"));
        emplDropTextView.setTypeface(((MainActivity) context).getTypeFace("demibold"));
    }
    private void createViews(View view){
        employeeChangeTextView=(TextView) view.findViewById(R.id.employeeChangeTextView);
        allCommentTextView=(TextView) view.findViewById(R.id.allCommentsTextView);
        newCommentTextView=(TextView) view.findViewById(R.id.newCommentTextView);
        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        newComments=(LinearLayout) view.findViewById(R.id.newCommentLinearLayout);
        newCommentFrame=(FrameLayout) view.findViewById(R.id.newCommentFrameLayout);
        allComments=(LinearLayout) view.findViewById(R.id.allCommentsLinearLayout);
        allCommentFrame=(FrameLayout) view.findViewById(R.id.allCommentsFrameLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        nameTextView=(TextView) view.findViewById(R.id.nameTextView);
        positionTextView=(TextView) view.findViewById(R.id.positionTextView);
        attendanceTextView=(TextView) view.findViewById(R.id.attendaceTextView);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        PercentageTextView=(TextView) view.findViewById(R.id.PercentageTextView);
        jalobaTextView=(TextView) view.findViewById(R.id.jalobaTextView);
        emplChangeButton=(TextView) view.findViewById(R.id.employeeChangeButton);
        emplDropTextView=(TextView) view.findViewById(R.id.employeeDropTextView);
        circlePhoneImageView=(ImageView) view.findViewById(R.id.circlePhoneImageView);
        ProgressBar=(ProgressBar) view.findViewById(R.id.ProgressBar);
    }


    public void setComment(boolean all){
        clearComments();
        if(all){
            allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            allCommentFrame.setVisibility(View.VISIBLE);
            ChangeComments(allCommentsList);
        }
        else{
            newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newCommentFrame.setVisibility(View.VISIBLE);
            ChangeComments(newCommentsList);
        }

    }
    public void clearComments(){
        newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        newCommentFrame.setVisibility(View.GONE);
        allCommentFrame.setVisibility(View.GONE);

    }
    public void ChangeComments(List<CommentForm> list){
            commentForms.clear();
            commentForms.addAll(list);
            commentAdapter.notifyDataSetChanged();
    }
    public void showSpinner(){
        spinner.performClick();
    }


    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"workers/"+id+"/";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "CONNECTION_TROUBLE", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setInfo(JSONObject object){
        try{
            Double dos=object.getDouble("attendance_rate");
            int perfomance=(int)Math.round(dos*100);
            ProgressBar.setProgress(perfomance);
            PercentageTextView.setText(perfomance+"%");
            Date date=new Date();
            Calendar calendar=Calendar.getInstance();calendar.setTime(date);
            String dateString="";
            dateString+=((MainActivity)getActivity()).data[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR);
            Log.d(dateString,dateString);
            dateTextView.setText(dateString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void delete(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"workers/"+id+"/";
        StringRequest ob=new StringRequest(Request.Method.DELETE, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "ОПУ уволен", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(ob);
    }
}
