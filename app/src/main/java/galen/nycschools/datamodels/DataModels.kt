package galen.nycschools.datamodels

import androidx.lifecycle.MutableLiveData
import galen.nycschools.networking.SchoolRequestHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataModels @Inject constructor(private val requestHandler: SchoolRequestHandler) {

    val selectedSchool: MutableLiveData<SchoolDetailedInfo?> = MutableLiveData()

    val schools: MutableLiveData<Array<SchoolGeneralInfo>> =
        MutableLiveData<Array<SchoolGeneralInfo>>().apply {
            requestHandler.getSchoolInfo {
                this.value = it
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
