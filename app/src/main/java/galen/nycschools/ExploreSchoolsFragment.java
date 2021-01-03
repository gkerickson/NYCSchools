package galen.nycschools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreSchoolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreSchoolsFragment extends Fragment {

    private static final String ARG_SCHOOLS = "schools";

    public ExploreSchoolsFragment() {
        // Required empty public constructor
    }

    public static ExploreSchoolsFragment newInstance(School[] schools) {
        ExploreSchoolsFragment fragment = new ExploreSchoolsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHOOLS, schools);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_schools, container, false);
    }
}