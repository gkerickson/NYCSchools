package galen.nycschools

import android.os.Handler
import android.os.Looper
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import galen.nycschools.datamodels.SchoolDetailedInfo
import galen.nycschools.datamodels.SchoolGeneralInfo
import galen.nycschools.fragments.ExploreSchoolsFragment
import galen.nycschools.fragments.LoadingFragment
import galen.nycschools.fragments.SchoolDetailsFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor(private val stateProvider: StateProvider){

    // TODO: Experiment with androidx navigation tools to replace this

    fun startNavigation(loadingFragment: LoadingFragment) {
        stateProvider.schools.observe(loadingFragment) {
            loadingFragment.parentFragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
                    ).replace(R.id.app_body_container, ExploreSchoolsFragment())
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
        stateProvider.setSelectSchool(schoolIndex)
        fragmentManager.apply {
            val loadingFragment = LoadingFragment()
            beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
                    )
                    .replace(R.id.app_body_container, loadingFragment)
                    .commitNow()

            val loadingToDetails: () -> Unit = {
                beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out
                        )
                        .replace(R.id.app_body_container, SchoolDetailsFragment())
                        .commit()
            }
            val requestTime = System.currentTimeMillis()
            stateProvider.selectedSchool.observe(
                    loadingFragment
            ) { info: SchoolDetailedInfo? ->
                if(info?.name != null) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - requestTime >= LOADING_DELAY_MS) {
                        loadingToDetails()
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed(
                                loadingToDetails,
                                LOADING_DELAY_MS - (currentTime -  requestTime)
                        )
                    }
                }
            }
        }
    }

    fun back(fragmentManager: FragmentManager) {
        stateProvider.remoteSelectedSchool()
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_out, R.anim.fade_out, R.anim.fade_in, R.anim.slide_in)
                .replace(R.id.app_body_container, ExploreSchoolsFragment())
                .commit()
    }

    companion object {
        const val LOADING_DELAY_MS = 2000
    }
}