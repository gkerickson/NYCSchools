package galen.nycschools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SchoolRequestHandler requestHandler;
    private SchoolRequestHandler.SuccessHandler successHandler = new SchoolRequestHandler.SuccessHandler() {
        @Override
        public void onSuccess(School[] schools) {
            Log.d(TAG, "Got this many schools: " + schools.length);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestHandler = new SchoolRequestHandler(this);

        requestHandler.getSchoolInfo(successHandler);
    }
}
