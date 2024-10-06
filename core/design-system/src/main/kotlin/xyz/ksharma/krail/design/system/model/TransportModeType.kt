package xyz.ksharma.krail.design.system.model

/**
 * Color code resources - https://opendata.transport.nsw.gov.au/resources
 *
 * NSW Transport Key to icons and line codes
 * https://transportnsw.info/plan/help/key-to-icons-line-codes
 */
enum class TransportModeType(val hexColorCode: String, val modeName: String) {
    Bus(hexColorCode = "#00B5EF", modeName = "Bus"),
    Ferry(hexColorCode = "#5AB031", modeName = "Ferry"),
    LightRail(hexColorCode = "#EE343F", modeName = "Light Rail"),
    Metro(hexColorCode = "#009B77", modeName = "Metro"),
    Train(hexColorCode = "#F6891F", modeName = "Train"),
    Coach(hexColorCode = "#742282", modeName = "Coach"),
}
