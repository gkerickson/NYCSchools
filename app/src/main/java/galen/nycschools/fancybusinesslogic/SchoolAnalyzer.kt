package galen.nycschools.fancybusinesslogic

import galen.nycschools.datamodels.SchoolDetailedInfo
import galen.nycschools.datamodels.SchoolGeneralInfo

object SchoolAnalyzer {

    // TODO: Invent a way to objectively know that one school is better than another

    private fun checkGradRate(gradRate: Int): Boolean {
        return gradRate > 70
    }

    fun isRecommended(school: SchoolDetailedInfo): Boolean {
        return checkGradRate(school.graduationRate)
    }

    fun isRecommended(school: SchoolGeneralInfo): Boolean {
        return checkGradRate(school.graduationRate)
    }
}