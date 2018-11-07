package com.studio.dynamica.icgroup.NotificationFragments;


import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.NotificationAdapter;
import com.studio.dynamica.icgroup.Forms.NotificationForm;
import com.studio.dynamica.icgroup.R;

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
public class NotificationFragment extends Fragment {
    RecyclerView notificationRecyclerView;
    List<NotificationForm> forms, main, archive;
    NotificationAdapter adapter;
    TextView newNotificationsTextView, archiveNotificationsTextView;
    FrameLayout progressLayout;
    int page=0;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);

        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(notificationRecyclerView, LinearLayoutManager.VERTICAL);
       forms=new ArrayList<>();main=new ArrayList<>();archive=new ArrayList<>();
        forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));
        adapter=new NotificationAdapter(forms);
        notificationRecyclerView.setAdapter(adapter);

        newNotificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPage(0);
            }
        });
        archiveNotificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPage(1);
            }
        });
        getRequest();
        return view;
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"notices/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONArray array){
        try{
            forms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String objectid=object.getString("object_id");
                JSONObject user=object.getJSONObject("user");
                JSONObject content_type=object.getJSONObject("content_type");
                String title=object.getString("title");

                String model=content_type.getString("model");
                String authorName=object.getString("author_fullname");

                String role=object.getString("author_role");
                String position=((MainActivity)getActivity()).positions.get(role);
                Boolean arc=object.getBoolean("is_archived");
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-5);

                String type="Задача";

                NotificationForm form=new NotificationForm(type,created,title,authorName, position);
                form.setId(objectid);
                form.setObjectType(model);
                form.setIs_archive(arc);
                if(arc){
                    archive.add(form);
                }
                else{
                    main.add(form);
                }
            }
            checkPage();
        }
        catch (JSONException e){

        }
    }
    private void createViews(View view){
        notificationRecyclerView=(RecyclerView) view.findViewById(R.id.notificationsRecyclerView);
        archiveNotificationsTextView=(TextView) view.findViewById(R.id.archiveNotificationsTextView);
        newNotificationsTextView=(TextView) view.findViewById(R.id.newNotificationsTextView);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
    }
    private void checkPage(){
        forms.clear();
        if(page==0){
            forms.addAll(main);
            newNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            archiveNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
        else{
            forms.addAll(archive);
            archiveNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newNotificationsTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
        adapter.notifyDataSetChanged();
    }
    private void setPage(int a){
        page=a;
        checkPage();
    }
}
