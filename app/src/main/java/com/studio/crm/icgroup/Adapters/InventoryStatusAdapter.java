package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.CheckApiForm;
import com.studio.crm.icgroup.Forms.CheckForm;
import com.studio.crm.icgroup.Forms.InventoryStatusFactoryForm;
import com.studio.crm.icgroup.Forms.InventoryStatusForm;
import com.studio.crm.icgroup.Forms.RemontForms;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryStatusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private class myHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;
        InventoryAdapter inventoryAdapter;
        TextView textView;
        List<InventoryStatusForm> statusForms;
        String url;
        String id, kind;
        int index;
        private myHolder(View view){
            super(view);
            url=((MainActivity)context).MAIN_URL;
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
            textView=(TextView) view.findViewById(R.id.text);
            ((MainActivity)context).setType("demibold",textView);
        }
        private void setInfo(InventoryStatusFactoryForm factoryForm){
            index=list.indexOf(factoryForm);
            statusForms=factoryForm.getStatusForms();
            kind=factoryForm.getKey();
            ((MainActivity) context).setRecyclerViewOrientation(recyclerView, LinearLayout.VERTICAL);
            Log.d(index+" array",statusForms.size()+"");
            inventoryAdapter=new InventoryAdapter(statusForms);
            recyclerView.setAdapter(inventoryAdapter);
            textView.setText(factoryForm.getText());
        if(!factoryForm.isUpdated())
            getCompls();
        else{
            if(statusForms.size()>0 && checkForms.size()>0){
                int l=0;
                for(InventoryStatusForm statusForm:statusForms){
                    for(CheckForm form:checkForms){
                        if(form.getConsumption().equals(statusForm.getId())){
                            l++;
                            statusForm.setStatus(false);
                            List<RemontForms> forms=new ArrayList<>();
                            if(form.getReplace()>0) forms.add(new RemontForms("На ремонте",form.getReplace()));
                            if(form.getRepair()>0) forms.add(new RemontForms("Заменен",form.getRepair()));
                            if(form.getMissing()>0) forms.add(new RemontForms("Утерян",form.getMissing()));
                            if(form.getMoving()>0) forms.add(new RemontForms("Перенаправлен",form.getMoving()));
                            if(form.getRemainder()>0) forms.add(new RemontForms("Переизбыток",form.getMoving()));
                            statusForm.setRemontForms(forms);
                            break;
                        }
                    }
                }
                Log.d("count",l+" cpunt");
                inventoryAdapter.notifyDataSetChanged();
            }
        }
        }
        private void getCompls(){
            String url=this.url+"consumptions/?point="+point+"&inventory__kind="+kind;
            Log.d(index +"url",url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    setCons(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){ @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT " + ((MainActivity) context).token);
                return headers;
            }
            };
            ((MainActivity)context).requestQueue.add(arrayRequest);
        }
        private void setCons(JSONArray array){
            try{

                statusForms.clear();
                for(int i=0;i<array.length();i++){
                    JSONObject object=array.getJSONObject(i);
                    JSONObject inventory=object.getJSONObject("inventory"),
                            un=inventory.getJSONObject("unit");
                    String unit=un.getString("name"), vendor=inventory.getString("vendor_code");
                    //String uni=unit;
                    String id=object.getString("id");
                    String name=inventory.getString("name");
                    int quantity=object.getInt("quantity");
                    InventoryStatusForm statusForm=new InventoryStatusForm(name, id, quantity);
                    statusForm.setVendor(vendor);
                    statusForm.setUnit(unit);
                    statusForm.setStatus(true);
                    statusForms.add(statusForm);
                }
                InventoryStatusFactoryForm factoryForm=list.get(index);
                factoryForm.setStatusForms(statusForms);
                factoryForm.setUpdated(true);
         //       list.set(index, factoryForm);
                notifyItemChanged(index);
               // getChecks();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    String point;

    public void setPoint(String point) {
        this.point = point;
    }

    List<InventoryStatusFactoryForm> list;
    Context context;
    public InventoryStatusAdapter(List<InventoryStatusFactoryForm> form){
        list=form;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, int position) {
        myHolder holder=(myHolder) holder1;
        holder.setInfo(list.get(position));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.inventory_status_row, parent, false);
            return new myHolder(view);

    }

    List<CheckForm> checkForms;

    public void setCheckForms(List<CheckForm> checkForms) {
        this.checkForms = checkForms;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
