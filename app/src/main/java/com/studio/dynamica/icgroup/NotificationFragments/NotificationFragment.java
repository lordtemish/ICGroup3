package com.studio.dynamica.icgroup.NotificationFragments;


import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.NotificationAdapter;
import com.studio.dynamica.icgroup.Forms.NotificationForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {
    RecyclerView notificationRecyclerView;

    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_notification, container, false);

        createViews(view);

        ((MainActivity)getActivity()).setRecyclerViewOrientation(notificationRecyclerView, LinearLayoutManager.VERTICAL);
        List<NotificationForm> forms=new ArrayList<>();
        forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));forms.add(new NotificationForm("","","","",""));
        NotificationAdapter adapter=new NotificationAdapter(forms);
        notificationRecyclerView.setAdapter(adapter);

        return view;
    }
    private void createViews(View view){
        notificationRecyclerView=(RecyclerView) view.findViewById(R.id.notificationsRecyclerView);
    }
}
