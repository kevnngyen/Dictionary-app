package com.example.dictionary_app.data.repository

import android.app.Application
import com.example.dictionary_app.R
import com.example.dictionary_app.data.mapper.toWordItem
import com.example.dictionary_app.domain.model.WordItem
import com.example.dictionary_app.data.api.DictionaryApi
import com.example.dictionary_app.util.Result
import com.example.dictionary_app.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject


/**
 * Pass our word into and loads in the view modeller
 */

class DictionaryRepositoryImplementation @Inject constructor(
    val dictionaryApi: DictionaryApi,
    val application: Application
) : DictionaryRepository {

    override suspend fun getWordResult(word: String): Flow<Result<WordItem>> {
      return flow {
          emit(Result.Loading(true))

          val remoteWordResultDto = try {
              dictionaryApi.getWordResults(word)
          } catch (e : HttpException) {
              e.printStackTrace()
              emit(Result.Error(application.getString(R.string.can_t_get_results)))
              emit(Result.Loading(false))
              return@flow
          } catch (e : IOException) {
              e.printStackTrace()
              emit(Result.Error(application.getString(R.string.can_t_get_results)))
              emit(Result.Loading(false))
              return@flow
          } catch (e : Exception) {
              e.printStackTrace()
              emit(Result.Error(application.getString(R.string.can_t_get_results)))
              emit(Result.Loading(false))
              return@flow
          }

          remoteWordResultDto.let { wordResultDto ->
              wordResultDto[0].let { wordItemDto ->
                  emit(Result.Success(wordItemDto.toWordItem()))
                  emit(Result.Loading(false))
                  return@flow
              }
          }

      }
    }


}