package project.iksandecade.avp.network

import okhttp3.OkHttpClient
import project.iksandecade.avp.util.Constant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient {
    companion object {
        var retrofit: Retrofit? = null
        fun request(): Retrofit? {
            if (retrofit == null) {
                val builder = OkHttpClient.Builder()
                val okHttpClient = builder.build()

                retrofit = Retrofit.Builder()
                        .baseUrl(Constant.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .client(okHttpClient)
                        .build()
            }
            return retrofit
        }
    }
}