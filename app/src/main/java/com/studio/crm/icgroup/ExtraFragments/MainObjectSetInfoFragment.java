package com.studio.crm.icgroup.ExtraFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Adapters.ButtonAdapter;
import com.studio.crm.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectSetInfoFragment extends FrameLayout {
    FrameLayout wholeLayout;
    RecyclerView recyclerView;
    List<String> strings;
    ButtonAdapter buttonAdapter;
    ConstraintLayout clearLayout;
    public MainObjectSetInfoFragment(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public MainObjectSetInfoFragment(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public MainObjectSetInfoFragment(Context context){
        super(context);
        initView();
    }

    private void initView(){
        View view=inflate(getContext(),R.layout.fragment_main_object_set_info, null);
        addView(view);
        createViews(view);

        ((MainActivity) getContext()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
         buttonAdapter=new ButtonAdapter(strings,getContext());
         recyclerView.setAdapter(buttonAdapter);
    }
    public void setListener(OnClickListener onClickListener){
        clearLayout.setOnClickListener(onClickListener);
    }
    public void setLinsteners(List<OnClickListener> linsteners){
        buttonAdapter.setListener(linsteners);
    }
    public void setList(List<String> list){
        strings.clear();
        strings.addAll(list);
        buttonAdapter.notifyDataSetChanged();
    }
    public void setWholeLayoutList(OnClickListener list){
        wholeLayout.setOnClickListener(list);
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        wholeLayout=(FrameLayout) view.findViewById(R.id.wholeLayout);
        clearLayout=(ConstraintLayout) view.findViewById(R.id.clearLayout);

        strings=new ArrayList<>();
    }

}
