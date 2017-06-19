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
import com.tregouet.messesapp.modules.model.Schedule;
import com.tregouet.messesapp.modules.model.SearchResult;

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

    private ArrayList<SearchResult> results = new ArrayList<>();
    private SearchListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_list, container, false);

        ButterKnife.bind(this, view);

        initRecyclerview();

        showResult();

        return view;
    }

    private void initRecyclerview() {
        resultsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new SearchListAdapter(getActivity(), new ArrayList<SearchResult>());
        resultsRecyclerview.setAdapter(adapter);
    }

    private void showResult() {
        SearchResult searchResult = new SearchResult();
        searchResult.setName("Sainte Trinité de Paris");
        searchResult.setAddress("Place d'Estienne d'Orves");
        searchResult.setZipcode("75009");
        searchResult.setCity("Paris");
        searchResult.setLatitude(48.876575f);
        searchResult.setLongitude(2.331733f);
        searchResult.setImage("http://www.montjoye.net/images/abbayes-eglises/idf/75/sainte-trinite/eglise-sainte-trinite-saint-lazare-gare.jpg");
        ArrayList<Integer> hours = new ArrayList<>();
        hours.add(11);
        searchResult.setSchedule(new Schedule("Lundi", hours));

        SearchResult searchResult2 = new SearchResult();
        searchResult2.setName("Sainte Trinité de Paris");
        searchResult2.setAddress("Place d'Estienne d'Orves");
        searchResult2.setZipcode("75009");
        searchResult2.setCity("Paris");
        searchResult2.setLatitude(48.876575f);
        searchResult2.setLongitude(2.331733f);
        searchResult2.setImage("http://www.montjoye.net/images/abbayes-eglises/idf/75/sainte-trinite/eglise-sainte-trinite-saint-lazare-gare.jpg");
        ArrayList<Integer> hours2 = new ArrayList<>();
        hours2.add(19);
        searchResult2.setSchedule(new Schedule("Mardi", hours2));

        SearchResult searchResult3 = new SearchResult();
        searchResult3.setName("Sainte Trinité de Paris");
        searchResult3.setAddress("Place d'Estienne d'Orves");
        searchResult3.setZipcode("75009");
        searchResult3.setCity("Paris");
        searchResult3.setLatitude(48.876575f);
        searchResult3.setLongitude(2.331733f);
        searchResult3.setImage("http://www.montjoye.net/images/abbayes-eglises/idf/75/sainte-trinite/eglise-sainte-trinite-saint-lazare-gare.jpg");
        ArrayList<Integer> hours3 = new ArrayList<>();
        hours3.add(18);
        searchResult3.setSchedule(new Schedule("Dimanche", hours3));

        results.add(searchResult);
        results.add(searchResult2);
        results.add(searchResult3);
        adapter.refreshList(results);

        if (results.size() != 0) {
            title.setText(String.format(getString(R.string.number_of_results), results.size()));
        } else {
            title.setText(getString(R.string.zero_result));

        }
    }
}
