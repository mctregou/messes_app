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
import com.tregouet.messesapp.modules.model.SearchResult;
import com.tregouet.messesapp.util.Tools;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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
            name.setText(result.getName());
            address.setText(result.getAddress());
            if (result.getZipcode() != null && !result.getZipcode().equals("") && result.getCity() != null && !result.getCity().equals("")) {
                zipcode.setText(String.format(context.getString(R.string.zipcode_city), result.getZipcode(), result.getCity()));
            } else if (result.getZipcode() != null) {
                zipcode.setText(result.getZipcode());
            } else if (result.getCity() != null) {
                zipcode.setText(result.getCity());
            } else {
                zipcode.setText("");
            }
            schedule.setText(String.format(context.getString(R.string.schedule), result.getSchedule().getDay(), result.getSchedule().getStringHours()));

            System.out.println("adapter = " + result.getImage());
            if (result.getImage() != null && !result.getImage().equals("")) {
                Glide.with(context)
                        .load(result.getImage())
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
                //TODO show church
            }
        }
    }
}