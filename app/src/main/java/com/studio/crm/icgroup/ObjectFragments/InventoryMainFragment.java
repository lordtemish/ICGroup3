package com.studio.crm.icgroup.ObjectFragments;


import android.os.Bundle;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AddOrderAdapter;
import com.studio.crm.icgroup.Adapters.EquipmentAdapter;
import com.studio.crm.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.crm.icgroup.Adapters.InventorizationAdapter;
import com.studio.crm.icgroup.Adapters.InventoryListAdapter;
import com.studio.crm.icgroup.Adapters.InventoryTopAdapter;
import com.studio.crm.icgroup.Adapters.MaterialAdapter;
import com.studio.crm.icgroup.Forms.AddOrderForm;
import com.studio.crm.icgroup.Forms.EquipmentForm;
import com.studio.crm.icgroup.Forms.InventorizationForm;
import com.studio.crm.icgroup.Forms.MaterialForm;
import com.studio.crm.icgroup.Forms.OrderForm;
import com.studio.crm.icgroup.Forms.RemontForms;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMainFragment extends Fragment {
    ImageView leftImageView, rightImageView, plusImageView, dateArrowImageView;
    TextView pageTextView, archiveOrderTextView, newOrderTextView,dateTextView;
    LinearLayout textsLayout;
    RecyclerView topRecycler;
    ConstraintLayout progressLayout, dateLayout,pointLayout;
    RecyclerView inventoryRecycler, equipmentReq;
    String[] a={"Инвентарь","Инвентаризация","Заявки на пополнение"}, months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
    FrameLayout progressFrame;
    int page=0;
    String id="", prodid="";
    Calendar cal;
    /*EquipmentAdapter equipmentAdapter;
    MaterialAdapter materialAdapter;*/
    InventorizationAdapter inventorizationAdapter;
    List<InventorizationForm> inventorizationForms;
    AddOrderAdapter addOrderAdapter;
    List<AddOrderForm> addOrderForms, newOrderForms, archOrderForms;
    InventoryListAdapter listAdapter;
    List<String> reqList;
    InventoryTopAdapter topAdapter;
    NumberPicker datePicker, yearPicker;
    boolean object=false, arch=false, plused=false;
    public InventoryMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        prodid=getArguments().getString("producer");
        object=getArguments().getBoolean("object",false);
        View view=inflater.inflate(R.layout.fragment_inventory_main, container, false);
        cal=Calendar.getInstance();
        cal.setTime(new Date());
        createViews(view);
        createAdapters();

        ((MainActivity) getActivity()).setRecyclerViewOrientation(inventoryRecycler, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(topRecycler, LinearLayoutManager.HORIZONTAL);
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=0;i<3;i++){
            final int k=i;
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page=k;
                    checkPage();
                }
            });
        }
        topAdapter=new InventoryTopAdapter(listeners);
        topRecycler.setAdapter(topAdapter);


        //inventoryRecycler.setAdapter(equipmentAdapter);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(equipmentReq,LinearLayout.HORIZONTAL);
        //equipmentReq.setAdapter(listAdapter);
        pickerSettings();
        createListeners();

        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(-1);
            }
        });
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(+1);
            }
        });
        checkPage();

        dateArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrow();
            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrow();
            }
        });

        if(!object){
            plusImageView.setVisibility(View.GONE);
        }

        yearPicker.setValue(cal.get(Calendar.YEAR));
        datePicker.setValue(cal.get(Calendar.MONTH)+1);
        setDate();
        return view;
    }
    private void pickerSettings(){
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(10);

        yearPicker.setMinValue(cal.get(Calendar.YEAR)-1);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+2);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    private void createViews(View view){
        leftImageView=(ImageView) view.findViewById(R.id.ImageLeftView);
        rightImageView=(ImageView) view.findViewById(R.id.ImageRightView);
        pageTextView=(TextView) view.findViewById(R.id.pageTextView);
        textsLayout=(LinearLayout) view.findViewById(R.id.textsLayout);
        progressFrame=(FrameLayout) view.findViewById(R.id.progressFrame);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);
        inventoryRecycler=(RecyclerView) view.findViewById(R.id.inventoryRecycler);
        topRecycler=(RecyclerView) view.findViewById(R.id.topRecycler);
        equipmentReq=(RecyclerView) view.findViewById(R.id.equipmentRec);
        datePicker=(NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        dateLayout=(ConstraintLayout) view.findViewById(R.id.dateLayout);
        pointLayout=(ConstraintLayout) view.findViewById(R.id.pointLayout);
        archiveOrderTextView=(TextView) view.findViewById(R.id.archiveOrderTextView);
        newOrderTextView=(TextView) view.findViewById(R.id.newOrderTextView);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);

        plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
        dateArrowImageView=(ImageView) view.findViewById(R.id.dateArrowImageView);

    }

    private void createAdapters(){
        /*List<EquipmentForm> equipmentForms=new ArrayList<>();
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт",5));
        equipmentForms.add(new EquipmentForm("name","someid",4, remontForms,orderForms));equipmentForms.add(new EquipmentForm("name","someid",4,new ArrayList<RemontForms>(),orderForms));equipmentForms.add(new EquipmentForm("name","someid",4, remontForms, orderForms));equipmentForms.add(new EquipmentForm("name","someid",4));

        equipmentAdapter=new EquipmentAdapter(equipmentForms);

        List<MaterialForm> materialForms=new ArrayList<>();
        materialForms.add(new MaterialForm("","",2,5));materialForms.add(new MaterialForm("","",2,5, orderForms));materialForms.add(new MaterialForm("","",2,5, orderForms));

        materialAdapter=new MaterialAdapter(materialForms);*/

        List<InventorizationForm> inventorizationForms=new ArrayList<>();
        this.inventorizationForms=new ArrayList<>();
        this.inventorizationForms.addAll(inventorizationForms);
        inventorizationAdapter=new InventorizationAdapter(this.inventorizationForms);

        List<AddOrderForm> addOrderForms=new ArrayList<>();
        this.addOrderForms=new ArrayList<>();this.addOrderForms.addAll(addOrderForms);
        newOrderForms=addOrderForms;archOrderForms=new ArrayList<>();

        addOrderAdapter=new AddOrderAdapter(this.addOrderForms);

        reqList=new ArrayList<>();
        reqList.add("Оборудование");
        reqList.add("Расходный материал");
        reqList.add("Инвентарь");
        reqList.add("УМС");
        reqList.add("Сезонный инвентарь");
        reqList.add("Спец. одежда");
        listAdapter=new InventoryListAdapter(reqList);
        listAdapter.setId(id);
        listAdapter.setObject(object);
      /*  reqAdapter=new EquipmentReqAdapter(reqList);
        reqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEquip(reqAdapter.getClicked());
            }
        });*/
    }
   /* private void setEquip(int a){
        switch (a){
            case 0:
                inventoryRecycler.setAdapter(equipmentAdapter);
                break;
            case 1:
                inventoryRecycler.setAdapter(materialAdapter);
                break;
                default:
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        plused=false;
        checkPage();
    }

    private void createListeners(){
        newOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invOrdersChange(true);
            }
        });
        archiveOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invOrdersChange(false);
            }
        });
        plusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             createCheckGroup();
            }
        });
    }
    private void checkPage(int a){
        page+=a;
        checkPage();
    }
    private void checkPage(){
        if(page<0) page=a.length-1;
        if(page==a.length) page=0;
        pageTextView.setText(a[page]);
        equipmentReq.setVisibility(View.GONE);
        switch (page){
            case 0:
                setProgressLayout();
                inventoryRecycler.setAdapter(listAdapter);
                break;
            case 1:
                setProgressLayout(true);
                inventoryRecycler.setAdapter(inventorizationAdapter);
                getCheckGroups();
                break;
            default:
                setTextsLayout();
                inventoryRecycler.setAdapter(addOrderAdapter);
                getReplenishments();
                break;
        }
        topAdapter.setPage(page);
        topAdapter.notifyDataSetChanged();
    }
    private void setProgressLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
        pointLayout.setVisibility(View.GONE);
    }
    private void setProgressLayout(boolean a){
        progressLayout.setVisibility(View.VISIBLE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.VISIBLE);
        pointLayout.setVisibility(View.GONE);
    }
    private void setTextsLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.VISIBLE);
        dateLayout.setVisibility(View.GONE);
        pointLayout.setVisibility(View.GONE);
    }

    private void invOrdersChange(boolean New){
        arch=!New;
        if(New){
            addOrderForms.clear();
            addOrderForms.addAll(newOrderForms);
            addOrderAdapter.notifyDataSetChanged();
            archiveOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
            newOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        }
        else {
            addOrderForms.clear();
            addOrderForms.addAll(archOrderForms);
            addOrderAdapter.notifyDataSetChanged();
            archiveOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
    }
    private void onArrow(){
        if(dateTextView.getVisibility()==View.VISIBLE){
            dateArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            dateTextView.setVisibility(View.GONE);
            yearPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
        }
        else{
            dateArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            dateTextView.setVisibility(View.VISIBLE);
            yearPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            setDate();
        }
    }
    private void setDate(){
        cal.set(Calendar.MONTH,datePicker.getValue()-1);
        cal.set(Calendar.YEAR,yearPicker.getValue());
        dateTextView.setText(months[datePicker.getValue()-1]+" "+yearPicker.getValue());
        getCheckGroups();
       // Log.d("TIME",getstartendString());
    }


    private void getCheckGroups(){
        progressFrame.setVisibility(View.VISIBLE);
        String s=getstartendString();
        String url=((MainActivity)getActivity()).MAIN_URL+"checkgroups/?point="+id+s;
        Log.d("checkURL",url);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressFrame.setVisibility(View.GONE);
                setCGroups(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеднения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    public String getstartendString(){
        String s="&";
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        s+="created_at__gte="+((MainActivity)getActivity()).inputFormat.format(cal.getTime()).substring(0,10);
        cal.add(Calendar.MONTH,+1);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        s+="&created_at__lte="+((MainActivity)getActivity()).inputFormat.format(cal.getTime()).substring(0,10);
        return s;
    }
    private void setCGroups(JSONArray array){
        try{
            inventorizationForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                JSONObject receiving=object.getJSONObject("receiving");
                String created_at=object.getString("created_at");
                String created=((MainActivity)getActivity()).getdate(created_at);
                created=created.substring(0,created.length()-6);
                String name=receiving.getString("fullname"), role=receiving.getString("role");
                double match_rate=object.getDouble("match_rate");String position=((MainActivity)getActivity()).positions.get(role);
                int rate=Integer.parseInt(""+Math.round(match_rate*100));
                InventorizationForm form=new InventorizationForm(created,name, position, rate );
                form.setId(object.getString("id"));
                inventorizationForms.add(form);
            }
            inventorizationAdapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getReplenishments(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"replenishments";
        if(object)
            url+="/?point="+id;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setRepls(response);
                progressFrame.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }
        };
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void setRepls(JSONArray array){
        try {
            archOrderForms.clear();newOrderForms.clear();
            addOrderForms.clear();
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String
                        created_at=object.getString("created_at"),
                        deadline=object.getString("deadline"),
                        description=object.getString("description"),
                        id=object.getString("id"),
                kind=object.getString("kind"), status=object.getString("status");
                int pri=object.getInt("priority");
                String rKind=((MainActivity)getActivity()).replKinds.get(kind);
                String date=((MainActivity)getActivity()).getdate(created_at);
                date=date.substring(0,date.length()-6);
                String priority="Низкий";
                switch (pri){
                    case 3:
                        priority="Высокий";
                        break;
                    case 2:
                        priority="Средний";
                        break;
                }
                if(deadline.length()==20 && deadline.length()>1){
                    deadline=deadline.substring(0,deadline.length()-1)+".0Z";
                }
                if(created_at.length()==20){
                    created_at=created_at.substring(0,created_at.length()-1)+".0Z";
                }
                Date created = ((MainActivity) getActivity()).getNowDate(created_at), dead = ((MainActivity) getActivity()).getNowDate(deadline);
                Date now = new Date();
                long wDays = dead.getTime() - created.getTime(), nDays = now.getTime() - created.getTime();
                int days = Integer.parseInt(TimeUnit.DAYS.convert(wDays, TimeUnit.MILLISECONDS) + "");
                int nowdays = Integer.parseInt(TimeUnit.DAYS.convert(nDays, TimeUnit.MILLISECONDS) + "");
                if(wDays<nDays){
                    days=0;
                    nowdays=0;
                }
                if (days - nowdays > 0) {
                    Log.d("nowdays", days + " " + nowdays);
                    nowdays = days - nowdays;
                    Log.d("nowdays", (days - nowdays > 0) + "");
                } else {
                    nowdays = 0;
                }
                AddOrderForm orderForm=new AddOrderForm(date,id,"", priority, status, rKind, nowdays, days);
                orderForm.setMass(object.isNull("count"));
                if(status.equals("FAILED") || status.equals("CLOSED")){
                    archOrderForms.add(orderForm);
                }
                else{
                    newOrderForms.add(orderForm);
                }
            }
            invOrdersChange(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createCheckGroup() {
        if (!plused) {
            plused=true;
            progressFrame.setVisibility(View.VISIBLE);
            String url = ((MainActivity) getActivity()).MAIN_URL + "checkgroups/";
            JSONObject object = new JSONObject();
            try {
                object.put("point", id);
                int idd=10;
                if(prodid.length()>0){
                    idd=Integer.parseInt(prodid);
                }
                object.put("receiving", idd);
                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressFrame.setVisibility(View.GONE);
                        Log.d("respa", response.toString());
                        Fragment fragment = new InventoryPassInventorizationFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("point", id);
                        bundle.putString("producer", prodid);
                        String CG="";
                        try{CG=response.getString("id");}catch (Exception e){e.printStackTrace();}
                        bundle.putString("id", CG);
                        fragment.setArguments(bundle);
                        ((MainActivity) getActivity()).setFragment(R.id.content_frame, fragment);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressFrame.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                        plused=false;
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
                ((MainActivity) getActivity()).requestQueue.add(objectRequest);
            } catch (Exception e) {
                e.printStackTrace();
                progressFrame.setVisibility(View.GONE);
            }
        }
    }
}
