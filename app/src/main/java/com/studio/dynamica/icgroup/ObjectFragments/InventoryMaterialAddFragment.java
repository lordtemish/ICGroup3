package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.ExtraFragments.RadioFragment;
import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMaterialAddFragment extends Fragment {

    RadioFragment radioFragment;
    public InventoryMaterialAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_material_add, container, false);
        createViews(view);
        radioFragment=new RadioFragment();
        Bundle bundle=new Bundle();bundle.putInt("checked",0);
        radioFragment.setArguments(bundle);
        ((MainActivity)getActivity()).setFragmentNoBackStack(R.id.priorityFrame,radioFragment);
        return view;
    }
    private void createViews(View view){

    }

}
