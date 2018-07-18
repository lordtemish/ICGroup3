package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.GraphViewXML;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ServicesAdapter;
import com.studio.dynamica.icgroup.Forms.ServiceForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceObjectMainFragment extends Fragment {
    LinearLayout graphLinear;
    ConstraintLayout allServicesLayout;
    GraphViewXML graphViewXML;
    RecyclerView allServicesRecycler;
    ImageView allServicesImage;
    FrameLayout addNewServiceFrame;
    public ServiceObjectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_service_object_main, container, false);

        graphLinear=(LinearLayout) view.findViewById(R.id.graphLinearLayout);
        allServicesLayout=(ConstraintLayout) view.findViewById(R.id.allServicesLayout);
        allServicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServices();
            }
        });
        allServicesImage=(ImageView) view.findViewById(R.id.allServicesImageView);
        allServicesRecycler=(RecyclerView) view.findViewById(R.id.allServicesRecyclerView);

        addNewServiceFrame=(FrameLayout) view.findViewById(R.id.addNewServiceFrameLayout);
        addNewServiceFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewServiceOpen();
            }
        });

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        allServicesRecycler.setLayoutManager(layoutManager);
        allServicesRecycler.setItemAnimator(new DefaultItemAnimator());
        setServicesAdapter();


        graphViewXML=(GraphViewXML) view.findViewById(R.id.graphView);
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 30),
                new DataPoint(1, 30),
                new DataPoint(2, 60),
                new DataPoint(3, 20),
                new DataPoint(4, 50)
        }); LineGraphSeries<DataPoint> series23 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 33),
                new DataPoint(1, 15),
                new DataPoint(2, 40),
                new DataPoint(3, 29),
                new DataPoint(4, 11),
                new DataPoint(5, 22)
        });

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 20),
                new DataPoint(1, 14),
                new DataPoint(2, 49),
                new DataPoint(3, 79),
                new DataPoint(4, 21),
                new DataPoint(5, 11)
        });

        graphViewXML.getSecondScale().addSeries(series2);
        graphViewXML.getSecondScale().addSeries(series23);
        graphViewXML.getSecondScale().addSeries(series4);
        graphViewXML.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphViewXML.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){

        });
        graphViewXML.getSecondScale().setMinY(0);
        graphViewXML.getSecondScale().setMaxY(100);

        series2.setColor(Color.RED);

        return view;
    }
    public void openServices(){
        if(graphLinear.getVisibility()==View.GONE) {
            graphLinear.setVisibility(View.VISIBLE);
            allServicesImage.setImageResource(R.drawable.ic_arrowup);
        }
        else{
            graphLinear.setVisibility(View.GONE);
            allServicesImage.setImageResource(R.drawable.ic_arrowdown);
        }
    }
    public void setServicesAdapter(){
        List<ServiceForm> forms=new ArrayList<>();
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","failed","Обычный", 4,6));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","process","Обычный", 4,8));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","inwait","Обычный", 10,10));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","relate","Обычный", 1,6));
        forms.add(new ServiceForm("Какойто тип задачт","Тemirlan Almassov","accepted","Обычный", 7,7));
        ServicesAdapter adapter=new ServicesAdapter(forms);
        allServicesRecycler.setAdapter(adapter);
    }
    public void addNewServiceOpen(){
        ((MainActivity)getActivity()).setFragment(R.id.content_frame,new AddNewServiceFragment());
    }
}
