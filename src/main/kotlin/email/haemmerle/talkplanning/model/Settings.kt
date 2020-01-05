package email.haemmerle.talkplanning.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

val START_OF_TIME: LocalDate = LocalDate.of(2000, 1, 1)
val END_OF_TIME: LocalDate = LocalDate.of(9999, 12, 31)

class Settings(initialMeetingTimes : List<MeetingTime> = listOf()) {
    private val meetingTimes = mutableListOf<MeetingTime>()

    init {
        meetingTimes.addAll(initialMeetingTimes)
    }

    fun getMeetingTimes ()= meetingTimes.toList()

    fun setMeetingTime(meetingDay : DayOfWeek, meetingTime: LocalTime, from: LocalDate?) {
        removeEntriesCoveredByNewOne(from)
        limitToDateOnOverlappingEntries(from)
        meetingTimes.add(MeetingTime(meetingDay, meetingTime, from?: START_OF_TIME, END_OF_TIME ))
    }

    private fun removeEntriesCoveredByNewOne(from: LocalDate?) {
        val coveredEntries = meetingTimes.filter { meetingTimeEntry ->
            meetingTimeEntry.from.isAfter(from?: START_OF_TIME) || meetingTimeEntry.from.isEqual(from?: START_OF_TIME)
        }
        meetingTimes.removeAll(coveredEntries)
    }

    private fun limitToDateOnOverlappingEntries(from: LocalDate?) {
        meetingTimes.forEach { meetingTimeEntry ->
            if (from != null && (meetingTimeEntry.to.isAfter(from) || meetingTimeEntry.to.isEqual(from)))
                meetingTimeEntry.to = from.minusDays(1)
        }
    }
}

class MeetingTime (val meetingDay : DayOfWeek, val meetingTime : LocalTime, val from : LocalDate, var to : LocalDate)

interface SettingsRepo {
    fun get(): Settings
    fun save(settings: Settings)
}