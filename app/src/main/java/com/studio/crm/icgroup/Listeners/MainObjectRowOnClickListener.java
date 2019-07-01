package com.studio.crm.icgroup.Listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studio.crm.icgroup.Activities.MainActivity;
import com.studio.crm.icgroup.Forms.MainObjectRowForm;
import com.studio.crm.icgroup.ObjectFragments.MainObjectMainFrament;
import com.studio.crm.icgroup.R;

public class MainObjectRowOnClickListener implements View.OnClickListener{
    String id;
    String name;
    Context context;
    MainObjectRowForm form;
    public MainObjectRowOnClickListener(MainObjectRowForm form, Context context) {
        this.id = form.getId();
        this.name=form.getName();
        this.form=form;
        this.context=context;
    }

    @Override
    public void onClick(View v)
    {
        MainActivity mainActivity=(MainActivity)context;
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        bundle.putInt("location",form.getLocation());
        bundle.putString("city",form.getCity());
        MainObjectMainFrament frament=new MainObjectMainFrament();
        frament.setArguments(bundle);
        mainActivity.setFragment(R.id.content_frame,frament);
    }
}
