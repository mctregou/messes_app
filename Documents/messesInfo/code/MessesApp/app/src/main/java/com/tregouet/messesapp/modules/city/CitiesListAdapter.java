package com.tregouet.messesapp.modules.city;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Schedule;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mctregouet on 02/11/16.
 */

public class CitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Schedule> schedules;
    private Context context;

    public CitiesListAdapter(Context context, ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        this.context = context;
    }

    public void refreshList(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_city, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CityItemViewHolder) holder).bind(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return (schedules == null) ? 0 : schedules.size();
    }

    public class CityItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city)
        TextView city;

        public CityItemViewHolder(View inflate) {
            super(inflate);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Schedule schedule) {
            city.setText("Paris");
        }

        @OnClick(R.id.background)
        public void chooseCity() {

        }
    }
}