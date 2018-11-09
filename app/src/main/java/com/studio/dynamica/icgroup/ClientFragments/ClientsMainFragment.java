package com.studio.dynamica.icgroup.ClientFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CityRadioAdapter;
import com.studio.dynamica.icgroup.Adapters.ClientsMainAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.dynamica.icgroup.Forms.ClientsMainForm;
import com.studio.dynamica.icgroup.Forms.ClientsPointForm;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.R;

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
public class ClientsMainFragment extends Fragment {

        RecyclerView recyclerView, cityRecycler;
        ProgressBar progressBar;
        SwipeRefreshLayout swipeRefreshLayout;
        ConstraintLayout mainObjectRegionLayout;
        TextView mainObjectRegionTextView;
        CityRadioAdapter cityRadioAdapter;
        List<String> cityNames;
        ImageView arrowCity;
    int city=-1;    HashMap<Integer,String> cities;
    boolean changed=false;
    List<ClientsMainForm> list;
    ClientsMainAdapter adapter;
    public ClientsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_clients_main, container, false);
        createViews(view);
        List<ClientsPointForm> pointForms=new ArrayList<>();
        pointForms.add(new ClientsPointForm("1","MEGA 1",3));
        pointForms.add(new ClientsPointForm("1","MEGA 2",4));
        pointForms.add(new ClientsPointForm("1","MEGA 3",5));
        ClientsMainForm form=new ClientsMainForm("1","ТОО Байлар","Байбек Мухамедиев",3);
        form.setPointForms(pointForms);
        ClientsMainForm form1=new ClientsMainForm("1","ТОО Байлар","Байбек Мухамедиев",3);
        form1.setPointForms(pointForms);
        list.add(form);
        list.add(form1);
        adapter=new ClientsMainAdapter(list);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(cityRecycler, LinearLayoutManager.VERTICAL);

        cityRadioAdapter=new CityRadioAdapter(cityNames);
        cityRecycler.setAdapter(cityRadioAdapter);
        getLocations();
        mainObjectRegionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showCityRec();
            }
        });

        return view;
    }
    private void showCityRec(){
        if(cityRecycler.getVisibility()==View.VISIBLE){
            cityRecycler.setVisibility(View.GONE);
            arrowCity.setImageResource(R.drawable.ic_arrowdown_green);
        }
        else{
            cityRecycler.setVisibility(View.VISIBLE);
            arrowCity.setImageResource(R.drawable.ic_arrowup_green);
        }
    }
    private void createViews(View view){

        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        cityRecycler=(RecyclerView) view.findViewById(R.id.cityRecycler);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        mainObjectRegionLayout=(ConstraintLayout)view.findViewById(R.id.mainObjectRegionLayout);
        mainObjectRegionTextView=(TextView)view.findViewById(R.id.mainObjectRegionTextView);
        arrowCity=(ImageView)view.findViewById(R.id.arrowCity);

        cities=new HashMap<>();
        cityNames=new ArrayList<>();
        list=new ArrayList<>();
    }
    private void getLocations(){
        progressBar.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"locations/";
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                cities.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        cities.put(jsonObject.getInt("id"),jsonObject.getString("name"));
                    } catch (Exception e) {
                        break;
                    }
                    setCities();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setCities(){
        List<String> values=new ArrayList<>();
        List<Integer> keys=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(final Integer j:cities.keySet()){
            keys.add(j);
            values.add(cities.get(j));
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    city=j;
                    changed=true;
                    mainObjectRegionTextView.setText(cities.get(j));
                    onSwipeRefresh();
                }
            });
        }
       cityNames.clear();
        cityNames.addAll(values);
        cityRadioAdapter.setListeners(listeners);
        cityRadioAdapter.notifyDataSetChanged();
        if(values.size()>0)
        mainObjectRegionTextView.setText(values.get(0));
    }
    private void onSwipeRefresh(){
        swipeRefreshLayout.setRefreshing(false);

    }
    /*private void checkData(){
        if(new Date().getTime()-time>120000 || changed){
            list.clear();
            adapter.notifyDataSetChanged();
            String l="points/?";
            if(city>-1){
                l+="location="+city;
            }
            Log.d("url",l);
            //getRequest(l);
            if(cities.size()<1){
                getLocations();
            }
        }
        else{
            List<MainObjectRowForm> rowForms=new ArrayList<MainObjectRowForm>();
            rowForms.addAll(list);
            setClients(rowForms);
        }
    }*/
    private void setClients(){

    }
}
