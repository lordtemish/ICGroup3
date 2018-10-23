package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.studio.dynamica.icgroup.Forms.MainFramentProgressForm;
import com.studio.dynamica.icgroup.Forms.PhonesRowForm;
import com.studio.dynamica.icgroup.R;

import java.util.List;

public class MainFramentProgressRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public class myHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView percentage;
        ProgressBar progressBar;
            public myHolder(View view){
                super(view);
                name=(TextView) view.findViewById(R.id.mainFramentProgressRowTextView);name.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/avenir-light.ttf"));
                percentage=(TextView) view.findViewById(R.id.mainFramentProgressRowPercentageTextView);percentage.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AVENIRNEXT-DEMIBOLD.ttf"));
                progressBar=(ProgressBar) view.findViewById(R.id.mainFramentProgressRowProgressBar);
            }
            private void setInfo(MainFramentProgressForm form){

            }
    }
    List<MainFramentProgressForm> list;
    Context context;
        public MainFramentProgressRecycleAdapter(List<MainFramentProgressForm> list,Context context){
            this.list=list;
            this.context=context;
        }
    @Override
    public  void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position){
        myHolder holder=(myHolder) holder1;
        MainFramentProgressForm form=list.get(position);
        holder.name.setText(form.getName()+"");
        holder.percentage.setText(form.getPercentage()+"%");
        holder.progressBar.setProgress(form.getPercentage());

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup groupm, int viewType){
        View itemView = LayoutInflater.from(groupm.getContext()).inflate(R.layout.mainframentprogress_row, groupm, false);
        return new MainFramentProgressRecycleAdapter.myHolder(itemView);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
