package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class RadioButtonView extends FrameLayout {
    List<RadioButton> radioButtons;
    List<LinearLayout> radioLinears;
    int[] radios={R.id.button1, R.id.button2,R.id.button3,R.id.button4}, gals={R.id.firstGal,R.id.secondGal,R.id.thirdGal,R.id.fourthGal};
    int page=0;
    public RadioButtonView(Context context, AttributeSet attrs,int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public RadioButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RadioButtonView(Context context) {
        super(context);
        initView();
    }
    private void initView() {
        View view = inflate(getContext(), R.layout.radiobutton_view, null);
        addView(view);
        createViews(view);
        setChecked(page);
    }
    private void createViews(View view){
        radioButtons=new ArrayList<>();radioLinears=new ArrayList<>();
        for(int i=0;i<4;i++){
            radioButtons.add((RadioButton) view.findViewById(radios[i]));
            radioLinears.add((LinearLayout) view.findViewById(gals[i]));
            radioLinears.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setClear();
                    setChecked(radioLinears.indexOf(view));
                }
            });
        }
    }
    private void setClear(){
        for(int i=0;i<4;i++){
            radioButtons.get(i).setChecked(false);

        }
    }
    private void setChecked(int i){
        radioButtons.get(i).setChecked(true);
        page=i;
    }
}
