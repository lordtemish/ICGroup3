package com.studio.crm.icgroup.ObjectFragments;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.CityRadioAdapter;
import com.studio.crm.icgroup.Adapters.MainObjectAdapter;
import com.studio.crm.icgroup.Adapters.TasktypeAdapter;
import com.studio.crm.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.crm.icgroup.Forms.MainObjectRowForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectFragment extends Fragment {
    ProgressBar progressBar;
    RadioButton searchRadio;
    MainObjectAdapter mainObjectAdapter;CityRadioAdapter cityRadioAdapter;
    RecyclerView mainObjectRecycle, cityRecycler , tasktypeRecycler;
    ConstraintLayout RegionLayout;
    ImageView  searchImage;
    TextView RegionTextView;
    ArrayList<MainObjectRowForm> list;
    TextView mainObjectTitle;
    SwipeRefreshLayout refreshLayout;
    View.OnClickListener goneClick;
    HashMap<String,String> kindMap;
    HashMap<Integer,String> cities;
    List<String> cityNames, tasktypeNames, kindList;
    TasktypeAdapter taskTypeAdapter;
    EditText nameEditText;
    LinearLayout cityLayout, nameLayout;
    boolean client, all=true, search=true;
    long time;
        boolean changed=false;
    int city=1;
    String kind="";
    public MainObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        createViews(view);
        mainObjectTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-It.ttf"));


         list=new ArrayList<>();
        mainObjectAdapter=new MainObjectAdapter(list,getActivity());
        mainObjectAdapter.setClient(client);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        mainObjectRecycle.setLayoutManager(mLayoutManager);
        mainObjectRecycle.setItemAnimator(new DefaultItemAnimator());
        mainObjectRecycle.setAdapter(mainObjectAdapter);

        cityRadioAdapter=new CityRadioAdapter(cityNames);
        cityRadioAdapter.setListeners(new ArrayList<View.OnClickListener>());
        cityRecycler.setAdapter(cityRadioAdapter);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(cityRecycler,LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(tasktypeRecycler,LinearLayoutManager.HORIZONTAL);
        taskTypeAdapter=new TasktypeAdapter(tasktypeNames);
        tasktypeRecycler.setAdapter(taskTypeAdapter);


        RegionTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));


        goneClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };


        RegionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCities();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
       checkRoles();

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

        return view;
    }
    private void checkRoles(){
        String role=((MainActivity)getActivity()).role;
        if(role.equals("CLIENT") || role.equals("POINT") || role.equals("PRODUCTION_CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO"))
            all=false;
        else
            all=true;
        if(all) {
            tasktypeRecycler.setVisibility(View.VISIBLE);
            cityLayout.setVisibility(View.VISIBLE);
            showCities();
            setKinds();
        }
        else{
            tasktypeRecycler.setVisibility(View.GONE);
            cityLayout.setVisibility(View.GONE);
            RegionLayout.setVisibility(View.GONE);
            onSwipeRefresh();
        }
    }
    private void showCities(){
        if(cityRecycler.getVisibility()==View.VISIBLE){
            cityRecycler.setVisibility(View.GONE);

        }
        else{
            cityRecycler.setVisibility(View.VISIBLE);

        }
    }
    private void checkData(){
        if(((new Date().getTime()-time>120000 || changed) && all) || (all && search) ){
            Log.d("checkData",all+"");
            if(all) {
                list.clear();
                mainObjectAdapter.notifyDataSetChanged();
                String l = "points/?";
                if (kind.length() > 0) {
                    l += "kind=" + kind;
                    if (city > -1) {
                        l += "&";
                    }
                }
                if (city > -1) {
                    l += "location=" + city;
                }
                if (cityNames.size() < 1) {
                    getRequest("locations/");
                }
                Log.d("url", l);
                getRequest(l);
            }
        }
        else{
            if(all) {
                List<MainObjectRowForm> rowForms = new ArrayList<MainObjectRowForm>();
                rowForms.addAll(list);
                setPoints(rowForms);
            }
            else{
                String url="points/?";
                if(nameEditText.getText().length()>0){
                    url+="name="+nameEditText.getText();
                }
                getRequest(url);
            }
        }
    }

    private void onSwipeRefresh(){
        refreshLayout.setRefreshing(false);

        checkData();
    }
    public void setPoints(List<MainObjectRowForm> list){
        this.list.clear();
        this.list.addAll(list);
        /*
        this.list.add(new MainObjectRowForm("7894561651","Temirlan",80,10,9,7));
        */
        mainObjectAdapter.notifyDataSetChanged();

        changed=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkData();
        checkRoles();
    }

    public void getRequest(final String url1){

        String url=((MainActivity)getActivity()).MAIN_URL+url1;
        if(search && nameEditText.getText().length()>0) url+="&name="+nameEditText.getText();
        progressBar.setVisibility(View.VISIBLE);
        Log.d("TaskURL",url);
        JsonArrayRequest getRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        progressBar.setVisibility(View.GONE);
                        Log.d("Respone", response.length()+" \n"+response.toString());
                        if(url1.contains("points")) {
                            List<MainObjectRowForm> rowForms = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    long resu=Math.round(jsonObject.getDouble("result_rate")*100);
                                    int result=Integer.parseInt(resu+"");
                                    MainObjectRowForm form=new MainObjectRowForm(jsonObject.getInt("id") + "", jsonObject.getString("name"),result , jsonObject.getInt("workers_count"), jsonObject.getInt("complaints_count"), jsonObject.getInt("tasks_count"));
                                    form.setLocation(jsonObject.getJSONObject("location").getInt("id"));
                                    form.setCity(cities.get(form.getLocation()));
                                    rowForms.add(form);
                                    time=new Date().getTime();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            setPoints(rowForms);
                        }
                        else{
                            showCities();
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.d("Respone", error.getMessage()+"");
                        setPoints(new ArrayList<MainObjectRowForm>());
                        Toast.makeText(getContext(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }
        ){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        (((MainActivity)getActivity()).requestQueue).add(getRequest);
    }

    private void setSearch(){
        search=!search;
        searchRadio.setChecked(search);
        if(search){
            nameLayout.setVisibility(View.VISIBLE);
        }
        else{
            nameLayout.setVisibility(View.GONE);
        }
    }
    private void createViews(View view){
        client=((MainActivity) getActivity()).client;

        searchRadio=(RadioButton) view.findViewById(R.id.searchRadio);
        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        mainObjectRecycle=(RecyclerView) view.findViewById(R.id.mainObjectRecycle);
        cityRecycler=(RecyclerView) view.findViewById(R.id.cityRecycler);
        tasktypeRecycler=(RecyclerView) view.findViewById(R.id.tasktypeRecycler);

        RegionLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRegionLayout);
        cityLayout=(LinearLayout) view.findViewById(R.id.cityLayout);
        nameLayout=(LinearLayout) view.findViewById(R.id.nameLayout);
        nameEditText=(EditText) view.findViewById(R.id.nameEditText);

        RegionTextView=(TextView) view.findViewById(R.id.mainObjectRegionTextView);

        searchImage=(ImageView) view.findViewById(R.id.searchImage);

        searchRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSearch();
            }
        });

        kindMap=new HashMap<>();
        kindList=new ArrayList<>();
        cities=new HashMap<>();
        kindMap.put("SHOPPING_CENTER","Торговый центр");kindList.add("SHOPPING_CENTER");
        kindMap.put("BUSINESS_CENTER","Бизнес центр");kindList.add("BUSINESS_CENTER");
        kindMap.put("SMALL_OBJECT","Малый объект");kindList.add("SMALL_OBJECT");
        kindMap.put("MIDDLE_OBJECT","Средний объект");kindList.add("MIDDLE_OBJECT");
        kindMap.put("BIG_OBJECT","Большой объект");kindList.add("BIG_OBJECT");
        kindMap.put("INDUSTRIAL_BASE","Пром.\nбаза");kindList.add("INDUSTRIAL_BASE");

        List<String> cities=new ArrayList<>();
        cityNames=new ArrayList<>();
        tasktypeNames=new ArrayList<>();
        cities.add("Алматы");
        cities.add("Астана");
        cities.add("Караганды");
        cities.add("Кокшетау");
    }
    private void setVal(String s, boolean city){
        if(city){
            RegionTextView.setText(s);
        }
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
                    setVal(cities.get(j),true);
                    onSwipeRefresh();

                }
            });
        }
        cityRadioAdapter.setListeners(listeners);
        cityNames.clear();
        cityNames.addAll(values);
        setCityNames();
        if(keys.size()>0){
            city=keys.get(0);
        }
        //if(cityNames.size()>city)
        setVal(cities.get(city),true);
    }
    private void setKinds(){
        final List<String> values=new ArrayList<>();
        List<String> keys=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(final String j:kindList){
            keys.add(j);
            values.add(kindMap.get(j));
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kind=j;
                    changed=true;
                    onSwipeRefresh();
                }
            });
        }
        if(keys.size()>0){
            kind=keys.get(0);
        }
        tasktypeNames.clear();
        tasktypeNames.addAll(values);
        taskTypeAdapter.setListeners(listeners);
        taskTypeAdapter.notifyDataSetChanged();
        onSwipeRefresh();
    }
    private void setCityNames(){
        cityNames.clear();
        for(Integer i:cities.keySet()){
            cityNames.add(cities.get(i));
        }

        cityRadioAdapter.notifyDataSetChanged();
    }
}
