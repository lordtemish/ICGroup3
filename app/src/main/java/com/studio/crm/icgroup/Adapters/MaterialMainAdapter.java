package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.MaterialMainForm;
import com.studio.crm.icgroup.Forms.MaterialMiniForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaterialMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        boolean updated=false, open=false;
        TextView nameTextView , vendorTextView, unitLabelTextView, unitTextView, periodLabelTextView, periodTextView,wholeLabelTextView, todayLabelTextView, totalNumTextView
                , totalUnitTextView, todayNumTextView, todayUnitTextView;
        ProgressBar ProgressBar, pogressWait;
        ImageView arrowImageView;
        RecyclerView recyclerView;
        List<MaterialMiniForm> miniForms;
        MaterialMiniAdapter adapter;
        String id="";
        private myHolder(View view){
            super(view);
            miniForms=new ArrayList<>();
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            vendorTextView=(TextView)view.findViewById(R.id.vendorTextView);
            unitTextView=(TextView)view.findViewById(R.id.unitTextView);
            unitLabelTextView=(TextView)view.findViewById(R.id.unitLabelTextView);
            periodLabelTextView=(TextView)view.findViewById(R.id.periodLabelTextView);
            periodTextView=(TextView)view.findViewById(R.id.periodTextView);
            wholeLabelTextView=(TextView)view.findViewById(R.id.wholeLabelTextView);
            todayLabelTextView=(TextView)view.findViewById(R.id.todayLabelTextView);
            totalNumTextView=(TextView)view.findViewById(R.id.totalNumTextView);
            totalUnitTextView=(TextView)view.findViewById(R.id.totalUnitTextView);
            todayNumTextView=(TextView)view.findViewById(R.id.todayNumTextView);
            todayUnitTextView=(TextView)view.findViewById(R.id.todayUnitTextView);
            ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
            pogressWait=(ProgressBar)view.findViewById(R.id.pogressWait);
            arrowImageView=(ImageView) view.findViewById(R.id.arrowImageView);
            recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView  );
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);

            arrowImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(updated)
                    open=!open;
                    setOpen();
                }
            });

            miniForms=new ArrayList<MaterialMiniForm>();
            ((MainActivity)context).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
            adapter=new MaterialMiniAdapter(miniForms);
            recyclerView.setAdapter(adapter);
            ((MainActivity)context).setType("demibold", nameTextView, unitTextView, periodTextView, todayLabelTextView, totalNumTextView, totalUnitTextView, todayNumTextView, todayUnitTextView);
            ((MainActivity)context).setType("light", vendorTextView, periodLabelTextView, wholeLabelTextView);

        }
        private void setInfo(MaterialMainForm form){
            id=form.getId();
            nameTextView.setText(form.getName());
            vendorTextView.setText(form.getVendor());
            unitTextView.setText(form.getUnit());
            totalNumTextView.setText(form.getWhole()+"");
            totalUnitTextView.setText(form.getwUnit());
            todayNumTextView.setText(form.getToday()+"");
            todayUnitTextView.setText(form.gettUnit());
        }
        private void setOpen(){
            if(updated){
                arrowImageView.setVisibility(View.VISIBLE);
                pogressWait.setVisibility(View.GONE);
                if(open){
                    if(miniForms.size()==0){
                        open=false;
                        setOpen();
                        Toast.makeText(context, "Не используется на объектах", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        arrowImageView.setImageResource(R.drawable.ic_arrowdown);
                        recyclerView.setVisibility(View.VISIBLE);
                        ProgressBar.setVisibility(View.GONE);
                    }
                }
                else{
                    arrowImageView.setImageResource(R.drawable.ic_arrowright);
                    recyclerView.setVisibility(View.GONE);
                    ProgressBar.setVisibility(View.VISIBLE);
                }
            }
            else{
                getRequest();
            }
        }
        private void getRequest(){
            arrowImageView.setVisibility(View.GONE);
            pogressWait.setVisibility(View.VISIBLE);
            String url=((MainActivity)context).MAIN_URL+"consumptions/?inventory="+id;
            JsonArrayRequest objectRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    setReq(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    arrowImageView.setVisibility(View.VISIBLE);
                    pogressWait.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(objectRequest);
        }
        private void setReq(JSONArray array){
            try {
                updated=true;
                open = true;
                miniForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONObject inventory=object.getJSONObject("inventory");
                    JSONObject point=object.getJSONObject("point");
                    String unit=inventory.getJSONObject("unit").getString("name");
                    String uni=unit;
                    String id=object.getString("id"), name=point.getString("name");
                    double qua=object.getDouble("quantity"), limit=object.getDouble("limit");
                    int ttl=Integer.parseInt(""+Math.round(qua*100/limit));
                    MaterialMiniForm materialMiniForm=new MaterialMiniForm(id,name, 0,0,0);
                    materialMiniForm.settUnit(uni);
                    materialMiniForm.setwUnit(uni);
                    miniForms.add(materialMiniForm);
                }
                Log.d("thisl",array.length()+" "+miniForms.size());
                adapter.notifyDataSetChanged();
                setOpen();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    Context context;
    List<MaterialMainForm> list;
    public MaterialMainAdapter(List<MaterialMainForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.materialmain_row, parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder) holder1;
            holder.setInfo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
