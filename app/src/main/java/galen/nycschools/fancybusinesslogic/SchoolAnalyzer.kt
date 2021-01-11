package galen.nycschools.fancybusinesslogic

import galen.nycschools.datamodels.SchoolGeneralInfo

object SchoolAnalyzer {
    fun isRecommended(school: SchoolGeneralInfo): Boolean {
        return try {
            school.graduationRate.toDouble() > 0.7
        } catch (e: NumberFormatException) {
            false
        }
    }
}