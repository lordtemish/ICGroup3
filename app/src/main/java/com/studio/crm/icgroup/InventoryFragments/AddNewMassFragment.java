package com.studio.crm.icgroup.InventoryFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.MassCreationAdapter;
import com.studio.crm.icgroup.Forms.EquipmentForm;
import com.studio.crm.icgroup.Forms.MassCreationForm;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewMassFragment extends Fragment {

    FrameLayout progressFrame;
    RecyclerView recyclerView;
    MassCreationAdapter adapter;
    List<MassCreationForm> forms;
    TextView depLabelTextView, orderNameTextView, depTextView, justText, cancelOrderTextView, acceptOrderTextView;
    String id, kind, array;
    private NumberPicker datePicker, yearPicker;
    private TextView monthTextView, yearTextView;
    private ImageView dateArrowImageView, yearArrowImageView;

    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    Calendar cal;
    public AddNewMassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("point");
        kind=getArguments().getString("kind");
        array=getArguments().getString("array");
        View view=inflater.inflate(R.layout.fragment_add_new_mass, container, false);
        cal=Calendar.getInstance();
        createViews(view);

        adapter=new MassCreationAdapter(forms,true);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        recyclerView.setAdapter(adapter);

        try {
            setInfo(new JSONArray(array));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        cancelOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });
        acceptOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIt();
            }
        });
        return view;
    }
    private void createViews(View view){
        progressFrame=(FrameLayout)view.findViewById(R.id.progressFrame);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);

        cancelOrderTextView=(TextView)view.findViewById(R.id.cancelOrderTextView);
        acceptOrderTextView=(TextView)view.findViewById(R.id.acceptOrderTextView);
        forms=new ArrayList<>();

        datePicker = (NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker = (NumberPicker) view.findViewById(R.id.yearPicker);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);
        dateArrowImageView = (ImageView) view.findViewById(R.id.dateArrowImageView);
        yearArrowImageView = (ImageView) view.findViewById(R.id.yearArrowImageView);

        pickerSettings();
        setListeners();
    }

    private void setInfo(JSONArray array){
        try{
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                JSONObject inventory = object.getJSONObject("inventory");
                // JSONObject company = inventory.getJSONObject("company");
                String name = inventory.getString("name"), id = object.getString("id"), unit =inventory.getJSONObject("unit").getString("name"), vendor_code = inventory.getString("vendor_code");
                int num = object.getInt("remainder"), lim=object.getInt("limit");
                int repair = object.getInt("repair"), replace = object.getInt("replace");
                List<RemontForms> remontForms = new ArrayList<>();
                if (repair > 0) {
                    RemontForms form = new RemontForms("На ремонте", repair);
                    remontForms.add(form);
                }
                if (replace > 0) {
                    RemontForms form = new RemontForms("На замене", replace);
                    remontForms.add(form);
                }
                MassCreationForm creationForm=new MassCreationForm(name,vendor_code,unit,0,num, lim);
                creationForm.setId(id);
                creationForm.setSet(true);
                forms.add(creationForm);
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void saveIt(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"replenishments/mass_creation/";
        JSONObject params=new JSONObject();
        try {
            Date date=new Date();
            String tex=((MainActivity)getActivity()).inputFormat.format(date);
            params.put("point",Integer.parseInt(((MainActivity)getActivity()).point));
            params.put("respondent",Integer.parseInt(((MainActivity)getActivity()).userid));
            params.put("deadline", tex);
            params.put("month",datePicker.getValue());
            params.put("year",yearPicker.getValue());
          //  Log.d("MONTH", datePicker.getValue()+" "+yearPicker.getValue());
            JSONArray array=new JSONArray();
            for(MassCreationForm form:forms){
                JSONObject object=new JSONObject();
                object.put("count",form.getTotal());
                object.put("consumption",Integer.parseInt(form.getId()));
                array.put(object);
            }
            params.put("orders",array);
        }
        catch (Exception e){

        }
        Log.d("para,s",params.toString());
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                Log.d("response",response.toString());
                Toast.makeText(getActivity(), "Заявка успешно создана", Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                if(error.networkResponse.statusCode==400){
                    Toast.makeText(getActivity(), "Вы превысили лимит", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(request);
    }


    private void pickerSettings() {
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(9);

        yearPicker.setMinValue(cal.get(Calendar.YEAR)-2);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+1);
        yearPicker.setValue(2018);
    }

    private void setListeners(){
        View.OnClickListener dateLi=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateClick();
            }
        };
        yearArrowImageView.setOnClickListener(dateLi);
        dateArrowImageView.setOnClickListener(dateLi);
        monthTextView.setOnClickListener(dateLi);
        yearTextView.setOnClickListener(dateLi);

    }
    private void dateClick(){
        if(datePicker.getVisibility()!=View.VISIBLE){
            dateArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            yearPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
            monthTextView.setVisibility(View.GONE);
            yearTextView.setVisibility(View.GONE);
        }
        else {
            dateArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            yearPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            monthTextView.setVisibility(View.VISIBLE);
            yearTextView.setVisibility(View.VISIBLE);

            monthTextView.setText(months[datePicker.getValue()-1]);
            yearTextView.setText(yearPicker.getValue()+"");

        }
    }
}
