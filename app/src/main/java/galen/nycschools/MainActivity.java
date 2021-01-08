package galen.nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import galen.nycschools.datamodels.DataModels;
import galen.nycschools.datamodels.SchoolGeneralInfo;
import galen.nycschools.fragments.ExploreSchoolsFragment;
import galen.nycschools.networking.SchoolRequestHandler;
import galen.nycschools.networking.SchoolRequestHandler.SuccessHandler;

public class MainActivity extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    private final SuccessHandler<SchoolGeneralInfo[]> successHandler = schools -> {
//        Bundle schoolsBundle = new Bundle();
//        schoolsBundle.putSerializable(ExploreSchoolsFragment.ARG_SCHOOLS, schools);
//
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setReorderingAllowed(true)
//                .addToBackStack(null)
//                .replace(R.id.app_body_container, ExploreSchoolsFragment.class, schoolsBundle)
//                .commit();
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Init context dependent utilities first
        SchoolRequestHandler.init(this);
        DataModels.INSTANCE.start();

        setContentView(R.layout.activity_main);
    }
}
