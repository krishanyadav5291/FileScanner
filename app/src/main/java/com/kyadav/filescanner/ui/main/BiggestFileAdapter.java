package com.kyadav.filescanner.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kyadav.filescanner.R;
import com.kyadav.filescanner.base.BaseViewHolder;
import com.kyadav.filescanner.datamodel.FileFrequencyModel;

import java.io.File;
import java.util.List;

/**
 * Created by kyadav on 20/04/18.
 */

public class BiggestFileAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<File> list;

    public BiggestFileAdapter(List<File> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.biggest_file_row, parent, false);
        return new BiggestFileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<File> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends BaseViewHolder {

        public TextView fileName;
        public TextView fileSize;

        public MyViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            fileSize = itemView.findViewById(R.id.fileSize);
        }

        @Override
        public void clear() {
            fileName.setText("");
            fileSize.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);
            fileName.setText(list.get(position).getName());
            fileSize.setText(list.get(position).length() / 1048576 + " MB");

        }
    }
}
