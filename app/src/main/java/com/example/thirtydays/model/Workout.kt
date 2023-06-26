package com.example.thirtydays.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Workout (
    @StringRes val nameRes: Int,
    @StringRes val instructionRes: Int,
    @DrawableRes val imageRes: Int,
    val numberOfReps: Int?,
    val durationInSeconds: Int?,
)