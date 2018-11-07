package com.studio.dynamica.icgroup.EmployeesFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.MiniObjectAdapter;
import com.studio.dynamica.icgroup.Forms.MiniObjectForm;
import com.studio.dynamica.icgroup.Forms.MiniObjectTaskForm;
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
public class MiniObjectFragment extends Fragment {
    HashMap<String, String> kindMap;
    RecyclerView recyclerView;
    FrameLayout progressLayout;
    List<MiniObjectTaskForm> scTForms,bcTForms,soTForms,ibTForms;
    List<MiniObjectForm> objectForms;
    MiniObjectAdapter adapter;
    public MiniObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mini_object, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        objectForms=new ArrayList<>();
        objectForms.add(new MiniObjectForm(kindMap.get("SHOPPING_CENTER"),scTForms));
        objectForms.add(new MiniObjectForm(kindMap.get("BUSINESS_CENTER"),bcTForms));
        objectForms.add(new MiniObjectForm(kindMap.get("SMALL_OBJECT"),soTForms));
        objectForms.add(new MiniObjectForm(kindMap.get("INDUSTRIAL_BASE"),ibTForms));
         adapter=new MiniObjectAdapter(objectForms);
         recyclerView.setAdapter(adapter);
         progressLayout.setVisibility(View.GONE);
         getRequest();
        return view;
    }
    private void getRequest(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"points/";
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
        ((MainActivity)
        getActivity()).requestQueue.add(arrayRequest);
    }
    private void setInfo(JSONArray array){
        try {
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    double perfomnace=object.getDouble("performance_rate"), rate=object.getDouble("result_rate");
                    String name=object.getString("name");
                    String id=object.getString("id");
                    int per=(int)Math.round(perfomnace*100), rat=(int)Math.round(rate*5);
                    MiniObjectTaskForm form=new MiniObjectTaskForm(id,name,rat,per);
                    String kind=object.getString("kind");
                    int o=0;
                    switch (kind){
                        case "SHOPPING_CENTER":
                            o=0;
                            break;
                        case "BUSINESS_CENTER":
                            o=1;
                            break;
                        case "SMALL_OBJECT":
                            o=2;
                            break;
                        case "INDUSTRIAL_BASE":
                            o=3;
                            break;
                    }
                    objectForms.get(o).addForm(form);


                }
                adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);

        kindMap=new HashMap<>();
        kindMap.put("SHOPPING_CENTER","Торговый центр");
        kindMap.put("BUSINESS_CENTER","Бизнес центр");
        kindMap.put("SMALL_OBJECT","Малый объект");
        kindMap.put("INDUSTRIAL_BASE","Промышленная база");

        scTForms=new ArrayList<>();
        bcTForms=new ArrayList<>();
        soTForms=new ArrayList<>();
        ibTForms=new ArrayList<>();
    }
}
