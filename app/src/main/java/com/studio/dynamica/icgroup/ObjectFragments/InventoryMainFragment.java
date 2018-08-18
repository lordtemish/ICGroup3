package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMainFragment extends Fragment {
    ImageView leftImageView, rightImageView;
    TextView pageTextView;
    LinearLayout textsLayout;
    ConstraintLayout progressLayout;
    RecyclerView inventoryRecycler;
    String[] a={"Оборудование", "Расходный материал","Инвентаризация","Заявки на пополнение"};
    int page=0;
    public InventoryMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_main, container, false);
        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(inventoryRecycler, LinearLayoutManager.VERTICAL);

        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(-1);
            }
        });
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(+1);
            }
        });
        checkPage();
        return view;
    }
    private void createViews(View view){
        leftImageView=(ImageView) view.findViewById(R.id.ImageLeftView);
        rightImageView=(ImageView) view.findViewById(R.id.ImageRightView);
        pageTextView=(TextView) view.findViewById(R.id.pageTextView);
        textsLayout=(LinearLayout) view.findViewById(R.id.textsLayout);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);
        inventoryRecycler=(RecyclerView) view.findViewById(R.id.inventoryRecycler);
    }
    private void checkPage(int a){
        page+=a;
        checkPage();
    }
    private void checkPage(){
        if(page<0) page=a.length-1;
        if(page==a.length) page=0;
        pageTextView.setText(a[page]);
        switch (page){
            case 0:
                setProgressLayout();
                break;
            case 1:

                break;
            case 2:
                setProgressLayout();
                break;
            default:
                   setTextsLayout();
                break;
        }
    }
    private void setProgressLayout(){
        progressLayout.setVisibility(View.VISIBLE);
        textsLayout.setVisibility(View.GONE);
    }
    private void setTextsLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.VISIBLE);
    }
}
