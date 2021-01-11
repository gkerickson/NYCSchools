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
import galen.nycschools.fancybusinesslogic.SchoolAnalyzer;

@AndroidEntryPoint
public class SchoolDetailsFragment extends Fragment {

    @Inject StateProvider stateProvider;
    @Inject NavigationManager navigationManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SchoolDetailedInfo info = stateProvider.getSelectedSchool().getValue();
        assert info != null;

        // TODO: Maybe use a ViewModel here, would be nice to pull some of this logic into anther class
        //          just to clean things anyways
        TextView nameView = view.findViewById(R.id.school_name);
        nameView.setText(info.name);
        View thumbsUp = view.findViewById(R.id.imageView);
        if(SchoolAnalyzer.INSTANCE.isRecommended(info)) {
            thumbsUp.setVisibility(View.VISIBLE);
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }
        else {
            thumbsUp.setVisibility(View.GONE);
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        ((TextView) view.findViewById(R.id.school_detail_location)).setText(info.location);
        ((TextView) view.findViewById(R.id.school_detail_students)).setText(String.format("%d", info.totalStudents));
        ((TextView) view.findViewById(R.id.school_detail_total_grades)).setText(info.grades);

        if(info.graduationRate > 100 || info.graduationRate < 0 || info.collegeCareerRate > 100 || info.collegeCareerRate < 0) {
            view.findViewById(R.id.layout_graduation).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.layout_graduation).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.graduation_rate)).setText(String.format("%d%%", info.graduationRate));
            ((TextView) view.findViewById(R.id.college_career_rate)).setText(String.format("%d%%", info.collegeCareerRate));
        }

        if(info.satTestTakers < 1) {
            view.findViewById(R.id.layout_sat).setVisibility(View.GONE);
            view.findViewById(R.id.sat_test_takers).setVisibility(View.GONE);
            view.findViewById(R.id.sat_test_takers_label).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.layout_sat).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.sat_test_takers)).setText(String.format("%d", info.satTestTakers));
            ((TextView) view.findViewById(R.id.reading)).setText(String.format("%d", info.satReading));
            ((TextView) view.findViewById(R.id.writing)).setText(String.format("%d", info.satWriting));
            ((TextView) view.findViewById(R.id.math)).setText(String.format("%d", info.satMath));
        }

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