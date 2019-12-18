package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.ClientsMainForm;
import com.studio.crm.icgroup.Forms.ClientsPointForm;
import com.studio.crm.icgroup.Forms.PointInfoHolder;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        TextView title,role,nameTextView, objectsLabel;
        ImageView settings,avatar, arrowDown;
        RecyclerView  recyclerView;
        ProgressBar progressBar;
        String id;
        boolean open=false, first=false;
        ClientsMainForm form;
        ClientsPointAdapter adapter;
        private myHolder(View view){
            super(view);
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            title=(TextView)view.findViewById(R.id.title);
            role=(TextView)view.findViewById(R.id.role);
            objectsLabel=(TextView)view.findViewById(R.id.objectsLabel);
            settings=(ImageView) view.findViewById(R.id.settings);
            arrowDown=(ImageView) view.findViewById(R.id.arrowDown);
            avatar=(ImageView) view.findViewById(R.id.avatar);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);

            ((MainActivity)context).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);

            ((MainActivity)context).setType("demibold", title,nameTextView, objectsLabel);
            ((MainActivity)context).setType("light", role);
            arrowDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    open=!open;
                    checkOpen();
                }
            });
            recyclerView.setVisibility(View.GONE);
            progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        }
        private void checkOpen(){
            if(open){
                if(!first){
                    first=true;
                    getRequest();
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    arrowDown.setImageResource(R.drawable.ic_arrowup);
                }
            }
            else{
                recyclerView.setVisibility(View.GONE);
                arrowDown.setImageResource(R.drawable.ic_arrowdown);
            }
        }
        private void setInfo(ClientsMainForm form){
                id=form.getId();
                title.setText(form.getName());
                nameTextView.setText(form.getFullname());
                this.form=form;
                adapter=new ClientsPointAdapter(form.getPointForms());
                  recyclerView.setAdapter(adapter);

        }
        private void getRequest(){
            progressBar.setVisibility(View.VISIBLE);
            String url=((MainActivity)context).MAIN_URL+"points/?client="+id;
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressBar.setVisibility(View.GONE);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject point = response.getJSONObject(i);
                            form.getPointForms().clear();

                            double result_rate=point.getDouble("score_rate");
                            String name=point.getString("name"), id=point.getString("id");
                            int rate=Integer.parseInt(Math.round(result_rate*5)+"");
                            ClientsPointForm cForm=new ClientsPointForm(id,name, rate);
                            PointInfoHolder infoHolder=new PointInfoHolder();
                            infoHolder.setId(id);
                            infoHolder.setLocation(0);
                            infoHolder.setName(name);
                            infoHolder.setCity("");
                            cForm.setInfoHolder(infoHolder);

                            form.getPointForms().add(cForm);

                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        open=true;
                        checkOpen();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    open=false;
                    checkOpen();
                    first=false;
                }
            }){ @Override
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

    }
    Context context;
    List<ClientsMainForm> list;
    public ClientsMainAdapter(List<ClientsMainForm> forms){
        list=forms;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
            myHolder holder=(myHolder)holder1;
            holder.setInfo(list.get(position));
            if(position==list.size()-1){

            }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.clientsmain_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
