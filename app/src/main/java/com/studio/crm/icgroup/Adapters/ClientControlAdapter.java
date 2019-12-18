package com.studio.crm.icgroup.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.AcceptForm;
import com.studio.crm.icgroup.Forms.CheckListForm;
import com.studio.crm.icgroup.Forms.ChooseAcceptForm;
import com.studio.crm.icgroup.Forms.CommentForm;
import com.studio.crm.icgroup.Forms.MessageForm;
import com.studio.crm.icgroup.Forms.OlkForm;
import com.studio.crm.icgroup.Forms.PhonesRowForm;
import com.studio.crm.icgroup.Forms.svodkaRateForm;
import com.studio.crm.icgroup.ObjectFragments.CheckListInfoFragment;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientControlAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public String cityName="";
    private int location=0;


    public void setLocation(int location) {
        this.location = location;
    }

    public void setCity(String city) {
        this.cityName = city;
    }

    public int getLocation() {
        return location;
    }

    public String getCity() {
        return cityName;
    }

    private class OlkHolder extends RecyclerView.ViewHolder{
        int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
        ConstraintLayout markLayout, wholeLayout;
        RecyclerView commentsRecyclerView, acceptRecycler, phonesRecyclerView;
        TextView wrapTextView, dateTextView, nameTextView, positionTextView, infoLabel, averageMarkTextView, quality, qualityMark, looking, lookingMark, inventory, inventoryMark,olkTook
                , authorName, authorPosition, contactorName;
        LinearLayout extraLayout, infoLayout;
        FrameLayout progressLayout;
        Context context;
        boolean updated=false;
        String id="", point;
        List<MessageForm> messageForms;
        List<PhonesRowForm> rowForms;
        List<AcceptForm> acceptForms;
        PhonesAdapter phonesAdapter;
        MessageAdapter messageAdapter;
        AcceptAdapter acceptAdapter;
        List<String[]> strings;



        private OlkHolder(View v){
            super(v);
            strings=new ArrayList<>();
            strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
            context=v.getContext();
            acceptRecycler=(RecyclerView) v.findViewById(R.id.acceptRecyclerView);
            phonesRecyclerView=(RecyclerView) v.findViewById(R.id.phonesRecyclerView);
            infoLayout=(LinearLayout) v.findViewById(R.id.infoLayout);
            extraLayout=(LinearLayout) v.findViewById(R.id.extraLayout);
            wrapTextView=(TextView) v.findViewById(R.id.wrapTextView);
            dateTextView=(TextView) v.findViewById(R.id.dateTextView);
            nameTextView=(TextView) v.findViewById(R.id.nameTextView);
            authorName=(TextView) v.findViewById(R.id.authorName);
            authorPosition=(TextView) v.findViewById(R.id.authorPosition);
            contactorName=(TextView) v.findViewById(R.id.contactorName);
            infoLabel=(TextView) v.findViewById(R.id.infoLabel);
            positionTextView=(TextView) v.findViewById(R.id.positionTextView);
            quality=(TextView) v.findViewById(R.id.quality);
            qualityMark=(TextView) v.findViewById(R.id.qualityMark);
            looking=(TextView) v.findViewById(R.id.looking);
            lookingMark=(TextView) v.findViewById(R.id.lookingMark);
            inventory=(TextView) v.findViewById(R.id.inventory);
            inventoryMark=(TextView) v.findViewById(R.id.inventoryMark);
            averageMarkTextView=(TextView) v.findViewById(R.id.averageMarkTextView);
            olkTook=(TextView) v.findViewById(R.id.olkTook);
            markLayout=(ConstraintLayout) v.findViewById(R.id.markLayout);

            wholeLayout=(ConstraintLayout) v.findViewById(R.id.wholeLayout);

            progressLayout=(FrameLayout) v.findViewById(R.id.progressLayout);
            commentsRecyclerView=(RecyclerView) v.findViewById(R.id.commentsRecyclerView);
            wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clicked();
                }
            });
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(markLayout.getVisibility()==View.VISIBLE){
                        clicked();
                    }
                }
            });
            setFontType();
        }
        private void setFontType(){
            setTypeFace("demibold", nameTextView, infoLabel, averageMarkTextView, qualityMark, lookingMark, inventoryMark,olkTook);
            setTypeFace("light", wrapTextView, dateTextView, positionTextView, quality, looking, inventory);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }

        public void setMessageForms(List<MessageForm> messageForms) {
            this.messageForms = messageForms;
        }

        public void setAcceptForms(List<AcceptForm> acceptForms) {
            this.acceptForms = acceptForms;
        }
        public void addAcceptForms(AcceptForm... acceptForms){
            for (int i=0;i<acceptForms.length;i++){
                this.acceptForms.add(acceptForms[i]);
            }
        }

        public void setAcceptAdapter(AcceptAdapter acceptAdapter) {
            this.acceptAdapter = acceptAdapter;
        }

        public void setMessageAdapter(MessageAdapter messageAdapter) {
            this.messageAdapter = messageAdapter;
        }

        public void setPhonesAdapter(PhonesAdapter phonesAdapter) {
            this.phonesAdapter = phonesAdapter;
        }

        public AcceptAdapter getAcceptAdapter() {
            return acceptAdapter;
        }

        public MessageAdapter getMessageAdapter() {
            return messageAdapter;
        }

        public PhonesAdapter getPhonesAdapter() {
            return phonesAdapter;
        }

        public void setRowForms(List<PhonesRowForm> rowForms) {
            this.rowForms = rowForms;
        }

        private void setInfo(OlkForm form){
            dateTextView.setText(form.getDate());
            averageMarkTextView.setText(form.getMark()+"");
            nameTextView.setText(form.getName());
            authorName.setText(form.getName());
            positionTextView.setText(form.getPosition());
            authorPosition.setText(form.getPosition());


            id=form.getId();
        }
        private void clicked(){
            if (updated) {
                if (markLayout.getVisibility() == View.VISIBLE) {
                    markLayout.setVisibility(View.GONE);
                    infoLayout.setVisibility(View.GONE);
                    wrapTextView.setVisibility(View.VISIBLE);
                    wrapTextView.setText("Свернуть");
                    extraLayout.setVisibility(View.VISIBLE);
                } else {
                    markLayout.setVisibility(View.VISIBLE);
                    infoLayout.setVisibility(View.VISIBLE);
                    wrapTextView.setVisibility(View.GONE);
                    wrapTextView.setText("Развернуть");
                    extraLayout.setVisibility(View.GONE);
                }
            }
            else{
                String url=((MainActivity) context).MAIN_URL+"controls/"+id;
                progressLayout.setVisibility(View.VISIBLE);
                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            updated = true;
                            progressLayout.setVisibility(View.GONE);
                            messageForms.clear();
                            String message = response.getString("comment");
                            if(message.length()>0)
                            messageForms.add(new MessageForm(message));
                            JSONObject poin=response.getJSONObject("point");
                            point=poin.getString("id");
                            JSONObject contactor=poin.getJSONObject("contactor");
                            String cName=contactor.getString("fullname");
                            contactorName.setText(cName);

                            JSONArray positions=response.getJSONArray("positions");
                            if(positions.length()>2){
                                JSONObject qua=positions.getJSONObject(0);
                                quality.setText(qua.getJSONObject("position").getString("name"));
                                qualityMark.setText(qua.getString("rate"));
                                JSONObject loo=positions.getJSONObject(1);
                                looking.setText(loo.getJSONObject("position").getString("name"));
                                lookingMark.setText(loo.getString("rate"));
                                JSONObject inv=positions.getJSONObject(2);
                                inventory.setText(inv.getJSONObject("position").getString("name"));
                                inventoryMark.setText(inv.getString("rate"));
                            }
                            else{
                                qualityMark.setText(0+"");
                                lookingMark.setText(0+"");
                                inventoryMark.setText(0+"");
                            }
                            /*
                            JSONObject author=response.getJSONObject("author");
                            rowForms.clear();
                            String role=author.getString("role");
                            String position=((MainActivity)context).positions.get(role);
                            rowForms.add(new PhonesRowForm(false,author.getString("fullname"),position,author.getString("phone")));

                            phonesAdapter.notifyDataSetChanged();*/
                            acceptAdapter.notifyDataSetChanged();
                            messageAdapter.notifyDataSetChanged();

                            /*
                            if(response.isNull("is_executive_permitted")){ is_executive_permitted=-1; }
                            else{is_executive_permitted=0;
                            if(response.getBoolean("is_executive_permitted")){
                                is_executive_permitted=1;
                            }
                                }
                            if(response.isNull("is_technical_permitted")){ is_technical_permitted=-1; }
                            else{is_technical_permitted=0;
                                if(response.getBoolean("is_technical_permitted")){
                                    is_technical_permitted=1;
                                }

                            }
                            if(response.isNull("is_producer_permitted")){ is_producer_permitted=-1; }
                            else{
                                is_producer_permitted=0;
                                if(response.getBoolean("is_producer_permitted")){
                                    is_producer_permitted=1;
                                }
                            }
                            if(response.isNull("is_curator_permitted")){ is_curator_permitted=-1; }
                            else{
                                is_curator_permitted=0;
                                if(response.getBoolean("is_curator_permitted")){
                                    is_curator_permitted=1;
                                }
                            }
                            if(response.isNull("is_contactor_permitted")){ is_contactor_permitted=-1; }
                            else{
                                is_contactor_permitted=0;
                                if(response.getBoolean("is_contactor_permitted")){
                                    is_contactor_permitted=1;
                                }
                            }*/

                            //getAccepts();

                            clicked();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressLayout.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }){  @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)context).token);
                    return headers;
                }};
                ((MainActivity) context).requestQueue.add(jsonObjectRequest);
            }
        }
        private void getAccepts(){
            String url=((MainActivity)context).MAIN_URL;
            String execUrl=url+"employees/"+ "?user__role=ADMIN_EXECUTIVE";
            JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, execUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setExec(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(0,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            String techinUrl=url+"employees/" + "?user__role=ADMIN_TECHNICAL";
            JsonArrayRequest admin_techRequest=new JsonArrayRequest(Request.Method.GET, techinUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setTech(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(1,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, url+"points/"+point, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    try {
                        if(!response.isNull("contactor")) {
                            JSONObject contactor = response.getJSONObject("contactor");
                            if (is_contactor_permitted > -1)
                                strings.set(4, new String[]{contactor.getString("fullname"), contactor.getString("role"), "-1"});
                        }
                        if(!response.isNull("producer")) {
                            JSONObject producer = response.getJSONObject("producer");
                            if(is_producer_permitted>-1)
                                strings.set(2,new String[]{producer.getString("fullname"),producer.getString("role"), "-1"});
                        }
                        if(!response.isNull("curator")) {
                            JSONObject curator = response.getJSONObject("curator");

                            if (is_curator_permitted > -1)
                                strings.set(3, new String[]{curator.getString("fullname"), curator.getString("role"), "-1"});
                        }
                        checkAccepts();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(admin_execRequest);
            ((MainActivity)context).requestQueue.add(admin_techRequest);
            ((MainActivity)context).requestQueue.add(admin_contRequest);
        }
        private void setExec(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_executive_permitted>-1)
                strings.set(0, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void setTech(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_technical_permitted>-1)
                strings.set(1, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void checkAccepts(){
            acceptForms.clear();
            acceptAdapter.notifyDataSetChanged();
            int l=0;
            for(String[] a:strings){
                if(a.length==0){
                    l++;
                    continue;
                }
                Log.d("Strings"+l,a.toString());
                String name=a[0];
                String role=((MainActivity)context).positions.get(a[1]);
                String dep="";
                if(!a[2].equals("-1") && ((MainActivity)context).dpids.indexOf(a[2])!=-1) {
                    dep= ((MainActivity) context).departments.get(((MainActivity) context).dpids.indexOf(a[2]));
                }
                Boolean cl=l==4;
                String sta="Не подтвержденно";
                Boolean stat=false;
                switch (l){
                    case 0:
                        stat=is_executive_permitted==1;
                        break;
                    case 1:
                        stat=is_technical_permitted==1;
                        break;
                    case 2:
                        stat=is_producer_permitted==1;
                        break;
                    case 3:
                        stat=is_curator_permitted==1;
                        break;
                    case 4:
                        stat=is_contactor_permitted==1;
                        break;

                }
                AcceptForm acceptForm=new AcceptForm(name, dep, role, sta,stat);
                acceptForms.add(acceptForm);
                l++;
            }
            acceptAdapter.notifyDataSetChanged();
        }
    }
    private class checkListHolder extends RecyclerView.ViewHolder{
        ConstraintLayout wholeLayout;
        TextView dateTextView,revisorTextView, nameTextView, rateTextView;
        String id, rate;
        private checkListHolder(View v){
            super(v);
            wholeLayout=(ConstraintLayout) v.findViewById(R.id.wholeLayout);
            dateTextView=(TextView) v.findViewById(R.id.dateTextView);
            revisorTextView=(TextView) v.findViewById(R.id.revisorTextView);
            nameTextView=(TextView) v.findViewById(R.id.nameTextView);
            rateTextView=(TextView) v.findViewById(R.id.rateTextView);
            setFontType();
        }
        private void setInfo(CheckListForm form){
            dateTextView.setText(form.getDate());
            nameTextView.setText(form.getFIO());
            rateTextView.setText(form.getMark()+"");
            id=form.getId();
            rate=form.getMark()+"";
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment=new CheckListInfoFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("rate",rate);
                    fragment.setArguments(bundle);
                    ((MainActivity) context).setFragment(R.id.content_frame,fragment);
                }
            });
        }
        private void setFontType(){
            setTypeFace("demibold", dateTextView, nameTextView, rateTextView);
            setTypeFace("light", revisorTextView);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
    }
    private class svodkaRateHolder extends RecyclerView.ViewHolder{
        List<String[]> strings;
        int is_executive_permitted=-1,is_technical_permitted=-1,is_producer_permitted=-1,is_curator_permitted=-1,is_contactor_permitted=-1;
        RecyclerView rateStartRecycler, commentsRecyclerView, acceptRecyclerView;
        TextView workerName,workerPosition,  dateOpenTextView,averageOpenTextView, averageMarkTextView,wrapTextView,dateTextView, positionTextView, nameTextView, placeNum, placeNumLabel, cityLabel, city, address, addressLabel, clientLabel, clientName, clientPosition,tookLabel;
        ImageView arrowImage;
        LinearLayout extraLayout, wholeLayout;
        ConstraintLayout closeLayout,openLayout;
        FrameLayout progressLayout;
        MessageAdapter adapter;
        AcceptAdapter acceptAdapter;
        List<MessageForm> messageForms;
        List<AcceptForm> acceptForms;
        int pos=0;
        String id="", poin="";
        boolean updated=false;

        public void setAcceptForms(List<AcceptForm> acceptForms) {
            this.acceptForms = acceptForms;
        }

        public void setAcceptAdapter(AcceptAdapter acceptAdapter) {
            this.acceptAdapter = acceptAdapter;
        }

        public void setMessageForms(List<MessageForm> messageForms) {
            this.messageForms = messageForms;
        }

        public void setAdapter(MessageAdapter adapter) {
            this.adapter = adapter;
        }

        private svodkaRateHolder(View view){
            super(view);
            strings=new ArrayList<>();strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});strings.add(new String[]{});
            wholeLayout=(LinearLayout) view.findViewById(R.id.wholeLayout);
            extraLayout=(LinearLayout) view.findViewById(R.id.extraLayout);
            closeLayout=(ConstraintLayout) view.findViewById(R.id.closeLayout);
            openLayout=(ConstraintLayout) view.findViewById(R.id.openLayout);
            progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
            arrowImage=(ImageView) view.findViewById(R.id.arrowImageView);
            rateStartRecycler=(RecyclerView) view.findViewById(R.id.starsRecyclerView);
            commentsRecyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
            acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
            wrapTextView=(TextView) view.findViewById(R.id.wrapTextView);
            averageMarkTextView=(TextView) view.findViewById(R.id.averageMarkTextView);
            averageOpenTextView=(TextView) view.findViewById(R.id.averageOpenTextView);
            dateTextView=(TextView) view.findViewById(R.id.dateTextView);
            dateOpenTextView=(TextView) view.findViewById(R.id.dateOpenTextView);
            workerName=(TextView) view.findViewById(R.id.workerName);
            nameTextView=(TextView) view.findViewById(R.id.nameTextView);
            positionTextView=(TextView) view.findViewById(R.id.positionTextView);
            workerPosition=(TextView) view.findViewById(R.id.workerPosition);
            placeNum=(TextView) view.findViewById(R.id.placeNum);
            placeNumLabel=(TextView) view.findViewById(R.id.placeNumLabel);
            cityLabel=(TextView) view.findViewById(R.id.cityLabel);
            city=(TextView) view.findViewById(R.id.city);
            address=(TextView) view.findViewById(R.id.address);
            addressLabel=(TextView) view.findViewById(R.id.addressLabel);
            clientLabel=(TextView) view.findViewById(R.id.clientLabel);
            clientName=(TextView) view.findViewById(R.id.clientName);
            clientPosition=(TextView) view.findViewById(R.id.clientPosition );
            tookLabel=(TextView) view.findViewById(R.id.tookLabel );

            wrapTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClicked();
                }
            });
            wholeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(extraLayout.getVisibility()==View.GONE){
                        buttonClicked();
                    }
                }
            });
            setFontType();
        }
        private void setFontType(){
            setTypeFace("demibold", nameTextView, dateTextView, placeNum, city, address, clientName);
            setTypeFace("light",positionTextView, placeNumLabel, cityLabel, addressLabel, clientLabel, clientPosition, tookLabel);
        }
        private void setTypeFace(String s, TextView... textViews){
            for(int i=0;i<textViews.length;i++){
                textViews[i].setTypeface(((MainActivity)context).getTypeFace(s));
            }
        }
        private void setArrowResource(int resource){
            arrowImage.setImageResource(resource);
        }
        private void setVisibility(int visibility){
            extraLayout.setVisibility(visibility);
            wrapTextView.setVisibility(visibility);
            openLayout.setVisibility(visibility);
        }

        private void setInfo(svodkaRateForm form, int pos){
            this.pos=pos;
            dateTextView.setText(form.getDate());
            dateOpenTextView.setText(form.getDate());
            nameTextView.setText(form.getName());
            workerName.setText(form.getName());
            positionTextView.setText(form.getPosition());
            workerPosition.setText(form.getPosition());
            averageMarkTextView.setText(form.getRate()+"");
            averageOpenTextView.setText(form.getRate()+"");

            id=form.getId();
        }
        private void buttonClicked(){
            if(updated) {
                if (extraLayout.getVisibility() == View.VISIBLE) {
                    setArrowResource(R.drawable.ic_arrowdown);
                    setVisibility(View.GONE);
                    closeLayout.setVisibility(View.VISIBLE);
                } else {
                    setArrowResource(R.drawable.ic_arrowup);
                    setVisibility(View.VISIBLE);
                    closeLayout.setVisibility(View.GONE);
                }
            }
            else{
                progressLayout.setVisibility(View.VISIBLE);
                String url=((MainActivity)context).MAIN_URL+"controls/"+id;
                JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        try {
                            updated=true;
                            JSONObject point = response.getJSONObject("point");
                            poin=point.getString("id");
                            placeNum.setText(point.getString("name"));
                            JSONObject contactor = point.getJSONObject("contactor");
                            String address1=point.getString("address");
                            String CName=contactor.getString("fullname");
                            String CRole=contactor.getString("role");
                            String position=((MainActivity) context).positions.get(CRole);

                            String comment=response.getString("comment");

                            messageForms.clear();
                            if(comment.length()>0){
                                messageForms.add(new MessageForm(comment));
                            }
                            adapter.notifyDataSetChanged();
                            city.setText(cityName);
                            address.setText(address1);
                            clientName.setText(CName);
                            clientPosition.setText(position);


                          /*  if(response.isNull("is_executive_permitted")){ is_executive_permitted=-1; }
                            else{is_executive_permitted=0;
                                if(response.getBoolean("is_executive_permitted")){
                                    is_executive_permitted=1;
                                }
                            }
                            if(response.isNull("is_technical_permitted")){ is_technical_permitted=-1; }
                            else{is_technical_permitted=0;
                                if(response.getBoolean("is_technical_permitted")){
                                    is_technical_permitted=1;
                                }

                            }
                            if(response.isNull("is_producer_permitted")){ is_producer_permitted=-1; }
                            else{
                                is_producer_permitted=0;
                                if(response.getBoolean("is_producer_permitted")){
                                    is_producer_permitted=1;
                                }
                            }
                            if(response.isNull("is_curator_permitted")){ is_curator_permitted=-1; }
                            else{
                                is_curator_permitted=0;
                                if(response.getBoolean("is_curator_permitted")){
                                    is_curator_permitted=1;
                                }
                            }
                            if(response.isNull("is_contactor_permitted")){ is_contactor_permitted=-1; }
                            else{
                                is_contactor_permitted=0;
                                if(response.getBoolean("is_contactor_permitted")){
                                    is_contactor_permitted=1;
                                }
                            }
*/
                           // getAccepts();
                            buttonClicked();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                    }
                }){  @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT "+((MainActivity)context).token);
                    return headers;
                }};
                ((MainActivity)context).requestQueue.add(objectRequest);
            }
        }

        private void getAccepts(){
            String url=((MainActivity)context).MAIN_URL;
            String execUrl=url+"employees/"+ "?user__role=ADMIN_EXECUTIVE";
            JsonArrayRequest admin_execRequest=new JsonArrayRequest(Request.Method.GET, execUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setExec(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(0,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            String techinUrl=url+"employees/" + "?user__role=ADMIN_TECHNICAL";
            JsonArrayRequest admin_techRequest=new JsonArrayRequest(Request.Method.GET, techinUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    if(response.length()>0){
                        try {
                            setTech(response.getJSONObject(0));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    else{
                        strings.set(1,new String[]{});
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            JsonObjectRequest admin_contRequest=new JsonObjectRequest(Request.Method.GET, url+"points/"+poin, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressLayout.setVisibility(View.GONE);
                    try {
                        JSONObject contactor=response.getJSONObject("contactor");
                        JSONObject producer=response.getJSONObject("producer");
                        JSONObject curator=response.getJSONObject("curator");
                        if(is_producer_permitted>-1)
                            strings.set(2,new String[]{producer.getString("fullname"),producer.getString("role"), "-1"});
                        if(is_curator_permitted>-1)
                            strings.set(3,new String[]{curator.getString("fullname"),curator.getString("role"), "-1"});
                        if(is_contactor_permitted>-1)
                            strings.set(4,new String[]{contactor.getString("fullname"),contactor.getString("role"), "-1"});
                        checkAccepts();
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Проблемы соедeнения", Toast.LENGTH_SHORT).show();
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)context).token);
                return headers;
            }};
            ((MainActivity)context).requestQueue.add(admin_execRequest);
            ((MainActivity)context).requestQueue.add(admin_techRequest);
            ((MainActivity)context).requestQueue.add(admin_contRequest);
        }
        private void setExec(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_executive_permitted>-1)
                    strings.set(0, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void setTech(JSONObject re){
            try {
                JSONObject object=re.getJSONObject("user");
                if(is_technical_permitted>-1)
                    strings.set(1, new String[]{object.getString("fullname"),object.getString("role"), re.getString("department")});
                checkAccepts();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        private void checkAccepts(){
            acceptForms.clear();
            acceptAdapter.notifyDataSetChanged();
            int l=0;
            for(String[] a:strings){
                if(a.length==0){
                    l++;
                    continue;
                }
                Log.d("Strings"+l,a.toString());
                String name=a[0];
                String role=((MainActivity)context).positions.get(a[1]);
                String dep="";
                if(!a[2].equals("-1") && ((MainActivity)context).dpids.indexOf(a[2])!=-1) {
                    dep= ((MainActivity) context).departments.get(((MainActivity) context).dpids.indexOf(a[2]));
                }
                Boolean cl=l==4;
                String sta="Не подтвержденно";
                Boolean stat=false;
                switch (l){
                    case 0:
                        stat=is_executive_permitted==1;
                        break;
                    case 1:
                        stat=is_technical_permitted==1;
                        break;
                    case 2:
                        stat=is_producer_permitted==1;
                        break;
                    case 3:
                        stat=is_curator_permitted==1;
                        break;
                    case 4:
                        stat=is_contactor_permitted==1;
                        break;

                }
                AcceptForm acceptForm=new AcceptForm(name, dep, role, sta,stat);
                acceptForms.add(acceptForm);
                l++;
            }
            acceptAdapter.notifyDataSetChanged();
        }
    }
    Context context;
    private int page;
    List<Object> list;
    String id;
    public ClientControlAdapter(List<Object> list, int page){
        this.list=list;
        this.page=page;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context= parent.getContext();
       int layout=R.layout.olk_row;
       if(page==1){ layout=R.layout.check_list_row; }
       if(page==2){ layout=R.layout.svodka_rate_row; }
        View view= LayoutInflater.from(context).inflate(layout,parent,false);
        RecyclerView.ViewHolder holder;
        switch (page){
            case 1:
                holder=new checkListHolder(view);
                break;
            case 2:
                holder=new svodkaRateHolder(view);
                break;
                default:
                    holder=new OlkHolder(view);
                    break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder3, int position) {
        switch (page){
            case 0:
                OlkHolder holder=(OlkHolder)holder3;
                OlkForm olkForm=(OlkForm) list.get(position);
                holder.setInfo(olkForm);
                RecyclerView commentsRecycle=holder.commentsRecyclerView;
                RecyclerView.LayoutManager manager0=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView phonesRecycle=holder.phonesRecyclerView;
                RecyclerView.LayoutManager manager01=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);

                RecyclerView accceptReycler=holder.acceptRecycler;
                RecyclerView.LayoutManager manager02=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);

                List<MessageForm> messageForm=new ArrayList<>();messageForm.add(new MessageForm(context.getResources().getString(R.string.bigtext)));
                List<PhonesRowForm> rowForms=new ArrayList<>();rowForms.add(new PhonesRowForm(false,"Темирлан Алмасов","ОПУ","87017000154"));
                List<AcceptForm> acceptForms=new ArrayList<>();

                PhonesAdapter adapter01=new PhonesAdapter(rowForms, context);
                phonesRecycle.setLayoutManager(manager01);
                phonesRecycle.setItemAnimator(new DefaultItemAnimator());
                phonesRecycle.setAdapter(adapter01);

                AcceptAdapter acceptAdapter=new AcceptAdapter(acceptForms);
                accceptReycler.setItemAnimator(new DefaultItemAnimator());
                accceptReycler.setLayoutManager(manager02);
                accceptReycler.setAdapter(acceptAdapter);

                MessageAdapter adapter0=new MessageAdapter(messageForm);
                commentsRecycle.setLayoutManager(manager0);
                commentsRecycle.setItemAnimator(new DefaultItemAnimator());
                commentsRecycle.setAdapter(adapter0);
                holder.setMessageAdapter(adapter0);holder.setMessageForms(messageForm);
                holder.setRowForms(rowForms);holder.setPhonesAdapter(adapter01);
                holder.setAcceptAdapter(acceptAdapter);holder.setAcceptForms(acceptForms);
                break;
            case 1:
                checkListHolder holder1=(checkListHolder) holder3;
                CheckListForm form=(CheckListForm) list.get(position);
                holder1.setInfo(form);
                break;
            case 2:
                svodkaRateForm form2=(svodkaRateForm) list.get(position);

                svodkaRateHolder holder2=(svodkaRateHolder) holder3;
                holder2.setInfo(form2,position);
                RecyclerView recyclerView=holder2.rateStartRecycler;
                RecyclerView commentsRecycler=holder2.commentsRecyclerView;
                RecyclerView accceptReycler0=holder2.acceptRecyclerView;

                List<MessageForm> messageForms=new ArrayList<>();messageForms.add(new MessageForm("Fuck that shitasdlfhjkashfjkashfjhaskjfhaslfhj"));
                List<AcceptForm> acceptForms0=new ArrayList<>();acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));acceptForms0.add(new AcceptForm("Темирлан Алмасов","Отдел продаж","ОПУ","Выполнил",true));


                MessageAdapter adapter1=new MessageAdapter(messageForms);
                ((MainActivity) context).setRecyclerViewOrientation(commentsRecycler,LinearLayoutManager.VERTICAL);
                commentsRecycler.setAdapter(adapter1);

                RateStarsAdapter adapter=new RateStarsAdapter(form2.getRate());
                ((MainActivity) context).setRecyclerViewOrientation(recyclerView,LinearLayoutManager.VERTICAL);
                recyclerView.setAdapter(adapter);

                AcceptAdapter acceptAdapter0=new AcceptAdapter(acceptForms0);
                ((MainActivity) context).setRecyclerViewOrientation(accceptReycler0,LinearLayoutManager.HORIZONTAL);
                accceptReycler0.setAdapter(acceptAdapter0);

                holder2.setMessageForms(messageForms);
                holder2.setAdapter(adapter1);
                holder2.setAcceptAdapter(acceptAdapter0);
                holder2.setAcceptForms(acceptForms0);

                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
