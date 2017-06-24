package com.tregouet.messesapp.modules.church;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Schedule;
import com.tregouet.messesapp.model.SearchResult;
import com.tregouet.messesapp.util.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mctregouet on 02/11/16.
 */

public class SchedulesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Schedule> schedules;
    private Context context;

    public SchedulesListAdapter(Context context, ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        this.context = context;
    }

    public void refreshList(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SearchItemViewHolder) holder).bind(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return (schedules == null) ? 0 : schedules.size();
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.day)
        TextView day;

        @BindView(R.id.hours)
        TextView hours;

        @BindView(R.id.description)
        TextView description;

        public SearchItemViewHolder(View inflate) {
            super(inflate);

            ButterKnife.bind(this, itemView);
        }

        public void bind(Schedule schedule) {
            day.setText(schedule.getDay());
            hours.setText(schedule.getStringHours());
            if (schedule.getDescription() != null && !schedule.getDescription().equals("")) {
                description.setVisibility(View.VISIBLE);
                description.setText(schedule.getDescription());
            } else {
                description.setVisibility(View.GONE);
            }
        }
    }
}