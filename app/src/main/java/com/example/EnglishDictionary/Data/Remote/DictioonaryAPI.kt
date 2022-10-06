package com.example.EnglishDictionary

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

val APP_ID : String = "APPLICATION_ID"
val APP_KEY : String = "APPLICATION_KEY"

val client = OkHttpClient().newBuilder().addInterceptor {
    val req = it.request().newBuilder().addHeader("app_id" , APP_ID ).addHeader("app_key" , APP_KEY).build()
    it.proceed(req)
}.build()

const val BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/"

val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()


interface DicAPI {
    @GET("entries/en/{word}")
    fun getRes(@Path("word") word : String)
        : Call<oxfordJSON>

}

object DicAPIAccess{
    val DictionaryRes : DicAPI by lazy { retrofit.create(DicAPI::class.java) }
}