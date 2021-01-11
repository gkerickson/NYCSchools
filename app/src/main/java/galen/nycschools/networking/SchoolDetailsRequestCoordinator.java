package galen.nycschools.networking;

import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import galen.nycschools.datamodels.SchoolDetailedInfo;
import galen.nycschools.datamodels.SchoolGeneralInfo;

import static galen.nycschools.networking.NycSchoolDataHandler.NYC_SAT_SCORES_ENDPOINT;
import static galen.nycschools.networking.NycSchoolDataHandler.NYC_SCHOOLS_ENDPOINT;
import static galen.nycschools.networking.NycSchoolDataHandler.SCHOOL_DETAILS_SELECT_QUERY_STRING;
import static galen.nycschools.networking.NycSchoolDataHandler.fCOLLEGE_RATE;
import static galen.nycschools.networking.NycSchoolDataHandler.fGRADES;
import static galen.nycschools.networking.NycSchoolDataHandler.fMATH;
import static galen.nycschools.networking.NycSchoolDataHandler.fPARTICIPANTS;
import static galen.nycschools.networking.NycSchoolDataHandler.fREADING;
import static galen.nycschools.networking.NycSchoolDataHandler.fSTUDENTS;
import static galen.nycschools.networking.NycSchoolDataHandler.fWRITING;

class SchoolDetailsRequestCoordinator {
    private final SchoolRequestQueue queue;

    // TODO: Replace lock with threadsafe container for info
    // Lock is used to insure access to schoolDataFound, satDataFound, and SchoolDetailedInfo is
    // threadsafe. Do not access or change these values without ensuring thread safety.
    private final Lock lock = new ReentrantLock();
    private boolean schoolDataFound = false;
    private boolean satDataFound = false;
    private final SchoolDetailedInfo info;

    public SchoolDetailsRequestCoordinator(SchoolRequestQueue queue) {
        this.queue = queue;
        this.info = new SchoolDetailedInfo();
    }

    private void addSatData(JSONObject school) {
        info.satMath = school.optInt(fMATH);
        info.satReading = school.optInt(fREADING);
        info.satWriting = school.optInt(fWRITING);
        info.satTestTakers = school.optInt(fPARTICIPANTS);
    }

    private void addSchoolData(JSONObject school) {
        String grades = school.optString(fGRADES);
        if(grades.length() > 10) {
            grades = "Unique grades";
        }
        info.grades = grades;
        info.totalStudents = school.optInt(fSTUDENTS);
        double careerRate = school.optDouble(fCOLLEGE_RATE);
        if(!Double.isNaN(careerRate)) {
            info.collegeCareerRate = (int)(careerRate * 100);
        } else {
            info.collegeCareerRate = -1;
        }
    }

    public void getSchoolDetails(SchoolGeneralInfo school, SchoolRequestHandler.SuccessHandler<SchoolDetailedInfo> handler) {
        this.info.name = school.name;
        this.info.location = school.location;
        this.info.graduationRate = school.graduationRate;

        String satRequest; String schoolRequest;
        try {
            satRequest = String.format("%s?dbn=%s", NYC_SAT_SCORES_ENDPOINT, school.dbn);
            schoolRequest = String.format("%s?$query=%s",
                    NYC_SCHOOLS_ENDPOINT,
                    URLEncoder.encode(
                            String.format("%s where dbn='%s'", SCHOOL_DETAILS_SELECT_QUERY_STRING, school.dbn),
                            "UTF-8")
                            );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        queue.add(new JsonArrayRequest(
                satRequest,
                response -> {
                    lock.lock();
                    if(response.length() > 0) {
                        addSatData(response.optJSONObject(0));
                    }
                    satDataFound = true;
                    boolean isDone = schoolDataFound;
                    lock.unlock();
                    if (isDone) {
                        handler.onSuccess(info);
                    }
                },
                error -> {
                    // TODO: Handle retry / error state
                    Log.e("Coordinator", String.valueOf(error.networkResponse.statusCode));
                }
        ));
        queue.add( new JsonArrayRequest(
                schoolRequest,
                response -> {
                    lock.lock();
                    if(response.length() > 0) {
                        addSchoolData(response.optJSONObject(0));
                    }
                    schoolDataFound = true;
                    boolean isDone = satDataFound;
                    lock.unlock();
                    if (isDone){
                        handler.onSuccess(info);
                    }
                },
                error -> {
                    // TODO: Handle retry / error state
                    Log.e("Coordinator", String.valueOf(error.networkResponse.statusCode));
                }
        ));
    }
}
