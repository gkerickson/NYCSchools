package galen.nycschools.networking;

import org.json.JSONArray;
import org.json.JSONObject;

import galen.nycschools.datamodels.SchoolDetailedInfo;
import galen.nycschools.datamodels.SchoolGeneralInfo;

final class NycSchoolDataHandler {
    private NycSchoolDataHandler() {}

    // API Endpoints
    static final String NYC_SCHOOLS_ENDPOINT = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";
    static final String NYC_SAT_SCORES_ENDPOINT = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json";

    // Required for ExploreSchools
    private static final String fDbn = "dbn";
    private static final String fNAME = "school_name";
    private static final String fLOCATION = "location";
    private static final String fGRADUATION_RATE = "graduation_rate";
    private static final String SCHOOL_QUERY_STRING = String.format("SELECT %s, %s, %s, %s", fDbn, fNAME, fLOCATION, fGRADUATION_RATE);
    static final String ALL_SCHOOLS_REQUEST = NYC_SCHOOLS_ENDPOINT + "?$query=" + SCHOOL_QUERY_STRING;

    // Required for Details
    static final String fGRADES = "grades2018";
    static final String fSTUDENTS = "total_students";
    static final String fCOLLEGE_RATE = "college_career_rate";
    static final String SCHOOL_DETAILS_SELECT_QUERY_STRING = String.format("SELECT %s, %s, %s", fGRADES, fSTUDENTS, fCOLLEGE_RATE);

    // Required for SAT data
    static final String fMATH = "sat_math_avg_score";
    static final String fREADING = "sat_critical_reading_avg_score";
    static final String fWRITING = "sat_writing_avg_score";
    static final String fPARTICIPANTS = "num_of_sat_test_takers";

    static SchoolGeneralInfo[] getSchoolsFromJson(JSONArray rawData) {
        int numberOfSchools = rawData.length();
        JSONObject school;
        SchoolGeneralInfo[] schools = new SchoolGeneralInfo[numberOfSchools];
        for(int i = 0; i < numberOfSchools; i++) {
            school = rawData.optJSONObject(i);
            schools[i] = new SchoolGeneralInfo(
                    school.optString(fDbn),
                    school.optString(fNAME),
                    school.optString(fLOCATION),
                    school.optString(fGRADUATION_RATE)
            );
        }
        return schools;
    }
}
