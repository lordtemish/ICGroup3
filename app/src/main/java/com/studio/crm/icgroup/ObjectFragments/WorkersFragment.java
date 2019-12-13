package com.studio.crm.icgroup.ObjectFragments;


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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.ProgressPhonesAdapter;
import com.studio.crm.icgroup.Forms.PhonesRowForm;
import com.studio.crm.icgroup.Forms.ProgressPhoneForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkersFragment extends Fragment {

    PassportObjectInfoListAddNewEmployeeFragment employeeFragment;
    ConstraintLayout employeeLayout, progressLayout;
    String id;
    int shifts=0;
    List<TextView> smenaSTextView;
    List<TextView> pages;
    List<List<List<ProgressPhoneForm>>> allPositionsForms;
    List<List<ProgressPhoneForm>>  allForms;
    List<ProgressPhoneForm> forms;
    ProgressPhonesAdapter adapter1;
    RecyclerView progressPhoneRecycler;
    int page=0;
    public WorkersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id","0");
        shifts=getArguments().getInt("shift_count");

        View view=inflater.inflate(R.layout.fragment_workers, container, false);
        createViews(view);
        setListeners();
        checkSmenaPages();

        getRequest();
        return view;
    }
    private void createViews(View view){
        employeeFragment=new PassportObjectInfoListAddNewEmployeeFragment();
        View.OnClickListener requestListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRequest();
            }
        };
        employeeFragment.click(requestListener);

        employeeLayout=(ConstraintLayout) view.findViewById(R.id.addNewEmployeeLayout);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);

        smenaSTextView=new ArrayList<>();
        smenaSTextView.add((TextView) view.findViewById(R.id.firstSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.SecondSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.thirdSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.interns));

        progressPhoneRecycler=(RecyclerView) view.findViewById(R.id.progressPhoneRecycle);

        allPositionsForms=new ArrayList<>();

        allForms=new ArrayList<>();
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        forms=new ArrayList<>();
        forms.addAll(allForms.get(0));
        adapter1=new ProgressPhonesAdapter(forms,getActivity());
        adapter1.setShifts(shifts);
        progressPhoneRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressPhoneRecycler.setItemAnimator(new DefaultItemAnimator());
        progressPhoneRecycler.setAdapter(adapter1);


        for(TextView i:smenaSTextView){
            i.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
        }

        int[] ids={R.id.page0,R.id.page1,R.id.page2,R.id.page3};
        pages=new ArrayList<>();
        for(int i=0;i<4;i++){
            pages.add((TextView)view.findViewById(ids[i]));
            final int j=i;
            pages.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page=j;
                }
            });
        }
    }

    private void setListeners(){
        employeeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddNewEmployee();
            }
        });

        for(TextView view:smenaSTextView){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSmena(v);
                }
            });
        }
    }

    private void checkSmenaPages(){
        setSmenasVisib();
        for(int i=shifts;i<3;i++){
            smenaSTextView.get(i).setVisibility(View.GONE);
        }
    }
    private void setSmenasVisib(){ for(int i =0;i<3;i++){smenaSTextView.get(i).setVisibility(View.VISIBLE);}}


    private void getRequest() {
        try {
            progressLayout.setVisibility(View.VISIBLE);
            final String url = ((MainActivity) getActivity()).MAIN_URL + "workers/?point="+id;
            JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    List<List<ProgressPhoneForm>> phoneForms = new ArrayList<>();
                    phoneForms.add(new ArrayList<ProgressPhoneForm>());
                    phoneForms.add(new ArrayList<ProgressPhoneForm>());
                    phoneForms.add(new ArrayList<ProgressPhoneForm>());
                    phoneForms.add(new ArrayList<ProgressPhoneForm>());
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject object = response.getJSONObject(i);
                            if (object.isNull("user")) continue;
                            int shift = object.getInt("shift");
                            JSONObject user = object.getJSONObject("user");
                            Log.d("FUCKINGTROUBLE", "" + user.get("fullname"));
                            String id = object.getString("id"), userid=user.getString("id");
                            String status=object.getString("status");
                            boolean is_contract=object.getBoolean("is_contract");
                            if(!status.equals("REMOVE")) {
                                  /*  if (object.getBoolean("is_trainee")) {
                                        ProgressPhoneForm progressPhoneForm = new ProgressPhoneForm(new PhonesRowForm(true, user.get("fullname") + "", "Стажёр", user.getString("phone") + ""), Integer.parseInt("" + Math.round(object.getDouble("attendance_rate") * 100)));
                                        progressPhoneForm.setStatus(status);
                                        progressPhoneForm.setSalary(object.getInt("salary"));
                                        progressPhoneForm.setContract(is_contract);
                                        progressPhoneForm.setId(id);
                                        progressPhoneForm.setUserid(userid);
                                        phoneForms.get(3).add(progressPhoneForm);
                                    } else {*/
                                ProgressPhoneForm progressPhoneForm = new ProgressPhoneForm(new PhonesRowForm(true, user.get("fullname") + "", ((MainActivity)getActivity()).workerKinds.get(object.getString("kind")), user.getString("phone") + ""), Integer.parseInt("" + Math.round(object.getDouble("attendance_rate") * 100)));
                                progressPhoneForm.setStatus(status);
                                String kind=object.getString("kind");
                                progressPhoneForm.setRole(kind);
                                progressPhoneForm.setSalary(object.getInt("salary"));
                                progressPhoneForm.setContract(is_contract);
                                progressPhoneForm.setId(id);
                                progressPhoneForm.setUserid(userid);
                                if(!kind.equals("INTERN"))
                                phoneForms.get(shift - 1).add(progressPhoneForm);
                                else{
                                    phoneForms.get(3).add(progressPhoneForm);
                                }
                                //}
                            }

                        } catch (JSONException e) {
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
                    return headers;
                }
            };
            (((MainActivity) getActivity()).requestQueue).add(arrayRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSmena(List<List<ProgressPhoneForm>> forms){
        progressLayout.setVisibility(View.GONE);
        allForms.clear();allForms.addAll(forms);
        setSmena(0);
    }
    public void setSmena(int a){
        try {
            clearSmena();

            smenaSTextView.get(a).setTextColor(getActivity().getResources().getColor(R.color.black));
            forms.clear();
            forms.addAll(allForms.get(a));
            adapter1.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
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

    private void setAddNewEmployee(){
        Bundle bundle=getArguments();
        bundle.putString("point",id);
        bundle.putInt("shifts",shifts);
       // bundle.putBoolean("is_trainee",is_trainee);
        employeeFragment.setArguments(bundle);
        ((MainActivity) getActivity()).setFragment(R.id.extra_frame,employeeFragment);
    }

}
