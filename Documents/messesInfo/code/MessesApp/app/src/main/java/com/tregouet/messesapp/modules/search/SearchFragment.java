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
import com.tregouet.messesapp.model.Activity;
import com.tregouet.messesapp.model.Church;
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

        ArrayList<Integer> hours1 = new ArrayList<>();
        hours1.add(11);
        hours1.add(18);
        hours1.add(20);

        ArrayList<Integer> hours2 = new ArrayList<>();
        hours2.add(11);

        ArrayList<Integer> hours3 = new ArrayList<>();
        hours3.add(20);

        SearchResult searchResult = new SearchResult();
        Church church = new Church();
        church.setName("Sainte Trinité de Paris");
        church.setAddress("Place d'Estienne d'Orves");
        church.setZipcode("75009");
        church.setCity("Paris");
        church.setPhone("01 48 74 12 77");
        church.setWebsite("http://latriniteparis.com/");
        church.setMail("contact@latriniteparis.com");
        church.setLatitude(48.876575f);
        church.setLongitude(2.331733f);
        church.setImage("http://www.montjoye.net/images/abbayes-eglises/idf/75/sainte-trinite/eglise-sainte-trinite-saint-lazare-gare.jpg");

        Activity activity = new Activity();
        ArrayList<Schedule> masses = new ArrayList<>();
        masses.add(new Schedule("Lundi", hours1));
        masses.add(new Schedule("Mercredi", hours1));
        masses.add(new Schedule("Samedi et Dimanche", hours2));
        activity.setMasses(masses);

        ArrayList<Schedule> openings = new ArrayList<>();
        openings.add(new Schedule("Lundi", hours1));
        openings.add(new Schedule("Mercredi", hours1));
        openings.add(new Schedule("Samedi et Dimanche", hours2));
        activity.setOpenings(openings);

        ArrayList<Schedule> confessions = new ArrayList<>();
        confessions.add(new Schedule("Lundi", hours1));
        confessions.add(new Schedule("Mercredi", hours1));
        confessions.add(new Schedule("Samedi et Dimanche", hours2));
        activity.setConfessions(confessions);

        ArrayList<Schedule> adorations = new ArrayList<>();
        adorations.add(new Schedule("Lundi", hours1));
        adorations.add(new Schedule("Mercredi", hours1));
        adorations.add(new Schedule("Samedi et Dimanche", hours2));
        activity.setAdorations(adorations);

        ArrayList<Schedule> praises = new ArrayList<>();
        praises.add(new Schedule("Mercredi", hours3));
        activity.setPraises(praises);

        ArrayList<Schedule> receptions = new ArrayList<>();
        receptions.add(new Schedule("Mercredi", hours3));
        activity.setReceptions(receptions);

        ArrayList<Schedule> rosaries = new ArrayList<>();
        rosaries.add(new Schedule("Vendredi", hours3));
        activity.setRosaries(rosaries);

        church.setActivity(activity);
        ArrayList<Integer> hours = new ArrayList<>();
        hours.add(11);
        searchResult.setSchedule(new Schedule("Lundi", hours));
        searchResult.setChurch(church);

        SearchResult searchResult2 = new SearchResult();
        Church church2 = new Church();
        church2.setName("Eglise Notre Dame de Lorette");
        church2.setAddress("8 rue Choron");
        church2.setZipcode("75009");
        church2.setCity("Paris");
        church2.setLatitude(48.877651f);
        church2.setLongitude(2.341785f);
        church2.setPhone("01 48 78 92 72");
        church2.setWebsite("http://www.notredamedelorette.org/");
        church2.setMail("nd.lorette@wanadoo.fr");
        church2.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/NotreDameDeLoretteFacadeSud.JPG/280px-NotreDameDeLoretteFacadeSud.JPG");

        Activity activity2 = new Activity();
        ArrayList<Schedule> masses2 = new ArrayList<>();
        masses2.add(new Schedule("Lundi", hours1));
        masses2.add(new Schedule("Mercredi", hours1));
        masses2.add(new Schedule("Samedi et Dimanche", hours2));
        activity2.setMasses(masses2);

        ArrayList<Schedule> openings2 = new ArrayList<>();
        openings2.add(new Schedule("Lundi", hours1));
        openings2.add(new Schedule("Mercredi", hours1));
        openings2.add(new Schedule("Samedi et Dimanche", hours2));
        activity2.setOpenings(openings2);

        ArrayList<Schedule> confessions2 = new ArrayList<>();
        confessions2.add(new Schedule("Lundi", hours1));
        confessions2.add(new Schedule("Mercredi", hours1));
        confessions2.add(new Schedule("Samedi et Dimanche", hours2));
        activity2.setConfessions(confessions2);

        ArrayList<Schedule> adorations2 = new ArrayList<>();
        adorations2.add(new Schedule("Lundi", hours1));
        adorations2.add(new Schedule("Mercredi", hours1));
        adorations2.add(new Schedule("Samedi et Dimanche", hours2));
        activity2.setAdorations(adorations2);

        ArrayList<Schedule> praises2 = new ArrayList<>();
        praises2.add(new Schedule("Mercredi", hours3));
        activity2.setPraises(praises2);

        ArrayList<Schedule> receptions2 = new ArrayList<>();
        receptions2.add(new Schedule("Mercredi", hours3));
        activity2.setReceptions(receptions2);

        ArrayList<Schedule> rosaries2 = new ArrayList<>();
        rosaries2.add(new Schedule("Vendredi", hours3));
        activity.setRosaries(rosaries2);
        church2.setActivity(activity2);

        searchResult2.setSchedule(new Schedule("Dimanche", hours2));
        searchResult2.setChurch(church2);

        SearchResult searchResult3 = new SearchResult();
        Church church3 = new Church();
        church3.setName("Eglise Saint Louis d'Antin");
        church3.setAddress("63 rue Caumartin");
        church3.setZipcode("75009");
        church3.setCity("Paris");
        church3.setLatitude(48.874500f);
        church3.setLongitude(2.327754f);
        church3.setPhone("01 45 26 65 34");
        church3.setWebsite("http://www.saintlouisantin.fr/horaires.php");
        church3.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/2/24/%C3%89glise_Saint-Louis-d%27Antin_2.jpg/280px-%C3%89glise_Saint-Louis-d%27Antin_2.jpg");

        Activity activity3 = new Activity();
        ArrayList<Schedule> masses3 = new ArrayList<>();
        masses3.add(new Schedule("Lundi", hours1));
        masses3.add(new Schedule("Mercredi", hours1));
        masses3.add(new Schedule("Samedi et Dimanche", hours2));
        activity3.setMasses(masses3);

        ArrayList<Schedule> openings3 = new ArrayList<>();
        openings3.add(new Schedule("Lundi", hours1));
        openings3.add(new Schedule("Mercredi", hours1));
        openings3.add(new Schedule("Samedi et Dimanche", hours2));
        activity3.setOpenings(openings3);

        ArrayList<Schedule> confessions3 = new ArrayList<>();
        confessions3.add(new Schedule("Lundi", hours1));
        confessions3.add(new Schedule("Mercredi", hours1));
        confessions3.add(new Schedule("Samedi et Dimanche", hours2));
        activity3.setConfessions(confessions3);

        ArrayList<Schedule> adorations3 = new ArrayList<>();
        adorations3.add(new Schedule("Lundi", hours1));
        adorations3.add(new Schedule("Mercredi", hours1));
        adorations3.add(new Schedule("Samedi et Dimanche", hours2));
        activity3.setAdorations(adorations3);

        ArrayList<Schedule> praises3 = new ArrayList<>();
        praises3.add(new Schedule("Mercredi", hours3));
        activity3.setPraises(praises3);

        ArrayList<Schedule> receptions3 = new ArrayList<>();
        receptions3.add(new Schedule("Mercredi", hours3));
        activity3.setReceptions(receptions3);

        ArrayList<Schedule> rosaries3 = new ArrayList<>();
        rosaries3.add(new Schedule("Vendredi", hours3));
        activity.setRosaries(rosaries3);
        church3.setActivity(activity3);


        searchResult3.setSchedule(new Schedule("Dimanche", hours3));
        searchResult3.setChurch(church3);



        results.add(searchResult);
        results.add(searchResult2);
        results.add(searchResult3);
        EventBus.getDefault().postSticky(new SearchEvent(results));
    }

}
