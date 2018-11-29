package com.studio.dynamica.icgroup.ExtraFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.ObjectFragments.InventoryEquipmentAddFragment;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends Fragment {
    List<LinearLayout> galLayouts;
    List<ImageView> galImageViews;
    List<TextView> galTextViews;
    String[] tex={"Низкий","Средний","Высокий","Очень важно"};

    int checked=0;
    public RadioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        galImageViews=new ArrayList<>();galLayouts=new ArrayList<>();galTextViews=new ArrayList<>();
        View view =inflater.inflate(R.layout.fragment_radio, container, false);
        createViews(view);
        clearAll();
        setChecked();
        return view;
    }
    private void createViews(View view){
        galImageViews.add((ImageView) view.findViewById(R.id.firstGalImageView));galImageViews.add((ImageView) view.findViewById(R.id.secondGalImageView));galImageViews.add((ImageView) view.findViewById(R.id.thirdGalImageView));
        galTextViews.add((TextView) view.findViewById(R.id.firstGalTextView));galTextViews.add((TextView) view.findViewById(R.id.secondGalTextView));galTextViews.add((TextView) view.findViewById(R.id.thirdGalTextView));
        galLayouts.add((LinearLayout) view.findViewById(R.id.firstGal));galLayouts.add((LinearLayout) view.findViewById(R.id.secondGal));galLayouts.add((LinearLayout) view.findViewById(R.id.thirdGal));

        for(int i=0;i<3;i++){
            galTextViews.get(i).setText(tex[i]);
            ((MainActivity)getActivity()).setType("demibold",galTextViews.get(i));
            galLayouts.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearAll();
                    int a=galLayouts.indexOf(view);
                    checked=a;
                    setChecked();
                }
            });
        }

    }

    private void clearAll(){
        for(int i=0;i<3;i++){
            galImageViews.get(i).setBackgroundResource(R.drawable.grey_circle);
            galTextViews.get(i).setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
    }
    private void setChecked(){
        galImageViews.get(checked).setBackgroundResource(R.drawable.green_circle);
        galTextViews.get(checked).setTextColor(getActivity().getResources().getColor(R.color.black));
        getArguments().putInt("checked",checked);
    }

    public int getChecked() {
        return checked;
    }
}
