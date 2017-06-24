package com.tregouet.messesapp.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Schedule;
import com.tregouet.messesapp.model.SearchResult;
import com.tregouet.messesapp.modules.main.MainActivity;
import com.tregouet.messesapp.util.slidingtab.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.tabs)
    SlidingTabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        initSlidingTabLayout();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showResult();
    }

    private void initSlidingTabLayout() {
        SearchViewPagerAdapter adapter = new SearchViewPagerAdapter(getActivity().getSupportFragmentManager(), 2);
        pager.setAdapter(adapter);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
        tabs.setPage(0);
    }

    private void showResult() {
        ArrayList<SearchResult> results = new ArrayList<>();
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
        searchResult2.setName("Eglise Notre Dame de Lorette");
        searchResult2.setAddress("8 rue Choron");
        searchResult2.setZipcode("75009");
        searchResult2.setCity("Paris");
        searchResult2.setLatitude(48.877651f);
        searchResult2.setLongitude(2.341785f);
        searchResult2.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/NotreDameDeLoretteFacadeSud.JPG/280px-NotreDameDeLoretteFacadeSud.JPG");
        ArrayList<Integer> hours2 = new ArrayList<>();
        hours2.add(10);
        searchResult2.setSchedule(new Schedule("Dimanche", hours2));

        SearchResult searchResult3 = new SearchResult();
        searchResult3.setName("Eglise Saint Louis d'Antin");
        searchResult3.setAddress("63 rue Caumartin");
        searchResult3.setZipcode("75009");
        searchResult3.setCity("Paris");
        searchResult3.setLatitude(48.874500f);
        searchResult3.setLongitude(2.327754f);
        searchResult3.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/%C3%89glise_Saint-Louis-d%27Antin_2.jpg/280px-%C3%89glise_Saint-Louis-d%27Antin_2.jpg");
        ArrayList<Integer> hours3 = new ArrayList<>();
        hours3.add(18);
        searchResult3.setSchedule(new Schedule("Dimanche", hours3));

        results.add(searchResult);
        results.add(searchResult2);
        results.add(searchResult3);
        EventBus.getDefault().postSticky(new SearchEvent(results));
    }

}
