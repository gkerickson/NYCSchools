package galen.nycschools.datamodels

import androidx.lifecycle.MutableLiveData
import galen.nycschools.networking.SchoolRequestHandler

object DataModels {
    fun start() {}

    val selectedSchool: MutableLiveData<SchoolDetailedInfo> = MutableLiveData()

    val schools: MutableLiveData<Array<SchoolGeneralInfo>> =
        MutableLiveData<Array<SchoolGeneralInfo>>().apply {
            SchoolRequestHandler.getInstance().getSchoolInfo {
                this.value = it
            }
        }

    fun setSelectSchool(index: Int) {
        SchoolRequestHandler.getInstance().getSchoolDetails(schools.value?.get(index)) {
            selectedSchool.value = it
        }
    }
}
