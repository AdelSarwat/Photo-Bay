package com.example.photobay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mData;
    private OnItemClickListener mlistener;

    public PhotoAdapter(Context context, List<String> mData) {
        this.mContext = context;
        this.mData = mData;
    }


    public void SetOnItemClickListener(OnItemClickListener mlistener){
        this.mlistener = mlistener;
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    @NonNull


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.with(mContext).load(mData.get(position)).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_res);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mlistener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mlistener.OnItemClick(position);
                        }
                    }

                }
            });

        }
    }
}
