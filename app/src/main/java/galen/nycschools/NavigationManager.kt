package galen.nycschools

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import galen.nycschools.datamodels.DataModels
import galen.nycschools.datamodels.SchoolDetailedInfo
import galen.nycschools.fragments.ExploreSchoolsFragment
import galen.nycschools.fragments.LoadingFragment
import galen.nycschools.fragments.SchoolDetailsFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor(private val dataModels: DataModels){
    fun startNavigation(loadingFragment: LoadingFragment) {
        dataModels.schools.observe(loadingFragment) {
            Log.e(TAG, "schools updated")
            loadingFragment.parentFragmentManager.beginTransaction()
                    .replace(R.id.app_body_container, ExploreSchoolsFragment())
                    .setReorderingAllowed(true)
                    .commit()
        }
    }

    interface ExploreToDetailsCallback {
        fun navigate(index: Int)
    }

    fun exploreToDetailsCallbackFactory(fragmentManager: FragmentManager): ExploreToDetailsCallback =
            object : ExploreToDetailsCallback {
                override fun navigate(index: Int) {
                    exploreToDetails(fragmentManager, index)
                }
            }

    private fun exploreToDetails(fragmentManager: FragmentManager, schoolIndex: Int) {
        Log.e(TAG, "exploreToDetails")
        dataModels.setSelectSchool(schoolIndex)
        fragmentManager.apply {
            val loadingFragment = LoadingFragment()
            beginTransaction()
                    .add(R.id.app_body_container, loadingFragment)
                    .commitNow()

            dataModels.selectedSchool.observe(
                    loadingFragment
            ) { info: SchoolDetailedInfo? ->
                Log.e(TAG, "selectedSchool change")
                if(info?.name != null) {
                    beginTransaction()
                            .remove(findFragmentById(R.id.app_body_container)!!)
                            .commitNow()
                    Log.e(TAG, "selectedSchool removed loading")
                    Log.e(TAG, "selectedSchool removed adding details")
                    beginTransaction()
                            .add(R.id.app_body_container, SchoolDetailsFragment())
                            .addToBackStack(null)
                            .setReorderingAllowed(true)
                            .commit()
                    Log.e(TAG, "selectedSchool removed added details")
                }
            }
        }
    }

    fun back(fragmentManager: FragmentManager) {
        dataModels.remoteSelectedSchool()
        fragmentManager.popBackStack()
    }

    companion object {
        val TAG = "NAVIGATION"
    }
}