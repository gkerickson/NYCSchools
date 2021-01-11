package galen.nycschools.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import galen.nycschools.datamodels.SchoolDetailedInfo;
import galen.nycschools.datamodels.SchoolGeneralInfo;

import static galen.nycschools.networking.NycSchoolDataHandler.ALL_SCHOOLS_REQUEST;

@Singleton
public class SchoolRequestHandler {

    // TODO: Maybe use dagger module to package and distribute objects in networking package

    private static final String TAG = "SchoolRequestHandler";

    private static SchoolRequestQueue queue;

    // Tracks active requests to prevent duplicate calls
    private final ConcurrentHashMap<String, SchoolDetailsRequestCoordinator> map = new ConcurrentHashMap<>();

    @Inject
    public SchoolRequestHandler(@ApplicationContext Context context) {
        queue = new SchoolRequestQueue(context);
    }

    public interface SuccessHandler<T> {
        void onSuccess(T schoolData);
    }

    private final Response.ErrorListener errorListener = error -> {
        // TODO: Handle network errors better
        if(error != null && error.networkResponse != null) {
            Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
        }
    };

    public void getSchoolInfo(SuccessHandler<SchoolGeneralInfo[]> successHandler) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                ALL_SCHOOLS_REQUEST,
                response -> successHandler.onSuccess(NycSchoolDataHandler.getSchoolsFromJson(response)),
                errorListener
        );
        queue.add(jsonRequest);
    }

    public void getSchoolDetails(SchoolGeneralInfo school, SuccessHandler<SchoolDetailedInfo> handler) {
        SchoolDetailsRequestCoordinator request = new SchoolDetailsRequestCoordinator(queue);
        Object previousValue = map.putIfAbsent(school.dbn, request);
        if (previousValue == null) {
            request.getSchoolDetails(school, response -> {
                map.remove(school.dbn);
                handler.onSuccess(response);
            });
        }
    }
}
