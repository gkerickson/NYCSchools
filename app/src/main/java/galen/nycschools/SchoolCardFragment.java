package galen.nycschools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import galen.nycschools.datamodels.SchoolGeneralInfo;

public class SchoolCardFragment extends Fragment {
    private static final String TAG = "SchoolCardFragment";
    public static final String ARG_SCHOOL = "school";
    private SchoolGeneralInfo school;

    public static SchoolCardFragment newInstance(SchoolGeneralInfo school) {
        Log.e(TAG, "CALLED");
        SchoolCardFragment fragment = new SchoolCardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SCHOOL, school);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            school = (SchoolGeneralInfo) getArguments().getSerializable(ARG_SCHOOL);
        } else {
            Log.e(TAG, "No arguments provided.");
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