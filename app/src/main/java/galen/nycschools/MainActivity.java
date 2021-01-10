package galen.nycschools;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import galen.nycschools.fragments.LoadingFragment;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                navigationManager.back(getSupportFragmentManager());
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        navigationManager.startNavigation(
            (LoadingFragment) this.getSupportFragmentManager().findFragmentById(R.id.app_body_container)
        );
    }
}
