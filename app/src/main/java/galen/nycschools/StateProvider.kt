package galen.nycschools

import androidx.lifecycle.MutableLiveData
import galen.nycschools.datamodels.SchoolDetailedInfo
import galen.nycschools.datamodels.SchoolGeneralInfo
import galen.nycschools.networking.SchoolRequestHandler
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateProvider @Inject constructor(private val requestHandler: SchoolRequestHandler) {

    val selectedSchool: MutableLiveData<SchoolDetailedInfo?> = MutableLiveData()

    val schools: MutableLiveData<Array<SchoolGeneralInfo>> =
        MutableLiveData<Array<SchoolGeneralInfo>>().apply {
            requestHandler.getSchoolInfo { schools ->
                this.value = schools.apply {
                    sortWith(SchoolGeneralInfo.ComparatorImpl())
                }
            }
        }

    fun setSelectSchool(index: Int) {
        if(index >= 0) {
            requestHandler.getSchoolDetails(schools.value?.get(index)) {
                selectedSchool.value = it
            }
        }
    }

    fun remoteSelectedSchool() {
        selectedSchool.value = null
    }
}
