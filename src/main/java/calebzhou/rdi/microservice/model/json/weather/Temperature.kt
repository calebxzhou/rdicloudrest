package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Temperature (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Float? = null,
  @SerializedName("min"  ) var min  : Float?    = null,
  @SerializedName("avg"  ) var avg  : Double? = null

)