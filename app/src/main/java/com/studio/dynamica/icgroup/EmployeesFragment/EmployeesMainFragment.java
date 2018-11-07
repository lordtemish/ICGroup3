package com.studio.dynamica.icgroup.EmployeesFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CityRadioAdapter;
import com.studio.dynamica.icgroup.Adapters.EmployeeAdapter;
import com.studio.dynamica.icgroup.Adapters.MainEmployeesAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.dynamica.icgroup.Forms.EmployeeForm;
import com.studio.dynamica.icgroup.Forms.MainEmployeeForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployeesMainFragment extends Fragment {
    EmployeeAdapter adapter;CityRadioAdapter cityRadioAdapter;
    TextView cityTextLayout;
    List<EmployeeForm> forms;
    FrameLayout progressLayout;
    LinearLayout cityLayout;
    RecyclerView recyclerView, cityRecycler;
    ImageView arrowImageView;
    public EmployeesMainFragment() {
        // Required empty public constructor
    }
    HashMap<Integer, String> cities;
    List<String> cit;
    List<String> cityNames;
    int city=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_employees_main, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setType("demibold",cityTextLayout);
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showCity();
            }
        });
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView,LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(cityRecycler,LinearLayoutManager.VERTICAL);
        cityRadioAdapter=new CityRadioAdapter(cityNames);
        cityRecycler.setAdapter(cityRadioAdapter);
        getCitites();

        adapter=new EmployeeAdapter(forms);
        recyclerView.setAdapter(adapter);
        showCity();
        return view;
    }
    private void showCity(){
        if(cityRecycler.getVisibility()==View.VISIBLE){
            cityRecycler.setVisibility(View.GONE);
            arrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
        }
        else{
            cityRecycler.setVisibility(View.VISIBLE);
            arrowImageView.setImageResource(R.drawable.ic_arrowup_green);
        }
    }
    private void getCitites() {
        progressLayout.setVisibility(View.VISIBLE);
        String url = ((MainActivity) getActivity()).MAIN_URL + "locations/";
        JsonArrayRequest j = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        cities.put(jsonObject.getInt("id"), jsonObject.getString("name"));
                    } catch (Exception e) {
                        break;
                    }
                    setCities();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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
        ((MainActivity) getActivity()).requestQueue.add(j);
    }
    private void setCities(){
       cit=new ArrayList<>();
       cityNames.clear();
       List<View.OnClickListener> listeners=new ArrayList<>();
       for(final Integer i:cities.keySet()){
           cit.add(cities.get(i));
           listeners.add(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   city=i;
                   checkCity();
               }
           });
       }
       cityNames.addAll(cit);
       cityRadioAdapter.setListeners(listeners);
       cityRadioAdapter.notifyDataSetChanged();
       checkCity();
    }
    private void checkCity(){
        if(cities.size()==0){
            cityTextLayout.setText("Выберите город");
        }
        else {
            cityTextLayout.setText(cities.get(city));
            getRequest();
        }

    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"departments/?location="+city;
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
        }){@Override
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
                    EmployeeForm form=new EmployeeForm(object.getString("id"),object.getString("name"),object.getInt("employees_count"),Integer.parseInt(""+Math.round(object.getDouble("performance_rate")*100)));
                    forms.add(form);
                }
                adapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(city==-1){
                cityTextLayout.setText("Выберите город");
            }
            else{
                cityTextLayout.setText(cities.get(city));
            }
        }
    private void createViews(View view){
        forms=new ArrayList<>();
        cityLayout=(LinearLayout)view.findViewById(R.id.cityLayout);
        cityTextLayout=(TextView) view.findViewById(R.id.cityTextLayout);
        cities=new HashMap<>();
        cityNames=new ArrayList<>();
        cityRecycler=(RecyclerView) view.findViewById(R.id.cityRecycler);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        arrowImageView=(ImageView)view.findViewById(R.id.arrowImageView);
    }
}
