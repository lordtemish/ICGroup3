package com.studio.dynamica.icgroup.ObjectFragments;


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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CommentaryAdapter;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderInfoFragment extends Fragment {
    RecyclerView commentaryRecyclerView;
    LinearLayout statusLayout;
    TextView statusTextView, idTextView, priorityTextView, typeLabelTextView, typeTextView, fullnameTextView, departmentTextView, dateTextView
            ,dayLeftTextView, kolvoTextView, unitTextView, equipmentNameTextView, vendorTextView, num1TextView, num2TextView,bottomTextVoew;
    ImageView bottomGalochka;
    FrameLayout progressFrame;
    String id;
    int d1,d2;
    ProgressBar ProgressBar, ProgressBar1;
    public AddOrderInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        d1=getArguments().getInt("day1");d2=getArguments().getInt("day2");
        View view=inflater.inflate(R.layout.fragment_add_order_info, container, false);
        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(commentaryRecyclerView, LinearLayoutManager.VERTICAL);
        //CommentaryForm commentaryForm=new CommentaryForm("25.07.2018","Temirlan ALmassov",getActivity().getResources().getString(R.string.bigtext));
        CommentaryAdapter commentaryAdapter=new CommentaryAdapter(new ArrayList<CommentaryForm>());
        commentaryRecyclerView.setAdapter(commentaryAdapter);
    //    setStatus(getArguments().getString("status", ""));
        getReplenishment();

        int num=0;
        if(d2>0){
            num=Integer.parseInt(Math.round(Double.parseDouble(d1+"")/Double.parseDouble(d2+"")*100)+"");
        }
        ProgressBar.setProgress(num);
        dayLeftTextView.setText("Осталось дней: "+d1+"/"+d2);
        return view;
    }
    private void createViews(View view){
        commentaryRecyclerView=(RecyclerView) view.findViewById(R.id.commentaryRecyclerView);
        statusLayout=(LinearLayout) view.findViewById(R.id.statusLayout);

        ProgressBar=(ProgressBar)view.findViewById(R.id.ProgressBar);
        ProgressBar1=(ProgressBar)view.findViewById(R.id.ProgressBar1);

        statusTextView=(TextView) view.findViewById(R.id.statusTextView);
        idTextView=(TextView) view.findViewById(R.id.idTextView);
        priorityTextView=(TextView) view.findViewById(R.id.priorityTextView);
        typeLabelTextView=(TextView) view.findViewById(R.id.typeLabelTextView);
        typeTextView=(TextView) view.findViewById(R.id.typeTextView);
        fullnameTextView=(TextView) view.findViewById(R.id.fullnameTextView);
        departmentTextView=(TextView) view.findViewById(R.id.departmentTextView);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);
        dayLeftTextView=(TextView) view.findViewById(R.id.dayLeftTextView);
        kolvoTextView=(TextView) view.findViewById(R.id.kolvoTextView);
        unitTextView=(TextView) view.findViewById(R.id.unitTextView);
        equipmentNameTextView=(TextView) view.findViewById(R.id.equipmentNameTextView);
        vendorTextView=(TextView) view.findViewById(R.id.vendorTextView);
        num1TextView=(TextView) view.findViewById(R.id.num1TextView);
        num2TextView=(TextView) view.findViewById(R.id.num2TextView);
        bottomTextVoew=(TextView) view.findViewById(R.id.bottomTextVoew);
        bottomGalochka=(ImageView) view.findViewById(R.id.bottomGalochka);

        progressFrame=(FrameLayout) view.findViewById(R.id.progressFrame);
    }

    private void setStatus(String s){
        switch (s){
            case "PROCESSING":
                statusLayout.setBackgroundResource(R.drawable.icgreen_page);
                statusTextView.setText("Актуально");
                bottomTextVoew.setText("Актуально");
                bottomGalochka.setBackgroundResource(R.drawable.green_circle);
                break;
            case "accepted":
                statusLayout.setBackgroundResource(R.drawable.failedrow_green);
                statusTextView.setText("Выполненно");
                break;
            case "WAITING":
                statusLayout.setBackgroundResource(R.drawable.inwait_yellowpage);
                statusTextView.setText("Ожидает");
                bottomTextVoew.setText("Ожидает");
                bottomGalochka.setBackgroundResource(R.drawable.yellow_circle);
                break;
            case "FINISHED":
                statusLayout.setBackgroundResource(R.drawable.closed_page);
                statusTextView.setText("Завершенно");
                bottomTextVoew.setText("Завершенно");
                bottomGalochka.setBackgroundResource(R.drawable.closed_circle);
                break;
            case "timeout":
                statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                statusTextView.setText("Время вышло");
                break;
            case "CLOSED":
                statusLayout.setBackgroundResource(R.drawable.closed_page);
                statusTextView.setText("Закрыто");
                break;
            case "FAILED":
                statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                statusTextView.setText("Провалено");
                bottomTextVoew.setText("Провалено");
                bottomGalochka.setBackgroundResource(R.drawable.related_darkgreen_circle);
                break;
        }
    }

    private void getReplenishment(){
        progressFrame.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"replenishments/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressFrame.setVisibility(View.GONE);
                setReplenishment(response);
                Log.d("RespaAddOrder",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){@Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT " + ((MainActivity) getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setReplenishment(JSONObject object){
        try {
            String id=object.getString("id"), status=object.getString("status"), kind=object.getString("kind"), created_at=object.getString("created_at");
            int priority=object.getInt("priority"), count=object.getInt("count");
            String prio="Низкий",invKind=((MainActivity)getActivity()).replKinds.get(kind), created=((MainActivity)getActivity()).getdate(created_at);
            created=created.substring(0,created.length()-6);
            JSONObject respondent=object.getJSONObject("respondent"),author=object.getJSONObject("author"), consumption=object.getJSONObject("consumption");
            JSONObject inventory=consumption.getJSONObject("inventory");
            String name=inventory.getString("name"),vendor=inventory.getString("vendor_code"), unit=inventory.getJSONObject("unit").getString("name");
            int quantity=consumption.getInt("quantity"),remainder=consumption.getInt("remainder");
            String invUnit=unit;


            if(priority>1){
                prio="Средний";
                if(priority>2){
                    prio="Высокий";
                }
            }
                setStatus(status);
            dateTextView.setText(created);
            idTextView.setText("IC"+id);
            priorityTextView.setText(prio);
            typeTextView.setText(invKind);
            fullnameTextView.setText(respondent.getString("fullname"));
            unitTextView.setText(invUnit);
            kolvoTextView.setText(count+"");
            vendorTextView.setText(vendor);
            equipmentNameTextView.setText(name);
            int n=0;
            num1TextView.setText(0+"/");
            num2TextView.setText(""+0);
            if(quantity>0 && quantity>=remainder){
                n=Integer.parseInt(""+Math.round(Double.parseDouble(""+(quantity-remainder))/Double.parseDouble(quantity+"")*100));
                num1TextView.setText(quantity-remainder+"/");
                num2TextView.setText(""+quantity);
            }
            ProgressBar1.setProgress(n);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
