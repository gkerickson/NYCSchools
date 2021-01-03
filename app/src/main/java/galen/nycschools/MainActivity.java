package galen.nycschools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SchoolRequestHandler requestHandler;
    private SchoolRequestHandler.SuccessHandler successHandler = schools -> {
        RecyclerView recyclerView = findViewById(R.id.schoolsView);
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
