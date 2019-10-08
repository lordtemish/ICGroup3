package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceChooseView extends FrameLayout{
    TextView name, nothing, attendanceTextView, replaceTextView;
    ArrayAdapter<String> adapter, pointAdapter, myAdapter, hoursAdapter;
    List<TextView> textViews,texts;
    List<ConstraintLayout> layouts;
    ConstraintLayout attendanceLayout, replaceLayout;
    Context context;
    Spinner spinner, spinner3, spinnerHours;
    List<RadioButton> buttons;
    FrameLayout saveLayout,spinnerFrame, spinnerHoursFrame, spinnerFrame3, progressFrame;
    List<String> zamena, zids, points, pids, myTypes, hours;
    List<Boolean> zamenaContracts;
    LinearLayout spinnerLinear2, spinnerLinear3,spinnerHoursLinear, zamenaLayout;
    int chose=0, location=1, point=0, shifts=0, hours_p=0;
    boolean replace=false, myObjects=true, is_contract=true, z_contract=true;
    String kind="";
    String id, pointid;

    public void setPointid(String pointid) {
        this.pointid = pointid;
    }

    LinearLayout radioGroup;
    RadioButton myObject, otherObject;

    public void setShifts(int shifts) {
        this.shifts = shifts;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
        getPoints();
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setZamena(List<String> zamena) {
   /*     this.zamena.clear();
        zids.clear();
        this.zamena.add("Выберите работника");
        for(String i:zamena) {
            String[] s=i.split(" ");
            String name="";
            String id=s[0];
            for(int iw=1;iw<s.length;iw++){
                name+=s[iw]+" ";
            }
            if(id.equals(this.id)) continue;
            this.zamena.add(name);
            zids.add(id);
        }*/
        setSpinner();
    }

    public List<String> getZamena() {
        return zamena;
    }

    public AttendanceChooseView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public AttendanceChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AttendanceChooseView(Context context) {
        super(context);
        initView();
    }
    private void initView(){
        zamena=new ArrayList<>();zids=new ArrayList<>();zamenaContracts=new ArrayList<>();
        points=new ArrayList<>();pids=new ArrayList<>();
        myTypes=new ArrayList<>();
        hours=new ArrayList<>();
        for(int i=0;i<12;i++){
            String s;
            int num=((i+1)*2);
            s=num+" час";
            if(num<6 || num>20) s+="а";
            else s+="ов";
            hours.add(s);
        }

        View view = inflate(getContext(), R.layout.attendance_choose_view, null);
        context=view.getContext();
        addView(view);
        createViews(view);

        attendanceLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                replace=false;
                setReplace();
            }
        });
        replaceLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                replace=true;
                setReplace();
            }
        });

        radioGroup.setVisibility(GONE);
        spinnerLinear2.setVisibility(GONE);
        spinnerLinear3.setVisibility(GONE);
    }
    private void createViews(View view){
        name=(TextView) view.findViewById(R.id.name);
        spinner=(Spinner) view.findViewById(R.id.spinner);
        spinner3=(Spinner) view.findViewById(R.id.spinner3);
        spinnerHours=(Spinner) view.findViewById(R.id.spinnerHours);
        nothing=(TextView)view.findViewById(R.id.nothing);

        radioGroup=(LinearLayout) view.findViewById(R.id.radioGroup);
        myObject=(RadioButton)view.findViewById(R.id.myObject);
        myAdapter=new ArrayAdapter<String>(context,R.layout.simple_spinner_item,myTypes);
        otherObject=(RadioButton)view.findViewById(R.id.otherObject);
        myObject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                myObjects=true;
                checkObjects();
            }
        });
        otherObject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                myObjects=false;
                checkObjects();
            }
        });

        attendanceTextView=(TextView)view.findViewById(R.id.attendanceTextView);
        replaceTextView=(TextView)view.findViewById(R.id.replaceTextView);

        attendanceLayout=(ConstraintLayout)view.findViewById(R.id.attendanceLayout);
        replaceLayout=(ConstraintLayout)view.findViewById(R.id.replaceLayout);


        texts=new ArrayList<>();textViews=new ArrayList<>();layouts=new ArrayList<>();buttons=new ArrayList<>();
        texts.add((TextView) view.findViewById(R.id.plusText));texts.add((TextView) view.findViewById(R.id.minusText));texts.add((TextView) view.findViewById(R.id.illText));texts.add((TextView) view.findViewById(R.id.replText));texts.add((TextView) view.findViewById(R.id.halfText));texts.add((TextView) view.findViewById(R.id.thirdText));
        textViews.add((TextView) view.findViewById(R.id.plusTextView));textViews.add((TextView) view.findViewById(R.id.minusTextView));textViews.add((TextView) view.findViewById(R.id.illTextView));textViews.add((TextView) view.findViewById(R.id.replTextView));textViews.add((TextView) view.findViewById(R.id.halfTextView));textViews.add((TextView) view.findViewById(R.id.thirdTextView));
        layouts.add((ConstraintLayout) view.findViewById(R.id.plusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.minusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.illLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.replLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.halfLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.thirdLayout));
        buttons.add((RadioButton) view.findViewById(R.id.plusRadio));buttons.add((RadioButton) view.findViewById(R.id.minusRadio));buttons.add((RadioButton) view.findViewById(R.id.illRadio));buttons.add((RadioButton) view.findViewById(R.id.replRadio));buttons.add((RadioButton) view.findViewById(R.id.halfRadio));buttons.add((RadioButton) view.findViewById(R.id.thirdRadio));
        saveLayout=(FrameLayout) view.findViewById(R.id.saveLayout);
        progressFrame=(FrameLayout) view.findViewById(R.id.progressFrame);
        spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrame);
        spinnerFrame3=(FrameLayout) view.findViewById(R.id.spinnerFrame3);
        spinnerHoursFrame=(FrameLayout) view.findViewById(R.id.spinnerHoursFrame);
        spinnerLinear2=(LinearLayout) view.findViewById(R.id.spinnerLinear2);
        spinnerLinear3=(LinearLayout) view.findViewById(R.id.spinnerLinear3);
        spinnerHoursLinear=(LinearLayout) view.findViewById(R.id.spinnerHoursLinear);
        zamenaLayout=(LinearLayout) view.findViewById(R.id.zamenaLayout);
        for(int i=0;i<6;i++){
            final int j=i;
            layouts.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    setChose(layouts.indexOf(view));
                }
            });
        }
        clearAll();setChose(0);
        checkObjects();
    }

    private void setReplace(){
        clearAll();
        if(!replace){
            attendanceLayout.setBackgroundResource(R.drawable.icgreen_page);
            attendanceTextView.setTextColor(getContext().getResources().getColor(R.color.white));
            replaceLayout.setBackgroundResource(R.drawable.whiterow_page);
            replaceTextView.setTextColor(getContext().getResources().getColor(R.color.black));

            for(int i=1;i<=3;i++)
                layouts.get(i).setVisibility(VISIBLE);

            spinnerLinear2.setVisibility(GONE);
            spinnerLinear3.setVisibility(GONE);
            radioGroup.setVisibility(GONE);
            setChose(0);
        }
        else{

            replaceLayout.setBackgroundResource(R.drawable.icgreen_page);
            replaceTextView.setTextColor(getContext().getResources().getColor(R.color.white));
            attendanceLayout.setBackgroundResource(R.drawable.whiterow_page);
            attendanceTextView.setTextColor(getContext().getResources().getColor(R.color.black));

            for(int i=1;i<=3;i++)
                layouts.get(i).setVisibility(GONE);

            spinnerLinear2.setVisibility(VISIBLE);
            spinnerLinear3.setVisibility(VISIBLE);
            radioGroup.setVisibility(VISIBLE);
            setChose(0);
        }
        checkContract();
    }

    public int getHours_p() {
        return (hours_p+1)*2;
    }

    public boolean isZ_contract() {
        return z_contract;
    }

    public boolean isIs_contract() {
        return is_contract;
    }

    private void checkContract(){
        if(is_contract){
            if(!replace)
            layouts.get(2).setVisibility(VISIBLE);

            layouts.get(4).setVisibility(VISIBLE);
            layouts.get(5).setVisibility(VISIBLE);
        }
        else {
            layouts.get(2).setVisibility(GONE);
            layouts.get(4).setVisibility(GONE);
            layouts.get(5).setVisibility(GONE);
        }

        if(replace && chose>0){
            boolean z_contract=zamenaContracts.get(chose-1);
            if(z_contract){
                spinnerHoursLinear.setVisibility(GONE);
                layouts.get(2).setVisibility(GONE);

                layouts.get(4).setVisibility(VISIBLE);
                layouts.get(5).setVisibility(VISIBLE);
            }
            else {
                spinnerHoursLinear.setVisibility(VISIBLE);

                layouts.get(2).setVisibility(GONE);
                layouts.get(4).setVisibility(GONE);
                layouts.get(5).setVisibility(GONE);
            }
            this.z_contract=z_contract;
        }
    }

    public void setKind(String kind) {
        this.kind = kind;
        checkKind();
    }
    private void checkKind(){
        if(kind.equals("INTERN")){
            zamenaLayout.setVisibility(GONE);
            spinnerHoursLinear.setVisibility(GONE);
            for(int i=1;i<=5;i++){
                layouts.get(i).setVisibility(GONE);
            }
        }
        else{
            zamenaLayout.setVisibility(VISIBLE);
            for(int i=1;i<=5;i++){
                layouts.get(i).setVisibility(VISIBLE);
            }
            setReplace();
        }
    }

    public void setIs_contract(boolean is_contract) {
        this.is_contract = is_contract;
        checkContract();
        clearAll();
        setChose(0);
    }

    public void checkObjects(){
        if(myObjects){
            myTypes.clear();
            myTypes.add("Выберите объект");
            myTypes.add("Сдельщик");
            for(int i=0;i<shifts;i++)
                myTypes.add((i+1)+" смена");
            myAdapter.notifyDataSetChanged();
            spinner3.setAdapter(myAdapter);
            getWorkers();
        }
        else{
            spinner3.setAdapter(pointAdapter);
        }
    }
    private void clearAll(){
        for(int i=0;i<6;i++){
                texts.get(i).setTextColor(getContext().getResources().getColor(R.color.darkgrey));
                layouts.get(i).setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
                buttons.get(i).setChecked(false);
        }
    }
    private void clearIt(int i){
        texts.get(i).setTextColor(getContext().getResources().getColor(R.color.darkgrey));
        layouts.get(i).setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
        buttons.get(i).setChecked(false);
    }
    private void setChose(int i){
        spinnerHoursLinear.setVisibility(GONE);
        setIt(i);
        /*if(i!=3) {
            setIt(i);
        }
        else{
            replace=!replace;
            if(replace) {
                setIt(3);
                spinnerLinear2.setVisibility(VISIBLE);
                spinnerLinear3.setVisibility(VISIBLE);
            }
            else{
                clearIt(3);
                spinnerLinear2.setVisibility(GONE);
                spinnerLinear3.setVisibility(GONE);
            }
        }*/
    }
    private void setIt(int i){
        Log.d("CHECKING", is_contract+" "+i);
        if(!is_contract && i==0)
            spinnerHoursLinear.setVisibility(VISIBLE);
        texts.get(i).setTextColor(getContext().getResources().getColor(R.color.black));
        layouts.get(i).setBackgroundResource(R.drawable.lightgreyrow_page);
        buttons.get(i).setChecked(true);
    }
    public int getCheckedPosition(){
        for(int i=0;i<buttons.size();i++){
            if(buttons.get(i).isChecked()){
                return i;
            }
        }
        return 0;
    }

    public boolean isReplace() {
        return replace;
    }

    public String getChose() {
        if(chose>0)
        return  zids.get(chose-1);
        else
            return "-1";
    }

    public void setListener(OnClickListener listener){
        saveLayout.setOnClickListener(listener);
    }
    private void setSpinner(){
        adapter=new ArrayAdapter<String>(context,R.layout.simple_spinner_item,zamena){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};


        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chose=i;
                if(chose>0)
                Log.d("CHOSECHOSE",chose+" "+zids.get(chose-1)+" "+getChose());
                checkContract();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        hoursAdapter=new ArrayAdapter<String>(context,R.layout.simple_spinner_item,hours){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }
        };
        spinnerHoursFrame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerHours.performClick();
            }
        });
        spinnerHours.setAdapter(hoursAdapter);
        spinnerHours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hours_p=i;
                Log.d("HOURS",i+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void  setPointSp(){
        pointAdapter=new ArrayAdapter<String>(context,R.layout.simple_spinner_item,points){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};

        spinnerFrame3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner3.performClick();
            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                point=i;
                getWorkers();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getPoints(){
        progressFrame.setVisibility(VISIBLE);
        String url=((MainActivity)context).MAIN_URL+"points/?location="+location;
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressFrame.setVisibility(GONE);
                setPoints(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressFrame.setVisibility(GONE);
                Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)context).token);
            return headers;
        }};((MainActivity)context).requestQueue.add(arrayRequest);
    }
    private void setPoints(JSONArray array){
        try {
            points.clear();pids.clear();
            points.add("Выберите объект");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String name=object.getString("name"), id=object.getString("id");
                pids.add(id);points.add(name);
            }
            chose=0;
            zamena.clear();zids.clear();
            setSpinner();
            setPointSp();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getWorkers(){
        chose=0;
        if(point<=0){
            Toast.makeText(context, "Выберите объект", Toast.LENGTH_SHORT).show();
        }
        else{
            progressFrame.setVisibility(VISIBLE);
            String url="";
            if(!myObjects)
                url=((MainActivity)context).MAIN_URL+"workers/?point="+pids.get(point-1);
            else{
                int item=spinner3.getSelectedItemPosition();
                url+=((MainActivity)context).MAIN_URL+"workers/?point="+pointid;
                if(item==1){
                    url+="&kind=PIECER";
                }
                else if(item>1){
                    url+="&shift="+(item-1);
                }
                else{
                    Toast.makeText(context, "Выберите объект", Toast.LENGTH_SHORT).show();
                }
            }
            Log.d("WORKERURL",url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressFrame.setVisibility(GONE);
                    setWorkers(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressFrame.setVisibility(GONE);
                    Toast.makeText(context, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
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
    private void setWorkers(JSONArray array){
        try {List<String> list=new ArrayList<>();
            zamena.clear();zids.clear();zamena.add("Выберите объект");
            for(int i=0;i<array.length();i++){
                JSONObject object=array.getJSONObject(i);
                String status=object.getString("status");
                boolean is_contract=object.getBoolean("is_contract");
                JSONObject user=object.getJSONObject("user");
                String id= object.getString("id"),name=user.getString("fullname");
                if(id.equals(this.id))
                    continue;
                Log.d("WORKER "+id+" : "+name,status);
                if(status.equals("STABLE"))
                {
                    zamena.add(name);zids.add(id);zamenaContracts.add(is_contract);
                }
            }
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){e.printStackTrace();}
    }

    public void setType(String s){

    }
}
