package com.studio.crm.icgroup.Activities;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.crm.icgroup.R;
import com.studio.crm.icgroup.StartFragments.StartPage;
import com.studio.crm.icgroup.StartFragments.StartTextFragment;

import java.util.HashMap;

public class StartActivity extends AppCompatActivity {
    Button pass,next;
    ImageView secondaryImage;
    static final int PAGE_COUNT = 4;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
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
            if(a>3){

            }
            else {
                viewPager.setCurrentItem(a);
            }
        }
    };
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        page=0;
        secondaryImage=(ImageView) findViewById(R.id.secondaryImage);
        pass=(Button) findViewById(R.id.passStart);
        pass.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
        next=(Button) findViewById(R.id.next);
        next.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/avenir-light.ttf"));
        setPage(page);

        pass.setOnClickListener(stop);
        next.setOnClickListener(nextListener);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        pagerAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setSec(position);
                Log.d(""+position,"POS"+position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public void setSec(int p){
        next.setOnClickListener(nextListener);
        switch (p){
            case 0:
                secondaryImage.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                break;
            case 1:
                secondaryImage.setBackgroundResource    (R.drawable.copythree);
                break;
            case 2:
                secondaryImage.setBackgroundResource(R.drawable.copytwo);
                break;
            case 3:
                secondaryImage.setBackgroundResource(R.drawable.copyone);
                next.setOnClickListener(stop);
                break;
        }
    }
    public Fragment setPage(int a){
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
                break;
                default:
                    fragment=new Fragment();
                    StartActivity.this.finish();

        }
        return fragment;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        HashMap<Integer,Fragment> fragments=new HashMap<>();

        @Override
        public Fragment getItem(int position) {
            Fragment testfr;
            if(position >0) {
                testfr = setPage(position);
            }
            else{
                testfr=setPage(position);
            }
            return testfr;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }
}
