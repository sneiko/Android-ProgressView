package com.fdev.epars.network

import android.graphics.Bitmap
import com.fdev.epars.Config
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

/*
 * Project: ePars
 * Package: com.fdev.epars.network
 * Authod: Neikovich Sergey
 * Date: 18.02.2019
 */
@Module
class NetworkProvideService @Inject constructor() {

    @Provides
    fun providesService(): NetworkInterface {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        return retrofit.create(NetworkInterface::class.java)
    }


}