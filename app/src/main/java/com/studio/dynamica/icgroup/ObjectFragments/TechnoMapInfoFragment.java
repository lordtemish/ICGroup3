package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Adapters.WashAdapter;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.Forms.WashForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechnoMapInfoFragment extends Fragment {
    RecyclerView technicView,washView, statusComment, commentsRecycler;

    public TechnoMapInfoFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_techno_map_info, container, false);
        List<WashForm> washForms=new ArrayList<>();
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));
        washForms.add(new WashForm("","",6));
        technicView=(RecyclerView) view.findViewById(R.id.technicRecyclerView);
        WashAdapter washAdapter=new WashAdapter(washForms);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        technicView.setLayoutManager(layoutManager);
        technicView.setItemAnimator(new DefaultItemAnimator());
        technicView.setAdapter(washAdapter);

        washView=(RecyclerView) view.findViewById(R.id.washRecyclerView);
        RecyclerView.LayoutManager layoutManager1=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        washView.setLayoutManager(layoutManager1);
        washView.setItemAnimator(new DefaultItemAnimator());
        washView.setAdapter(washAdapter);

        List<MessageForm> forms=new ArrayList<>();
        List<MessageForm> forms1=new ArrayList<>();
        forms.add(new MessageForm("some text i am just writing some tex cause i need some big text for my programm, so wgile i was writing whis programm, i was thinking bout a lot of things"));
        forms1.add(new MessageForm("qwe","","",5));
        MessageAdapter messageAdapter=new MessageAdapter(forms);
        MessageAdapter messageAdapter1=new MessageAdapter(forms1);
        statusComment=(RecyclerView) view.findViewById(R.id.statusCommentsRecyclerView);
        RecyclerView.LayoutManager layoutManager2=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        statusComment.setLayoutManager(layoutManager2);
        statusComment.setItemAnimator(new DefaultItemAnimator());
        statusComment.setAdapter(messageAdapter);
        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        RecyclerView.LayoutManager layoutManager3=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        commentsRecycler.setLayoutManager(layoutManager3);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        commentsRecycler.setAdapter(messageAdapter1);
        return view;
    }

}
