package com.studio.dynamica.icgroup.ExtraFragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.GraphicDateAdapter;
import com.studio.dynamica.icgroup.Forms.GraphicDateForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class GraphicDateFrame extends FrameLayout{
    RecyclerView recyclerView;
    List<TextView> textViews;
    List<OnClickListener> listeners;
    List<GraphicDateForm> dateForms;
    GraphicDateAdapter adapter;
    public GraphicDateFrame(Context context, AttributeSet attrs, int defstyle){
        super(context,attrs,defstyle);
        initView();
    }
    public GraphicDateFrame(Context context, AttributeSet attrs){
        super(context,attrs);
        initView();
    }
    public GraphicDateFrame(Context context){
        super(context);
        initView();

    }
    private void initView() {
        listeners=new ArrayList<>();
        View view = inflate(getContext(), R.layout.graphicdate_frame, this);
        createViews(view);

        ((MainActivity)view.getContext()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.HORIZONTAL);
        dateForms=new ArrayList<>();
        dateForms.add(new GraphicDateForm());
         adapter=new GraphicDateAdapter(dateForms);
        recyclerView.setAdapter(adapter);
        
        recyclerView.scrollToPosition(dateForms.size()-2);
        for(TextView i:textViews)
        ((MainActivity)getContext()).setType("demibold",i);
    }
    public void setListeners(List<OnClickListener > clickListeners){
        listeners=clickListeners;
    }
    public void setList(List<GraphicDateForm> graphicDateForms){
            dateForms.clear();dateForms.addAll(graphicDateForms);
            adapter.notifyDataSetChanged();
    }
    private void createViews(View view){
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
        textViews=new ArrayList<>();
        textViews.add((TextView)view.findViewById(R.id.layout0Text));
        textViews.add((TextView)view.findViewById(R.id.layout1Text));
        textViews.add((TextView)view.findViewById(R.id.layout2Text));
        textViews.add((TextView)view.findViewById(R.id.layout3Text));
        textViews.add((TextView)view.findViewById(R.id.layout4Text));
        textViews.add((TextView)view.findViewById(R.id.layout5Text));
    }
    public void onScroll(int position){
        try{
            recyclerView.smoothScrollToPosition(position);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
