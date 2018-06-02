package com.studio.dynamica.icgroup.Activities;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;
import com.studio.dynamica.icgroup.StartFragments.StartPage;
import com.studio.dynamica.icgroup.StartFragments.StartTextFragment;

public class StartActivity extends AppCompatActivity {
    Button pass,next;

    public void removeView(View view) {
        ViewGroup vg = (ViewGroup) (view.getParent());
        vg.removeView(view);
    }
    View.OnClickListener stop=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            StartActivity.this.finish();
        }
    };
    View.OnClickListener nextListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            page++;
            p(page);
        }
        void p(int a){
            setPage(a);
        }
    };
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        page=0;
        FrameLayout frameLayout=(FrameLayout)findViewById(R.id.startFrame);
        pass=(Button) findViewById(R.id.passStart);
        pass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
        next=(Button) findViewById(R.id.next);
        next.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
        setPage(page);

        pass.setOnClickListener(stop);
        next.setOnClickListener(nextListener);
    }
    public void setPage(int a){
        Fragment fragment;
        Bundle bundle = new Bundle();
        switch (a){
            case 0:
                fragment=(Fragment) new StartPage();
                break;
            case 1:
                fragment=(Fragment) new StartTextFragment();
                bundle.putString("startext","Постановка\nвнеплановых\nзадач");
                fragment.setArguments(bundle);
                break;
            case 2:
                fragment=(Fragment) new StartTextFragment();
                bundle.putString("startext","Моментальная\nобратная связь");
                fragment.setArguments(bundle);
                break;
            case 3:
                fragment=(Fragment) new StartTextFragment();
                bundle.putString("startext","Оценка качества в\nрежиме реального\nвремени");
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment=(Fragment) new StartTextFragment();
                bundle.putString("startext","Корпоративная\nплощадка");
                fragment.setArguments(bundle);
                next.setOnClickListener(stop);
                break;
                default:
                    fragment=new Fragment();
                    StartActivity.this.finish();

        }
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.startFrame,fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
