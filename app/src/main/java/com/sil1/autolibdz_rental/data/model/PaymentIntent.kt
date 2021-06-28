package com.sil1.autolibdz_rental.data.model

import com.google.gson.annotations.SerializedName

data class PaymentIntent (
    @SerializedName("clientSecret")
    var clientSecret:String,
    @SerializedName("message")
    var message:String,
)