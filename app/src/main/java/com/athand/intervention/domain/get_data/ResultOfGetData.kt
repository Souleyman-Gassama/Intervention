package com.athand.intervention.domain.get_data

import java.util.Objects

data class ResultOfGetData(
    var success: Boolean,
    var data: Any?,
    var message: String?
)