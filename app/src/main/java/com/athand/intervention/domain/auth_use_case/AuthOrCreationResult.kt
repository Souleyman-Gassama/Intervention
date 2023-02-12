package com.athand.intervention.domain.auth_use_case

data class AuthOrCreationResult(
    var isSuccessful: Boolean,
    var message: String?,
    var inputErrorMap: MutableMap<String, Boolean>
)