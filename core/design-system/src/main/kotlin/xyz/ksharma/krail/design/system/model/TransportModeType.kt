package xyz.ksharma.krail.design.system.model

/**
 * Color code resources - https://opendata.transport.nsw.gov.au/resources
 */
enum class TransportModeType(val hexColorCode: String, modeName: String) {
    Bus(hexColorCode = "#00B5EF", modeName = "Bus"),
    Ferry(hexColorCode = "#5AB031", modeName = "Ferry"),
    LightRail(hexColorCode = "#EE343F", modeName = "Light Rail"),
    Metro(hexColorCode = "#009B77", modeName = "Metro"),
    Train(hexColorCode = "#F6891F", modeName = "Train"),
}
