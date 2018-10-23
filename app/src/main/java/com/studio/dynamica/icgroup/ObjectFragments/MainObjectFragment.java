package com.studio.dynamica.icgroup.ObjectFragments;


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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MainObjectAdapter;
import com.studio.dynamica.icgroup.ExtraFragments.MainObjectSetInfoFragment;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectFragment extends Fragment {
    ProgressBar progressBar;
    MainObjectAdapter mainObjectAdapter;
    RecyclerView mainObjectRecycle;
    ConstraintLayout RegionLayout;
    TextView RegionTextView;
    ConstraintLayout ObjectTypeLayout;
    TextView ObjectTypeTextView;
    ArrayList<MainObjectRowForm> list;
    TextView mainObjectTitle;
    SwipeRefreshLayout refreshLayout;
    MainObjectSetInfoFragment setInfoFragment;
    View.OnClickListener goneClick;
    HashMap<String,String> kindMap;
    HashMap<Integer,String> cities;
    boolean client;
    long time;
        boolean changed=false;
    int city=-1;
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
       /* list.add(new MainObjectRowForm("125","ТРЦ Moskva metropoliten",55,4,25,21));
        list.add(new MainObjectRowForm("002","Mega SILKWAY",2,1,5,21));
        list.add(new MainObjectRowForm("022","Mega SILKWAY",100,2,15,2));
        list.add(new MainObjectRowForm("042","Mega SILKWAY",88,3,35,23));
        list.add(new MainObjectRowForm("005","Mega SILKWAY",14,4,15,2));*/
        mainObjectAdapter=new MainObjectAdapter(list,getActivity());
        mainObjectAdapter.setClient(client);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        mainObjectRecycle.setLayoutManager(mLayoutManager);
        mainObjectRecycle.setItemAnimator(new DefaultItemAnimator());
        mainObjectRecycle.setAdapter(mainObjectAdapter);


        RegionTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));


        ObjectTypeTextView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));

        goneClick=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.GONE);
                ((MainActivity)getActivity()).setPressable(true,null);
            }
        };
        setInfoFragment.setWholeLayoutList(goneClick);


        RegionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.VISIBLE);
                setCities();
                ((MainActivity) getActivity()).setPressable(false,goneClick);
            }
        });
        ObjectTypeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.VISIBLE);
                setKinds();
                ((MainActivity) getActivity()).setPressable(false,goneClick);
            }
        });

        getRequest("points");
        getRequest("locations/");
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onSwipeRefresh();
            }
        });
        return view;
    }
    private void checkData(){
        if(new Date().getTime()-time>120000 || changed){
            list.clear();
            mainObjectAdapter.notifyDataSetChanged();
            String l="points/?";
            if(kind.length()>0){
                l+="kind="+kind;
                if(city>-1){
                    l+="&";
                }
            }
            if(city>-1){
                l+="location="+city;
            }
            Log.d("url",l);
            getRequest(l);
            if(cities.size()<1){
                getRequest("locations/");
            }
        }
        else{
            List<MainObjectRowForm> rowForms=new ArrayList<MainObjectRowForm>();
            rowForms.addAll(list);
            setPoints(rowForms);
        }
    }

    private void onSwipeRefresh(){
        refreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.VISIBLE);
        checkData();
    }
    public void setPoints(List<MainObjectRowForm> list){
        this.list.clear();
        this.list.addAll(list);
        /*
        this.list.add(new MainObjectRowForm("7894561651","Temirlan",80,10,9,7));
        */
        mainObjectAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        changed=false;
    }
    public void getRequest(final String url1){
        final String url=((MainActivity)getActivity()).MAIN_URL+url1;

        JsonArrayRequest getRequest= new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response){
                        Log.d("Respone", response.length()+" \n"+response.toString());
                        if(url1.contains("points")) {
                            List<MainObjectRowForm> rowForms = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    rowForms.add(new MainObjectRowForm(jsonObject.getInt("id") + "", jsonObject.getString("name"), jsonObject.getInt("result_rate"), jsonObject.getInt("workers_count"), jsonObject.getInt("complaints_count"), jsonObject.getInt("tasks_count")));
                                    time=new Date().getTime();
                                } catch (Exception e) {
                                    break;
                                }
                            }
                            setPoints(rowForms);
                        }
                        else{
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    cities.clear();
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
                        Log.d("Respone", error.getMessage()+"");
                        setPoints(new ArrayList<MainObjectRowForm>());
                    }
                }
        );
        (((MainActivity)getActivity()).requestQueue).add(getRequest);
    }


    private void createViews(View view){
        client=((MainActivity) getActivity()).client;

        progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        mainObjectRecycle=(RecyclerView) view.findViewById(R.id.mainObjectRecycle);

        RegionLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectRegionLayout);
        RegionTextView=(TextView) view.findViewById(R.id.mainObjectRegionTextView);

        ObjectTypeLayout=(ConstraintLayout) view.findViewById(R.id.mainObjectTypeLayout);
        ObjectTypeTextView=(TextView) view.findViewById(R.id.mainObjectTypeTextView);
        setInfoFragment=(MainObjectSetInfoFragment) view.findViewById(R.id.setInfoFragment);

        kindMap=new HashMap<>();
        cities=new HashMap<>();
        kindMap.put("SHOPPING_CENTER","Торговый центр");
        kindMap.put("BUSINESS_CENTER","Бизнес центр");
        kindMap.put("SMALL_OBJECT","Малый объект");
        kindMap.put("INDUSTRIAL_BASE","Промышленная база");

        setInfoFragment.setVisibility(View.GONE);
        List<String> cities=new ArrayList<>();
        cities.add("Алматы");
        cities.add("Астана");
        cities.add("Караганды");
        cities.add("Кокшетау");
        setInfoFragment.setList(cities);
    }
    private void setVal(String s, boolean city){
        if(city){
            RegionTextView.setText(s);
        }
        else{
            ObjectTypeTextView.setText(s);
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
                    ((MainActivity) getActivity()).onBackPressed();
                    onSwipeRefresh();
                }
            });
        }
        setInfoFragment.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city=-1;
                changed=true;
                setVal("Регион",true);
                ((MainActivity) getActivity()).onBackPressed();
                onSwipeRefresh();
            }
        });
        setInfoFragment.setList(values);
        setInfoFragment.setLinsteners(listeners);
    }
    private void setKinds(){
        final List<String> values=new ArrayList<>();
        List<String> keys=new ArrayList<>();
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(final String j:kindMap.keySet()){
            keys.add(j);
            values.add(kindMap.get(j));
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    kind=j;
                    changed=true;
                    setVal(kindMap.get(j),false);
                    ((MainActivity) getActivity()).onBackPressed();
                    onSwipeRefresh();
                }
            });
        }
        setInfoFragment.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kind="";
                changed=true;
                setVal("Тип объекта",false);
                ((MainActivity) getActivity()).onBackPressed();
                onSwipeRefresh();
            }
        });
        setInfoFragment.setList(values);
        setInfoFragment.setLinsteners(listeners);
    }

}
