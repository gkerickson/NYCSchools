package galen.nycschools.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import galen.nycschools.NavigationManager;
import galen.nycschools.R;
import galen.nycschools.StateProvider;
import galen.nycschools.datamodels.SchoolDetailedInfo;

@AndroidEntryPoint
public class SchoolDetailsFragment extends Fragment {

    @Inject
    StateProvider stateProvider;
    @Inject NavigationManager navigationManager;

    public SchoolDetailsFragment() { }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SchoolDetailedInfo info = stateProvider.getSelectedSchool().getValue();
        ((TextView) view.findViewById(R.id.school_name)).setText(info.name);
        ((TextView) view.findViewById(R.id.school_detail_location)).setText(info.location);
        ((TextView) view.findViewById(R.id.school_detail_students)).setText(info.totalStudents);
        ((TextView) view.findViewById(R.id.school_detail_total_grades)).setText(info.grades);
        ((TextView) view.findViewById(R.id.graduation_rate)).setText(info.graduationRate);
        ((TextView) view.findViewById(R.id.college_career_rate)).setText(info.collegeCareerRate);
        ((TextView) view.findViewById(R.id.sat_test_takers)).setText(info.satTestTakers);
        ((TextView) view.findViewById(R.id.reading)).setText(info.satReading);
        ((TextView) view.findViewById(R.id.writing)).setText(info.satWriting);
        ((TextView) view.findViewById(R.id.math)).setText(info.satMath);
        view.findViewById(R.id.detail_nav_button).setOnClickListener(v ->
                navigationManager.back(this.getParentFragmentManager())
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_school_details, container, false);
    }
}