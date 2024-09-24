package xyz.ksharma.krail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import xyz.ksharma.krail.design.system.theme.StartTheme
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.formatTo12HourTime
import xyz.ksharma.krail.trip_planner.domain.DateTimeHelper.utcToAEST
import xyz.ksharma.krail.trip_planner.network.api.model.StopType
import xyz.ksharma.krail.trip_planner.network.api.repository.TripPlanningRepository
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tripPlanningRepo: TripPlanningRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configures the system bars with a transparent background.
        enableEdgeToEdge()

        lifecycleScope.launch {
            tripPlanningRepo.stopFinder(
                stopType = StopType.STOP,
                stopSearchQuery = "Seven Hills"
            ).onSuccess { response ->
                Timber.d("Seven Hills: ${response}")

                val stopIds = response.locations
                    .filter { location -> location.productClasses?.contains(1) == true && location.type == StopType.STOP.type }
                    .map { location -> location.id }

                Timber.d(
                    "Seven Hills stopId: ${stopIds}"
                )
            }.onFailure { error ->
                Timber.e("error: ${error.message}", error)
            }

            /*   x =
                   tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Central")
               Timber.d("Central: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
               x =
                   tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Townhall")
               Timber.d("Townhall: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
               x =
                   tripPlanningRepo.stopFinder(stopType = StopType.ANY, stopSearchQuery = "Rockdale")
               Timber.d("Rockdale: ${x.getOrNull()?.locations?.map { it.productClasses.contains(1) }}")
   */

            var tripResponse = tripPlanningRepo.trip()
            tripResponse.onSuccess { trip ->
                Timber.d("Journeys: ${trip.journeys?.size}")

                trip.journeys?.map {
                    it.legs?.forEach {
                        Timber.d(
                            "departureTimeEstimated: ${
                                it.origin?.departureTimeEstimated?.utcToAEST()?.formatTo12HourTime()
                            }," +
                                    " Destination: ${
                                        it.destination?.arrivalTimeEstimated?.utcToAEST()
                                            ?.formatTo12HourTime()
                                    }, " +
                                    "Duration: ${it.duration}, " +
                                    "transportation: ${it.transportation}",
                        )
                    }
                }

            }.onFailure {
                Timber.e("error: ${it.message}")
            }
        }

        setContent {
            StartTheme {
                KrailApp()
            }
        }
    }
}

/**
 *
 * Seven Hills: StopFinderResponse(version=10.6.14.22, locations=[Location(id=streetID:1500000035::95308018:-1:Seven Hills Rd:Kings Langley:Seven Hills Rd::Seven Hills Rd: 2147:ANY:DIVA_STREET:4866145:3742687:GDAV:nsw,
 * isGlobalId=null, name=Seven Hills Rd, Kings Langley, disassembledName=Seven Hills Rd, coord=[-33.761046, 150.952602], type=street, matchQuality=242, isBest=false, parent=Parent(id=placeID:95308018:-1, name=Kings Langley, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=streetID:1500000147::95307004:-1:Seven Hills Rd:Bella Vista:Seven Hills Rd::Seven Hills Rd: 2153:ANY:DIVA_STREET:4866669:3742042:GDAV:nsw, isGlobalId=null, name=Seven Hills Rd, Bella Vista, disassembledName=Seven Hills Rd, coord=[-33.755061, 150.95798], type=street, matchQuality=242, isBest=false, parent=Parent(id=placeID:95307004:-1, name=Bella Vista, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=streetID:1500000117::95307002:-1:Seven Hills Rd:Baulkham Hills:Seven Hills Rd::Seven Hills Rd: 2153:ANY:DIVA_STREET:4868116:3742803:GDAV:nsw, isGlobalId=null, name=Seven Hills Rd, Baulkham Hills, disassembledName=Seven Hills Rd, coord=[-33.761407, 150.973883], type=street, matchQuality=242, isBest=false, parent=Parent(id=placeID:95307002:-1, name=Baulkham Hills, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=streetID:1500000016::95307002:-1:Seven Hills Way:Baulkham Hills:Seven Hills Way::Seven Hills Way: 2153:ANY:DIVA_STREET:4866831:3741948:GDAV:nsw, isGlobalId=null, name=Seven Hills Way, Baulkham Hills, disassembledName=Seven Hills Way, coord=[-33.754159, 150.959686], type=street, matchQuality=243, isBest=false, parent=Parent(id=placeID:95307002:-1, name=Baulkham Hills, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=streetID:1500000007::95905010:-1:Seven Hills Rd:Collombatti:Seven Hills Rd::Seven Hills Rd: 2440:ANY:DIVA_STREET:5060840:3439667:GDAV:nsw, isGlobalId=null, name=Seven Hills Rd, Collombatti, disassembledName=Seven Hills Rd, coord=[-30.957242, 152.867278], type=street, matchQuality=242, isBest=false, parent=Parent(id=placeID:95905010:-1, name=Collombatti, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=streetID:1500000005::95905006:-1:Seven Hills Rd:Bellimbopinni:Seven Hills Rd::Seven Hills Rd: 2440:ANY:DIVA_STREET:5064612:3443106:GDAV:nsw, isGlobalId=null, name=Seven Hills Rd, Bellimbopinni, disassembledName=Seven Hills Rd, coord=[-30.986312, 152.90847], type=street, matchQuality=242, isBest=false, parent=Parent(id=placeID:95905006:-1, name=Bellimbopinni, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=poiID:858284534:95308020:-1:Seven Hills West Public School:Lalor Park:Seven Hills West Public School:ANY:POI:4863251:3743143:GDAV:nsw, isGlobalId=null, name=Seven Hills West Public School, Lalor Park, disassembledName=Seven Hills West Public School, coord=[-33.766145, 150.921613], type=poi, matchQuality=235, isBest=false, parent=Parent(id=placeID:95308020:-1, name=Lalor Park, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=suburbID:95308035:1:Seven Hills:4865032:3744004:GDAV, isGlobalId=null, name=Seven Hills, disassembledName=null, coord=[-33.77328, 150.941156], type=suburb, matchQuality=250, isBest=false, parent=Parent(id=placeID:95308035:1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null), Location(id=poiID:858282363:95308035:-1:Bert Oldfield Public School:Seven Hills:Bert Oldfield Public School:ANY:POI:4863474:3744414:GDAV:nsw, isGlobalId=null, name=Bert Oldfield Public School, Seven Hills, disassembledName=Bert Oldfield Public School, coord=[-33.777505, 150.924537], type=poi, matchQuality=229, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=
 *                         null),
 *                         Location(id=poiID:858279204:95308035:-1:Bike Locker at Seven Hills Station (Terminus Rd):Seven Hills:Bike Locker at Seven Hills Station (Terminus Rd):ANY:POI:4864535:3744048:GDAV:nsw, isGlobalId=null, name=Bike Locker at Seven Hills Station (Terminus Rd), Seven Hills, disassembledName=Bike Locker at Seven Hills Station (Terminus Rd), coord=[-33.773847, 150.935819], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858279203:95308035:-1:Bike Locker at Seven Hills Station (Boomerang Pl):Seven Hills:Bike Locker at Seven Hills Station (Boomerang Pl):ANY:POI:4864467:3744143:GDAV:nsw, isGlobalId=null, name=Bike Locker at Seven Hills Station (Boomerang Pl), Seven Hills, disassembledName=Bike Locker at Seven Hills Station (Boomerang Pl), coord=[-33.774725, 150.935125], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858279240:95308035:-1:Bike Shed at Seven Hills Station, Terminus Rd:Seven Hills:Bike Shed at Seven Hills Station, Terminus Rd:ANY:POI:4864632:3744058:GDAV:nsw, isGlobalId=null, name=Bike Shed at Seven Hills Station, Terminus Rd, Seven Hills, disassembledName=Bike Shed at Seven Hills Station, Terminus Rd, coord=[-33.773904, 150.936868], type=poi, matchQuality=231, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858282403:95308035:-1:Blacktown Road Childrens Centre:Seven Hills:Blacktown Road Childrens Centre:ANY:POI:4862955:3745739:GDAV:nsw, isGlobalId=null, name=Blacktown Road Childrens Centre, Seven Hills, disassembledName=Blacktown Road Childrens Centre, coord=[-33.789606, 150.919489], type=poi, matchQuality=229, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290103:95308035:-1:Brahms Reserve:Seven Hills:Brahms Reserve:ANY:POI:4866314:3743203:GDAV:nsw, isGlobalId=null, name=Brahms Reserve, Seven Hills, disassembledName=Brahms Reserve, coord=[-33.76563, 150.954637], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290155:95308035:-1:Camillo Park:Seven Hills:Camillo Park:ANY:POI:4864382:3743198:GDAV:nsw, isGlobalId=null, name=Camillo Park, Seven Hills, disassembledName=Camillo Park, coord=[-33.766252, 150.93382], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290212:95308035:-1:Chopin Park:Seven Hills:Chopin Park:ANY:POI:4866080:3743274:GDAV:nsw, isGlobalId=null, name=Chopin Park, Seven Hills, disassembledName=Chopin Park, coord=[-33.76635, 150.952145], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858275333:95308035:-1:El Alamein Reserve:Seven Hills:El Alamein Reserve:ANY:POI:4863229:3745031:GDAV:nsw, isGlobalId=null, name=El Alamein Reserve, Seven Hills, disassembledName=El Alamein Reserve, coord=[-33.783141, 150.922151], type=poi, matchQuality=231, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858275392:95308035:-1:Enid Chalker Reserve:Seven Hills:Enid Chalker Reserve:ANY:POI:4865485:3742502:GDAV:nsw, isGlobalId=null, name=Enid Chalker Reserve, Seven Hills, disassembledName=Enid Chalker Reserve, coord=[-33.759609, 150.945416], type=poi, matchQuality=230, isBest=f
 *                         alse, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858287111:95308035:-1:Grantham Heritage Park:Seven Hills:Grantham Heritage Park:ANY:POI:4864005:3744812:GDAV:nsw, isGlobalId=null, name=Grantham Heritage Park, Seven Hills, disassembledName=Grantham Heritage Park, coord=[-33.780904, 150.930423], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858287112:95308035:-1:Grantham Reserve:Seven Hills:Grantham Reserve:ANY:POI:4863858:3745739:GDAV:nsw, isGlobalId=null, name=Grantham Reserve, Seven Hills, disassembledName=Grantham Reserve, coord=[-33.789296, 150.92922], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290650:95308035:-1:Himalaya Park:Seven Hills:Himalaya Park:ANY:POI:4863176:3745856:GDAV:nsw, isGlobalId=null, name=Himalaya Park, Seven Hills, disassembledName=Himalaya Park, coord=[-33.790582, 150.921919], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858276127:95308035:-1:International Peace Park:Seven Hills:International Peace Park:ANY:POI:4863519:3743920:GDAV:nsw, isGlobalId=null, name=International Peace Park, Seven Hills, disassembledName=International Peace Park, coord=[-33.773045, 150.924819], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858283420:95308035:-1:It's A Small World:Seven Hills:It's A Small World:ANY:POI:4864915:3743617:GDAV:nsw, isGlobalId=null, name=It's A Small World, Seven Hills, disassembledName=It's A Small World, coord=[-33.769838, 150.939735], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858276181:95308035:-1:Jamberoo Park:Seven Hills:Jamberoo Park:ANY:POI:4863270:3746172:GDAV:nsw, isGlobalId=null, name=Jamberoo Park, Seven Hills, disassembledName=Jamberoo Park, coord=[-33.793394, 150.923062], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858287350:95308035:-1:Kura Park:Seven Hills:Kura Park:ANY:POI:4864429:3745852:GDAV:nsw, isGlobalId=null, name=Kura Park, Seven Hills, disassembledName=Kura Park, coord=[-33.790116, 150.93542], type=poi, matchQuality=234, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290880:95308035:-1:Linden Park:Seven Hills:Linden Park:ANY:POI:4864646:3743285:GDAV:nsw, isGlobalId=null, name=Linden Park, Seven Hills, disassembledName=Linden Park, coord=[-33.766944, 150.9367], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858290963:95308035:-1:Maureen Caird Reserve:Seven Hills:Maureen Caird Reserve:ANY:POI:4863479:3745467:GDAV:nsw, isGlobalId=null, name=Maureen Caird Reserve, Seven Hills, disassembledName=Maureen Caird Reserve, coord=[-33.786978, 150.925024], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858276948:95308035:-1:Melody Gardens:Seven Hills:Melody Gardens:ANY:POI:4866347:3742826:GDAV:nsw, isGlobalId=null, name=
 *                         Melody Gardens, Seven Hills, disassembledName=Melody Gardens, coord=[-33.762227, 150.954836], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858291047:95308035:-1:Mount Carmel Park:Seven Hills:Mount Carmel Park:ANY:POI:4865781:3742791:GDAV:nsw, isGlobalId=null, name=Mount Carmel Park, Seven Hills, disassembledName=Mount Carmel Park, coord=[-33.762107, 150.948724], type=poi, matchQuality=231, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858277342:95308035:-1:Orana Park:Seven Hills:Orana Park:ANY:POI:4863201:3744474:GDAV:nsw, isGlobalId=null, name=Orana Park, Seven Hills, disassembledName=Orana Park, coord=[-33.778139, 150.92162], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858280611:95308035:-1:Park&Ride - Seven Hills:Seven Hills:Park&Ride - Seven Hills:ANY:POI:4864625:3743962:GDAV:nsw, isGlobalId=null, name=Park&Ride - Seven Hills, Seven Hills, disassembledName=Park&Ride - Seven Hills, coord=[-33.773042, 150.936753], type=poi, matchQuality=238, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858277520:95308035:-1:Pioneer Park:Seven Hills:Pioneer Park:ANY:POI:4864004:3743507:GDAV:nsw, isGlobalId=null, name=Pioneer Park, Seven Hills, disassembledName=Pioneer Park, coord=[-33.769162, 150.929875], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858277849:95308035:-1:Rotaract Hill:Seven Hills:Rotaract Hill:ANY:POI:4864394:3744003:GDAV:nsw, isGlobalId=null, name=Rotaract Hill, Seven Hills, disassembledName=Rotaract Hill, coord=[-33.773491, 150.934281], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858271091:95308035:-1:Seven Hills Community Centre:Seven Hills:Seven Hills Community Centre:ANY:POI:4864586:3743853:GDAV:nsw, isGlobalId=null, name=Seven Hills Community Centre, Seven Hills, disassembledName=Seven Hills Community Centre, coord=[-33.772075, 150.936288], type=poi, matchQuality=236, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858284533:95308035:-1:Seven HIlls North Public School:Seven Hills:Seven HIlls North Public School:ANY:POI:4866321:3742525:GDAV:nsw, isGlobalId=null, name=Seven HIlls North Public School, Seven Hills, disassembledName=Seven HIlls North Public School, coord=[-33.759527, 150.954431], type=poi, matchQuality=234, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858285624:95308035:-1:Seven Hills Plaza:Seven Hills:Seven Hills Plaza:ANY:POI:4864219:3744196:GDAV:nsw, isGlobalId=null, name=Seven Hills Plaza, Seven Hills, disassembledName=Seven Hills Plaza, coord=[-33.775288, 150.932475], type=poi, matchQuality=241, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858281827:95308035:-1:Seven Hills Post Office:Seven Hills:Seven Hills Post Office:ANY:POI:4864147:3744135:GDAV:nsw, isGlobalId=null, name=Seven Hills Post Office, Seven Hills, disassembledName=Seven Hills Post Office, coord=[-33.774763, 150.931674], type=poi, matchQuality=237, isBest=false, parent=Parent(id=placeID:953
 *                         08035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *
 *                         Location(id=214710, isGlobalId=true, name=Seven Hills Station, Seven Hills, disassembledName=Seven Hills Station, coord=[-33.774351, 150.936123], type=stop, matchQuality=986, isBest=true, parent=Parent(id=placeID:95308035:1, name=Seven Hills, type=locality), assignedStops=null, properties=Properties(stopId=10101234), productClasses=[1, 5, 11]),
 *
 *                         Location(id=poiID:858281828:95308035:-1:Seven Hills West Post Office:Seven Hills:Seven Hills West Post Office:ANY:POI:4864527:3743790:GDAV:nsw, isGlobalId=null, name=Seven Hills West Post Office, Seven Hills, disassembledName=Seven Hills West Post Office, coord=[-33.771528, 150.935626], type=poi, matchQuality=235, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858278057:95308035:-1:Snowy Reserve:Seven Hills:Snowy Reserve:ANY:POI:4863759:3746192:GDAV:nsw, isGlobalId=null, name=Snowy Reserve, Seven Hills, disassembledName=Snowy Reserve, coord=[-33.793406, 150.92834], type=poi, matchQuality=232, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858278064:95308035:-1:Soldiers settlement reserve:Seven Hills:Soldiers settlement reserve:ANY:POI:4864562:3744808:GDAV:nsw, isGlobalId=null, name=Soldiers settlement reserve, Seven Hills, disassembledName=Soldiers settlement reserve, coord=[-33.780676, 150.936423], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858285663:95308035:-1:Sydney Joseph Drive Shopping Centre:Seven Hills:Sydney Joseph Drive Shopping Centre:ANY:POI:4865646:3742996:GDAV:nsw, isGlobalId=null, name=Sydney Joseph Drive Shopping Centre, Seven Hills, disassembledName=Sydney Joseph Drive Shopping Centre, coord=[-33.763999, 150.947355], type=poi, matchQuality=228, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858284992:95308035:-1:The Meadows Public School:Seven Hills:The Meadows Public School:ANY:POI:4865128:3744906:GDAV:nsw, isGlobalId=null, name=The Meadows Public School, Seven Hills, disassembledName=The Meadows Public School, coord=[-33.781363, 150.942562], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858291523:95308035:-1:Topaz Park:Seven Hills:Topaz Park:ANY:POI:4863162:3745511:GDAV:nsw, isGlobalId=null, name=Topaz Park, Seven Hills, disassembledName=Topaz Park, coord=[-33.787483, 150.921626], type=poi, matchQuality=233, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=poiID:858294169:95308035:-1:Woolworths Supermarket:Seven Hills:Woolworths Supermarket:ANY:POI:4864104:3744224:GDAV:nsw, isGlobalId=null, name=Woolworths Supermarket, Seven Hills, disassembledName=Woolworths Supermarket, coord=[-33.775579, 150.931247], type=poi, matchQuality=230, isBest=false, parent=Parent(id=placeID:95308035:-1, name=Seven Hills, type=locality), assignedStops=null, properties=null, productClasses=null),
 *                         Location(id=streetID:1500000000::95905036:-1:Two Hills Lane:Seven Oaks:Two Hills Lane::Two Hills Lane: 2440:ANY:DIVA_STREET:5066787:3444265:GDAV:nsw, isGlobalId=null, name=Two Hills Lane, Seven Oaks, disassembledName=Two Hills Lane, coord=[-30.995671, 152.931774], type=street, matchQuality=239, isBest=false, parent=Parent(id=placeID:95905036:-1, name=Seven Oaks, type=locality), assignedStops=null, properties=null, productClasses=null)])
 *
 */
