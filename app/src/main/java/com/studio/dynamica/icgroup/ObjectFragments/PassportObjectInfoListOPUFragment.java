package com.studio.dynamica.icgroup.ObjectFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.CommentAdapter;
import com.studio.dynamica.icgroup.Forms.CommentForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListOPUFragment extends Fragment {
    RecyclerView commentsRecycler;
    Spinner spinner;
    List<CommentForm> commentForms, newCommentsList, allCommentsList;
    CommentAdapter commentAdapter;
    LinearLayout newComments,allComments;
    TextView newCommentTextView,allCommentTextView;
    FrameLayout newCommentFrame, allCommentFrame;

    public PassportObjectInfoListOPUFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_passport_object_info_list_opu, container, false);

        commentsRecycler=(RecyclerView) view.findViewById(R.id.commentsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        commentsRecycler.setLayoutManager(mLayoutManager);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());
        commentForms=new ArrayList<>();

        newCommentsList=new ArrayList<>();
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));
        newCommentsList.add(new CommentForm("Kopbay Dauren","02.08.2018"));
        newComments=(LinearLayout) view.findViewById(R.id.newCommentLinearLayout);
        newCommentFrame=(FrameLayout) view.findViewById(R.id.newCommentFrameLayout);
        newComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setComment(false);
            }
        });
        newCommentTextView=(TextView) view.findViewById(R.id.newCommentTextView);

        allCommentsList=new ArrayList<>();
        allCommentsList.add(new CommentForm("Temirlan Almassov","23.09.2015"));
        allCommentsList.add(new CommentForm("Temirlan Almassov","23.09.2015"));
        allCommentsList.add(new CommentForm("Temirlan Almassov","23.09.2015"));

        commentForms.addAll(newCommentsList);
        allComments=(LinearLayout) view.findViewById(R.id.allCommentsLinearLayout);
        allCommentFrame=(FrameLayout) view.findViewById(R.id.allCommentsFrameLayout);
        allComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setComment(true);
            }
        });
        allCommentTextView=(TextView) view.findViewById(R.id.allCommentsTextView);



        commentAdapter=new CommentAdapter(commentForms);
        commentsRecycler.setAdapter(commentAdapter);
        String[] spinnerList={"Выберите Сотрудника","asdasd","asdasd"};
        spinner=(Spinner) view.findViewById(R.id.employeeChangeSpinner);
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.simple_spinner_item,spinnerList);
        FrameLayout spinnerFrame=(FrameLayout) view.findViewById(R.id.spinnerFrameImage);
        spinnerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinner();
            }
        });
        spinner.setAdapter(spinnerAdapter);
        return view;
    }
    public void setComment(boolean all){
        clearComments();
        if(all){
            allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            allCommentFrame.setVisibility(View.VISIBLE);
            ChangeComments(allCommentsList);
        }
        else{
            newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newCommentFrame.setVisibility(View.VISIBLE);
            ChangeComments(newCommentsList);
        }

    }
    public void clearComments(){
        newCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        allCommentTextView.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        newCommentFrame.setVisibility(View.GONE);
        allCommentFrame.setVisibility(View.GONE);

    }
    public void ChangeComments(List<CommentForm> list){
            commentForms.clear();
            commentForms.addAll(list);
            commentAdapter.notifyDataSetChanged();
    }
    public void showSpinner(){
        spinner.performClick();
    }

}
