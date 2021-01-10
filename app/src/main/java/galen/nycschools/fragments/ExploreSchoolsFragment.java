package galen.nycschools.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import galen.nycschools.NavigationManager;
import galen.nycschools.R;
import galen.nycschools.datamodels.DataModels;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class ExploreSchoolsFragment extends Fragment {
    public ExploreSchoolsFragment() { }

    @Inject DataModels dataModels;
    @Inject NavigationManager navigationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exploreView = inflater.inflate(R.layout.fragment_explore_schools, container, false);

        RecyclerView recyclerView = exploreView.findViewById(R.id.schoolsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(
                new SchoolAdapter(
                        navigationManager.exploreToDetailsCallbackFactory(getParentFragmentManager()),
                        dataModels.getSchools().getValue())
        );

        return exploreView;
    }
}