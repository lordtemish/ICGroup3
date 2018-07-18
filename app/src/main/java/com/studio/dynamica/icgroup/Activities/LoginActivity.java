package com.studio.dynamica.icgroup.Activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;

import com.studio.dynamica.icgroup.AuthFragments.LoginFragment;
import com.studio.dynamica.icgroup.R;

public class LoginActivity extends AppCompatActivity {
    TextView lorem;
    TextView ICG;
    FrameLayout loginFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ICG=(TextView) findViewById(R.id.ICGlogin);
        lorem=(TextView) findViewById(R.id.loremLog);
        loginFrame=(FrameLayout) findViewById(R.id.inputLoginFrame);
        ICG.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
        lorem.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
        Fragment fragment=new LoginFragment();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.inputLoginFrame,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    public void onBackPressed(){

    }
}
