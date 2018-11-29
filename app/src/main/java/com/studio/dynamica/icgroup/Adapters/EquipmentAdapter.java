package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryEquipmentAddFragment;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryEquipmentInfoFragment;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EquipmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        LinearLayout wholeLayout;
        ConstraintLayout passportLayout;
        TextView noOrders, nameTextView,idTextView,numberTextView;
        RecyclerView remontRecycler, orderRecycler;
        ImageView openArrow, plusImageView;
        FrameLayout progressLayout;
        boolean open, updated=false;
        Context context;
        String id="";
        private myHolder(View view){
            super(view);
            context=view.getContext();
            plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
            openArrow=(ImageView) view.findViewById(R.id.openArrowImageView);
            noOrders=(TextView) view.findViewById(R.id.noOrdersTextView);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            idTextView=(TextView) view.findViewById(R.id.idTextView);
            numberTextView=(TextView) view.findViewById(R.id.numberTextView);
            wholeLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            remontRecycler=(RecyclerView) view.findViewById(R.id.remontRecycler);
            orderRecycler=(RecyclerView) view.findViewById(R.id.orderRecyclerView);
            passportLayout=(ConstraintLayout) view.findViewById(R.id.passportLayout);
            progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
            passportLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            open=false;

            openArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setOpen(!open);
                }
            });
            ((MainActivity)context).setType("demibold",nameTextView, numberTextView);
            ((MainActivity)context).setType("light",idTextView);
        }

        public void setInfo(final EquipmentForm form){
            List<RemontForms> remontForms=form.getRemontForms();

            nameTextView.setText(form.getName());
            idTextView.setText(form.getVendor_code());
            numberTextView.setText(form.getNum());
            id=form.getId();
            if(remontForms.size()>0){
                ((MainActivity) context).setRecyclerViewOrientation(remontRecycler, LinearLayoutManager.VERTICAL);
                RemontAdapter remontAdapter=new RemontAdapter(remontForms);
                remontRecycler.setAdapter(remontAdapter);
            }
            else{
                ((MainActivity) context).setRecyclerViewOrientation(remontRecycler, LinearLayoutManager.VERTICAL);
                RemontAdapter remontAdapter=new RemontAdapter(new ArrayList<RemontForms>());
                remontRecycler.setAdapter(remontAdapter);
            }
            plusImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new InventoryEquipmentAddFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("num",form.getNum());
                    bundle.putString("vendor",form.getVendor_code());
                    bundle.putString("name",form.getName()  );
                    fragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
        private void getReq(){
            progressLayout.setVisibility(View.VISIBLE);
            String url=((MainActivity)context).MAIN_URL+"consumptions/"+id+"/actual_replenishments/";
            Log.d("eqAdapterURL", url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setReq(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(arrayRequest);
        }
        private void setReq(JSONArray array){
            try{
                updated=true;
                ((MainActivity) context).setRecyclerViewOrientation(orderRecycler, LinearLayoutManager.VERTICAL);
                List<OrderForm> orderForms=new ArrayList<>();
                OrderAdapter orderAdapter=new OrderAdapter(orderForms, new InventoryEquipmentInfoFragment());
                orderRecycler.setAdapter(orderAdapter);
                if(array.length()>0){
                    for(int i=0;i<array.length();i++) {
                        JSONObject object=array.getJSONObject(i);
                        int priority=object.getInt("priority");
                        String status=object.getString("status");
                        String id=object.getString("id");
                        String created_at=object.getString("created_at");
                        String deadline=object.getString("deadline");
                        String date=((MainActivity)context).getdate(created_at);
                        if(date.length()>10){
                            date=date.substring(0,date.length()-6);
                        }
                        if(created_at.length()==20) created_at=created_at.substring(0,created_at.length()-1)+".0Z";
                        if(deadline.length()==20) deadline=deadline.substring(0,deadline.length()-1)+".0Z";
                        Date created = ((MainActivity) context).inputFormat.parse(created_at), dead = ((MainActivity)context).inputFormat.parse(deadline);
                        Date now = new Date();
                        long wDays = dead.getTime() - created.getTime(), nDays = now.getTime() - created.getTime();
                        int days = Integer.parseInt(TimeUnit.DAYS.convert(wDays, TimeUnit.MILLISECONDS) + "");
                        int nowdays = Integer.parseInt(TimeUnit.DAYS.convert(nDays, TimeUnit.MILLISECONDS) + "");
                        if(created.getTime()>dead.getTime()){
                            days=0;
                            nowdays=0;
                        }
                        else {
                            if (days - nowdays > 0) {
                                Log.d("nowdays", days + " " + nowdays);
                                nowdays = days - nowdays;
                                Log.d("nowdays", (days - nowdays > 0) + "");
                            } else {
                                nowdays = 0;
                            }
                        }
                        String pri="Низкий";
                        switch (priority){ case 2: pri="Средний"; break;case 3: pri="Высокий";
                        break;}
                        OrderForm form=new OrderForm(date, "IC"+id, "Снабжения",pri, status, nowdays, days);
                        form.setId(id);
                        orderForms.add(form);
                    }
                    orderAdapter.notifyDataSetChanged();
                }
                else{
                    noOrders.setVisibility(View.VISIBLE);
                }
                setOpen(true);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        private void setOpen(boolean tr){
            if(updated) {
                int visibility;
                if (tr) {
                    visibility = View.VISIBLE;
                } else {
                    visibility = View.GONE;
                }
                setImageDown(!tr);
                wholeLayout.setVisibility(visibility);
                open = tr;
            }
            else{
                getReq();
            }
        }
        private void setImageDown(boolean tr){
            if(tr){
                openArrow.setImageResource(R.drawable.ic_arrowdown);
            }
            else openArrow.setImageResource(R.drawable.ic_arrowup);
        }
    }
    List<EquipmentForm> list;
    Context context;
    public EquipmentAdapter(List<EquipmentForm> forms){
        list=forms;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.equipment_row,parent,false);
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
