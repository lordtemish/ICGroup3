package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.ExtraFragments.RadioFragment;
import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryEquipmentAddFragment extends Fragment {
    String[] a={"Снабжение", "Уборка"};
    FrameLayout radioFrame, spinnerFrame, typeSpinnerFrame;
    RadioFragment radioFragment;
    Spinner employeeChangeSpinner,typeSpinner;
    public InventoryEquipmentAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_equipment_add, container, false);
        createViews(view);
        radioFragment=new RadioFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("checked",0);
        radioFragment.setArguments(bundle);
        ((MainActivity)getActivity()).setFragmentNoBackStack(R.id.radioFrameLaoyut,radioFragment);


        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),R.layout.simple_spinner_item,a);
        employeeChangeSpinner.setAdapter(arrayAdapter);
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSp();
            }
        });
        ArrayAdapter<String> arrayAdapter2=new ArrayAdapter<>(getActivity(),R.layout.simple_spinner_item,a);
        typeSpinner.setAdapter(arrayAdapter2);
        typeSpinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typeSpinner.performClick();
            }
        });

        return view;
    }
    private void showSp(){
        employeeChangeSpinner.performClick();
    }
    private void createViews(View view){
        employeeChangeSpinner=(Spinner) view.findViewById(R.id.employeeChangeSpinner);
        typeSpinner=(Spinner)view.findViewById(R.id.typeChangeSpinner);
        spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        typeSpinnerFrame=(FrameLayout) view.findViewById(R.id.typeSpinnerFrameImage);
        radioFrame=(FrameLayout) view.findViewById(R.id.radioFrameLaoyut);
    }

    public int getChecked(){
       return radioFragment.getArguments().getInt("checked");
    }

}
