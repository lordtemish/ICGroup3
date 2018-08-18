package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Adapters.RadioAdapter;
import com.studio.dynamica.icgroup.Adapters.UserRowAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.Forms.RadioForm;
import com.studio.dynamica.icgroup.Forms.UserRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JalobaInfoFragment extends Fragment {
    RecyclerView userRecyclerView, messageRecyclerView, radioRecyclerView, acceptRecyclerView;
    ConstraintLayout makeAnswerLayout;
    LinearLayout answerLayout;
    public JalobaInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_jaloba_info, container, false);
        createViews(view);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(userRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(messageRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(radioRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(acceptRecyclerView, LinearLayoutManager.HORIZONTAL);

        List<UserRowForm> rowForms=new ArrayList<>();
        UserRowForm form=new UserRowForm("Temirlan","начальник отдела",getActivity().getResources().getString(R.string.datestring), "Отправитель","Представитель клиента");
        form.setUrl("https://image.freepik.com/free-photo/bokeh-light-of-gold-glitters_1220-1862.jpg");
        rowForms.add(new UserRowForm( "Жалоба","Качество услуг"));
        rowForms.add(form);
        List<RadioForm> radioForms=new ArrayList<>();
        radioForms.add(new RadioForm(true,"Халатное отношение"));
        radioForms.add(new RadioForm(false,"Не соответствующий внешний вид"));
        radioForms.add(new RadioForm(true,"Хамит на работе"));
        List<AcceptForm> acceptForms=new ArrayList<>();
        acceptForms.add(new AcceptForm("","","","",true));
        acceptForms.add(new AcceptForm("","","","",false));
        acceptForms.add(new AcceptForm("","","","",true));
        acceptForms.add(new AcceptForm("","","","",true));

        UserRowAdapter userRowAdapter=new UserRowAdapter(rowForms);
        MessageAdapter messageAdapter=new MessageAdapter(new MessageForm(getActivity().getResources().getString(R.string.bigtext)));
        RadioAdapter radioAdapter=new RadioAdapter(radioForms);
        AcceptAdapter acceptAdapter=new AcceptAdapter(acceptForms);

        userRecyclerView.setAdapter(userRowAdapter);
        messageRecyclerView.setAdapter(messageAdapter);
        radioRecyclerView.setAdapter(radioAdapter);
        acceptRecyclerView.setAdapter(acceptAdapter);

        setAnswerable(false);

        return view;
    }

    private void createViews(View view){
        userRecyclerView=(RecyclerView) view.findViewById(R.id.userRecyclerView);
        messageRecyclerView=(RecyclerView) view.findViewById(R.id.messageRecyclerView);
        radioRecyclerView=(RecyclerView) view.findViewById(R.id.radioRecyclerView);
        acceptRecyclerView=(RecyclerView) view.findViewById(R.id.acceptRecyclerView);

        makeAnswerLayout=(ConstraintLayout) view.findViewById(R.id.makeAnswerLayout);
        answerLayout=(LinearLayout) view.findViewById(R.id.answerLayout);
    }
    public void setAnswerable(boolean a){
        if(a){
            makeAnswerLayout.setVisibility(View.VISIBLE);
            answerLayout.setVisibility(View.GONE);
        }
        else{
            makeAnswerLayout.setVisibility(View.GONE);
            answerLayout.setVisibility(View.VISIBLE);
        }
    }
}
