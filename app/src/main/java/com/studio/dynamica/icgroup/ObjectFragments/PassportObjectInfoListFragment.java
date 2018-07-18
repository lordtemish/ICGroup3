package com.studio.dynamica.icgroup.ObjectFragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Adapters.PhonesAdapter;
import com.studio.dynamica.icgroup.Adapters.ProgressPhonesAdapter;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.Forms.ProgressPhoneForm;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassportObjectInfoListFragment extends Fragment {
    HashMap<String, TextView> mapText;
    RecyclerView phonesRecycler;
    RecyclerView progressPhoneRecycler;
    View view;
    List<TextView> smenaSTextView;
    List<RecyclerView.Adapter> smenaSAdapter;
    ProgressPhonesAdapter adapter1;

    List<List<ProgressPhoneForm>>  allForms;
    List<ProgressPhoneForm> forms;
    TextView title;
    public PassportObjectInfoListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view=inflater.inflate(R.layout.fragment_passport_object_info_list, container, false);
       title=(TextView) view.findViewById(R.id.title);
       title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
       smenaSTextView=new ArrayList<>();
       smenaSAdapter=new ArrayList<>();
        mapText=new HashMap<>();
        addAlltoMap();
        setListener();
        for(TextView i:smenaSTextView){
            i.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
        }
        for (String i:
             mapText.keySet()) {
            if(i.contains("TextView")) {
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-MediumCn.ttf"));
            }
            else{
                mapText.get(i).setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"fonts/AvenirNextLTPro-Bold.ttf"));
            }
        }
        phonesRecycler=(RecyclerView) view.findViewById(R.id.phonesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager=new LinearLayoutManager(getActivity());
        List<PhonesRowForm> phonesRowFormList=new ArrayList<>();
        phonesRowFormList.add(new PhonesRowForm(true,"Рыскулова Динара","Администратор"));
        phonesRowFormList.add(new PhonesRowForm(true,"Рыскулова Динара","Администратор"));
        PhonesAdapter adapter=new PhonesAdapter(phonesRowFormList,getActivity());
        phonesRecycler.setLayoutManager(mLayoutManager);
        phonesRecycler.setItemAnimator(new DefaultItemAnimator());
        phonesRecycler.setAdapter(adapter);
        progressPhoneRecycler=(RecyclerView) view.findViewById(R.id.progressPhoneRecycle);
        allForms=new ArrayList<>();
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        allForms.add(new ArrayList<ProgressPhoneForm>());
        forms=new ArrayList<>();
        allForms.get(0).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(0).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55,"Замена",new PhonesRowForm(false,"Темирлан   Алмасов","ОПУ")));
        allForms.get(1).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(1).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55));
        allForms.get(2).add(new ProgressPhoneForm(new PhonesRowForm(false,"Temirlan Almassov","OPU"), 55,"Замена",new PhonesRowForm(false,"Темирлан   Алмасов","ОПУ")));
        forms.addAll(allForms.get(0));
        adapter1=new ProgressPhonesAdapter(forms,getActivity());
        smenaSAdapter.add(adapter1);
        RecyclerView.LayoutManager mLayoutManager1=new LinearLayoutManager(getActivity());
        progressPhoneRecycler.setLayoutManager(mLayoutManager1);
        progressPhoneRecycler.setItemAnimator(new DefaultItemAnimator());
        progressPhoneRecycler.setAdapter(adapter1);

        return view;
    }
    public void setSmena(int a){
        clearSmena();
        smenaSTextView.get(a).setTextColor(getActivity().getResources().getColor(R.color.black));
        forms.clear();
        forms.addAll(allForms.get(a));
       adapter1.notifyDataSetChanged();
    }
    public void setSmena(View view){
        String tag=view.getTag().toString();
        int a=Integer.parseInt(tag);
        setSmena(a);
    }
    public void clearSmena(){
        for(TextView view:smenaSTextView){
            view.setTextColor(getActivity().getResources().getColor(R.color.greyy));
        }
    }
    public void setListener(){
        int l=0;
        for(TextView view:smenaSTextView){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSmena(v);
                }
            });
        }
    }
    public void addAlltoMap(){
        smenaSTextView.add((TextView) view.findViewById(R.id.firstSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.SecondSmena));
        smenaSTextView.add((TextView) view.findViewById(R.id.interns));
        mapText.put("infoListObjectNameTextView",(TextView) view.findViewById(R.id.infoListObjectNameTextView));
        mapText.put("infoListObjectName",(TextView) view.findViewById(R.id.infoListObjectName));
        mapText.put("infoListRegionTextView",(TextView) view.findViewById(R.id.infoListRegionTextView));
        mapText.put("infoListRegion",(TextView) view.findViewById(R.id.infoListRegion));
        mapText.put("infoListObjectAdressTextView",(TextView) view.findViewById(R.id.infoListObjectAddressTextView));
        mapText.put("infoListObjectAdress",(TextView) view.findViewById(R.id.infoListObjectAddress));

        mapText.put("infoListWorkStartTextView",(TextView) view.findViewById(R.id.infoListWorkStartTextView));
        mapText.put("infoListWorkStart",(TextView) view.findViewById(R.id.infoListWorkStart));
        mapText.put("infoListWorkStopTextView",(TextView) view.findViewById(R.id.infoListWorkStopTextView));
        mapText.put("infoListWorkStop",(TextView) view.findViewById(R.id.infoListWorkStop));
        mapText.put("infoListObjectAreaTextView",(TextView) view.findViewById(R.id.infoListObjectAreaTextView));
        mapText.put("infoListObjectArea",(TextView) view.findViewById(R.id.infoListObjectArea));

        mapText.put("infoListHeadTextView",(TextView) view.findViewById(R.id.infoListHeadTextView));
        mapText.put("infoListHead",(TextView) view.findViewById(R.id.infoListHead));
        mapText.put("infoListAdvisorTextView",(TextView) view.findViewById(R.id.infoListAdvisorTextView));
        mapText.put("infoListAdvisor",(TextView) view.findViewById(R.id.infoListAdvisor));
        mapText.put("infoListAdministratorTextView",(TextView) view.findViewById(R.id.infoListAdministratorTextView));
        mapText.put("infoListAdministrator",(TextView) view.findViewById(R.id.infoListAdministrator));
        mapText.put("infoListClientTextView",(TextView) view.findViewById(R.id.infoListClientTextView));
        mapText.put("infoListClient",(TextView) view.findViewById(R.id.infoListClient));
        mapText.put("infoListClientPreTextView",(TextView) view.findViewById(R.id.infoListClientPreTextView));
        mapText.put("infoListClientPre",(TextView) view.findViewById(R.id.infoListClientPre));

    }
}
