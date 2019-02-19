package com.fdev.epars.entity

/*
 * Project: ePars
 * Package: com.fdev.epars.entity
 * Authod: Neikovich Sergey
 * Date: 18.02.2019
 */
data class CompleteToken(
    var accessToken: String,
    var refreshToken: String,
    var expirationTime: String
)