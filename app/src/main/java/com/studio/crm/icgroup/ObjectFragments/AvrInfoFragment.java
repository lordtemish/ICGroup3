package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AvrPositionsAdapter;
import com.studio.crm.icgroup.Adapters.ServicesAdapter;
import com.studio.crm.icgroup.Forms.ServiceForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvrInfoFragment extends Fragment {

    String id="";
    List<TextView> topTexts;

    FrameLayout progressLayout;
    boolean permitted=true;
    RecyclerView avrPosRecyclerView,tasksRecyclerView;
    List<JSONObject> positionList;
    List<ServiceForm> serviceList;
    AvrPositionsAdapter positionsAdapter;
    ServicesAdapter servicesAdapter;
    public AvrInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");

        View view=inflater.inflate(R.layout.fragment_avr_info, container, false);
        createView(view);
        getRequest();
        return view;
    }
    private void createView(View view){
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);

        avrPosRecyclerView=(RecyclerView)view.findViewById(R.id.avrPosRecyclerView);
        tasksRecyclerView=(RecyclerView)view.findViewById(R.id.tasksRecyclerView);

        int[] topIds={R.id.dateOpenTextView,R.id.averageOpenTextView};
        topTexts=new ArrayList<>();
        topTexts.add((TextView)view.findViewById(topIds[0]));topTexts.add((TextView)view.findViewById(topIds[1]));

        ((MainActivity)getActivity()).setRecyclerViewOrientation(avrPosRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(tasksRecyclerView, LinearLayoutManager.VERTICAL);

        positionList=new ArrayList<>();serviceList=new ArrayList<>();
        positionsAdapter=new AvrPositionsAdapter(positionList);
        servicesAdapter=new ServicesAdapter(serviceList);
        avrPosRecyclerView.setAdapter(positionsAdapter);
        tasksRecyclerView.setAdapter(servicesAdapter);
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"controls/"+id;
        Log.d("AVR URL", url);
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
                Toast.makeText(getActivity(), "NO CONNECTION", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
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
        ((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setInfo(JSONObject response){
        try {
            String created_at=response.getString("created_at");
            double rate_avg=response.getDouble("rate_avg");
            int rate=Integer.parseInt(Math.round(rate_avg)+"");

            String created=((MainActivity)getActivity()).getdate(created_at);
            topTexts.get(0).setText(created.substring(0,15));
            topTexts.get(1).setText(rate+"");

            List<JSONObject> objects=new ArrayList<>();
            JSONArray positions=response.getJSONArray("avr_positions");
            for(int i=0;i<positions.length();i++){
                JSONObject object=positions.getJSONObject(i);
                objects.add(object);
            }
            positionList.addAll(objects);
            positionsAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
