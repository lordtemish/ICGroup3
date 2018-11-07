package com.studio.dynamica.icgroup.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.studio.dynamica.icgroup.R;

import java.util.ArrayList;
import java.util.List;

public class FilesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private class myHolder extends RecyclerView.ViewHolder{
        ImageView imageView,delImage, video;
        FrameLayout progressLayout;
        private myHolder(View view){
            super(view);
            imageView=(ImageView) view.findViewById(R.id.image);
            delImage=(ImageView) view.findViewById(R.id.delImage);
            video=(ImageView) view.findViewById(R.id.video);
            progressLayout=(FrameLayout)view.findViewById(R.id.progressLayout);
        }
        private void setInfo(Bitmap b, Boolean a){
            imageView.setImageBitmap(b);
            if(a){
                video.setVisibility(View.VISIBLE);
            }
            else{
                video.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder1, final int position) {
        myHolder holder=(myHolder)holder1;
        holder.setInfo(bitmaps.get(position), statuses.get(position));
        holder.delImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bitmaps.remove(position);
                notifyDataSetChanged();
            }
        });
    }
    private List<Bitmap> bitmaps;
    private List<Boolean> statuses;
    public FilesAdapter(List<Bitmap> bs){
        bitmaps=bs;
        statuses=new ArrayList<>();
    }

    public void setStatuses(List<Boolean> statuses) {
        this.statuses = statuses;
    }
    public void addStatus(Boolean status){
        statuses.add(status);
    }

    Context context;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view= LayoutInflater.from(context).inflate(R.layout.files_row,parent,false);
        return new myHolder(view);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
}
