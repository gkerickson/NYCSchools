package galen.nycschools.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import galen.nycschools.datamodels.SchoolGeneralInfo;

import static galen.nycschools.networking.NycOpenDataHandler.ALL_SCHOOLS_REQUEST;

public class SchoolRequestHandler {

    private static final String TAG = "SchoolRequestHandler";

    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    private static final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private final RequestQueue queue;

    public interface SuccessHandler {
        void onSuccess(SchoolGeneralInfo[] schools);
    }

    public SchoolRequestHandler(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public Response.Listener<JSONArray> successListenerFactory(SuccessHandler successHandler) {
        return response -> successHandler.onSuccess(NycOpenDataHandler.getSchoolsFromJson(response));
    }

    private final Response.ErrorListener errorListener = error -> {
        if(error != null && error.networkResponse != null) {
            Log.d(TAG, String.valueOf(error.networkResponse.statusCode));
        } else {
            Log.d(TAG, "NULL VALUE MYSTERY");
        }
    };

    public void getSchoolInfo(SuccessHandler successHandler) {
        JsonArrayRequest jsonRequest = new JsonArrayRequest(
                ALL_SCHOOLS_REQUEST,
                successListenerFactory(successHandler),
                errorListener
        );
        jsonRequest.setRetryPolicy(defaultRetryPolicy);
        queue.add(jsonRequest);
    }
}
