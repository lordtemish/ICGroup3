package com.studio.dynamica.icgroup.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.studio.dynamica.dummy.AppStatus;
import com.studio.dynamica.icgroup.MainFragments.DrawerFragment;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectFragment;
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
      setFragment(R.id.drawerFrame,drawerF);
    }

    public void setPage(int page){
        drawerLayout.closeDrawer(drawerLinearLayout);
        switch (page){
            case 0:
                MainObjectFragment mainObjectFragment=new MainObjectFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,mainObjectFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
                break;
        }
    }
    public void setFragment(int id,Fragment fragment){
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(id,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    public void openDrawer(){
        drawerLayout.openDrawer(drawerLinearLayout);
    }
    public void openDrawerOn(View view){
        drawerLayout.openDrawer(drawerLinearLayout);
    }
}
