package com.studio.dynamica.icgroup.Listeners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.studio.dynamica.icgroup.Activities.MainActivity;
import com.studio.dynamica.icgroup.Forms.MainObjectRowForm;
import com.studio.dynamica.icgroup.ObjectFragments.MainObjectMainFrament;
import com.studio.dynamica.icgroup.R;

public class MainObjectRowOnClickListener implements View.OnClickListener{
    String id;
    String name;
    Context context;
    public MainObjectRowOnClickListener(MainObjectRowForm form, Context context) {
        this.id = form.getId();
        this.name=form.getName();
        this.context=context;
    }

    @Override
    public void onClick(View v)
    {
        MainActivity mainActivity=(MainActivity)context;
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        bundle.putString("name",name);
        MainObjectMainFrament frament=new MainObjectMainFrament();
        frament.setArguments(bundle);
        mainActivity.setFragment(R.id.content_frame,frament);
    }
}
