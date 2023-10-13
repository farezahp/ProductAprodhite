package com.example.gridaprodhite.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Makeup(
    @StringRes val name: Int,
    @StringRes val brand: Int,
    @DrawableRes val image: Int
)
