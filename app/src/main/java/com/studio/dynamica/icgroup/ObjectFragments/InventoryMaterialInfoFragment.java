package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CommentaryAdapter;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMaterialInfoFragment extends Fragment {

    RecyclerView commentaryRecyclerView;
    public InventoryMaterialInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_material_info, container, false);
        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(commentaryRecyclerView, LinearLayout.VERTICAL);

        List<CommentaryForm> commentaryForms=new ArrayList<>();
        commentaryForms.add(new CommentaryForm("12.08.2019","Темирлан Алмасов",getActivity().getResources().getString(R.string.bigtext)));
        CommentaryAdapter commentaryAdapter=new CommentaryAdapter(commentaryForms);
        commentaryRecyclerView.setAdapter(commentaryAdapter);
        return view;
    }
    private void createViews(View view){
        commentaryRecyclerView=(RecyclerView) view.findViewById(R.id.commentaryRecyclerView);
    }
}
