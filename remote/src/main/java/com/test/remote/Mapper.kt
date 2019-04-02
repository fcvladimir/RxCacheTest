package com.test.remote

import com.test.domain.model.Info
import com.test.remote.model.InfoResult

class Mapper {

    fun toDomain(input: InfoResult): Info? {
        return Info(input.number)
    }
}