package com.sindorim.jetareader.di

import com.google.firebase.firestore.FirebaseFirestore
import com.sindorim.jetareader.network.BooksApi
import com.sindorim.jetareader.repository.BookRepository
import com.sindorim.jetareader.repository.FireRepository
import com.sindorim.jetareader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFireBookRepository() = FireRepository(
        queryBook = FirebaseFirestore.getInstance().collection("books")
    )

//    @Singleton
//    @Provides
//    fun provideBookRepository(api: BooksApi) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi(): BooksApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}