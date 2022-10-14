package com.yikyaktranslate.model

data class ResponseWrapper<T,Boolean,E: Exception>(
    var data: T?= null,
    var isLoading: Boolean?= null,
    var error: Exception?= null
)
