package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceChooseView extends FrameLayout{
    TextView name, nothing;
    List<TextView> textViews,texts;
    List<ConstraintLayout> layouts;
    List<RadioButton> buttons;
    public AttendanceChooseView(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public AttendanceChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AttendanceChooseView(Context context) {
        super(context);
        initView();
    }
    private void initView(){
        View view = inflate(getContext(), R.layout.attendance_choose_view, null);
        addView(view);
        createViews(view);
    }
    private void createViews(View view){
        name=(TextView) view.findViewById(R.id.name);
        nothing=(TextView)view.findViewById(R.id.nothing);

        texts=new ArrayList<>();textViews=new ArrayList<>();layouts=new ArrayList<>();buttons=new ArrayList<>();
        texts.add((TextView) view.findViewById(R.id.plusText));texts.add((TextView) view.findViewById(R.id.minusText));texts.add((TextView) view.findViewById(R.id.illText));
        textViews.add((TextView) view.findViewById(R.id.plusTextView));textViews.add((TextView) view.findViewById(R.id.minusTextView));textViews.add((TextView) view.findViewById(R.id.illTextView));
        layouts.add((ConstraintLayout) view.findViewById(R.id.plusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.minusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.illLayout));
        buttons.add((RadioButton) view.findViewById(R.id.plusRadio));buttons.add((RadioButton) view.findViewById(R.id.minusRadio));buttons.add((RadioButton) view.findViewById(R.id.illRadio));

        for(int i=0;i<3;i++){
            layouts.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    setChose(layouts.indexOf(view));
                }
            });
        }
    }

    private void clearAll(){
        for(int i=0;i<3;i++){
            texts.get(i).setTextColor(getContext().getResources().getColor(R.color.darkgrey));
            layouts.get(i).setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            buttons.get(i).setChecked(false);
        }
    }
    private void setChose(int i){
        texts.get(i).setTextColor(getContext().getResources().getColor(R.color.black));
        layouts.get(i).setBackgroundResource(R.drawable.grey_corners_page);
        buttons.get(i).setChecked(true);
    }
}
