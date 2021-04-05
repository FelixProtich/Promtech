package com.felix.promtech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ModelClass>tasksList;

    public RecyclerAdapter (List<ModelClass>tasksList){this.tasksList = tasksList;}

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_design, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        String campaign = tasksList.get(position).getTextview1();
        String campaign_name = tasksList.get(position).getTextview2();
        String campaign_id = tasksList.get(position).getTextview3();

        holder.setData(campaign, campaign_name, campaign_id);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView textView2;
        private TextView textView3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.campaign);
            textView2 = itemView.findViewById(R.id.campaign_name);
            textView3 = itemView.findViewById(R.id.campaign_id);

        }

        public void setData(String campaign, String campaign_name, String campaign_id) {

            textView.setText(campaign);
            textView2.setText(campaign_name);
            textView3.setText(campaign_id);

        }
    }
}
