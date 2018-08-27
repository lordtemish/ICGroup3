package com.studio.dynamica.icgroup.ObjectFragments;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shawnlin.numberpicker.NumberPicker;
import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Adapters.AddOrderAdapter;
import com.studio.dynamica.icgroup.Adapters.EquipmentAdapter;
import com.studio.dynamica.icgroup.Adapters.InventorizationAdapter;
import com.studio.dynamica.icgroup.Adapters.InventoryPassInventorizationFragment;
import com.studio.dynamica.icgroup.Adapters.MaterialAdapter;
import com.studio.dynamica.icgroup.Forms.AddOrderForm;
import com.studio.dynamica.icgroup.Forms.EquipmentForm;
import com.studio.dynamica.icgroup.Forms.InventorizationForm;
import com.studio.dynamica.icgroup.Forms.MaterialForm;
import com.studio.dynamica.icgroup.Forms.OrderForm;
import com.studio.dynamica.icgroup.Forms.RemontForms;
import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryMainFragment extends Fragment {
    ImageView leftImageView, rightImageView, plusImageView;
    TextView pageTextView, archiveOrderTextView, newOrderTextView;
    LinearLayout textsLayout;
    ConstraintLayout progressLayout, dateLayout;
    RecyclerView inventoryRecycler;
    String[] a={"Оборудование", "Расходный материал","Инвентаризация","Заявки на пополнение"}, months = {"Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};

    int page=0;

    EquipmentAdapter equipmentAdapter;
    MaterialAdapter materialAdapter;
    InventorizationAdapter inventorizationAdapter;
    List<InventorizationForm> inventorizationForms;
    AddOrderAdapter addOrderAdapter;
    List<AddOrderForm> addOrderForms, newOrderForms, archOrderForms;

    NumberPicker datePicker, yearPicker;
    public InventoryMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_inventory_main, container, false);
        createViews(view);
        createAdapters();

        ((MainActivity) getActivity()).setRecyclerViewOrientation(inventoryRecycler, LinearLayoutManager.VERTICAL);
        inventoryRecycler.setAdapter(equipmentAdapter);

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
        return view;
    }
    private void pickerSettings(){
        datePicker.setMinValue(1);
        datePicker.setMaxValue(months.length);
        datePicker.setDisplayedValues(months);
        datePicker.setValue(8);

        yearPicker.setMinValue(2016);
        yearPicker.setMaxValue(2019);
        yearPicker.setValue(2018);
    }
    private void createViews(View view){
        leftImageView=(ImageView) view.findViewById(R.id.ImageLeftView);
        rightImageView=(ImageView) view.findViewById(R.id.ImageRightView);
        pageTextView=(TextView) view.findViewById(R.id.pageTextView);
        textsLayout=(LinearLayout) view.findViewById(R.id.textsLayout);
        progressLayout=(ConstraintLayout) view.findViewById(R.id.progressLayout);
        inventoryRecycler=(RecyclerView) view.findViewById(R.id.inventoryRecycler);
        datePicker=(NumberPicker) view.findViewById(R.id.datePicker);
        yearPicker=(NumberPicker) view.findViewById(R.id.yearPicker);
        dateLayout=(ConstraintLayout) view.findViewById(R.id.dateLayout);
        archiveOrderTextView=(TextView) view.findViewById(R.id.archiveOrderTextView);
        newOrderTextView=(TextView) view.findViewById(R.id.newOrderTextView);

        plusImageView=(ImageView) view.findViewById(R.id.plusImageView);
    }

    private void createAdapters(){
        List<EquipmentForm> equipmentForms=new ArrayList<>();
        List<OrderForm> orderForms=new ArrayList<>();
        orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));orderForms.add(new OrderForm("","","","","",1,4));
        List<RemontForms> remontForms=new ArrayList<>();
        remontForms.add(new RemontForms("Ремонт",5));
        equipmentForms.add(new EquipmentForm("name","someid",4, remontForms,orderForms));equipmentForms.add(new EquipmentForm("name","someid",4,new ArrayList<RemontForms>(),orderForms));equipmentForms.add(new EquipmentForm("name","someid",4, remontForms, orderForms));equipmentForms.add(new EquipmentForm("name","someid",4));

        equipmentAdapter=new EquipmentAdapter(equipmentForms);

        List<MaterialForm> materialForms=new ArrayList<>();
        materialForms.add(new MaterialForm("","",2,5));materialForms.add(new MaterialForm("","",2,5, orderForms));materialForms.add(new MaterialForm("","",2,5, orderForms));

        materialAdapter=new MaterialAdapter(materialForms);

        List<InventorizationForm> inventorizationForms=new ArrayList<>();
        inventorizationForms.add(new InventorizationForm("","","",80));inventorizationForms.add(new InventorizationForm("","","",80));inventorizationForms.add(new InventorizationForm("","","",80));
        this.inventorizationForms=new ArrayList<>();
        this.inventorizationForms.addAll(inventorizationForms);
        inventorizationAdapter=new InventorizationAdapter(this.inventorizationForms);

        List<AddOrderForm> addOrderForms=new ArrayList<>();
        addOrderForms.add(new AddOrderForm("","","","","","",2,5));addOrderForms.add(new AddOrderForm("","","","","","",2,5));addOrderForms.add(new AddOrderForm("","","","","","",2,5));
        this.addOrderForms=new ArrayList<>();this.addOrderForms.addAll(addOrderForms);
        newOrderForms=addOrderForms;archOrderForms=new ArrayList<>();

        addOrderAdapter=new AddOrderAdapter(this.addOrderForms);
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
    private void checkPage(){
        if(page<0) page=a.length-1;
        if(page==a.length) page=0;
        pageTextView.setText(a[page]);
        switch (page){
            case 0:
                setProgressLayout();
                inventoryRecycler.setAdapter(equipmentAdapter);
                break;
            case 1:
                setProgressLayout();
                inventoryRecycler.setAdapter(materialAdapter);
                break;
            case 2:
                setProgressLayout(true);
                inventoryRecycler.setAdapter(inventorizationAdapter);
                break;
            default:
                setTextsLayout();
                inventoryRecycler.setAdapter(addOrderAdapter);
                break;
        }
    }
    private void setProgressLayout(){
        progressLayout.setVisibility(View.VISIBLE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
    }
    private void setProgressLayout(boolean a){
        progressLayout.setVisibility(View.VISIBLE);
        textsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.VISIBLE);
    }
    private void setTextsLayout(){
        progressLayout.setVisibility(View.GONE);
        textsLayout.setVisibility(View.VISIBLE);
        dateLayout.setVisibility(View.GONE);
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
}
