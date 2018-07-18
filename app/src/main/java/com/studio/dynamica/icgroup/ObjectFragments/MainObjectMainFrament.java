package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.ButtonAdapter;
import com.studio.dynamica.icgroup.Adapters.MainFramentProgressRecycleAdapter;
import com.studio.dynamica.icgroup.Adapters.PhonesAdapter;
import com.studio.dynamica.icgroup.Forms.MainFramentProgressForm;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.R;
import com.studio.dynamica.icgroup.StartFragments.StartPage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainObjectMainFrament extends Fragment {
    TextView mainObjectTitle;
    TextView name;
    TextView employees;
    TextView coms;
    TextView category;
    List<ImageView> stars;
    RecyclerView progresses;
    RecyclerView buttons;
    RecyclerView phonesRecycler;

    public MainObjectMainFrament() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_object_main_frament, container, false);
        mainObjectTitle=(TextView) view.findViewById(R.id.mainObjectTitle);
        mainObjectTitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-It.ttf"));
        name=(TextView) view.findViewById(R.id.mainObjectMainFragmentNameTextView);
        name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
        name.setText(this.getArguments().getString("name"));

        employees=(TextView) view.findViewById(R.id.mainObjectMainFramentEmployeesTextView);
        employees.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
        coms=(TextView) view.findViewById(R.id.mainObjectMainFramentCommsTextView);
        coms.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
        category=(TextView) view.findViewById(R.id.mainObjectMainFramentCategoryTextView);
        category.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));

        stars=new ArrayList<>();
        stars.add((ImageView) view.findViewById(R.id.mainObjectMainFragmentFav0));stars.add((ImageView) view.findViewById(R.id.mainObjectMainFragmentFav1));stars.add((ImageView) view.findViewById(R.id.mainObjectMainFragmentFav2));stars.add((ImageView) view.findViewById(R.id.mainObjectMainFragmentFav3));stars.add((ImageView) view.findViewById(R.id.mainObjectMainFragmentFav4));

        progresses=(RecyclerView) view.findViewById(R.id.mainObjectMainFramentProgressRecycler);
        List<MainFramentProgressForm> progresList=new ArrayList<>();
        progresList.add(new MainFramentProgressForm("Успеваемость",55));
        progresList.add(new MainFramentProgressForm("Посещаемость",85));
        progresList.add(new MainFramentProgressForm("Качество",15));
        progresList.add(new MainFramentProgressForm("Инветарь",38));
        MainFramentProgressRecycleAdapter progressRecycleAdapter=new MainFramentProgressRecycleAdapter(progresList,getActivity());
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        progresses.setLayoutManager(mLayoutManager);
        progresses.setItemAnimator(new DefaultItemAnimator());
        progresses.setAdapter(progressRecycleAdapter);

        buttons=(RecyclerView) view.findViewById(R.id.mainObjectMainFramentButtonsRecycler);
        List<String> buttonsList=new ArrayList<>();
        buttonsList.add("Паспорт объекта");
        buttonsList.add("Задачи");
        buttonsList.add("Технологическая карта");
        buttonsList.add("Контроль качества");
        buttonsList.add("Инвентарь");
        buttonsList.add("Жалобы");
        buttonsList.add("Посещения");
        List<View.OnClickListener> listeners=new ArrayList<>();
        View.OnClickListener listenerPassport=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new PasportObjectMainFragment()); }};
        View.OnClickListener serviceListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new ServiceObjectMainFragment()); }};
        View.OnClickListener TechnoMapListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new TechnoMapFragment()); }};
        View.OnClickListener ClientControlListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new ClientControlObjectMainFragment()); }};
        View.OnClickListener AttendanceListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new AttendanceMainFragment()); }};
        View.OnClickListener CommentsListener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new CommentsMainFragment()); }};
        View.OnClickListener listener=new View.OnClickListener() {@Override public void onClick(View v) { ((MainActivity)getActivity()).setFragment(R.id.content_frame,new InventoryMainFragment()); }};
        listeners.add(listenerPassport);
        listeners.add(serviceListener);
        listeners.add(TechnoMapListener);
        listeners.add(ClientControlListener);
        listeners.add(AttendanceListener);
        listeners.add(CommentsListener);
        listeners.add(listener);
        ButtonAdapter buttonAdapter=new ButtonAdapter(buttonsList,getActivity(),listeners,R.drawable.ic_arrowrightgreen);
        RecyclerView.LayoutManager mLayoutManagerq=new LinearLayoutManager(getActivity());
        buttons.setLayoutManager(mLayoutManagerq);
        buttons.setItemAnimator(new DefaultItemAnimator());
        buttons.setAdapter(buttonAdapter);

        phonesRecycler=(RecyclerView) view.findViewById(R.id.phonesRecyclerView);
        List<PhonesRowForm> phonesRowFormList=new ArrayList<>();
        phonesRowFormList.add(new PhonesRowForm(false,"Рыскулова А","Представитель клиента"));
        phonesRowFormList.add(new PhonesRowForm(true,"Сыздыков Н","Начальник производства"));
        phonesRowFormList.add(new PhonesRowForm(true,"Сыздыков Н","Куратор"));
        phonesRowFormList.add(new PhonesRowForm(true,"Сыздыков Н","Администратор"));
        PhonesAdapter adapter=new PhonesAdapter(phonesRowFormList,getActivity());
        RecyclerView.LayoutManager mLayoutManagerp=new LinearLayoutManager(getActivity());
        phonesRecycler.setLayoutManager(mLayoutManagerp);
        phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        phonesRecycler.setAdapter(adapter);
        setRate(3);
        return view;
    }

    public void setRate(int a){
        for(int i=0;i<5;i++){
            if(i<a){
                stars.get(i).setImageResource(R.drawable.ic_staryellow);
            }
            else{
                stars.get(i).setImageResource(R.drawable.ic_stargrey);
            }
        }
    }
}
