package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.google.gson.Gson;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.WorkScheduleAdapter;
import com.studio.crm.icgroup.Forms.ShiftForm;
import com.studio.crm.icgroup.Forms.WorkScheduleForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectWorkScheduleFragment extends Fragment {
    WorkScheduleAdapter adapter;
    List<WorkScheduleForm> list;
    RecyclerView recyclerView;
    FrameLayout progressLayout;
    TextView schCleaningTextView;

    private String id;
    public PassportObjectWorkScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_passport_object_work_schedule, container, false);
        createViews(view);

        list=new ArrayList<>();
        list.add(new WorkScheduleForm("Будние дни","Пн-Пт(ежедневно по 2 смены)",new ArrayList<ShiftForm>()));
        list.add(new WorkScheduleForm("Выходные дни","Сб-Вс(ежедневно по 2 смены)",new ArrayList<ShiftForm>()));
         adapter=new WorkScheduleAdapter(list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        schCleaningTextView.setTypeface(((MainActivity)getActivity()).getTypeFace("light"));
        getRequest("schedules/?point="+id);
        return view;
    }
    private void setInfo(JSONArray array){
        progressLayout.setVisibility(View.GONE);
        List<WorkScheduleForm> list=new ArrayList<>();
        List<ShiftForm> weekForms=new ArrayList<>(), weekendForms=new ArrayList<>();
        for(int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                ShiftForm form=new ShiftForm(object);
                int day=object.getInt("day");
                if(day<=5){
                    weekForms.add(form);
                }
                else{
                    weekendForms.add(form);
                }
            }
            catch (Exception e){
                e.printStackTrace();
                continue;
            }
        }
        WorkScheduleForm week=new WorkScheduleForm("Будние дни","Пн-Пт", weekForms), weekend=new WorkScheduleForm("Выходные дни","Сб-Вс", weekendForms);
        if(weekForms.size()>0)list.add(week);
        if(weekendForms.size()>0)list.add(weekend);
        Log.d("PASOBWOSCHFR weeks", new Gson().toJson(list)+"");
        this.list.clear();this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }
    private void getRequest(String url1){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+url1;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("PaObWoScFr REQUEST",response.toString());
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void createViews(View view){
        id=getArguments().getString("id");
        schCleaningTextView=(TextView) view.findViewById(R.id.schCleaningTextView);
        recyclerView=(RecyclerView) view.findViewById(R.id.PassportObjectSchRecycler);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
    }

}
