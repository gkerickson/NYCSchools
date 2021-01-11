package galen.nycschools.networking;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

class SchoolRequestQueue {
    private static final int MY_SOCKET_TIMEOUT_MS = 10000;
    private static final DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(
            MY_SOCKET_TIMEOUT_MS,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private final RequestQueue queue;
    public SchoolRequestQueue(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public <T> void add(Request<T> request) {
        request.setRetryPolicy(defaultRetryPolicy);
        queue.add(request);
    }
}
