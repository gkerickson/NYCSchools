package galen.nycschools.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import galen.nycschools.School;

public class SchoolRequestHandler {

    private static final String TAG = "SchoolRequestHandler";
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    private static final String NYC_SCHOOLS_ENDPOINT =
            "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    private static final String SCHOOL_QUERY_STRING = "SELECT school_name, neighborhood, finalgrades";
    private static final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    RequestQueue queue;
    SuccessHandler successHandler;

    public interface SuccessHandler {
        void onSuccess(School[] schools);
    }

    public SchoolRequestHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private final Response.Listener<JSONArray> jsonListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            int numberOfSchools = response.length();
            JSONObject school;
            String schoolName;
            School[] schools = new School[numberOfSchools];
            for(int i = 0; i < numberOfSchools; i++) {
                school = response.optJSONObject(i);
                schoolName = school.optString("school_name");
                schools[i] = new School(schoolName);
            }
            successHandler.onSuccess(schools);
        }
    };

    private final Response.ErrorListener errorListener = error -> {
        if(error != null && error.networkResponse != null) {
            Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
        } else {
            Log.d(TAG, "NULL VALUE MYSTERY");
        }
    };

    public void getSchoolInfo(SuccessHandler successHandler) {
        this.successHandler = successHandler;
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                NYC_SCHOOLS_ENDPOINT + "?$query=" + SCHOOL_QUERY_STRING,
                jsonListener,
                errorListener
        );
        jsonRequest.setRetryPolicy(defaultRetryPolicy);
        queue.add(jsonRequest);
    }
}
