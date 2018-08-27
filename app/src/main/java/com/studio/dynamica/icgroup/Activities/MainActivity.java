package com.studio.dynamica.icgroup.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.studio.dynamica.dummy.AppStatus;
import com.studio.dynamica.icgroup.MainFragments.DrawerFragment;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectFragment;
import com.studio.dynamica.icgroup.R;

public class MainActivity extends AppCompatActivity {
    Intent loginIntent;
    DrawerLayout drawerLayout;
    LinearLayout drawerLinearLayout;
    MainObjectFragment mainObjectFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainObjectFragment=new MainObjectFragment();

        drawerLinearLayout=(LinearLayout) findViewById(R.id.drawerLayout);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayoutAct);
        loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        if(true){
            startActivity(loginIntent);
        }
        boolean fvar= AppStatus.isFirst(this);
        fvar=true;
        if(fvar){
            Intent startIntent=new Intent(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
        }

        Fragment drawerF=new DrawerFragment();
      setFragment(R.id.drawerFrame,drawerF);
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
        switch (page){
            case 0:
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.content_frame,mainObjectFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
    public void onBackPressed(View view){
        onBackPressed();
    }

    public void setRecyclerViewOrientation(RecyclerView view, int orientation){
            view.setItemAnimator(new DefaultItemAnimator());
            view.setLayoutManager(new LinearLayoutManager(this,orientation,false));
    }
}
