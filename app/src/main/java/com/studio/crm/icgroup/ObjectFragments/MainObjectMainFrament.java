package com.studio.crm.icgroup.ObjectFragments;



import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.ButtonAdapter;
import com.studio.crm.icgroup.Adapters.MainFramentProgressRecycleAdapter;
import com.studio.crm.icgroup.Adapters.PhonesAdapter;
import com.studio.crm.icgroup.Adapters.RateStarsAdapter;
import com.studio.crm.icgroup.Forms.MainFramentProgressForm;
import com.studio.crm.icgroup.Forms.MainObjectRowForm;
import com.studio.crm.icgroup.Forms.PhonesRowForm;
import com.studio.crm.icgroup.R;
import com.studio.crm.icgroup.StartFragments.StartPage;

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
public class MainObjectMainFrament extends Fragment {
    TextView mainObjectTitle;
    TextView name;
    TextView employees;
    TextView coms;
    TextView category;
    ConstraintLayout progressBar;
    RecyclerView progresses, rateRecyclerView;
    RecyclerView buttons;
    RecyclerView phonesRecycler;
    RateStarsAdapter rateStarsAdapter;

    List<MainFramentProgressForm> progresList;
    MainFramentProgressRecycleAdapter progressRecycleAdapter;
    List<PhonesRowForm> phonesRowFormList;
    PhonesAdapter adapter;
    String id, city, prodid, role;
    boolean client;
    int rate=3, location=0, shift_count=0, janitor_shifts_count=0, gardener_shifts_count=0, plumber_shifts_count=0, electrician_shifts_count=0;
    public MainObjectMainFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role=((MainActivity)getActivity()).role;
        View view= inflater.inflate(R.layout.fragment_main_object_main_frament, container, false);
        createViews(view);

        mainObjectTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-It.ttf"));

        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
        name.setText(this.getArguments().getString("name"));

        employees.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));

        coms.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));

        category.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));



        progresList=new ArrayList<>();
        progresList.add(new MainFramentProgressForm("Успеваемость",55));
        progresList.add(new MainFramentProgressForm("Посещаемость",85));
        progresList.add(new MainFramentProgressForm("Качество",15));
        progresList.add(new MainFramentProgressForm("Инветарь",38));
        progressRecycleAdapter=new MainFramentProgressRecycleAdapter(progresList,getActivity());
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        progresses.setLayoutManager(mLayoutManager);
        progresses.setItemAnimator(new DefaultItemAnimator());
        progresses.setAdapter(progressRecycleAdapter);


        List<String> buttonsList=new ArrayList<>();
        buttonsList.add("Паспорт объекта");
        if(!client && (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("PRODUCTION") || (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO"))))
            buttonsList.add("Посещения");
        buttonsList.add("Задачи");
        if(true)
        buttonsList.add("Технологическая карта");
        if(client || (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("QC")|| (role.equals("PRODUCTION_NPO"))))
        buttonsList.add("Контроль качества");
        if(!client && (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("SUPPLY") || (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO"))))
        buttonsList.add("Инвентарь");
        if(client || role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("CHIEF")|| (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO")))
        buttonsList.add("Жалобы");

        List<View.OnClickListener> listeners=new ArrayList<>();
        View.OnClickListener listenerPassport=new View.OnClickListener() {@Override public void onClick(View v) {
            PasportObjectMainFragment fragment=new PasportObjectMainFragment();

            ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(fragment));
        }};
        View.OnClickListener serviceListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(new ServiceObjectMainFragment())); }};
        View.OnClickListener TechnoMapListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(new TechnoMapFragment())); }};
        View.OnClickListener ClientControlListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(new ClientControlObjectMainFragment())); }};
        View.OnClickListener AttendanceListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(new AttendanceMainFragment())); }};
        View.OnClickListener CommentsListener=new View.OnClickListener() {@Override public void onClick(View v) {
            Fragment fragment=new CommentsMainFragment();
            Bundle bundle=new Bundle();
            bundle.putString("type","point");
            bundle.putString("id",id);
            bundle.putInt("location",location);
            fragment.setArguments(bundle);
            ((MainActivity)getActivity()).setFragment(R.id.content_frame,fragment); }};
        View.OnClickListener listener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,getFragmentWithId(new InventoryMainFragment())); }};
        listeners.add(listenerPassport);
        if(!client && (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("PRODUCTION") || (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO"))))
            listeners.add( AttendanceListener);
        listeners.add(serviceListener);
        if(true)
        listeners.add(TechnoMapListener);
        if(client || (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("QC")|| ( role.equals("PRODUCTION_NPO"))))
        listeners.add(ClientControlListener);
        if(!client && (role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("SUPPLY") || (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO"))))
        listeners.add(listener);
        if(client || role.equals("SUPERADMIN") || role.contains("ADMIN_") || role.contains("CHIEF")|| (role.contains("CURATOR") || role.equals("PRODUCTION_ADMIN") || role.equals("PRODUCTION_NPO")))
        listeners.add(CommentsListener);

        ButtonAdapter buttonAdapter=new ButtonAdapter(buttonsList,getActivity(),listeners,R.drawable.ic_arrowrightgreen);
        RecyclerView.LayoutManager mLayoutManagerq=new LinearLayoutManager(getActivity());
        buttons.setLayoutManager(mLayoutManagerq);
        buttons.setItemAnimator(new DefaultItemAnimator());
        buttons.setAdapter(buttonAdapter);


        phonesRowFormList=new ArrayList<>();
         adapter=new PhonesAdapter(phonesRowFormList,getActivity());
        RecyclerView.LayoutManager mLayoutManagerp=new LinearLayoutManager(getActivity());
        phonesRecycler.setLayoutManager(mLayoutManagerp);
        phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        phonesRecycler.setAdapter(adapter);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(rateRecyclerView,LinearLayoutManager.HORIZONTAL);
        rateStarsAdapter=new RateStarsAdapter(rate);
        rateRecyclerView.setAdapter(rateStarsAdapter);
        setRate(3);

        getRequest("points/"+id);
        return view;
    }
    private Fragment getFragmentWithId(Fragment fragment){
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putInt("location",location);
        bundle.putString("city",city);
        bundle.putInt("shift_count",shift_count);
        bundle.putInt("janitor_shifts_count",janitor_shifts_count);
        bundle.putInt("gardener_shifts_count",gardener_shifts_count);
        bundle.putInt("plumber_shifts_count",plumber_shifts_count);
        bundle.putInt("electrician_shifts_count",electrician_shifts_count);
        bundle.putString("producer",prodid);
        bundle.putBoolean("object",true);
        fragment.setArguments(bundle);
        return fragment;
    }
    private void setInfo(JSONObject object){

            try {
                double mark1=object.getDouble("score_rate")*5;
                int mark=(int)Math.round(mark1);
                setRate(mark);
                int empl=object.getInt("workers_count");
                int comms=object.getInt("complaints_count");
                int categories=object.getInt("tasks_count");
                shift_count=object.getInt("shifts_count");
                janitor_shifts_count=object.getInt("janitor_shifts_count");
                gardener_shifts_count=object.getInt("gardener_shifts_count");
                plumber_shifts_count=object.getInt("plumber_shifts_count");
                electrician_shifts_count=object.getInt("electrician_shifts_count");;

                employees.setText(empl+"");
                coms.setText(comms+"");
                category.setText(categories+"");

                double usp=object.getDouble("performance_rate");
                double pos=object.getDouble("attendance_rate");
                double kac=object.getDouble("quality_rate");
                double inv=object.getDouble("inventory_rate");
                if(client){
                    setProgresses(doubletoInt(usp));
                }
                else
                setProgresses(doubletoInt(usp), doubletoInt(pos), doubletoInt(kac), doubletoInt(inv));

                List<PhonesRowForm> rowForms=new ArrayList<>();
                JSONObject contactor=getJsObject(object,"contactor");
                JSONObject producer=getJsObject(object,"producer");
                JSONObject curator=getJsObject(object,"curator");
                JSONObject administrator=getJsObject(object,"administrator");
                if(contactor!=null && !client)
                    rowForms.add(new PhonesRowForm(false, contactor.getString("fullname"), "Представитель клиента", contactor.getString("phone")));
                if(producer!=null) {
                    prodid=producer.getString("id");
                    rowForms.add(new PhonesRowForm(true, producer.getString("fullname"), "Начальник производства", producer.getString("phone")));
                }
                if(curator!=null)
                rowForms.add(new PhonesRowForm(true,curator.getString("fullname"),"Куратор", curator.getString("phone")));
                if(administrator!=null)
                rowForms.add(new PhonesRowForm(true,administrator.getString("fullname"),"Администратор", administrator.getString("phone")));
                setRowPhones(rowForms);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
    }
    private int doubletoInt(Double d){
        long a=Math.round(d*100);
        return Integer.parseInt(a+"");
    }
    private JSONObject getJsObject(JSONObject object,String s){
        try{
            return object.getJSONObject(s);
        }
        catch (JSONException e){
            return null;
        }
    }
    private void getRequest(String url1){
        final String url=((MainActivity)getActivity()).MAIN_URL+url1;

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(),
                        "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        (((MainActivity)getActivity()).requestQueue).add(objectRequest);
    }

    private void createViews(View view){
        client=((MainActivity) getActivity()).client;
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        category=(TextView) view.findViewById(R.id.mainObjectMainFramentCategoryTextView);
        coms=(TextView) view.findViewById(R.id.mainObjectMainFramentCommsTextView);
        name=(TextView) view.findViewById(R.id.mainObjectMainFragmentNameTextView);
        employees=(TextView) view.findViewById(R.id.mainObjectMainFramentEmployeesTextView);
        progresses=(RecyclerView) view.findViewById(R.id.mainObjectMainFramentProgressRecycler);
        buttons=(RecyclerView) view.findViewById(R.id.mainObjectMainFramentButtonsRecycler);
        phonesRecycler=(RecyclerView) view.findViewById(R.id.phonesRecyclerView);
        rateRecyclerView=(RecyclerView) view.findViewById(R.id.rateRecyclerView);
        progressBar=(ConstraintLayout) view.findViewById(R.id.progressLayout);

        id=getArguments().getString("id");
        ((MainActivity)getActivity()).setPoint(id);
        location=getArguments().getInt("location");
        ((MainActivity)getActivity()).setLocation(location+"");
        city=getArguments().getString("city","");
    }

    private void setRowPhones(List<PhonesRowForm> rowForms){
        phonesRowFormList.clear();
        phonesRowFormList.addAll(rowForms);
        adapter.notifyDataSetChanged();
    }

    public void setRate(int a){
        rateStarsAdapter.setRate(a);
        rateStarsAdapter.notifyDataSetChanged();
    }

    public void setProgresses(int a, int b, int c, int d){
        progresList.clear();
        progresList.add(new MainFramentProgressForm("Успеваемость",a));
        progresList.add(new MainFramentProgressForm("Посещаемость",b));
        progresList.add(new MainFramentProgressForm("Качество",c));
        progresList.add(new MainFramentProgressForm("Инветарь",d));
        progressRecycleAdapter.notifyDataSetChanged();
    }
    public void setProgresses(int a){
        progresList.clear();
        progresList.add(new MainFramentProgressForm("Успеваемость",a));
        progressRecycleAdapter.notifyDataSetChanged();
    }
}
