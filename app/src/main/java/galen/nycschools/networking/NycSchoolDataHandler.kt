package galen.nycschools.networking

import galen.nycschools.datamodels.SchoolGeneralInfo
import org.json.JSONArray
import org.json.JSONObject

internal object NycSchoolDataHandler {
    // API Endpoints
    const val NYC_SCHOOLS_ENDPOINT = "https://data.cityofnewyork.us/resource/s3k6-pzi2.json"
    const val NYC_SAT_SCORES_ENDPOINT = "https://data.cityofnewyork.us/resource/f9bf-2cp4.json"

    // Required for ExploreSchools
    private const val fDbn = "dbn"
    private const val fNAME = "school_name"
    private const val fLOCATION = "location"
    private const val fGRADUATION_RATE = "graduation_rate"
    private val SCHOOL_QUERY_STRING = String.format("SELECT %s, %s, %s, %s", fDbn, fNAME, fLOCATION, fGRADUATION_RATE)
    @JvmField
    val ALL_SCHOOLS_REQUEST = "$NYC_SCHOOLS_ENDPOINT?\$query=$SCHOOL_QUERY_STRING"

    // Required for Details
    const val fGRADES = "grades2018"
    const val fSTUDENTS = "total_students"
    const val fCOLLEGE_RATE = "college_career_rate"
    @JvmField
    val SCHOOL_DETAILS_SELECT_QUERY_STRING = String.format("SELECT %s, %s, %s", fGRADES, fSTUDENTS, fCOLLEGE_RATE)

    // Required for SAT data
    const val fMATH = "sat_math_avg_score"
    const val fREADING = "sat_critical_reading_avg_score"
    const val fWRITING = "sat_writing_avg_score"
    const val fPARTICIPANTS = "num_of_sat_test_takers"

    @JvmStatic
    fun getSchoolsFromJson(rawData: JSONArray): Array<SchoolGeneralInfo?> {
        val numberOfSchools = rawData.length()
        var school: JSONObject
        val schools = arrayOfNulls<SchoolGeneralInfo>(numberOfSchools)
        for (i in 0 until numberOfSchools) {
            school = rawData.optJSONObject(i)
            val dbn = school.optString(fDbn)
            val name = school.optString(fNAME)
            if (dbn.isEmpty() || name.isEmpty()) {
                continue
            }
            schools[i] = SchoolGeneralInfo(
                    dbn,
                    name,
                    school.optString(fLOCATION).run {
                        if(isEmpty()) this
                        else this.substring(0, this.indexOf('('))
                    },
                    school.optString(fGRADUATION_RATE)
            )
        }
        return schools
    }
}