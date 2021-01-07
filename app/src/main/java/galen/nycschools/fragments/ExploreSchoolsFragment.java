package galen.nycschools.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import galen.nycschools.MainActivity;
import galen.nycschools.R;
import galen.nycschools.datamodels.SchoolGeneralInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreSchoolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreSchoolsFragment extends Fragment {

    public static final String ARG_SCHOOLS = "schools";
    private SchoolAdapter adapter;

    public ExploreSchoolsFragment() { }

    public static ExploreSchoolsFragment newInstance(SchoolGeneralInfo[] schools) {
        ExploreSchoolsFragment fragment = new ExploreSchoolsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHOOLS, schools);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            adapter = new SchoolAdapter(
                    (SchoolGeneralInfo[]) getArguments().getSerializable(ARG_SCHOOLS),
                    getParentFragmentManager(),
                    ((MainActivity) getActivity()).schoolRequestHandler
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exploreView = inflater.inflate(R.layout.fragment_explore_schools, container, false);

        RecyclerView recyclerView = exploreView.findViewById(R.id.schoolsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);

        return exploreView;
    }
}