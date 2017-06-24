package com.tregouet.messesapp.modules.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Event;
import com.tregouet.messesapp.model.Schedule;
import com.tregouet.messesapp.model.SearchResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mctregouet on 02/11/2016.
 */

public class SearchListFragment extends Fragment {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.results_recyclerview)
    RecyclerView resultsRecyclerview;

    private SearchListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_list, container, false);

        ButterKnife.bind(this, view);

        initRecyclerview();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe(sticky = true)
    public void onSearchEventReceived(SearchEvent event) {
        adapter.refreshList(event.getResults());

        if (event.getResults().size() != 0) {
            title.setText(String.format(getString(R.string.number_of_results), event.getResults().size()));
        } else {
            title.setText(getString(R.string.zero_result));

        }
    }

    private void initRecyclerview() {
        resultsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new SearchListAdapter(getActivity(), new ArrayList<SearchResult>());
        resultsRecyclerview.setAdapter(adapter);
    }
}
