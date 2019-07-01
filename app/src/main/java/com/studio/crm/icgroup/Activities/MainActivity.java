package com.studio.crm.icgroup.Activities;
// https://crm.ic-group.kz
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.studio.crm.dummy.AppStatus;
import com.studio.crm.icgroup.Adapters.FilesAdapter;
import com.studio.crm.icgroup.ClientFragments.ClientsMainFragment;
import com.studio.crm.icgroup.EmployeesFragment.EmployeesMainFragment;
import com.studio.crm.icgroup.EmployeesFragment.OwnTasksFragment;
import com.studio.crm.icgroup.Forms.FileCompressor;
import com.studio.crm.icgroup.MainFragments.DrawerFragment;
import com.studio.crm.icgroup.MainFragments.MainProfileFragment;
import com.studio.crm.icgroup.NotificationFragments.NotificationFragment;
import com.studio.crm.icgroup.ObjectFragments.InventoryMainFragment;
import com.studio.crm.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.crm.icgroup.R;
import com.studio.crm.icgroup.Retrofit.RetroApi;
import com.studio.crm.icgroup.database.ICDatabaseHelper;


import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public final String MAIN_URL="https://crm.ic-group.kz/api/v0.1/", SERVER_URL="https://crm.ic-group.kz";
    //public final String MAIN_URL="https://icgroup.herokuapp.com/api/v0.1/", SERVER_URL="https://icgroup.herokuapp.com";
    SQLiteOpenHelper dbHelper;
    JSONObject lastRequest;
    Intent loginIntent;
    DrawerLayout drawerLayout;
    public SimpleDateFormat inputFormat, visitFormat;
    LinearLayout drawerLinearLayout;
    public boolean client=false;
    MainObjectFragment mainObjectFragment;
    FilesAdapter addServAdapter;
    List<Bitmap> addServFiles;
    View.OnClickListener onClickListener;
    int reqCode=3;
    boolean pressable=true;
    int page=-1;
    public RequestQueue requestQueue;
    public String location= "1";
    public String token="", name="", role="", avatar="", userid="", epmloyeeid="", point="";
    Intent startIntent;
    DrawerFragment drawerF;
    Calendar cal;

    public void setLocation(String location) {
        this.location = location;
    }

    FileCompressor fileCompressor;
    public HashMap<String,String> positions,  inventoryUnits, replKinds, clientKinds, dpKinds;
    public HashMap<String, ArrayList<String>> typeRoles;
    public List<String> departments, dpids, roles, rlids;
    public String[] months={"Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"},data={"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"},weeks={"Пн","Вт","Ср","Чт","Пт","Сб","Вс"};


    public void setDepartments(List<String> departments) {
        this.departments = departments;
    }

    public void setDpids(List<String> dpids) {
        this.dpids = dpids;
    }

    public void setPoint(String point) {
        this.point = point;
    }

  //  Call<ResponseBody> uploadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper=new ICDatabaseHelper(this);
        dpids=new ArrayList<>();departments=new ArrayList<>();roles=new ArrayList<>();rlids=new ArrayList<>();
        fileCompressor=new FileCompressor();
        cal=Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainObjectFragment=new MainObjectFragment();

       createViews();
        loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        if(true){
            checkToken();
            reqCode=3;
            if(token.length()==0) {
                startActivityForResult(loginIntent, 3);
            }
            else{
                getUserInfo();

            }
        }
        boolean fvar= AppStatus.isFirst(this);
        if(fvar){
             startIntent=new Intent(MainActivity.this,StartActivity.class);
             startActivity(startIntent);
        }

         drawerF=new DrawerFragment();
      setFragment(R.id.drawerFrame,drawerF);
      openDrawer();
        //Retrofit testing
      /*  Retrofit retrofit=new Retrofit.Builder().baseUrl(MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetroApi api=retrofit.create(RetroApi.class);
        uploadFile=api.uploadFile(RequestBody.create(MediaType.parse("text/plain"), "title"),
                MultipartBody.Part.createFormData(
                        "file",
                        file.getName(),
                        RequestBody.create(MediaType.parse("image"), file)));
*/

      requestMultiplePermissions();
    }
    private void uploadFile(Uri fileUri){
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request original = chain.request();

                okhttp3.Request request = original.newBuilder()
                        .header("Content-Type", "multipart/form-data")
                        .header("Accept", "application/json")
                        .header("Authorization", "JWT "+token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client=builder.build();
        Log.d("File send","TRYING");
        Retrofit retrofit=new Retrofit.Builder().baseUrl(MAIN_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        RetroApi api=retrofit.create(RetroApi.class);

        String pat= com.studio.crm.icgroup.Retrofit.FileUtils.getPath(this,fileUri);
        File file=new File(pat);
        Log.d("urlResponse", pat+" "+file.getName());
        //sendFile(pat);

        //RequestBody requestBody =RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "any title")
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("uploaded_file    ", file.getName(), requestBody);
        Call<ResponseBody> call=api.uploadFile(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("responseFile", new Gson().toJson(response.body())+" "+response.message()+" "+response.isSuccessful()+" "+response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("responseFileFailure", t.getMessage());
            }
        });
    }
    public void requestMultiplePermissions() {
        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                11);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();

    }

    public void setType(String type, TextView... a){
        for(int i=0;i<a.length;i++){
            a[i].setTypeface(getTypeFace(type));
        }

    }
    private void createViews(){
        drawerLinearLayout=(LinearLayout) findViewById(R.id.drawerLayout);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayoutAct);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
        inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        visitFormat=new SimpleDateFormat("yyyy-MM-dd");

        dpKinds=new HashMap<>();
        dpKinds.put("ADMIN","Администрация");
        dpKinds.put("PRODUCTION","Отдел производства");
        dpKinds.put("QC","Отдел контроля качества");
        dpKinds.put("SUPPLY","Отдел снабжения");
        dpKinds.put("MOBILE","Мобильная группа");
        clientKinds=new HashMap<>();
        clientKinds.put("LLP","ТОО");
        clientKinds.put("IE","ИП");
        replKinds=new HashMap<>();
        replKinds.put("ADDITION", "Пополнение"); replKinds.put("MOVEMENT", "Перемещение");replKinds.put("REPAIR", "На ремонт");replKinds.put("REPLACE", "На замену");
        inventoryUnits=new HashMap<>();
        inventoryUnits.put("PIECE","шт.");
        inventoryUnits.put("LITRE","л.");
        positions=new HashMap<>();
        positions.put("SUPERADMIN","Суперадмин");
        positions.put("CLIENT","Представитель Клиента");
        positions.put("POINT","Представитель Обьекта");
        positions.put("ADMIN_GENERAL","Генеральный директор");//Админы
        positions.put("ADMIN_TECHNICAL","Технический директор");
        positions.put("ADMIN_EXECUTIVE","Исполнительный директор");
        positions.put("ADMIN_DEPUTY","Помощник CEO");//Админы
        positions.put("PRODUCTION_CHIEF","Начальник отдела производства");//Отдел производства
        positions.put("PRODUCTION_CURATOR","Куратор отдела производства");//Отдел производства
        positions.put("PRODUCTION_ADMIN","Администратор");//Отдел производства
        positions.put("PRODUCTION_NPO","НПО");//Отдел производства
        positions.put("WORKER","НПО работник");
        positions.put("QC_CHIEF","Начальник отдела контроля качества");//Контроль качества
        positions.put("QC_TECHNIC","Технолог отдела контроля качества");
        positions.put("QC_REVISOR","Ревизор отдела контроля качества");
        positions.put("QC_MANAGER","Менеджер отдела контроля качества");//Контроль качества
        positions.put("SUPPLY_CHIEF","Начальник отдела снабжения");//снабжение
        positions.put("SUPPLY_MANAGER","Менеджер отдела снабжения");//снабжение
        positions.put("MOBILE_CHIEF","Начальник мобильной группы");//мобильная
        positions.put("MOBILE_WORKER","Работник мобильной группы");//мобильная
        positions.put("MOBILE_MANAGER","Менеджер мобильной группы");//мобильная
        typeRoles=new HashMap<>();
        typeRoles.put("ADMIN",new ArrayList<String>());
        typeRoles.put("QC",new ArrayList<String>());
        typeRoles.put("PRODUCTION",new ArrayList<String>());
        typeRoles.put("SUPPLY",new ArrayList<String>());
        typeRoles.put("MOBILE",new ArrayList<String>());
        for(String i:positions.keySet()){
            rlids.add(i);
            roles.add(positions.get(i));
            String s=positions.get(i);
            if(i.contains("ADMIN_")){
                typeRoles.get("ADMIN").add(s);
            }
            else if(i.contains("PRODUCTION_")){
                typeRoles.get("PRODUCTION").add(s);
            }
            else if(i.contains("QC_")){
                typeRoles.get("QC").add(s);
            }
            else if(i.contains("SUPPLY_")){
                typeRoles.get("SUPPLY").add(s);
            }
            else if(i.contains("MOBILE_")){
                typeRoles.get("MOBILE").add(s);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK ) {
            client=data.getBooleanExtra("client",false);
            token=data.getStringExtra("token");
            //Log.d("actvity transport",token);
            saveToken();
            checkClient();
            getUserInfo();
        }
        if(requestCode==1 && resultCode==RESULT_OK) {
            try {
                Log.d("file", "file added");
                Uri selectedMediaUri = data.getData();
                Log.d("path data",selectedMediaUri.toString());
                String path=selectedMediaUri.getPath();
                File file=new File(selectedMediaUri.getPath());
                if (selectedMediaUri.toString().contains("image")) {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedMediaUri);
                    addServFiles.add(bitmap);
                    addServAdapter.addStatus(false);
                    addServAdapter.notifyDataSetChanged();
                } else if (selectedMediaUri.toString().contains("video")) {
                    final String [] spl=file.getPath().split(":");
                    String pat= com.studio.crm.icgroup.Retrofit.FileUtils.getPath(this,selectedMediaUri);
                    Bitmap bitmap=createVideoThumbNail(pat);
                    addServFiles.add(bitmap);
                    addServAdapter.addStatus(true);
                    addServAdapter.notifyDataSetChanged();
                }
//                String cPath=path.substring(path.indexOf("content"),path.length());
  //              Log.d("pathDars",cPath+"");
                //sendFile(cPath);
                uploadFile(selectedMediaUri);
            }
            catch (IOException e){

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public Date getNowDate(String s) {
        try {
            Date date = inputFormat.parse(s);
            return getNowDate(date);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public Date getNowDate(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,+6);
        return calendar.getTime();
    }

    public String getGMTDate(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,-6);
        return inputFormat.format(calendar.getTime());
    }
    public String getdate(String calendar){
        try {
            if (calendar.length()==20){
                calendar=calendar.substring(0,calendar.length()-1)+".0Z";
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(inputFormat.parse(calendar));
            return getdate(cal);
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public String getdate(Date date){
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(date);
        return getdate(calendar);
    }
    public String getdate(Calendar calendar){
      //  cal.add(Calendar.HOUR_OF_DAY,+6);
        int hour=calendar.get(Calendar.HOUR_OF_DAY),minute=calendar.get(Calendar.MINUTE);
        String ho=hour+"", mi=minute+"";
        if(ho.length()==1){
            ho="0"+ho;
        }
        if(mi.length()==1){
            mi="0"+mi;
        }
        return calendar.get(Calendar.DAY_OF_MONTH)+" "+months[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+"|"+ho+":"+mi;
    }
    private void sendFile(String path){
        File file=new File(path);
        String size=file.length()+" = SIZE";
        Log.d("File Size",size);
        String url=MAIN_URL+"files/";
        SimpleMultiPartRequest multiPartRequest=new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fileRe",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getApplicationContext(), "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "multipart/form-data");
                headers.put("Content-Type", "multipart/form-data;boundary="+" ANY STRING");
                headers.put("Authorization", "JWT "+token);
                return headers;
            }
        };
        multiPartRequest.addFile("file",path);
      //  multiPartRequest.addMultipartParam("Regd_ID", "text/plain", "55");
        requestQueue.add(multiPartRequest);
    }
    public void setFileAdapterOs(List<Bitmap> bitmaps,FilesAdapter adapter){
        addServFiles=bitmaps;
        addServAdapter=adapter;
    }
    public Bitmap createVideoThumbNail(String path){
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(path,   MediaStore.Images.Thumbnails.MINI_KIND);
        return thumb;
    }
    private void checkClient(){
        drawerF.setClient(client);
    }

    public void setPhoto(String url, ImageView view){
        Transformation transformation = new RoundedTransformationBuilder()
                /*.borderColor(getResources().getColor(R.color.icgGreen))
                .borderWidthDp(1)*/
                .cornerRadiusDp(90)
                .oval(false)
                .build();
        Picasso.with(this).load(url).fit().transform(transformation).into(view);
    }

    public void setPage(int page){
        drawerLayout.closeDrawer(drawerLinearLayout);
        this.page=page;
        if(client){
            switch (page){
                case 0:
                    setFragment(R.id.content_frame,mainObjectFragment);
                    break;
                case 1:
                    Fragment fragment=new OwnTasksFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id","2");
                    bundle.putString("location","1");
                    fragment.setArguments(bundle);
                    setFragment(R.id.content_frame,fragment);
                    break;
                case 2:
                    setFragment(R.id.content_frame, new NotificationFragment());
                    break;
                case 3:
                    break;
            }
        }
        else {
            switch (page) {
                case 0:
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.content_frame, mainObjectFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                    break;
                case 1:
                    Fragment fragment=new OwnTasksFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("id","2");
                    bundle.putString("location","1");
                    fragment.setArguments(bundle);
                    setFragment(R.id.content_frame,fragment);
                    break;
                case 2:
                    setFragment(R.id.content_frame,new EmployeesMainFragment());
                    break;
                case 3:
                    setFragment(R.id.content_frame,new ClientsMainFragment());
                    break;
                case 4:
                    Fragment fragmen=new InventoryMainFragment();
                    Bundle undle=new Bundle();
                    undle.putString("id","");
                    undle.putBoolean("object",false);
                    fragmen.setArguments(undle);
                    setFragment(R.id.content_frame,fragmen);
                    break;
            }
        }
    }
    public void setPage(View view){
        String tag = view.getTag().toString();
        int money = Integer.parseInt(tag);
        this.setPage(money);
    }
    public void setFragment(int id,Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(id,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    public void setFragmentNoBackStack(int id,Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(id,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    public void openDrawer(){
        drawerLayout.openDrawer(drawerLinearLayout);
    }
    public void closeDrawer(){
        drawerLayout.closeDrawer(drawerLinearLayout);
    }
    public void openDrawerOn(View view){
        drawerLayout.openDrawer(drawerLinearLayout);
    }
    public void callPhone(String phone){
        if(phone.substring(0,1).equals("8")){

        }
        if(phone.substring(0,1).equals("7")){
            phone="+"+phone;
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phone));
        startActivity(callIntent);
    }
    @Override
    public void onBackPressed(){
        onBackPressedCheck();
    }
    public void onBackPressed(View view){
        onBackPressed();
    }

    public void setPressable(boolean pressable, View.OnClickListener c) {
        this.pressable = pressable;
        onClickListener=c;
    }

    public void onBackPressedCheck(){
        try {
            if (pressable) {
                super.onBackPressed();
            } else {
                try {
                    onClickListener.onClick(mainObjectFragment.getView());
                } catch (Exception e) {
                    e.printStackTrace();
                    pressable = true;
                    onBackPressedCheck();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public int getPixels(int a){
        final float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (a * scale + 0.5f);
        return pixels;
    }
    public void onClick(View view){

    }
    public void setRecyclerViewOrientation(RecyclerView view, int orientation){
            view.setItemAnimator(new DefaultItemAnimator());
            view.setLayoutManager(new LinearLayoutManager(this,orientation,false));
    }

    public Typeface getTypeFace( String s){
        switch (s){
            case "demibold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
            case "medium":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Medium.ttf"));
            case "light":
                return (Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
            case "black":
                return (Typeface.createFromAsset(getAssets(),"fonts/Avenir-Black.ttf"));
            case "bold":
                return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            case "regular":
                return Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Regular.ttf");
                default:
                    return (Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-It.ttf"));
        }
    }
    public String getDateText(String d){
        try {
            return getDateText(getNowDate(d));
        }
        catch (Exception e){
            return "";
        }
    }
    public String getDateText(Date date){
        if(date==null){
            return "";
        }
        else
        {
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH)+" "+months[cal.get(Calendar.MONTH)]+" "+ cal.get(Calendar.YEAR);
        }
    }


    public void logOut(){
        token="";
        userid="";
        role="";
        saveToken();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivityForResult(intent,3);
    }
    private void saveToken(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        if(token.length()>0) {
            contentValues.put("ID", token);
        }
        else{
            contentValues.put("ID", " ");
        }
        db.update("token",contentValues,"_id=?",new String[] {Integer.toString(1)});
        db.close();
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("token", new String[]{"ID"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        if(cursor.moveToFirst()){
            String token=cursor.getString(0);
            Log.d("fucking token here",token.length()+"");
            if(token.length()>1){
                this.token=token;
            }
            else{
                this.token="";
            }
        }
        db.close();
        cursor.close();
    }
    private void checkToken(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("token", new String[]{"ID"}, "_id = ?", new String[] {Integer.toString(1)},null,null,null);
        if(cursor.moveToFirst()){
            String token=cursor.getString(0);
            Log.d("fucking token here",token.length()+"");
            if(token.length()>1){
                this.token=token;
            }
            else{
                this.token="";
            }
        }
        db.close();
        cursor.close();
    }

    private void getUserInfo(){
        String url=MAIN_URL+"user/";
        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setUserInfo(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Проблемы соеденения", Toast.LENGTH_SHORT).show();
            }
        }){ @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json; charset=utf-8");
            headers.put("Authorization", "JWT "+token);
            return headers;
        }};
        requestQueue.add(objectRequest);
    }
    private void setUserInfo(JSONObject object){
        try{
            String name=object.getString("fullname");
            String role=object.getString("role");
            String id=object.getString("id");
            if(!object.isNull("avatar")){
                JSONObject avatar
                        =object.getJSONObject("avatar");
                this.avatar=SERVER_URL+avatar.getString("file");
            }
            this.name=name;
            this.role=role;
            if(role.equals("POINT") || role.equals("CLIENT")){
                client=true;
            }
            else{
                client=false;
            }
            userid=id;
            drawerF.setClient(client);
            drawerF.setRole(role);
            if(page==-1)
            setFragment(R.id.content_frame,new MainProfileFragment());
        }
        catch (Exception e){

        }
    }

}
