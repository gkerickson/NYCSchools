package galen.nycschools;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SchoolRequestHandler {
    RequestQueue queue;

    private static int MY_SOCKET_TIMEOUT_MS = 10000;

    private static String NYC_SCHOOLS_ENDPOINT =
            "https://data.cityofnewyork.us/Education/2017-DOE-High-School-Directory/s3k6-pzi2";

    public SchoolRequestHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d("GALEN", response.substring(0,500));
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if(error != null && error.networkResponse != null) {
                Log.d("GALEN", String.valueOf(error.networkResponse.statusCode));
            } else {
                Log.d("GALEN", "NULL VALUE MYSTERY");
            }
        }
    };

    protected void getSchoolInfo() {
        Log.d("GALEN", NYC_SCHOOLS_ENDPOINT);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                NYC_SCHOOLS_ENDPOINT,
                listener,
                errorListener
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}
