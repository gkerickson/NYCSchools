package galen.nycschools;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import galen.nycschools.datamodels.SchoolGeneralInfo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SchoolDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SchoolDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_SCHOOL = "school";

    private SchoolGeneralInfo school;

    public SchoolDetailsFragment() { }

    public static SchoolDetailsFragment newInstance(SchoolGeneralInfo school) {
        Log.e("SchoolDetailsFragment", "newInstance called");
        SchoolDetailsFragment fragment = new SchoolDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHOOL, school);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("SchoolDetailsFragment", "onCreate called");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.e("SchoolDetailsFragment", "onCreate got not null");
            school = (SchoolGeneralInfo) getArguments().getSerializable(ARG_SCHOOL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_details, container, false);
        ((TextView) view.findViewById(R.id.school_name)).setText(school.name);
        ((TextView) view.findViewById(R.id.school_attr_1)).setText(school.location);
        ((TextView) view.findViewById(R.id.school_attr_2)).setText(school.grades);

        return view;
    }
}