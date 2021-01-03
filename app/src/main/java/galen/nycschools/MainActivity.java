package galen.nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import galen.nycschools.networking.SchoolRequestHandler;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private final SchoolRequestHandler.SuccessHandler successHandler = schools -> {
        Bundle schoolsBundle = new Bundle();
        schoolsBundle.putSerializable(ExploreSchoolsFragment.ARG_SCHOOLS, schools);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_body_container, ExploreSchoolsFragment.class, schoolsBundle)
                .commit();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SchoolRequestHandler requestHandler = new SchoolRequestHandler(this);
        requestHandler.getSchoolInfo(successHandler);
    }
}
