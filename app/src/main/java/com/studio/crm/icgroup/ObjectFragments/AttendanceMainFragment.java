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
import com.google.gson.Gson;
import com.shawnlin.numberpicker.NumberPicker;
import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.AttendanceAdapter;
import com.studio.crm.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.crm.icgroup.ExtraFragments.AttendanceChooseView;
import com.studio.crm.icgroup.Forms.AttendanceRowForm;
import com.studio.crm.icgroup.Forms.AttendanceRowItemForm;
import com.studio.crm.icgroup.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttendanceMainFragment extends Fragment {
    List<AttendanceRowForm> rowForms;
    List<String> pages;
    EquipmentReqAdapter reqAdapter;
    FrameLayout todayFrame, progressLayout;
    ConstraintLayout smenaLayout, buttonLayout;
    LinearLayout hiddenLayout, statisticLayout;
    RecyclerView recyclerView, reqRecycler ;
    AttendanceChooseView attendanceChooseView;
    FrameLayout chooseLayout;
    View.OnClickListener emptyL, postL;
    NumberPicker datePicker, yearPicker;
    TextView mainObjectTitle, yearTextView, monthTextView, smenasTextView, smenaCountTextView, totalDays,allWorkers, absents, heres;
    ImageView dateArrowImageView, yearArrowImageView, leftCalArrow, rightCalArrow, ImageRightView, ImageLeftView, buttonImage;
    Calendar cal, cal2;
    List<String> strings;
    List<TextView> calTextViews;
    boolean swiped=false, today=true, working=false, start=true;
    String id="", choseworker="";
    List<JSONObject> atts;
    String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"}
        , weeks={"Пн","Вт","Ср","Чт","Пт","Сб","Вс"}, rolePages={"ОПУ/ОПУПТ","Адм. блок", "Сдельщики"}
    ;
    int shift_count=0,shift_count1=0,shift=1, location=1, shifts=0, janitor_shifts_count=0, gardener_shifts_count=0, plumber_shifts_count=0, electrician_shifts_count=0, page=0;
    AttendanceAdapter attendanceAdapter;


    public AttendanceMainFragment() {
        // Required empty public constructor
    }

    private void checkRole(){
        String role=((MainActivity)getActivity()).role;
        if(role.equals("SUPERADMIN") || role.contains("ADMIN_")){
            statisticLayout.setVisibility(View.VISIBLE);
        }
        else{
            statisticLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        id=getArguments().getString("id");
        shift_count=getArguments().getInt("shift_count");
        shift_count1=shift_count;
        janitor_shifts_count=getArguments().getInt("janitor_shifts_count");
        gardener_shifts_count=getArguments().getInt("gardener_shifts_count");
        plumber_shifts_count=getArguments().getInt("plumber_shifts_count");
        electrician_shifts_count=getArguments().getInt("electrician_shifts_count");
        shifts=shift_count*2;
        location=getArguments().getInt("location",1);
        atts=new ArrayList<>();
        cal=Calendar.getInstance();
        cal2=Calendar.getInstance();
        cal.setTime(new Date());
        View view = inflater.inflate(R.layout.fragment_attendance_main, container, false);
        createViews(view);

        checkRole();


        chooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseLayout.setVisibility(View.GONE);
            }
        });

        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(reqRecycler, LinearLayoutManager.HORIZONTAL);
        rowForms = new ArrayList<>();
        List<AttendanceRowItemForm> itemForms = new ArrayList<>();
        final int d=25;
        AttendanceRowItemForm itemForm=new AttendanceRowItemForm("",true, false, false);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setChooseLayout("");
            }
        };
        itemForms.add(itemForm);
        itemForms.add(new AttendanceRowItemForm("",true, false, false));
        itemForms.add(new AttendanceRowItemForm("",false, true, true));
        itemForms.add(new AttendanceRowItemForm("",false, true, false));
        itemForms.add(new AttendanceRowItemForm(""));
        AttendanceRowForm attendanceRowForm=new AttendanceRowForm("1","Темирлан", itemForms, 5, 7);
        attendanceRowForm.setListener(listener);
        rowForms.add(attendanceRowForm);
        rowForms.add(new AttendanceRowForm("2","Темирлан Алмасов", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("3","Темирлан Алмасов Даулетович", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("4","Надира Рыскулова", itemForms, 5, 7));
        rowForms.add(new AttendanceRowForm("5","Рыскулова Надира", itemForms, 5, 7));


        attendanceAdapter = new AttendanceAdapter(rowForms);
        recyclerView.setAdapter(attendanceAdapter);

        reqAdapter=new EquipmentReqAdapter(pages);
        reqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 checkReq();
            }
        });
        reqRecycler.setAdapter(reqAdapter);

        buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHidden();
            }
        });
        showHidden();

        pickerSettings();
        setDate(true);
        setFonttypes();
        setListeners();

        chooseLayout.setVisibility(View.GONE);

        ImageLeftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(-1);
            }
        });
        ImageRightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(+1);
            }
        });
        changePage(0);
        getPoint();

        attendanceChooseView.setLocation(location);
        return view;
    }
    private void checkReq(){
            Log.d("THIS PAGE", reqAdapter.getClicked()+"");
            int page=reqAdapter.getClicked();

            switch (page){
                case 0:
                    shifts=shift_count;
                    shift=1;
                    break;
                case 1:
                    shifts=shift_count;
                    shift=1;
                    break;
             /*   case 3:
                    if(janitor_shifts_count>0) {
                        shifts = janitor_shifts_count * 2;
                        shift = 1;
                    }
                    else{
                        reqAdapter.setClicked(0);
                        checkReq();
                    }
                    break;
                case 4:
                    if(gardener_shifts_count>0) {
                        shifts = gardener_shifts_count * 2;
                        shift = 1;
                    }
                    else {
                        reqAdapter.setClicked(0);
                        checkReq();
                    }
                    break;
                case 5:
                    if(plumber_shifts_count>0) {
                        shifts = plumber_shifts_count * 2;
                        shift = 1;
                    }
                    else{
                        reqAdapter.setClicked(0);
                        checkReq();
                    }
                    break;
                case 6:
                    if(electrician_shifts_count>0)
                        {
                            shifts = electrician_shifts_count * 2;
                            shift = 1;
                        }
                    else {
                        reqAdapter.setClicked(0);
                        checkReq();
                    }
                    break;*/
            }
            changePage(0);
    }
    private void changePage(int a){
        page+=a;
        if(page==-1){
            page=2;
        }
        if(page==3){
            page=0;
        }
        checkRoles();
    }
    private void checkRoles(){
        smenasTextView.setText(rolePages[page]);
        Log.d("QWEQEWQEW", rolePages[0]+" "+rolePages[1]+" "+rolePages[2]);
        if(page==0){
            reqRecycler.setVisibility(View.VISIBLE);
        }
        else{
            reqRecycler.setVisibility(View.GONE);
        }
        checkPage();
    }
    private void checkPage(){
        shift=reqAdapter.getClicked()+1;

        String shift="";
        if(page==0)shift+="&shift=" + (this.shift);
        String s="workers/?point=" + id + shift;
        getRequest(s);

        //}
       /*
           int page=reqAdapter.getClicked();
        smenaLayout.setVisibility(View.VISIBLE);
        smenasTextView.setText("Смена " + shift);
                getRequest("workers/?point=" + id + "&shift=" + shift);

        else if(page==1 || page==2){
            smenaLayout.setVisibility(View.GONE);
            switch (page){
                case 1:
                    getRequest("workers/?point=" + id + "&keep=PIECER");
                    break;
                    default:
                        getRequest("workers/?point=" + id + "&keep=INTERN");

            }
        }
        else{
            smenaLayout.setVisibility(View.VISIBLE);
            switch (page){
                case 3:

                    if(shift<=janitor_shifts_count) {
                        smenaLayout.setVisibility(View.VISIBLE);
                        smenasTextView.setText("Дневная смена " + shift);
                        getRequest("workers/?point=" + id + "&shift=" + shift + "&is_night=False&kind=JANITOR");
                    }
                    else{
                        smenasTextView.setText("Ночная смена " + (shift-janitor_shifts_count));
                        getRequest("workers/?point=" + id + "&shift=" + (shift-janitor_shifts_count) + "&is_night=True&kind=JANITOR");
                    }
                    break;
                case 4:
                    if(shift<=gardener_shifts_count) {
                        smenaLayout.setVisibility(View.VISIBLE);
                        smenasTextView.setText("Дневная смена " + shift);
                        getRequest("workers/?point=" + id + "&shift=" + shift + "&is_night=False&kind=GARDENER");
                    }
                    else{
                        smenasTextView.setText("Ночная смена " + (shift-gardener_shifts_count));
                        getRequest("workers/?point=" + id + "&shift=" + (shift-gardener_shifts_count) + "&is_night=True&kind=GARDENER");
                    }
                    break;
                case 5:
                    if(shift<=plumber_shifts_count) {
                        smenaLayout.setVisibility(View.VISIBLE);
                        smenasTextView.setText("Дневная смена " + shift);
                        getRequest("workers/?point=" + id + "&shift=" + shift + "&is_night=False&kind=PLUMBER");
                    }
                    else{
                        smenasTextView.setText("Ночная смена " + (shift-plumber_shifts_count));
                        getRequest("workers/?point=" + id + "&shift=" + (shift-plumber_shifts_count) + "&is_night=True&kind=PLUMBER");
                    }
                    break;
                    default:
                        if(shift<=electrician_shifts_count) {
                            smenaLayout.setVisibility(View.VISIBLE);
                            smenasTextView.setText("Дневная смена " + shift);
                            getRequest("workers/?point=" + id + "&shift=" + shift + "&is_night=False&kind=ELECTRICIAN");
                        }
                        else{
                            smenasTextView.setText("Ночная смена " + (shift-electrician_shifts_count));
                            getRequest("workers/?point=" + id + "&shift=" + (shift-electrician_shifts_count) + "&is_night=True&kind=ELECTRICIAN");
                        }

            }
        }*/
        // shifts- month year point

       /* if(today){  // if needed put it
            getShifts();
        }*/
    }
    public void setChooseLayout(String id, String kind, boolean is_contract){
        chooseLayout.setVisibility(View.VISIBLE);
        choseworker=id;
        attendanceChooseView.setId(id);
        attendanceChooseView.checkObjects();
        attendanceChooseView.setIs_contract(is_contract);
        attendanceChooseView.setKind(kind);
      //  Log.d("worker",choseworker);
    }
    private void postRequest(){
        if(attendanceChooseView.getChose().equals("-1") && attendanceChooseView.isReplace()){
            Toast.makeText(getActivity(), "Выберите заменяющего", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = ((MainActivity) getActivity()).MAIN_URL + "visits/";
            try {
                progressLayout.setVisibility(View.VISIBLE);
                JSONObject params = new JSONObject();
                int i = attendanceChooseView.getCheckedPosition();
                String s = "PRESENT";
                boolean repl=attendanceChooseView.isReplace(), contract=attendanceChooseView.isIs_contract(), z_contract=attendanceChooseView.isZ_contract();
                String st = attendanceChooseView.getChose();
                switch (i) {
                    case 1:
                        s = "ABSENT";
                        break;
                    case 2:
                        s = "ILL";
                        break;
                    case 3:
                        //change it
                        s = "CELEB";
                       /* String st = attendanceChooseView.getChose();
                        if (!st.equals("-1"))
                            params.put("replacer", st);
*/
                        break;
                    case 4:
                        s = "HALF";
                        break;
                    case 5:
                        s = "THIRD";
                        break;
                    default:
                        s = "FULL";
                }
                params.put("worker", choseworker);
                if(!repl) {
                    if(i==0 && !contract){
                        params.put("kind","HOURS");
                        params.put("hours",attendanceChooseView.getHours_p());
                    }
                    else
                    params.put("kind", s);
                }
                else{
                    params.put("kind","REPLACE");
                    if(z_contract)
                        params.put("replacer_kind",s);
                    else{
                        params.put("replacer_kind","HOURS");
                        params.put("hours",attendanceChooseView.getHours_p());
                    }
                    if (!st.equals("-1"))
                        params.put("replacer", st);
                }
                params.put("salary",0);
                Log.d("parametes", params.toString());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressLayout.setVisibility(View.GONE);
                        chooseLayout.setVisibility(View.GONE);
                        checkPage();
                        Log.d("resp", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
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

                ((MainActivity) getActivity()).requestQueue.add(jsonObjectRequest);
            } catch (Exception e) {

            }
        }
    }

    private void getRequest(String s){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+s+"&status=STABLE";
        Log.d("Attendance URL", url);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressLayout.setVisibility(View.GONE);
                setInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы подключения", Toast.LENGTH_LONG).show();
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
    private List<AttendanceRowItemForm> getEmptyList(){
        List<AttendanceRowItemForm> itemForms=new ArrayList<>();
        Calendar cal=Calendar.getInstance();
                cal.setTime(this.cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR,-4);
        SimpleDateFormat dateFormat=((MainActivity)getActivity()).visitFormat;
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,+1);
        itemForms.add(new AttendanceRowItemForm(dateFormat.format(cal.getTime())));
        cal.add(Calendar.DAY_OF_YEAR,-1);
        return itemForms;
    }
    private int getKindPage(String s){
        //Для сдельщика еще один пункт
        if(s.equals("OPU") || s.equals("JANITOR") || s.equals("INTERN")){
            return 0;
        }
        else if(s.equals("PIECER"))
            return 2;
        else return 1;
    }
    private void setInfo(JSONArray array){
        progressLayout.setVisibility(View.VISIBLE);
        rowForms.clear();
        strings=new ArrayList<>();
        Calendar calendar=Calendar.getInstance();calendar.setTime(new Date());
        int count=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        smenaCountTextView.setText( (shift)+"/"+shift_count);
       // totalDays.setText(count+"");
        Log.d("COUNTCOUNT",count+" ");
        int page=reqAdapter.getClicked();
        HashMap<String,List<AttendanceRowForm>> map=new HashMap<>();
        for(int i=0;i<array.length();i++){
            try {
                JSONObject object = array.getJSONObject(i);
                String kind=object.getString("kind");
                boolean is_contract=object.getBoolean("is_contract");
                if(!map.containsKey(kind)){
                    map.put(kind, new ArrayList<AttendanceRowForm>());
                }
                int kp=getKindPage(kind);
                if(kp!= page)
                    continue;
                String status=object.getString("status");
                JSONObject user=object.getJSONObject("user");

                boolean show=false;
                switch (this.page){
                    case 0:
                        if(kind.equals("OPU") || kind.equals("JANITOR") || kind.equals("INTERN")){
                            show=true;
                        }
                        break;
                    case 1:
                        if(kind.equals("GARDENER") || kind.equals("PLUMBER") || kind.equals("ELECTRICIAN") || kind.equals("DRIVER")){
                            show=true;
                        }
                        break;
                    case 2:
                        if(kind.equals("PIECER")){
                            show=true;
                        }
                        break;
                }
                Log.d("WORKER "+object.getString("id"),"boolean "+show);
                if(status.equals("STABLE") && show) {
                    //((MainActivity)getActivity()).workerKinds.get(kind)
                    AttendanceRowForm rowForm = new AttendanceRowForm(object.getString("id"), user.getString("fullname"), getEmptyList(), 0, 0);
                    rowForm.setKind(kind);
                    rowForm.setContract(is_contract);
                    map.get(kind).add(rowForm);
                    // rowForms.add(rowForm);
                  //      strings.add(rowForm.getId() + " " + rowForm.getName());
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        for(String key:map.keySet()){
            if(map.get(key).size()>0)
            rowForms.add(new AttendanceRowForm(((MainActivity)getActivity()).workerKinds.get(key)));
            for(AttendanceRowForm i:map.get(key)){
                rowForms.add(i);
            }
        }
     //   attendanceChooseView.setZamena(strings);
        attendanceAdapter.notifyDataSetChanged();
        String url=((MainActivity)getActivity()).MAIN_URL+"visits/?worker__point="+id;
        Log.d("this url",url);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                setAttReq(response);
                progressLayout.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Проблемы подключения", Toast.LENGTH_LONG).show();
            }
        }){  @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};
        ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
    }
    private void dateAttCheck(){
        for(AttendanceRowForm form:rowForms){
            if(form.isText())
                continue;
            else {
                Log.d("Booleans", working + " " + today + " " + form.getRowForms().get(3).isNothing());
                if (/*working && */today) {
                    final String id = form.getId(), kind=form.getKind();

                    if(kind.equals(null)){
                        Log.d("NULL", new Gson().toJson(form));
                    }


                    final boolean is_contract=form.isContract();
                    form.setListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setChooseLayout(id, kind, is_contract);
                            Log.d("THIS ID", id + "");
                            //     attendanceChooseView.setZamena(strings);
                        }
                    });
                }
           /* else{
                //form.setFucked(true);
                //form.setListener(emptyL);
            }*/
                form.setRowForms(getEmptyList());
            }
        }
        checkItemForms();
    }
    private void setAttReq(JSONArray array){
        try {
            int abs=0, acc=0;
            Calendar calendar=Calendar.getInstance();
            calendar.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1,0,0,0);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
               // Log.d("kind "+i, object.getString("kind"));
                atts.add(object);
                String kind=object.getString("kind");
                String date=object.getString("date"),month=cal.get(Calendar.MONTH)+1+"";
                if (month.length()==1) month="0"+month;

                if(date.substring(0,4).equals(""+cal.get(Calendar.YEAR)) && date.substring(5,7).equals(""+(month))) {
                    if (kind.contains("ABS") || kind.equals("ILL")) {
                        abs++;
                    } else {
                        acc++;
                    }
                }
            }
            Log.d("Attendance", "Total: "+array.length()+"  absents: "+abs+"  here: "+acc);
            absents.setText(abs+"");
            heres.setText(acc+"");
            dateAttCheck();
        }
        catch (Exception e){
            progressLayout.setVisibility(View.GONE);
        }
    }
    private void checkItemForms(){
        try {
            for (JSONObject object : atts) {
                String date=object.getString("date");
                String kind=object.getString("kind");

               // JSONObject worker=object.getJSONObject("worker");
                String id=object.getString("worker");//worker.getString("id");
                for(AttendanceRowForm ro:rowForms){
                    if(!ro.isText())
                    if(ro.getId().equals(id)){
                        int dd=0;
                        for(AttendanceRowItemForm itemForm:ro.getRowForms()){
                            if(itemForm.getDay().equals(date)){
                                Log.d("Dateid",id+" '"+date);
                                if(dd==3) {
                                    ro.setListener(emptyL);
                                }
                                dd++;
                                Log.d("attendancefound",id+" "+date+" "+kind);
                                itemForm.setAll();
                                switch (kind){
                                    case "CELEB":
                                        itemForm.setHoliday(true);
                                        break;
                                    case "REPLACE":
                                        itemForm.setReplace(true);
                                        break;
                                    case "ABSENT":
                                        itemForm.setAbsent(true);
                                        break;
                                    case "ILL":
                                        itemForm.setIll(true);
                                        break;
                                    case "HALF":
                                        itemForm.setHalf(true);
                                        break;
                                    case "THIRD":
                                        itemForm.setThird(true);
                                        break;
                                        default:
                                            itemForm.setPlus(true);
                                            break;
                                }
                                break;
                            }

                        }
                        break;
                    }
                }
            }
            attendanceAdapter.notifyDataSetChanged();
            if(start){
                start=false;
                dateChange(true);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void onSwipe(){
        swiped=true;
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
    private void dateChange(boolean a){
        int day=cal.get(Calendar.DAY_OF_MONTH);
        if(a){
            cal.add(Calendar.DAY_OF_YEAR,+5);
        }
        else {
            cal.add(Calendar.DAY_OF_YEAR,-5);
        }
        setDate(a);
    }
    private void setDate(boolean pl){

        cal2.setTime(new Date());
        if(cal.getTimeInMillis()>cal2.getTimeInMillis()){
            cal.setTime(cal2.getTime());
        }
        if(cal2.get(Calendar.DAY_OF_YEAR)==cal.get((Calendar.DAY_OF_YEAR))){
            setToday(true);
            attendanceAdapter.setToday(true);
            attendanceAdapter.notifyDataSetChanged();
        }
        else{
            setToday(false);
            attendanceAdapter.setToday(false);
            attendanceAdapter.notifyDataSetChanged();
        }
        datePicker.setValue(cal.get(Calendar.MONTH)+1);
        yearPicker.setValue(cal.get(Calendar.YEAR));
        monthTextView.setText(months[datePicker.getValue()-1]);
        yearTextView.setText(yearPicker.getValue()+"");

        cal.add(Calendar.DAY_OF_YEAR,1);
        int da=cal.get(Calendar.DAY_OF_MONTH);

        Log.d("DAY CHECKING", "DAY: "+da+"   "+(da<=6 && !pl)+"   "+(da>=0 && da<=5 && pl));
        for(int i=4;i>=0;i--){
            int day=cal.get(Calendar.DAY_OF_WEEK)-2;
            if(day==-1){
                day=6;
            }
            calTextViews.get(i).setText(cal.get(Calendar.DAY_OF_MONTH)+"\n"+(weeks[(day)%7]));
//            Log.d("day"+i,((cal.get(Calendar.DAY_OF_WEEK)-2)%7)+" ");
            cal.add(Calendar.DAY_OF_YEAR,-1);
        }
        cal.add(Calendar.DAY_OF_YEAR,+4);
        dateAttCheck();
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
            if(swiped){
                cal.set(yearPicker.getValue(),datePicker.getValue()-1,1);
                Log.d("Calendar info",yearPicker.getValue()+" "+datePicker.getValue()+" "+cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                cal.set(yearPicker.getValue(),datePicker.getValue()-1,cal.getMaximum(Calendar.DAY_OF_MONTH));
                setDate(true);
                swiped=false;
            }
        }
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

        leftCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(false);
            }
        });
        rightCalArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateChange(true);
            }
        });

        NumberPicker.OnValueChangeListener swipe=new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                onSwipe();
            }
        };
        datePicker.setOnValueChangedListener(swipe);
        yearPicker.setOnValueChangedListener(swipe);
    }
    private void setToday(boolean f){
        today=f;
        if(f){
            calTextViews.get(3).setBackgroundResource(R.drawable.green_circle);
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.white));
            todayFrame.setVisibility(View.VISIBLE);
        }
        else {
            calTextViews.get(3).setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
            calTextViews.get(3).setTextColor(getActivity().getResources().getColor(R.color.black));
            todayFrame.setVisibility(View.GONE);
        }
    }
    private void showHidden(){
        if(hiddenLayout.getVisibility()==View.VISIBLE){
            hiddenLayout.setVisibility(View.GONE);
            buttonImage.setImageResource(R.drawable.ic_arrowup_green);
            //buttonLayout.setBackgroundResource(R.drawable.wh);
        }
        else{
            buttonImage.setImageResource(R.drawable.ic_arrowdown_green);
            hiddenLayout.setVisibility(View.VISIBLE);

        }
    }
    private void createViews(View view) {
        chooseLayout=(FrameLayout) view.findViewById(R.id.chooseLayout);
        datePicker = (NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker = (NumberPicker) view.findViewById(R.id.yearPicker);
        recyclerView = (RecyclerView) view.findViewById(R.id.mainRecyclerView);
        reqRecycler = (RecyclerView) view.findViewById(R.id.reqRecycler);
        mainObjectTitle = (TextView) view.findViewById(R.id.mainObjectTitle);
        buttonImage = (ImageView) view.findViewById(R.id.buttonImage);
        dateArrowImageView = (ImageView) view.findViewById(R.id.dateArrowImageView);
        yearArrowImageView = (ImageView) view.findViewById(R.id.yearArrowImageView);
        leftCalArrow = (ImageView) view.findViewById(R.id.leftCalArrow);
        rightCalArrow = (ImageView) view.findViewById(R.id.rightCalArrow);
        ImageLeftView = (ImageView) view.findViewById(R.id.ImageLeftView);
        ImageRightView = (ImageView) view.findViewById(R.id.ImageRightView);
        monthTextView=(TextView) view.findViewById(R.id.monthTextView);
        yearTextView=(TextView) view.findViewById(R.id.yearTextView);
        smenasTextView=(TextView) view.findViewById(R.id.smenasTextView);
        smenaCountTextView=(TextView) view.findViewById(R.id.smenaCountTextView);
        allWorkers=(TextView) view.findViewById(R.id.allWorkers);
        absents=(TextView) view.findViewById(R.id.absents);
        heres=(TextView) view.findViewById(R.id.heres);
        totalDays=(TextView) view.findViewById(R.id.totalDays);

        attendanceChooseView=(AttendanceChooseView) view.findViewById(R.id.attendanceChooseView);
        attendanceChooseView.setShifts(shift_count);
        attendanceChooseView.setPointid(id);

        calTextViews=new ArrayList<>();
        calTextViews.add((TextView) view.findViewById(R.id.f1CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f2CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f3CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f4CalTextView));
        calTextViews.add((TextView) view.findViewById(R.id.f5CalTextView));

        statisticLayout=(LinearLayout) view.findViewById(R.id.statisticLayout);

        hiddenLayout=(LinearLayout) view.findViewById(R.id.hiddenLayout);
        todayFrame=(FrameLayout) view.findViewById(R.id.todayFrame);

        progressLayout=(FrameLayout) view.findViewById(R.id.progressLayout);
        smenaLayout=(ConstraintLayout) view.findViewById(R.id.smenaLayout);
        buttonLayout=(ConstraintLayout)view.findViewById(R.id.buttonLayout);


        emptyL=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        postL=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postRequest();
            }
        };
        attendanceChooseView.setListener(postL);

        pages=new ArrayList<>();
        for(int i=0;i<shift_count;i++){
            pages.add("Смена "+(i+1));
        }
        /*pages.add("ОПУ/ОПУ ПТ");
        pages.add("Адм Блок");
        pages.add("Сдельщик");*/
       /* pages.add("Стажировщик");
        pages.add("Дворник");
        pages.add("Садовник");
        pages.add("Сантехник");
        pages.add("Электрик");*/
    }
    private void setFonttypes(){
        setTypeFace("demibold", monthTextView, yearTextView);
        setTypeFace("light");
        setTypeFace("it", mainObjectTitle);
    }
    private void setTypeFace(String s, TextView... textViews) {
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setTypeface(((MainActivity) getActivity()).getTypeFace(s));
        }
    }

    private void getPoint(){
        progressLayout.setVisibility(View.VISIBLE);
        String url=((MainActivity)getActivity()).MAIN_URL+"points/"+id;
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            progressLayout.setVisibility(View.GONE);
            setPoint(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressLayout.setVisibility(View.GONE);
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
            return headers;
        }};((MainActivity)getActivity()).requestQueue.add(objectRequest);
    }
    private void setPoint(JSONObject object){
        try {
            int worker=object.getInt("workers_count");
            allWorkers.setText(worker+"");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getShifts(){
        try{
            progressLayout.setVisibility(View.VISIBLE);
            String url=((MainActivity)getActivity()).MAIN_URL+"shifts/?point="+id+"&month="+(cal.get(Calendar.MONTH)+1)+"&year="+cal.get(Calendar.YEAR)+"&shift="+shift;
            Log.d("url it is", url);
            JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    progressLayout.setVisibility(View.GONE);
                    setShifts(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                }
            }){@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", "JWT "+((MainActivity)getActivity()).token);
                return headers;
            }};
            ((MainActivity)getActivity()).requestQueue.add(arrayRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setShifts(JSONArray array){
        try{
            if(array.length()>0) {
                JSONObject object = array.getJSONObject(0);
                JSONArray days=object.getJSONArray("days");
                totalDays.setText(days.length()+"");
                setCheck(days);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setCheck(JSONArray array){
        try {
            int day=cal.get(Calendar.DAY_OF_MONTH);
            Log.d("Today day",day+" ");
           // boolean has=false;

           /* for(int i=0;i<array.length();i++){
                int d=array.getInt(i);
                if (d==day){
                    has=true;
                    break;
                }
            }*/

            dateAttCheck();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}