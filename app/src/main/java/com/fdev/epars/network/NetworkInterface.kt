package com.fdev.epars.network

import android.graphics.Bitmap
import com.fdev.epars.Config
import com.fdev.epars.entity.AuthorizeFormModel
import com.fdev.epars.entity.CompleteToken
import com.fdev.epars.entity.service_result.ServiceResult
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

/*
 * Project: ePars
 * Package: com.fdev.epars.network
 * Authod: Neikovich Sergey
 * Date: 18.02.2019
 */
interface NetworkInterface {

    // mark: authorization & registration
    @POST("Common/Authorization")
    fun authorizationPost(@Body formModel: AuthorizeFormModel): Observable<ServiceResult<CompleteToken>>

    @PUT("Common/Authorization")
    fun updateJWT()

    // mark: products

    // mark: handlers
}