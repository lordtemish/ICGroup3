package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class AttendanceChooseView extends FrameLayout{
    TextView name, nothing;
    List<TextView> textViews,texts;
    List<ConstraintLayout> layouts;
    Context context;
    Spinner spinner, spinner3;
    List<RadioButton> buttons;
    FrameLayout saveLayout,spinnerFrame, spinnerFrame3;
    List<String> zamena, zids;
    LinearLayout spinnerLinear2, spinnerLinear3;
    int chose=0;
    String id;


    public void setId(String id) {
        this.id = id;
    }

    ArrayAdapter<String> adapter;
    public void setZamena(List<String> zamena) {
        this.zamena.clear();
        zids.clear();
        this.zamena.add("Выберите объект");
        for(String i:zamena) {
            String[] s=i.split(" ");
            String name="";
            String id=s[0];
            for(int iw=1;iw<s.length;iw++){
                name+=s[iw]+" ";
            }
            if(id.equals(this.id)) continue;
            this.zamena.add(name);
            zids.add(id);
        }
        setSpinner();

    }

    public List<String> getZamena() {
        return zamena;
    }

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
        zamena=new ArrayList<>();zids=new ArrayList<>();
        View view = inflate(getContext(), R.layout.attendance_choose_view, null);
        context=view.getContext();
        addView(view);
        createViews(view);
    }
    private void createViews(View view){
        name=(TextView) view.findViewById(R.id.name);
        spinner=(Spinner) view.findViewById(R.id.spinner);
        spinner3=(Spinner) view.findViewById(R.id.spinner3);
        nothing=(TextView)view.findViewById(R.id.nothing);

        texts=new ArrayList<>();textViews=new ArrayList<>();layouts=new ArrayList<>();buttons=new ArrayList<>();
        texts.add((TextView) view.findViewById(R.id.plusText));texts.add((TextView) view.findViewById(R.id.minusText));texts.add((TextView) view.findViewById(R.id.illText));texts.add((TextView) view.findViewById(R.id.replText));texts.add((TextView) view.findViewById(R.id.halfText));texts.add((TextView) view.findViewById(R.id.thirdText));
        textViews.add((TextView) view.findViewById(R.id.plusTextView));textViews.add((TextView) view.findViewById(R.id.minusTextView));textViews.add((TextView) view.findViewById(R.id.illTextView));textViews.add((TextView) view.findViewById(R.id.replTextView));textViews.add((TextView) view.findViewById(R.id.halfTextView));textViews.add((TextView) view.findViewById(R.id.thirdTextView));
        layouts.add((ConstraintLayout) view.findViewById(R.id.plusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.minusLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.illLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.replLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.halfLayout));layouts.add((ConstraintLayout) view.findViewById(R.id.thirdLayout));
        buttons.add((RadioButton) view.findViewById(R.id.plusRadio));buttons.add((RadioButton) view.findViewById(R.id.minusRadio));buttons.add((RadioButton) view.findViewById(R.id.illRadio));buttons.add((RadioButton) view.findViewById(R.id.replRadio));buttons.add((RadioButton) view.findViewById(R.id.halfRadio));buttons.add((RadioButton) view.findViewById(R.id.thirdRadio));
        saveLayout=(FrameLayout) view.findViewById(R.id.saveLayout);
        spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrame);
        spinnerFrame3=(FrameLayout) view.findViewById(R.id.spinnerFrame3);
        spinnerLinear2=(LinearLayout) view.findViewById(R.id.spinnerLinear2);
        spinnerLinear3=(LinearLayout) view.findViewById(R.id.spinnerLinear3);
        for(int i=0;i<6;i++){
            layouts.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    setChose(layouts.indexOf(view));
                }
            });
        }
        clearAll();setChose(0);
    }

    private void clearAll(){
        for(int i=0;i<6;i++){
            texts.get(i).setTextColor(getContext().getResources().getColor(R.color.darkgrey));
            layouts.get(i).setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            buttons.get(i).setChecked(false);
        }
    }
    private void setChose(int i){
        texts.get(i).setTextColor(getContext().getResources().getColor(R.color.black));
        layouts.get(i).setBackgroundResource(R.drawable.grey_corners_line);
        buttons.get(i).setChecked(true);
        spinnerLinear2.setVisibility(GONE);
        if(i==3){
            spinnerLinear2.setVisibility(VISIBLE);
        }
    }
    public int getCheckedPosition(){
        for(int i=0;i<buttons.size();i++){
            if(buttons.get(i).isChecked()){
                return i;
            }
        }
        return 0;
    }

    public String getChose() {
        if(chose>0)
        return  zids.get(chose-1);
        else
            return "-1";
    }

    public void setListener(OnClickListener listener){
        saveLayout.setOnClickListener(listener);
    }
    private void setSpinner(){
        adapter=new ArrayAdapter<String>(context,R.layout.simple_spinner_item,zamena){
            public View getView(int position, View convertView,ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position,convertView,parent);
                ((TextView) v).setTypeface(((MainActivity) getContext()).getTypeFace("demibold"));
                return v;

            }};
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.performClick();
            }
        });
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chose=i;
                Log.d("CHOSECHOSE",chose+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
