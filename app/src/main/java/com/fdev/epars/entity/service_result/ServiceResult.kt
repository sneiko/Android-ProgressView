package com.fdev.epars.entity.service_result

/*
 * Project: ePars
 * Package: com.fdev.epars.entity
 * Authod: Neikovich Sergey
 * Date: 18.02.2019
 */
data class ServiceResult<T>(
    var resultObject: T,
    var status: ServiceResultStatus,
    var message: String
)