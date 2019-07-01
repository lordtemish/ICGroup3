package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.EquipmentMObjectForm;
import com.studio.crm.icgroup.Forms.EquipmentMainForm;
import com.studio.crm.icgroup.InventoryFragments.PointsFragment;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquipmentMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        EquipmentMObjectAdapter mObjectAdapter;
        int index;
        List<EquipmentMObjectForm> forms;
        ConstraintLayout using;
        ImageView buttonArrow;
        TextView nameTextView, idTextView, wholeQuanLabelTextView, numberTextView, unitTextView, passportEqTextButton, usingLabel;
        private myHolder(View view)
        {
            super(view);
            forms=new ArrayList<>();
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            idTextView=(TextView)view.findViewById(R.id.idTextView);
            wholeQuanLabelTextView=(TextView)view.findViewById(R.id.wholeQuanLabelTextView);
            numberTextView=(TextView)view.findViewById(R.id.numberTextView);
            unitTextView=(TextView)view.findViewById(R.id.unitTextView);
            passportEqTextButton=(TextView)view.findViewById(R.id.passportEqTextButton);
            usingLabel=(TextView)view.findViewById(R.id.usingLabel);
            using=(ConstraintLayout)view.findViewById(R.id.using);
            buttonArrow=(ImageView) view.findViewById(R.id.buttonArrow);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            mObjectAdapter=new EquipmentMObjectAdapter(forms);
            recyclerView.setAdapter(mObjectAdapter);
            ((MainActivity)context).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
            ((MainActivity)context).setType("demibold", nameTextView, numberTextView, unitTextView);
            ((MainActivity)context).setType("light", idTextView, wholeQuanLabelTextView, passportEqTextButton, usingLabel);
        }
        private void setInfo(EquipmentMainForm form){
            index=list.indexOf(form);
            forms.clear();forms.addAll(form.getmObjectForms());
            mObjectAdapter.notifyDataSetChanged();

            nameTextView.setText(form.getName());
            idTextView.setText(form.getVendor());
            numberTextView.setText(form.getQua()+"");
            unitTextView.setText(form.getUnit());
            getRequest(form.getId());
        }
        private void getRequest(String id){
            String url=((MainActivity)context).MAIN_URL+"consumptions/?inventory="+id;
          //  Log.d("this ConsUrl", url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    setArray(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)context).token);
                    return headers;
                }
            };
            ((MainActivity)context).requestQueue.add(arrayRequest);
        }
        private void setArray(final JSONArray array){
            try {
                forms.clear();
                if(array.length()==0){
                    using.setVisibility(View.GONE);
                    buttonArrow.setVisibility(View.GONE);
                }
                else {
                    using.setVisibility(View.VISIBLE);
                    buttonArrow.setVisibility(View.VISIBLE);
                    buttonArrow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            PointsFragment fragment=new PointsFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString("name", list.get(index).getName());
                            bundle.putString("array",array.toString());
                            fragment.setArguments(bundle);
                            ((MainActivity)context).setFragment(R.id.content_frame,fragment);
                        }
                    });
                }

                    for(int i=0;i<array.length();i++){
                        JSONObject object=array.getJSONObject(i);
                        JSONObject point=object.getJSONObject("point");
                        JSONObject inventory=object.getJSONObject("inventory");
    //                    JSONObject company=inventory.getJSONObject("company");
                   /*     String id=point.getString("id"), name=point.getString("name"), unit=inventory.getJSONObject("unit").getString("name");
                        String uni=unit;
                        double qua=object.getDouble("quantity"), limit=object.getDouble("limit");
                        int invento_rate=Integer.parseInt(""+Math.round(qua/limit*100));*/
                        int repair = object.getInt("repair"), replace = object.getInt("replace");
                        list.get(index).addRepair(repair);list.get(index).addReplace(replace);
                        /*EquipmentMObjectForm form=new EquipmentMObjectForm(id, name,uni, Integer.parseInt(Math.round(qua)+""),invento_rate);
                        forms.add(form);*/
                    }
                wholeQuanLabelTextView.setText(whole);
                 //   mObjectAdapter.notifyDataSetChanged();

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    List<EquipmentMainForm> list;
    Context context;
    String whole="Общее количество";
    public EquipmentMainAdapter(List<EquipmentMainForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.exuipmentmain_row,parent,false);
        return new myHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setWhole(String whole) {
        this.whole = whole;
    }
}