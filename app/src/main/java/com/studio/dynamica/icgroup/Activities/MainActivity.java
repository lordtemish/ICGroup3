package com.studio.dynamica.icgroup.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.studio.dynamica.dummy.AppStatus;
import com.studio.dynamica.icgroup.MainFragments.DrawerFragment;
import com.studio.dynamica.icgroup.R;

public class MainActivity extends AppCompatActivity {
    Intent loginIntent;
    DrawerLayout drawerLayout;
    LinearLayout drawerLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.drawerFrame,drawerF);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public void setPage(int page){
        drawerLayout.closeDrawer(drawerLinearLayout);
    }
}
