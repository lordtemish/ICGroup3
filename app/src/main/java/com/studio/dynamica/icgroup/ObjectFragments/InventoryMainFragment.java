package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AddOrderAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentReqAdapter;
import com.studio.dynamica.icgroup.Adapters.InventorizationAdapter;
import com.studio.dynamica.icgroup.Adapters.InventoryListAdapter;
import com.studio.dynamica.icgroup.Adapters.InventoryTopAdapter;
import com.studio.dynamica.icgroup.Adapters.MaterialAdapter;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.InventorizationForm;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMainFragment extends Fragment {
    ImageView leftImageView, rightImageView, plusImageView, dateArrowImageView;
    TextView pageTextView, archiveOrderTextView, newOrderTextView,dateTextView;
    LinearLayout textsLayout;
    RecyclerView topRecycler;
    ConstraintLayout progressLayout, dateLayout,pointLayout;
    RecyclerView inventoryRecycler, equipmentReq;
    String[] a={"Инвентарь","Инвентаризация","Заявки на пополнение"}, months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};

    int page=0;
    Calendar cal;
    /*EquipmentAdapter equipmentAdapter;
    MaterialAdapter materialAdapter;*/
    InventorizationAdapter inventorizationAdapter;
    List<InventorizationForm> inventorizationForms;
    AddOrderAdapter addOrderAdapter;
    List<AddOrderForm> addOrderForms, newOrderForms, archOrderForms;
    InventoryListAdapter listAdapter;
    List<String> reqList;
    InventoryTopAdapter topAdapter;
    NumberPicker datePicker, yearPicker;
    public InventoryMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_main, container, false);
        cal=Calendar.getInstance();
        cal.setTime(new Date());
        createViews(view);
        createAdapters();

        ((MainActivity) getActivity()).setRecyclerViewOrientation(inventoryRecycler, LinearLayoutManager.VERTICAL);
        ((MainActivity) getActivity()).setRecyclerViewOrientation(topRecycler, LinearLayoutManager.HORIZONTAL);
        List<View.OnClickListener> listeners=new ArrayList<>();
        for(int i=0;i<3;i++){
            final int k=i;
            listeners.add(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    page=k;
                    checkPage();
                }
            });
        }
        topAdapter=new InventoryTopAdapter(listeners);
        topRecycler.setAdapter(topAdapter);


        //inventoryRecycler.setAdapter(equipmentAdapter);
        ((MainActivity)getActivity()).setRecyclerViewOrientation(equipmentReq,LinearLayout.HORIZONTAL);
        //equipmentReq.setAdapter(listAdapter);
        pickerSettings();
        createListeners();

        leftImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(-1);
            }
        });
        rightImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPage(+1);
            }
        });
        checkPage();

        dateArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrow();
            }
        });
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onArrow();
            }
        });
        return view;
    }
    private void pickerSettings(){
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(10);

        yearPicker.setMinValue(cal.get(Calendar.YEAR)-1);
        yearPicker.setMaxValue(cal.get(Calendar.YEAR)+2);
        yearPicker.setValue(cal.get(Calendar.YEAR));
    }
    private void createViews(View view){
        leftImageView=(ImageView) view.findViewById(R.id.ImageLeftView);
        rightImageView=(ImageView) view.findViewById(R.id.ImageRightView);
        pageTextView=(TextView) view.findViewById(R.id.pageTextView);
        textsLayout=(LinearLayout) view.findViewById(R.id.textsLayout);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);
        inventoryRecycler=(RecyclerView) view.findViewById(R.id.inventoryRecycler);
        topRecycler=(RecyclerView) view.findViewById(R.id.topRecycler);
        equipmentReq=(RecyclerView) view.findViewById(R.id.equipmentRec);
        datePicker=(NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        dateLayout=(ConstraintLayout) view.findViewById(R.id.dateLayout);
        pointLayout=(ConstraintLayout) view.findViewById(R.id.pointLayout);
        archiveOrderTextView=(TextView) view.findViewById(R.id.archiveOrderTextView);
        newOrderTextView=(TextView) view.findViewById(R.id.newOrderTextView);
        dateTextView=(TextView) view.findViewById(R.id.dateTextView);

        plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
        dateArrowImageView=(ImageView) view.findViewById(R.id.dateArrowImageView);

    }

    private void createAdapters(){
        /*List<EquipmentForm> equipmentForms=new ArrayList<>();
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт",5));
        equipmentForms.add(new EquipmentForm("name","someid",4, remontForms,orderForms));equipmentForms.add(new EquipmentForm("name","someid",4,new ArrayList<RemontForms>(),orderForms));equipmentForms.add(new EquipmentForm("name","someid",4, remontForms, orderForms));equipmentForms.add(new EquipmentForm("name","someid",4));

        equipmentAdapter=new EquipmentAdapter(equipmentForms);

        List<MaterialForm> materialForms=new ArrayList<>();
        materialForms.add(new MaterialForm("","",2,5));materialForms.add(new MaterialForm("","",2,5, orderForms));materialForms.add(new MaterialForm("","",2,5, orderForms));

        materialAdapter=new MaterialAdapter(materialForms);*/

        List<InventorizationForm> inventorizationForms=new ArrayList<>();
        inventorizationForms.add(new InventorizationForm("","","",80));inventorizationForms.add(new InventorizationForm("","","",80));inventorizationForms.add(new InventorizationForm("","","",80));
        this.inventorizationForms=new ArrayList<>();
        this.inventorizationForms.addAll(inventorizationForms);
        inventorizationAdapter=new InventorizationAdapter(this.inventorizationForms);

        List<AddOrderForm> addOrderForms=new ArrayList<>();
        addOrderForms.add(new AddOrderForm("","","","","actual","",2,5));addOrderForms.add(new AddOrderForm("","","","","finished","",2,5));addOrderForms.add(new AddOrderForm("","","","","accepted","",4,4));
        addOrderForms.add(new AddOrderForm("","","","","cancel","",4,4));addOrderForms.add(new AddOrderForm("","","","","waiting","",4,4));addOrderForms.add(new AddOrderForm("","","","","timeout","",4,4));
        this.addOrderForms=new ArrayList<>();this.addOrderForms.addAll(addOrderForms);
        newOrderForms=addOrderForms;archOrderForms=new ArrayList<>();

        addOrderAdapter=new AddOrderAdapter(this.addOrderForms);

        reqList=new ArrayList<>();
        reqList.add("Оборудование");
        reqList.add("Расходный материал");
        reqList.add("Инвентарь");
        reqList.add("УМС");
        reqList.add("Сезонный инвентарь");
        reqList.add("Спец. одежда");
        listAdapter=new InventoryListAdapter(reqList);
      /*  reqAdapter=new EquipmentReqAdapter(reqList);
        reqAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEquip(reqAdapter.getClicked());
            }
        });*/
    }
   /* private void setEquip(int a){
        switch (a){
            case 0:
                inventoryRecycler.setAdapter(equipmentAdapter);
                break;
            case 1:
                inventoryRecycler.setAdapter(materialAdapter);
                break;
                default:
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        checkPage();
    }

    private void createListeners(){
        newOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invOrdersChange(true);
            }
        });
        archiveOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invOrdersChange(false);
            }
        });
        plusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).setFragment(R.id.content_frame,new InventoryPassInventorizationFragment());
            }
        });
    }
    private void checkPage(int a){
        page+=a;
        checkPage();
    }
    /*

               case 1:
                setProgressLayout();
                inventoryRecycler.setAdapter(materialAdapter);
                break;
     */
    private void checkPage(){
        if(page<0) page=a.length-1;
        if(page==a.length) page=0;
        pageTextView.setText(a[page]);
        equipmentReq.setVisibility(View.GONE);
        switch (page){
            case 0:
                setProgressLayout();
                inventoryRecycler.setAdapter(listAdapter);
                break;
            case 1:
                setProgressLayout(true);
                inventoryRecycler.setAdapter(inventorizationAdapter);
                break;
            default:
                setTextsLayout();
                inventoryRecycler.setAdapter(addOrderAdapter);
                break;
        }
        topAdapter.setPage(page);
        topAdapter.notifyDataSetChanged();
    }
    private void setProgressLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
        pointLayout.setVisibility(View.GONE);
    }
    private void setProgressLayout(boolean a){
        progressLayout.setVisibility(View.VISIBLE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.VISIBLE);
        pointLayout.setVisibility(View.VISIBLE);
    }
    private void setTextsLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.VISIBLE);
        dateLayout.setVisibility(View.GONE);
        pointLayout.setVisibility(View.GONE);
    }

    private void invOrdersChange(boolean New){
        if(New){
            addOrderForms.clear();
            addOrderForms.addAll(newOrderForms);
            addOrderAdapter.notifyDataSetChanged();
            archiveOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
            newOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
        }
        else {
            addOrderForms.clear();
            addOrderForms.addAll(archOrderForms);
            addOrderAdapter.notifyDataSetChanged();
            archiveOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.black));
            newOrderTextView.setTextColor(getActivity().getResources().getColor(R.color.darkgrey));
        }
    }
    private void onArrow(){
        if(dateTextView.getVisibility()==View.VISIBLE){
            dateArrowImageView.setImageResource(R.drawable.ic_arrowup_green);
            dateTextView.setVisibility(View.GONE);
            yearPicker.setVisibility(View.VISIBLE);
            datePicker.setVisibility(View.VISIBLE);
        }
        else{
            dateArrowImageView.setImageResource(R.drawable.ic_arrowdown_green);
            dateTextView.setVisibility(View.VISIBLE);
            yearPicker.setVisibility(View.GONE);
            datePicker.setVisibility(View.GONE);
            setDate();
        }
    }
    private void setDate(){
        dateTextView.setText(months[datePicker.getValue()-1]+" "+yearPicker.getValue());
    }
}
