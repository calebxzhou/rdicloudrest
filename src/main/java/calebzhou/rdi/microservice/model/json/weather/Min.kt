package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Min (

  @SerializedName("chn" ) var chn : Int? = null,
  @SerializedName("usa" ) var usa : Int? = null

)