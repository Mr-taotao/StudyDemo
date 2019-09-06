package com.example.chtlei.mydemo.photowall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 19-6-21.
 */

public class PhotoRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private String[] data;

    public PhotoRecycleViewAdapter (Context context , String[] obj) {
        this.context = context;
        data = obj;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycle_view_item_1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
//        Glide.with(context).load(data[position]).into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"onclick position is " + position,Toast.LENGTH_LONG).show();
            }
        });

        viewHolder.imageView1.setOnClickListener(v -> {
            Toast.makeText(context,"onclicklike position is " + position,Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager ();
//        if(manager instanceof GridLayoutManager){
//            GridLayoutManager gridLayoutManager = (GridLayoutManager)manager;
//            gridLayoutManager.setSpanSizeLookup (new GridLayoutManager.SpanSizeLookup () {
//                @Override
//                public int getSpanSize(int position) {
//                    return 1;
//                }
//            });
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> viewSparseArray;
        private ImageView imageView;
        private ImageView imageView1;

        public ViewHolder(View itemView) {
            super(itemView);

            if (viewSparseArray == null) {
                viewSparseArray = new SparseArray<>();
            }

            imageView = (ImageView) viewSparseArray.get(R.id.imageView2);
            imageView1 = (ImageView) viewSparseArray.get(R.id.imageView3);

            if (imageView == null) {
                imageView = itemView.findViewById(R.id.imageView2);
                viewSparseArray.put(R.id.imageView2, imageView);
            }
            if (imageView1 == null) {
                imageView1 = itemView.findViewById(R.id.imageView3);
                viewSparseArray.put(R.id.imageView3, imageView1);
            }
        }
    }
}
