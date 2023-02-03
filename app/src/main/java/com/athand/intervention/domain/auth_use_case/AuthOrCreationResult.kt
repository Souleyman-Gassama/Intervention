package com.athand.intervention.domain.auth

data class AuthOrCreationResult(
    var isSuccessful: Boolean,
    var message: String?,
    var inputErrorMap: MutableMap<String, Boolean>
)