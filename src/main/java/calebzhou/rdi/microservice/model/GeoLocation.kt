package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,21:09.
 */

/**
 *  地理天气
 */
//经度纬度
data class GeoLocation(val latitude:Double,val longitude:Double)
//RDI地址位置
data class RdiGeoLocation(val nation: String, val province: String, val city: String,
                     val district: String, val isp: String, val location: GeoLocation)
//ip2region开原地址库的信息
data class Ip2RegionData(val nation: String, val province: String, val city: String, val isp: String)
