package com.kyadav.filescanner.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyadav.filescanner.R;
import com.kyadav.filescanner.base.BaseViewHolder;
import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.datamodel.FileModel;

import java.io.File;
import java.util.List;

/**
 * Created by kyadav on 20/04/18.
 */

public class FileFrequencyAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    List<FileFrequencyModel> list;

    public FileFrequencyAdapter(List<FileFrequencyModel> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.frequency_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItems(List<FileFrequencyModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends BaseViewHolder {

        public TextView fileExtension;
        public TextView frequency;

        public MyViewHolder(View itemView) {
            super(itemView);
            fileExtension = itemView.findViewById(R.id.fileExtension);
            frequency = itemView.findViewById(R.id.frequency);
        }

        @Override
        public void clear() {
            fileExtension.setText("");
            frequency.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);
            fileExtension.setText(list.get(position).getFileExtension());
            frequency.setText(list.get(position).getFrequency());

        }
    }
}
