package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.R;

public class BigCounterView extends FrameLayout {
    private int max=1000;
    TextView minus, plus, number;
    int page=0;
    Context context;
    public BigCounterView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public BigCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BigCounterView(Context context) {
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.big_counter_view, null);
        addView(view);
        context=view.getContext();
        createViews(view);
        ((MainActivity)context).setType("demibold",minus, plus,  number);
        checkPage();
        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(-1);
            }
        });
        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(+1);
            }
        });
    }
    private void createViews(View view){
        minus=(TextView) view.findViewById(R.id.minusTextView);
        plus=(TextView) view.findViewById(R.id.plusTextView);
        number=(TextView) view.findViewById(R.id.numberTextView);
    }public void setMax(int max) {
        this.max = max;
    }

    public int getPage() {
        return page;
    }

    public int getMax() {
        return max;
    }
    private void changePage(int a){
        page+=a;
        if(page>max)
            page=0;
        if(page<0)
            page=max;
        checkPage();
    }

    public void setPage(int page) {
        this.page = page;
        checkPage();
    }

    private void checkPage(){
        number.setText(page+"");
    }
}