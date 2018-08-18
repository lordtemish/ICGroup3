package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AcceptAdapter;
import com.studio.dynamica.icgroup.Adapters.CheckListBoxAdapter;
import com.studio.dynamica.icgroup.Adapters.MessageAdapter;
import com.studio.dynamica.icgroup.Forms.AcceptForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxForm;
import com.studio.dynamica.icgroup.Forms.CheckListBoxRowForm;
import com.studio.dynamica.icgroup.Forms.MessageForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListInfoFragment extends Fragment {
    RecyclerView recyclerView, messagesInfoRecyclerView, infoAcceptRecyclerView;
    Spinner spinner;
    public CheckListInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_list_info, container, false);
        createViews(view);
        List<CheckListBoxForm> boxForms=new ArrayList<>();
        List<CheckListBoxRowForm> boxRowForms=new ArrayList<>();

        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));
        boxRowForms.add(new CheckListBoxRowForm("","",4));

        boxForms.add(new CheckListBoxForm("",true, boxRowForms));
        boxForms.add(new CheckListBoxForm("",false, boxRowForms));


        ((MainActivity) getActivity()).setRecyclerViewOrientation(recyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(messagesInfoRecyclerView, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(infoAcceptRecyclerView, LinearLayoutManager.HORIZONTAL);

        CheckListBoxAdapter adapter=new CheckListBoxAdapter(boxForms);
        recyclerView.setAdapter(adapter);

        MessageAdapter messageAdapter=new MessageAdapter(new MessageForm(getActivity().getResources().getString(R.string.bigtext)));
        messagesInfoRecyclerView.setAdapter(messageAdapter);

        List<AcceptForm> acceptForms=new ArrayList<>();
        acceptForms.add(new AcceptForm("","","","",false));acceptForms.add(new AcceptForm("","","","",false));acceptForms.add(new AcceptForm("","","","",false));acceptForms.add(new AcceptForm("","","","",true));
        AcceptAdapter acceptAdapter=new AcceptAdapter(acceptForms);
        infoAcceptRecyclerView.setAdapter(acceptAdapter);


        String[] numbers={"1","2","3","4","5"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),R.layout.rate_spinner_item,numbers){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view=null;
                view=super.getDropDownView(position, convertView, parent);

                return view;
            }
        };
        spinner.setAdapter(spinnerAdapter);

        return view;
    }


    private void createViews(View view){
        recyclerView=(RecyclerView) view.findViewById(R.id.checkListRecyclerView);
        spinner=(Spinner) view.findViewById(R.id.rateSpinner);
        messagesInfoRecyclerView=(RecyclerView) view.findViewById(R.id.messagesInfoRecyclerView);
        infoAcceptRecyclerView=(RecyclerView) view.findViewById(R.id.infoAcceptRecyclerView);
    }
}
