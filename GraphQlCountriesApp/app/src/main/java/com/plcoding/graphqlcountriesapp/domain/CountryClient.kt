package com.plcoding.graphqlcountriesapp.domain

import com.plcoding.CountryQuery

interface CountryClient {
    suspend fun getCountries(): List<SimpleCountry>
    suspend fun getCountry(code: String): DetailedCountry?
}