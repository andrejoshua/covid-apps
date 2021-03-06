package com.andre.apps.covid19updates.data.feature.summary.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany
import java.util.*

@Entity
data class HomeEntity(
    @Id
    var id: Long = 0,
    @NameInDb("new_confirmed")
    var newConfirmed: Int,
    @NameInDb("total_confirmed")
    var totalConfirmed: Int,
    @NameInDb("new_recovered")
    var newRecovered: Int,
    @NameInDb("total_recovered")
    var totalRecovered: Int,
    @NameInDb("new_deaths")
    var newDeaths: Int,
    @NameInDb("total_deaths")
    var totalDeaths: Int,
    @NameInDb("last_refreshed")
    var lastRefreshed: Date
) {

    lateinit var countries: ToMany<CountryItemEntity>
}
