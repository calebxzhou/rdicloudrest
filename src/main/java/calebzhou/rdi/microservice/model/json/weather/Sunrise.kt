package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Sunrise (

  @SerializedName("time" ) var time : String? = null

)