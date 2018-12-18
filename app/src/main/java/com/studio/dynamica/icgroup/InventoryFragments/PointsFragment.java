package com.studio.dynamica.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.EquipmentMObjectAdapter;
import com.studio.dynamica.icgroup.Forms.EquipmentMObjectForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PointsFragment extends Fragment {
    EquipmentMObjectAdapter mObjectAdapter;
    List<EquipmentMObjectForm> forms;
    TextView mainObjectTitle;
    RecyclerView recyclerView;
    String name, array;
    public PointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        name=getArguments().getString("name");
        array=getArguments().getString("array");
        View view=inflater.inflate(R.layout.fragment_points, container, false);
        createViews(view);
        mainObjectTitle.setText(name);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        mObjectAdapter=new EquipmentMObjectAdapter(forms);
        recyclerView.setAdapter(mObjectAdapter);
        try {
            setArray(new JSONArray(array));
        }
        catch (JSONException e){
           e.printStackTrace();}
        return view;
    }
    private void createViews(View view){
        forms=new ArrayList<>();
        mainObjectTitle=(TextView)view.findViewById(R.id.mainObjectTitle);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
    }
    private void setArray(JSONArray array){
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONObject point = object.getJSONObject("point");
                JSONObject inventory = object.getJSONObject("inventory");
//                    JSONObject company=inventory.getJSONObject("company");
                String id = point.getString("id"), name = point.getString("name"), unit = inventory.getJSONObject("unit").getString("name");
                String uni = unit;
                double qua = object.getDouble("quantity"), limit = object.getDouble("limit");
                int invento_rate = Integer.parseInt("" + Math.round(qua / limit * 100));

                EquipmentMObjectForm form = new EquipmentMObjectForm(id, name, uni, Integer.parseInt(Math.round(qua) + ""), invento_rate);
                forms.add(form);
            }
            mObjectAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
