package calebzhou.rdi.microservice.utils

import calebzhou.rdi.microservice.App
import calebzhou.rdi.microservice.model.Ip2RegionData
import com.ip2location.IP2Location
import com.ip2location.IPResult
import org.lionsoul.ip2region.xdb.Searcher
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

/**
 * Created by calebzhou on 2022-10-04,21:39.
 */
//两个开源库 ip2region和ip2location
class IpGeoUtils{
    companion object{
        private val ip2regionSearcher = Searcher.newWithBuffer(App::class.java.getResourceAsStream("/ip2region.xdb")?.readAllBytes())
        private val ip2locationSearcher = IP2Location()
        init {
            ip2locationSearcher.Open(App::class.java.getResourceAsStream("/ip2location_db.bin")?.readAllBytes())
        }
        fun searchIp2Region(ip:String) : Ip2RegionData{
            val split = Pattern.compile("\\|").split(ip2regionSearcher.search(ip))
            return Ip2RegionData(split[0],split[1],split[2],split[3])
        }
        fun searchIp2Location(ip:String) : IPResult{
            return ip2locationSearcher.IPQuery(ip)
        }
        fun getClientIP(request: HttpServletRequest): String {
            val xfHeader = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
            return xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()[0] // voor als ie achter een proxy zit
        }
    }

}
