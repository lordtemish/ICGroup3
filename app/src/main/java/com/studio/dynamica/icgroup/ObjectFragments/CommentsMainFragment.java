package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.JalobaAdapter;
import com.studio.dynamica.icgroup.Forms.JalobaForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsMainFragment extends Fragment {
    RecyclerView recyclerView;
    TextView notAnswered, ArchiveTextView;
    List<JalobaForm> firstForm, secondForm, jalobaForms;
    JalobaAdapter adapter;
    public CommentsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_comments_main, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);



        jalobaForms=new ArrayList<>();

        jalobaForms.add(new JalobaForm("02.08.2018","Представитель клиента","Темирлан","Ген. директор",getActivity().getResources().getString(R.string.bigtext)));jalobaForms.add(new JalobaForm("","","","",""));jalobaForms.add(new JalobaForm("","","","",""));jalobaForms.add(new JalobaForm("","","","",""));
        secondForm.addAll(jalobaForms);
        adapter=new JalobaAdapter(jalobaForms);
        recyclerView.setAdapter(adapter);

        setListeners();
        return view;
    }

    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        notAnswered=(TextView) view.findViewById(R.id.notAnsweredTextView);
        ArchiveTextView=(TextView) view.findViewById(R.id.ArchiveTextView);
        firstForm=new ArrayList<>();
        secondForm=new ArrayList<>();
    }
    private void setListeners(){
        notAnswered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jalobaForms.clear();
                jalobaForms.addAll(firstForm);
                setChose(0);
            }
        });
        ArchiveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jalobaForms.clear();
                jalobaForms.addAll(secondForm);
                setChose(1);
            }
        });
    }

    public void setChose(int a){
        switch (a){
            case 0:
                setBlackTextView(notAnswered);
                setGreyTextView(ArchiveTextView);
                adapterChanged();
                break;
                default:
                    setBlackTextView(ArchiveTextView);
                    setGreyTextView(notAnswered);
                    adapterChanged();
                    break;
        }
    }
    private void adapterChanged(){
        adapter.notifyDataSetChanged();
    }
    private void setBlackTextView(TextView t){
        t.setTextColor(getActivity().getResources().getColor(R.color.black));
    }
    private void setGreyTextView(TextView t){
        t.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
    }
}
