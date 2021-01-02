package galen.nycschools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SchoolRequestHandler requestHandler;
    private SchoolRequestHandler.SuccessHandler successHandler = schools -> {
        Log.d(TAG, "Got this many schools: " + schools.length);
        TextView mainText = findViewById(R.id.main_text);
        mainText.setText(schools[0].name);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.schoolsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SchoolAdapter(schools));
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestHandler = new SchoolRequestHandler(this);

        requestHandler.getSchoolInfo(successHandler);
    }
}
