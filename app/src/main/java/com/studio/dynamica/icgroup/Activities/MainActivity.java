package com.studio.dynamica.icgroup.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.studio.dynamica.dummy.AppStatus;
import com.studio.dynamica.icgroup.Adapters.FilesAdapter;
import com.studio.dynamica.icgroup.MainFragments.DrawerFragment;
import com.studio.dynamica.icgroup.NotificationFragments.NotificationFragment;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.dynamica.icgroup.R;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final String MAIN_URL="https://icgroup.herokuapp.com/api/v0.1/";
    JSONObject lastRequest;
    Intent loginIntent;
    DrawerLayout drawerLayout;
    public SimpleDateFormat inputFormat;
    LinearLayout drawerLinearLayout;
    public boolean client=false;
    MainObjectFragment mainObjectFragment;
    FilesAdapter addServAdapter;
    List<Bitmap> addServFiles;
    View.OnClickListener onClickListener;
    int reqCode;
    boolean pressable=true;
    int page=-1;
    public RequestQueue requestQueue;
    Intent startIntent;
    DrawerFragment drawerF;
    Calendar cal;

    String[] months={"Января","Февраля","Марта","Апреля","Мая","Июня","Июля","Августа","Сентября","Октября","Ноября","Декабря"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cal=Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainObjectFragment=new MainObjectFragment();

       createViews();
        loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        if(true){
            reqCode=3;
            startActivityForResult(loginIntent,reqCode);
        }
        boolean fvar= AppStatus.isFirst(this);
        fvar=true;
        if(fvar){
             startIntent=new Intent(MainActivity.this,StartActivity.class);
             startActivity(startIntent);
        }

         drawerF=new DrawerFragment();
      setFragment(R.id.drawerFrame,drawerF);

      openDrawer();
    }
    private void createViews(){
        drawerLinearLayout=(LinearLayout) findViewById(R.id.drawerLayout);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayoutAct);
        requestQueue= Volley.newRequestQueue(this);
        inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("actiivity transport",resultCode+" "+data+" ");
        if (requestCode == reqCode && resultCode == RESULT_OK && startIntent != null) {
            Log.d("actvity transport",data.getBooleanExtra("client",false)+"");
            client=data.getBooleanExtra("client",false);
            checkClient();
        }
        if(requestCode==1 && resultCode==RESULT_OK) {
            try {
                Log.d("file", "file added");
                Uri selectedMediaUri = data.getData();
                Log.d("path data",selectedMediaUri.toString());
                if (selectedMediaUri.toString().contains("image")) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedMediaUri);
                    addServFiles.add(bitmap);
                    addServAdapter.addStatus(false);
                    addServAdapter.notifyDataSetChanged();
                } else if (selectedMediaUri.toString().contains("video")) {
                    File file=new File(selectedMediaUri.getPath());
                    final String [] spl=file.getPath().split(":");
                    String pat=spl[0];
                    Log.d("video",""+file.getAbsolutePath());
                    Bitmap bitmap=createVideoThumbNail(pat);
                    addServFiles.add(bitmap);
                    addServAdapter.addStatus(true);
                    addServAdapter.notifyDataSetChanged();
                }
            }
            catch (IOException e){

            }
        }
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
        if(pressable){
            super.onBackPressed();
        }
        else{
            onClickListener.onClick(mainObjectFragment.getView());
        }
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


}
