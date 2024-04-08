package com.example.pcbuilderhelper.api
import okhttp3.Interceptor
import okhttp3.Response

class Header : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedRequest = originalRequest.newBuilder()
            .addHeader("User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36")
            .addHeader("Referer", "https://www.topachat.com/pages/produits_cat_est_micro_puis_rubrique_est_wpr_puis_marque_est_INTEL.html")
            .build()

        return chain.proceed(modifiedRequest)
    }
}