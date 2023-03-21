package com.example.clean.data

import com.example.clean.entity.UsefulActivity

class UsefulActivityDto(
    override val activity: String,
    override val accessibility: Double,
    override val type: String,
    override val participants: Int,
    override val price: Double,
    override val link: String,
    override val key: String,
) : UsefulActivity
