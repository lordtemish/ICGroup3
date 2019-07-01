package com.studio.crm.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.studio.crm.icgroup.R;

public class ICGraphicView  extends FrameLayout{
    public ICGraphicView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public ICGraphicView(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public ICGraphicView(Context context){
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.datechoose_view, null);
    }
}
