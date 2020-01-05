package email.haemmerle.talkplanning.persistence

import email.haemmerle.talkplanning.model.MeetingTime
import email.haemmerle.talkplanning.model.Settings
import email.haemmerle.talkplanning.model.SettingsRepo
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.DayOfWeek
import java.time.Instant.ofEpochSecond
import java.time.LocalTime
import java.time.ZoneOffset

class H2SettingsRepo : SettingsRepo {

    init {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(MeetingTimeTable)
        }
    }

    override fun get(): Settings {
        val meetingTimes = transaction {
            MeetingTimeTable.selectAll().toList().map {
                MeetingTime(
                        DayOfWeek.of(it[MeetingTimeTable.meetingDay]),
                        LocalTime.ofSecondOfDay(it[MeetingTimeTable.meetingTime].toLong()),
                        ofEpochSecond(it[MeetingTimeTable.from].toLong()).atOffset(ZoneOffset.UTC).toLocalDate(),
                        ofEpochSecond(it[MeetingTimeTable.to].toLong()).atOffset(ZoneOffset.UTC).toLocalDate()
                )
            }
        }
        return Settings(meetingTimes)
    }

    override fun save(settings: Settings) {
        transaction {
            MeetingTimeTable.deleteAll()
            settings.getMeetingTimes().forEach { meetingTimeEntry ->
                MeetingTimeTable.insert {
                    it[meetingDay] = meetingTimeEntry.meetingDay.value
                    it[meetingTime] = meetingTimeEntry.meetingTime.toSecondOfDay()
                    if (meetingTimeEntry.from != null) {
                        it[from] = meetingTimeEntry.from.atStartOfDay().toInstant(ZoneOffset.UTC).epochSecond
                    }
                    if (meetingTimeEntry.to != null) {
                        it[to] = meetingTimeEntry.to.atStartOfDay().toInstant(ZoneOffset.UTC).epochSecond
                    }
                }
            }
        }
    }
}

object MeetingTimeTable : IntIdTable("MeetingTimes") {
    val meetingDay = integer("meetingDay")
    val meetingTime = integer("meetingTime")
    val from = long("from")
    val to = long("to")
}