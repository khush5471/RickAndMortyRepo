package com.example.rickandmorty.di

import com.example.rickandmorty.BuildConfig
import com.example.rickandmorty.network.NetworkResult
import com.example.rickandmorty.network.Status
import com.example.rickandmorty.network.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilderModule {

    private const val CONNECTION_TIME_OUT: Long = 60
    private lateinit var retrofit: Retrofit

    @Provides
    @Singleton
    fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
        httpClient.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            chain.proceed(request.build())
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit
    }

    /*Gives the retrofit instance.
    * */
    fun getRetroInstance(): Retrofit {
        return retrofit
    }


    @Provides
    @Singleton
    fun getServiceInstance(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
    }


    // Handling error messages returned by Apis
    fun handleApiError(
        body: NetworkResult.Failure,
    ): String {
        val errorConverter: Converter<ResponseBody, Status> =
            getRetroInstance()
                .responseBodyConverter(Status::class.java, arrayOfNulls(0))

        if (body.isNetworkError != null && body.isNetworkError) {
            if (body.errorMessage != null && body.errorMessage.isNotEmpty()) {
                return body.errorMessage
            } else {
                return "Something went wrong during network connection maybe internet is off."
            }
        } else {
            try {
                errorConverter.convert(body.errorBody)?.let {
                    val error: Status = it
                    return error.message
                }
                return "Api error"
            } catch (e: Exception) {
                e.printStackTrace()
                return "Something went wrong."
            }
        }
        return ""


    }

}