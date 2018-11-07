package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.PhonesAdapter;
import com.studio.dynamica.icgroup.Adapters.ProgressPhonesAdapter;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.Forms.ProgressPhoneForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListFragment extends Fragment {
    HashMap<String, TextView> mapText;
    RecyclerView phonesRecycler;
    RecyclerView progressPhoneRecycler;
    View view;
    List<TextView> smenaSTextView;
    PhonesAdapter adapter;
    List<PhonesRowForm> phonesRowFormList;
    List<RecyclerView.Adapter> smenaSAdapter;
    ProgressPhonesAdapter adapter1;
    int shifts=0;
    ConstraintLayout employeeLayout, progressLayout;
    List<List<ProgressPhoneForm>>  allForms;
    List<ProgressPhoneForm> forms;
    TextView title;
    Boolean is_trainee=false;
    String id;
    LinearLayout emplLa;
    ImageView arrowTop;
    PassportObjectInfoListAddNewEmployeeFragment employeeFragment;
    public PassportObjectInfoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id","0");
       view=inflater.inflate(R.layout.fragment_passport_object_info_list, container, false);

       emplLa=(LinearLayout)view.findViewById(R.id.emplLa);
        arrowTop=(ImageView)view.findViewById(R.id.arrowTop);
       title=(TextView) view.findViewById(R.id.title);
       title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
       smenaSTextView=new ArrayList<>();
       smenaSAdapter=new ArrayList<>();
        mapText=new HashMap<>();
        addAlltoMap();
        setListener();
        for(TextView i:smenaSTextView){
            i.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
        }
        for (String i:
             mapText.keySet()) {
            if(i.contains("TextView")) {
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/avenir-light.ttf"));
            }
            else{
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/Avenir-Black.ttf"));
            }
        }
        phonesRecycler=(RecyclerView) view.findViewById(R.id.phonesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
         phonesRowFormList=new ArrayList<>();
        phonesRowFormList.add(new PhonesRowForm(true,"Рыскулова Динара","Администратор","+77477477447"));
        phonesRowFormList.add(new PhonesRowForm(true,"Рыскулова Динара","Администратор"));
        adapter=new PhonesAdapter(phonesRowFormList,getActivity());
        phonesRecycler.setLayoutManager(mLayoutManager);
        phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        phonesRecycler.setAdapter(adapter);
        progressPhoneRecycler=(RecyclerView) view.findViewById(R.id.progressPhoneRecycle);
        allForms=new ArrayList<>();
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        forms=new ArrayList<>();
        allForms.get(0).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(0).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55,"Замена",new PhonesRowForm(false,"Темирлан   Алмасов","ОПУ")));
        allForms.get(1).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(1).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(2).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55,"Замена",new PhonesRowForm(false,"Темирлан   Алмасов","ОПУ")));
        allForms.get(3).add(new ProgressPhoneForm(new PhonesRowForm(false,"",""),55));
        forms.addAll(allForms.get(0));
        adapter1=new ProgressPhonesAdapter(forms,getActivity());
        smenaSAdapter.add(adapter1);
        RecyclerView.LayoutManager mLayoutManager1=new LinearLayoutManager(getActivity());
        progressPhoneRecycler.setLayoutManager(mLayoutManager1);
        progressPhoneRecycler.setItemAnimator(new DefaultItemAnimator());
        progressPhoneRecycler.setAdapter(adapter1);

        employeeFragment=new PassportObjectInfoListAddNewEmployeeFragment();
        employeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddNewEmployee();
            }
        });

        getRequest("points/"+id);
        View.OnClickListener requestListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest("points/"+id);
            }
        };
        employeeFragment.click(requestListener);

        showEmpls();
        arrowTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmpls();
            }
        });
        return view;
    }
    private void showEmpls(){
        if(emplLa.getVisibility()==View.VISIBLE){
            emplLa.setVisibility(View.GONE);
            arrowTop.setImageResource(R.drawable.ic_arrowdown);
        }
        else{
            emplLa.setVisibility(View.VISIBLE);
            arrowTop.setImageResource(R.drawable.ic_arrowup);
        }
    }
    private void setAddNewEmployee(){
        Bundle bundle=new Bundle();
        bundle.putString("point",id);
        bundle.putInt("shifts",shifts);
        bundle.putBoolean("is_trainee",is_trainee);
        employeeFragment.setArguments(bundle);
        ((MainActivity) getActivity()).setFragment(R.id.extra_frame,employeeFragment);
    }
    private void checkSmenaPages(){
        setSmenasVisib();
        for(int i=shifts;i<3;i++){
            smenaSTextView.get(i).setVisibility(View.GONE);
        }
    }
    private void setSmenasVisib(){ for(int i =0;i<3;i++){smenaSTextView.get(i).setVisibility(View.VISIBLE);}}
    private void updateSmena(List<List<ProgressPhoneForm>> forms){
        progressLayout.setVisibility(View.GONE);
        allForms.clear();allForms.addAll(forms);
        setSmena(0);
    }
    public void setSmena(int a){
        clearSmena();
        if(a==3){
            is_trainee=true;
            }
            else
                is_trainee=false;
        smenaSTextView.get(a).setTextColor(getActivity().getResources().getColor(R.color.black));
        forms.clear();
        forms.addAll(allForms.get(a));
       adapter1.notifyDataSetChanged();
    }
    public void setSmena(View view){
        String tag=view.getTag().toString();
        int a=Integer.parseInt(tag);
        setSmena(a);
    }
    public void clearSmena() throws  NullPointerException{
        for(TextView view:smenaSTextView){
            try {
                view.setTextColor(getActivity().getResources().getColor(R.color.greyy));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void setListener(){
        int l=0;
        for(TextView view:smenaSTextView){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSmena(v);
                }
            });
        }
    }
    private void setPhonesAdapter(List<PhonesRowForm> forms){
        phonesRowFormList.clear();
        phonesRowFormList.addAll(forms);
        adapter.notifyDataSetChanged();
    }
    private void getRequest(final String url1){
        progressLayout.setVisibility(View.VISIBLE);
        final String url=((MainActivity)getActivity()).MAIN_URL+url1;
        if(url1.contains("points")) {
            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setInfo(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};
            (((MainActivity) getActivity()).requestQueue).add(objectRequest);
        }
        else if(url1.contains("workers")){
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    List<List<ProgressPhoneForm>> phoneForms=new ArrayList<>();phoneForms.add(new ArrayList<ProgressPhoneForm>());phoneForms.add(new ArrayList<ProgressPhoneForm>());phoneForms.add(new ArrayList<ProgressPhoneForm>());phoneForms.add(new ArrayList<ProgressPhoneForm>());
                    for(int i=0;i<response.length();i++){
                        try {

                            JSONObject object = response.getJSONObject(i);
                            if(object.isNull("user")) continue;
                            int shift=object.getInt("shift");
                            JSONObject user=object.getJSONObject("user");
                            Log.d("FUCKINGTROUBLE",""+user.get("fullname"));
                            String id=object.getString("id");
                            if(object.getBoolean("is_trainee")) {
                                ProgressPhoneForm progressPhoneForm=new ProgressPhoneForm(new PhonesRowForm(true, user.get("fullname") + "", "Стажёр", user.getString("phone") + ""), Integer.parseInt(""+Math.round(object.getDouble("attendance_rate")*100)));
                                progressPhoneForm.setId(id);
                                phoneForms.get(3).add(progressPhoneForm);
                                }
                                else {
                                ProgressPhoneForm progressPhoneForm=new ProgressPhoneForm(new PhonesRowForm(true, user.get("fullname") + "", "ОПУ", user.getString("phone") + ""), Integer.parseInt(""+Math.round(object.getDouble("attendance_rate")*100)));
                                progressPhoneForm.setId(id);
                                phoneForms.get(shift - 1).add(progressPhoneForm);
                            }

                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                    updateSmena(phoneForms);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                progressLayout.setVisibility(View.GONE);
                }
            }){  @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};
            (((MainActivity)getActivity()).requestQueue).add(arrayRequest);
        }
    }
    private void setInfo(JSONObject object){
        progressLayout.setVisibility(View.GONE);
        try {
            String name = object.getString("name");
            String c_at=object.getString("created_at");

            Date cr_at=null, fi_at=null;
            try{
                cr_at=((MainActivity) getActivity()).inputFormat.parse(c_at);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            String address=object.getString("address");
            String area=object.getString("area");
            JSONObject location=getJsObject(object,"location");

            JSONObject contactor=getJsObject(object,"contactor");
            JSONObject client=getJsObject(object,"client");
            JSONObject producer=getJsObject(object,"producer");
            JSONObject curator=getJsObject(object,"curator");
            JSONObject administrator=getJsObject(object,"administrator");
            try {
                shifts=object.getInt("shifts_count");
                mapText.get("infoListObjectName").setText(name);
                mapText.get("infoListRegion").setText(location.getString("name"));
                mapText.get("infoListObjectAddress").setText(address);
                mapText.get("infoListWorkStart").setText(((MainActivity)getActivity()).getDateText(cr_at));
                String ff="";
                if(!object.isNull("finished_at"));
                {
                    String f_at = object.getString("finished_at");
                    if(!f_at.equals("null"))
                    ff = ((MainActivity) getActivity()).getdate(f_at);
                }
                if(ff.length()>6)
                mapText.get("infoListWorkStop").setText(ff.substring(0,ff.length()-6));

                mapText.get("infoListObjectArea").setText(area);
                try {
                    phonesRowFormList.clear();
                    List<PhonesRowForm> phonesRowForms=new ArrayList<>();
                    if(producer!=null) {
                        mapText.get("infoListHead").setText(producer.getString("fullname"));
                        phonesRowForms.add(new PhonesRowForm(true, producer.getString("fullname"), "Начальник производства", producer.getString("phone")));
                    }
                    else{
                        mapText.get("infoListHead").setText("");
                    }
                    if(curator!=null) {
                        mapText.get("infoListAdvisor").setText(curator.getString("fullname"));
                        phonesRowForms.add(new PhonesRowForm(true, curator.getString("fullname"), "Куратор", curator.getString("phone")));
                    }
                    else{
                        mapText.get("infoListAdvisor").setText("");
                    }
                    if(administrator!=null) {
                        mapText.get("infoListAdministrator").setText(administrator.getString("fullname"));
                        phonesRowForms.add(new PhonesRowForm(true, administrator.getString("fullname"), "Администратор", administrator.getString("phone")));
                    }
                    else{
                        mapText.get("infoListAdministrator").setText("");
                    }
                    if(client!=null)
                    mapText.get("infoListClient").setText(client.getString("name"));
                    else{
                        mapText.get("infoListClient").setText("");
                    }
                    if(contactor!=null)
                    mapText.get("infoListClientPre").setText(contactor.getString("fullname"));
                    else mapText.get("infoListClientPre").setText("");
                    setPhonesAdapter(phonesRowForms);
                    adapter.notifyDataSetChanged();
                }
                catch (NullPointerException e){e.printStackTrace();
                }



            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        checkSmenaPages();
        getRequest("workers/?point="+id);
    }
    private void setShiftInfo(JSONArray array){

    }

    @Override
    public void onResume() {
        //getRequest("points/"+id);
        super.onResume();
    }

    private JSONObject getJsObject(JSONObject object, String s){
        try{
            return object.getJSONObject(s);
        }
        catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
    public void addAlltoMap(){
        employeeLayout=(ConstraintLayout) view.findViewById(R.id.addNewEmployeeLayout);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);

        smenaSTextView.add((TextView) view.findViewById(R.id.firstSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.SecondSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.thirdSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.interns));
        mapText.put("infoListObjectNameTextView",(TextView) view.findViewById(R.id.infoListObjectNameTextView));
        mapText.put("infoListObjectName",(TextView) view.findViewById(R.id.infoListObjectName));
        mapText.put("infoListRegionTextView",(TextView) view.findViewById(R.id.infoListRegionTextView));
        mapText.put("infoListRegion",(TextView) view.findViewById(R.id.infoListRegion));
        mapText.put("infoListObjectAddressTextView",(TextView) view.findViewById(R.id.infoListObjectAddressTextView));
        mapText.put("infoListObjectAddress",(TextView) view.findViewById(R.id.infoListObjectAddress));

        mapText.put("infoListWorkStartTextView",(TextView) view.findViewById(R.id.infoListWorkStartTextView));
        mapText.put("infoListWorkStart",(TextView) view.findViewById(R.id.infoListWorkStart));
        mapText.put("infoListWorkStopTextView",(TextView) view.findViewById(R.id.infoListWorkStopTextView));
        mapText.put("infoListWorkStop",(TextView) view.findViewById(R.id.infoListWorkStop));
        mapText.put("infoListObjectAreaTextView",(TextView) view.findViewById(R.id.infoListObjectAreaTextView));
        mapText.put("infoListObjectArea",(TextView) view.findViewById(R.id.infoListObjectArea));

        mapText.put("infoListHeadTextView",(TextView) view.findViewById(R.id.infoListHeadTextView));
        mapText.put("infoListHead",(TextView) view.findViewById(R.id.infoListHead));
        mapText.put("infoListAdvisorTextView",(TextView) view.findViewById(R.id.infoListAdvisorTextView));
        mapText.put("infoListAdvisor",(TextView) view.findViewById(R.id.infoListAdvisor));
        mapText.put("infoListAdministratorTextView",(TextView) view.findViewById(R.id.infoListAdministratorTextView));
        mapText.put("infoListAdministrator",(TextView) view.findViewById(R.id.infoListAdministrator));
        mapText.put("infoListClientTextView",(TextView) view.findViewById(R.id.infoListClientTextView));
        mapText.put("infoListClient",(TextView) view.findViewById(R.id.infoListClient));
        mapText.put("infoListClientPreTextView",(TextView) view.findViewById(R.id.infoListClientPreTextView));
        mapText.put("infoListClientPre",(TextView) view.findViewById(R.id.infoListClientPre));

    }
}
