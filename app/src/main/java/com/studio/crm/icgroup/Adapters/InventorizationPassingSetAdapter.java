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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.StringRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.ExtraFragments.RadioButtonFragment;
import com.studio.crm.icgroup.ExtraFragments.RadioButtonView;
import com.studio.crm.icgroup.Forms.InventorizationPassingSetForm;
import com.studio.crm.icgroup.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventorizationPassingSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        Context context;
        LinearLayout extraLayout;
        RadioButtonFragment radioButtonFragment;
        ImageView statusImageView;
        ConstraintLayout headerLayout;
        TextView nameTextView,idTextView;
        String id="", check="";int index=0;
        TextView saveTextView;
        FrameLayout progressFrame;
        EditText commentEditText;
        RadioButtonView radioButtonView;
        private myHolder(View view){
            super(view);
            context=view.getContext();
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            statusImageView=(ImageView) view.findViewById(R.id.statusImageView);
            headerLayout=(ConstraintLayout) view.findViewById(R.id.headerLayout);

            radioButtonView=(RadioButtonView) view.findViewById(R.id.radioButtonView);
            commentEditText=(EditText) view.findViewById(R.id.commentEditText);
            saveTextView=(TextView)view.findViewById(R.id.saveTextView);
            nameTextView=(TextView)view.findViewById(R.id.nameTextView);
            idTextView=(TextView)view.findViewById(R.id.idTextView);
            progressFrame=(FrameLayout) view.findViewById(R.id.progressFrame);

            radioButtonFragment=new RadioButtonFragment();
            Bundle bundle=new Bundle();bundle.putInt("checked",0);
            radioButtonFragment.setArguments(bundle);
        }
        private void setInfo(final InventorizationPassingSetForm form){
            id=form.getId();
            nameTextView.setText(form.getName());
            idTextView.setText(form.getVendor());
        index=list.indexOf(form);
        if(form.isCreated()){
            check=form.getCheck();
        }
            headerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean a=list.get(index).isStatus();
                    Log.d("clicked",a+" "+index+" "+form.isCreated());
                    if(a) {
                        list.get(index).setStatus(!a);
                        notifyItemChanged(index);
                    }
                    else{
                        if(form.isCreated()){
                            deleteIt();
                        }
                        else {
                            list.get(index).setStatus(!a);
                            notifyItemChanged(index);
                        }
                    }
                }
            });
            int repa=form.getRepa(), repl=form.getRepl(), miss=form.getMiss(), rema=form.getRema();
            String comment=form.getComment();
            boolean sta=form.isStatus();
            if(comment.length()>0){
                commentEditText.setText(comment);
            }
            if(repa>0){
                radioButtonView.setResult(0, repa);
            }
            if(repl>0){
                radioButtonView.setResult(3, repl);
            }
            if(miss>0){
                radioButtonView.setResult(2, miss);
            }
            if(rema>0){
                radioButtonView.setResult(1, rema);
            }
            if(!sta){
                extraLayout.setVisibility(View.VISIBLE);
                statusImageView.setBackgroundResource(R.drawable.yellow_circle);
            }
            else {
                statusImageView.setBackgroundResource(R.drawable.green_circle);
                extraLayout.setVisibility(View.GONE);
            }
            saveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(form.isCreated()){
                        updateIt(form);
                    }
                    else
                    saveIt(form);
                }
            });
        }
        private void deleteIt(){
            progressFrame.setVisibility(View.VISIBLE);
            String url=((MainActivity)context).MAIN_URL+"checks/"+check+"/";
            Log.d("deleteURL",url);
            StringRequest request=new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("deleteRespa",response);
                    progressFrame.setVisibility(View.GONE);
                    list.get(index).obNul();
                    notifyItemChanged(index);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressFrame.setVisibility(View.GONE);
                    Log.d("deleteRespa",error.getMessage());
                    error.printStackTrace();
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "JWT " + ((MainActivity) context).token);
                return headers;
            }};((MainActivity)context).requestQueue.add(request);
        }
        private void updateIt(InventorizationPassingSetForm form){
            if(list.get(index).isCreated()) {
                progressFrame.setVisibility(View.VISIBLE);
                String url = ((MainActivity) context).MAIN_URL + "checks/" + check + "/";
                JSONObject params = new JSONObject();
                try {
                    params.put("consumption", Integer.parseInt(form.getConsumption()));
                    params.put("group", Integer.parseInt(form.getId()));
                    if (commentEditText.getText().length() > 0) {
                        params.put("comment", commentEditText.getText() + "");
                    }
                    int[] a = radioButtonView.getResults();
                    int repair = a[0], replace = a[3], remainder = a[1], missing = a[2];
                    if (repair >= 0) {
                        params.put("repair", repair);
                    }
                    if (replace >= 0) {
                        params.put("replace", replace);
                    }
                    if (remainder >= 0) {
                        params.put("remainder", remainder);
                    }
                    if (missing >= 0) {
                        params.put("missing", missing);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("thisPARAMS", params.toString());
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        progressFrame.setVisibility(View.GONE);
                        Log.d("response in Check", object.toString());
                        try {
                            String id = object.getString("id");
                            list.get(index).setRepa(object.getInt("repair"));
                            list.get(index).setRepl(object.getInt("replace"));
                            list.get(index).setMiss(object.getInt("missing"));
                            list.get(index).setRema(object.getInt("remainder"));
                            list.get(index).setComment(object.getString("comment"));
                            list.get(index).setCheck(id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show();
                        notifyItemChanged(index);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressFrame.setVisibility(View.GONE);
                        Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json");
                        headers.put("Authorization", "JWT " + ((MainActivity) context).token);
                        return headers;
                    }
                };
                ((MainActivity) context).requestQueue.add(objectRequest);
            }
            else {
                Toast.makeText(context, "Update Exception", Toast.LENGTH_SHORT).show();
            }
        }
        private void saveIt(InventorizationPassingSetForm form){
            progressFrame.setVisibility(View.VISIBLE);
            String url=((MainActivity)context).MAIN_URL+"checks/";
            JSONObject params=new JSONObject();
            try {
                params.put("consumption", Integer.parseInt(form.getConsumption()));
                params.put("group",Integer.parseInt(form.getId()));
                if(commentEditText.getText().length()>0){
                    params.put("comment",commentEditText.getText()+"");
                }
                int[] a=radioButtonView.getResults();
                int repair=a[0], replace=a[3], remainder=a[1], missing=a[2];
                if(repair>=0){
                    params.put("repair",repair);
                }
                if(replace>=0){
                    params.put("replace",replace);
                }
                if(remainder>=0){
                    params.put("remainder",remainder);
                }
                if(missing>=0){
                    params.put("missing",missing);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            Log.d("thisPARAMS11", params.toString());
            JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject object) {
                    progressFrame.setVisibility(View.GONE);
                    Log.d("response in Check", object.toString());
                    list.get(index).setCreated(true);
                    try{
                        String id=object.getString("id");
                        list.get(index).setRepa(object.getInt("repair"));
                        list.get(index).setRepl(object.getInt("replace"));
                        list.get(index).setMiss(object.getInt("missing"));
                        list.get(index).setRema(object.getInt("remainder"));
                        list.get(index).setComment(object.getString("comment"));
                        list.get(index).setCheck(id);
                    }catch (Exception e){e.printStackTrace();}
                    Toast.makeText(context, "Успешно сохранено", Toast.LENGTH_SHORT).show();
                    notifyItemChanged(index);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressFrame.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "JWT " + ((MainActivity) context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(objectRequest);
        }
    }

    List<InventorizationPassingSetForm> list;
    Context context;
    public InventorizationPassingSetAdapter(List<InventorizationPassingSetForm> forms){
        list=forms;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.inventorization_passingset_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder) holder1;
        if (list.get(position).getCheck().length()>0){
            list.get(position).setStatus(false);
        }
        holder.setInfo(list.get(position));

    }
    private Boolean umc=false;

    public void setUmc(Boolean umc) {
        this.umc = umc;
    }

    public void destroRequest(int position){
        String url=((MainActivity)context).MAIN_URL+"checks/";

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
