package com.plcoding.graphqlcountriesapp.domain

import com.plcoding.CountryQuery

interface CountryClient {
    suspend fun getCountries(): List<CountryQuery.Country>
}