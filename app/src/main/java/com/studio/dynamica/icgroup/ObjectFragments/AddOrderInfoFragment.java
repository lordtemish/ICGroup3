package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.CommentaryAdapter;
import com.studio.dynamica.icgroup.Forms.CommentaryForm;
import com.studio.dynamica.icgroup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddOrderInfoFragment extends Fragment {
    RecyclerView commentaryRecyclerView;
    LinearLayout statusLayout;
    TextView statusTextView;
    public AddOrderInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_order_info, container, false);
        createViews(view);

        ((MainActivity) getActivity()).setRecyclerViewOrientation(commentaryRecyclerView, LinearLayoutManager.VERTICAL);
        CommentaryForm commentaryForm=new CommentaryForm("25.07.2018","Temirlan ALmassov",getActivity().getResources().getString(R.string.bigtext));
        CommentaryAdapter commentaryAdapter=new CommentaryAdapter(commentaryForm);
        commentaryRecyclerView.setAdapter(commentaryAdapter);
        setStatus(getArguments().getString("status", ""));
        return view;
    }
    private void createViews(View view){
        commentaryRecyclerView=(RecyclerView) view.findViewById(R.id.commentaryRecyclerView);
        statusLayout=(LinearLayout) view.findViewById(R.id.statusLayout);
        statusTextView=(TextView) view.findViewById(R.id.statusTextView);
    }

    private void setStatus(String s){
        switch (s){
            case "actual":

                break;
            case "accepted":
                statusLayout.setBackgroundResource(R.drawable.failedrow_green);
                statusTextView.setText("Выполненно");
                break;
            case "waiting":
                statusTextView.setText("Ожидает");
                break;
            case "finished":
                statusLayout.setBackgroundResource(R.drawable.closed_page);
                statusTextView.setText("Завершенно");
                break;
            case "timeout":
                statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                statusTextView.setText("Время вышло");
                break;
            case "cancel":
                statusLayout.setBackgroundResource(R.drawable.related_darkgreen_page);
                statusTextView.setText("Отказано");
                break;
        }
    }
}
