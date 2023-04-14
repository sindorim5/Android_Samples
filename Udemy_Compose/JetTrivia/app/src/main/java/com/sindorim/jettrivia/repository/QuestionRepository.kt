package com.sindorim.jettrivia.repository

import android.util.Log
import com.sindorim.jettrivia.data.DataOrException
import com.sindorim.jettrivia.model.QuestionItem
import com.sindorim.jettrivia.network.QuestionApi
import javax.inject.Inject

private const val TAG = "QuestionRepository_SDR"
class QuestionRepository @Inject constructor(private val api: QuestionApi) {

    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }
        return dataOrException
    }

}