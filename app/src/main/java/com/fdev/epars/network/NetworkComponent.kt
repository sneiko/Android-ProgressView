package com.fdev.epars.network

import android.app.Activity
import androidx.fragment.app.Fragment
import dagger.Component
import javax.inject.Singleton

/*
 * Project: ePars
 * Package: com.fdev.epars.network
 * Authod: Neikovich Sergey
 * Date: 18.02.2019
 */
@Singleton
@Component(modules = [NetworkProvideService::class])
interface NetworkComponent {
    fun providesService(): NetworkInterface
}