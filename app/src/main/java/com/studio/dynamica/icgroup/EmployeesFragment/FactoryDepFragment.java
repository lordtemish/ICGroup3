package com.studio.dynamica.icgroup.EmployeesFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
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
public class FactoryDepFragment extends Fragment {
    MainEmployeesAdapter adapter;
    RecyclerView recyclerView;
    LinearLayout cityLayout;
    TextView mainObjectTitle, cityTextLayout;
    List<MainEmployeeForm> forms;
    FrameLayout progressLayout;
    MainObjectSetInfoFragment setInfoFragment;
    String id, name, rlid="";
    boolean adm=false;
    public FactoryDepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        name=getArguments().getString("name");
        adm=getArguments().getBoolean("adm");
        View view=inflater.inflate(R.layout.fragment_factory_dep, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        forms=new ArrayList<>();
        adapter=new MainEmployeesAdapter(forms);
        adapter.setAdm(adm);
        recyclerView.setAdapter(adapter);
        mainObjectTitle.setText(name);
        ((MainActivity)getActivity()).setType("demibold", cityTextLayout);
        ((MainActivity)getActivity()).setType("light", mainObjectTitle);
        setInfoFragment.setVisibility(View.GONE);
        setInfoFragment.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.GONE);
                rlid="";
                setDp();
            }
        });
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=0;i<((MainActivity)getActivity()).rlids.size();i++){
            final String a=((MainActivity)getActivity()).rlids.get(i);
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setInfoFragment.setVisibility(View.GONE);
                    rlid=a;
                    setDp();
                }
            });
        }
        setInfoFragment.setWholeLayoutList(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.GONE);
            }
        });
        setInfoFragment.setLinsteners(listeners);
        setInfoFragment.setList(((MainActivity)getActivity()).roles);
        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoFragment.setVisibility(View.VISIBLE);
            }
        });
        setDp();
        return view;
    }
    private void setDp(){
        if(rlid.length()>0) {
            cityTextLayout.setText(((MainActivity) getActivity()).positions.get(rlid));
        }
        else{
            cityTextLayout.setText("Выберите должность");
        }
        getRequest();
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"employees/?department="+id;
        if(rlid.length()>0){
            url+="&user__role="+rlid;
        }
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
                JSONObject user=object.getJSONObject("user");
                MainEmployeeForm form=new MainEmployeeForm();
                String name=user.getString("fullname");
                int rate=Integer.parseInt(""+Math.round(object.getDouble("performance_rate")*100));
                int result_rate=Integer.parseInt(""+Math.round(object.getDouble("result_rate")*100));
                form.setName(name);form.setRate(result_rate);form.setResult_rate(rate);
                form.setId(object.getString("id"));
                String avatar=user.getString("avatar")+"";
                String pos=user.getString("role");
                String position=((MainActivity)getActivity()).positions.get(pos);
                form.setPosition(position);
                if(!avatar.equals("null")){

                }
                forms.add(form);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createViews(View view){
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        cityTextLayout=(TextView) view.findViewById(R.id.cityTextLayout);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        cityLayout=(LinearLayout) view.findViewById(R.id.cityLayout);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        setInfoFragment=(MainObjectSetInfoFragment)view.findViewById(R.id.setInfoFragment);
    }
}
