package com.tregouet.messesapp.modules.search;

import android.content.Context;
import android.content.Intent;
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
import com.tregouet.messesapp.model.Church;
import com.tregouet.messesapp.model.Schedule;
import com.tregouet.messesapp.model.SearchResult;
import com.tregouet.messesapp.modules.church.ChurchActivity;
import com.tregouet.messesapp.modules.church.ChurchEvent;
import com.tregouet.messesapp.util.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mctregouet on 02/11/16.
 */

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SearchResult> results;
    private Context context;

    public SearchListAdapter(Context context, ArrayList<SearchResult> results) {
        this.results = results;
        this.context = context;
    }

    public void refreshList(ArrayList<SearchResult> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SearchItemViewHolder) holder).bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return (results == null) ? 0 : results.size();
    }

    public class SearchItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.address)
        TextView address;

        @BindView(R.id.zipcode)
        TextView zipcode;

        @BindView(R.id.schedule)
        TextView schedule;

        @BindView(R.id.picture)
        ImageView picture;

        @BindView(R.id.background)
        RelativeLayout background;

        private SearchResult result;
        private long mOptionsLastClickTime = 0;

        public SearchItemViewHolder(View inflate) {
            super(inflate);

            ButterKnife.bind(this, itemView);
        }

        public void bind(SearchResult result) {
            this.result = result;
            name.setText(result.getChurch().getName());
            address.setText(result.getChurch().getAddress());
            if (result.getChurch().getZipcode() != null && !result.getChurch().getZipcode().equals("") && result.getChurch().getCity() != null && !result.getChurch().getCity().equals("")) {
                zipcode.setText(String.format(context.getString(R.string.zipcode_city), result.getChurch().getZipcode(), result.getChurch().getCity()));
            } else if (result.getChurch().getZipcode() != null) {
                zipcode.setText(result.getChurch().getZipcode());
            } else if (result.getChurch().getCity() != null) {
                zipcode.setText(result.getChurch().getCity());
            } else {
                zipcode.setText("");
            }
            schedule.setText(String.format(context.getString(R.string.schedule), result.getSchedule().getDay(), result.getSchedule().getStringHours()));

            System.out.println("adapter = " + result.getChurch().getImage());
            if (result.getChurch().getImage() != null && !result.getChurch().getImage().equals("")) {
                Glide.with(context)
                        .load(result.getChurch().getImage())
                        .into(picture);
            }
        }

        @OnClick(R.id.background)
        public void showChurch() {
            if (SystemClock.elapsedRealtime() - mOptionsLastClickTime < 1000) {
                return;
            }
            mOptionsLastClickTime = SystemClock.elapsedRealtime();

            if (Tools.isNetworkAvailable(context)) {
                EventBus.getDefault().postSticky(new ChurchEvent(result.getChurch()));
                context.startActivity(new Intent(context, ChurchActivity.class));
            }
        }
    }
}