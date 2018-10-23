package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.dynamica.icgroup.Adapters.ChooseAcceptAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.ChooseAcceptForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewCheckListFragment extends Fragment {

    RecyclerView recyclerView, acceptRecyclerView;
    TextView mainObjectTitle, addTextView,acceptLabel;
    boolean media=false;
    RadioButton mediaRadio;
    EditText commentEditText;
    FrameLayout commentLayout;
    public AddNewCheckListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_new_check_list, container, false);
        createViews(view);
        setFonttype();

        mediaRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaClicked();
            }
        });

        List<ChooseAcceptForm> acceptForms=new ArrayList<ChooseAcceptForm>();
        acceptForms.add(new ChooseAcceptForm("Отдел продаж","Темирлан Алмасов","ОПУ"));
        acceptForms.add(new ChooseAcceptForm("Отдел продаж","Темирлан Алмасов","ОПУ"));
        acceptForms.add(new ChooseAcceptForm("Отдел продаж","Темирлан Алмасов","ОПУ"));
        acceptForms.add(new ChooseAcceptForm("Отдел продаж","Темирлан Алмасов","ОПУ"));
        ChooseAcceptAdapter acceptAdapter=new ChooseAcceptAdapter(acceptForms);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView,LinearLayoutManager.VERTICAL);
        acceptRecyclerView.setAdapter(acceptAdapter);

        List<CheckListBoxForm> boxForms=new ArrayList<>();
        List<CheckListBoxRowForm> boxRowForms=new ArrayList<>();

        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));

        boxForms.add(new CheckListBoxForm("",true, boxRowForms));
        boxForms.add(new CheckListBoxForm("",false, boxRowForms));


        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);

        CheckListBoxAdapter adapter=new CheckListBoxAdapter(boxForms);
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);
        acceptLabel=(TextView) view.findViewById(R.id.acceptLabel);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        addTextView=(TextView) view.findViewById(R.id.addTextView);
        mediaRadio=(RadioButton) view.findViewById(R.id.mediaRadio);
        commentEditText=(EditText) view.findViewById(R.id.commentEditText);
        commentLayout=(FrameLayout) view.findViewById(R.id.commentLayout);
    }
    private void setFonttype(){
        setTypeFace("it",mainObjectTitle);
        setTypeFace("demibold", mediaRadio, addTextView);
        setTypeFace("light", acceptLabel);
    }
    private void setTypeFace(String s, TextView... textViews){
        for(int i=0;i<textViews.length;i++){
            textViews[i].setTypeface((((MainActivity)getActivity()).getTypeFace(s)));
        }
    }

    public void mediaClicked(){
        if(media){
            mediaRadio.setChecked(false);
            mediaRadio.setTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.greyy));
            commentEditText.setFocusableInTouchMode(false);
            commentEditText.setFocusable(false);
            commentEditText.setText("");
            commentLayout.setBackgroundResource((R.drawable.grey_line));
            media=false;
        }
        else{
            mediaRadio.setChecked(true);
            mediaRadio.setTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setHintTextColor(getActivity().getResources().getColor(R.color.black));
            commentEditText.setFocusableInTouchMode(true);
            commentEditText.setFocusable(true);
            commentLayout.setBackgroundResource((R.drawable.black_line));
            media=true;
        }

    }
}
