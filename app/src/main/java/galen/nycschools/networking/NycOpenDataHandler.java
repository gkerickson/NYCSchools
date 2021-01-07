package galen.nycschools.networking;

import org.json.JSONArray;
import org.json.JSONObject;

import galen.nycschools.datamodels.SchoolGeneralInfo;

final class NycOpenDataHandler {
    private NycOpenDataHandler() {}

    private static final String NYC_SCHOOLS_ENDPOINT = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json";

    private static final String fNAME = "school_name";
    private static final String fLOCATION = "location";
    private static final String fGRADUATION_RATE = "graduation_rate";

    private static final String SCHOOL_QUERY_STRING = String.format("SELECT %s, %s, %s", fNAME, fLOCATION, fGRADUATION_RATE);
    static final String ALL_SCHOOLS_REQUEST = NYC_SCHOOLS_ENDPOINT + "?$query=" + SCHOOL_QUERY_STRING;

    static SchoolGeneralInfo[] getSchoolsFromJson(JSONArray rawData) {
        int numberOfSchools = rawData.length();
        JSONObject school;
        SchoolGeneralInfo[] schools = new SchoolGeneralInfo[numberOfSchools];
        for(int i = 0; i < numberOfSchools; i++) {
            school = rawData.optJSONObject(i);
            schools[i] = new SchoolGeneralInfo(
                    school.optString(fNAME),
                    school.optString(fLOCATION),
                    school.optString(fGRADUATION_RATE)
            );
        }
        return schools;
    }
}
