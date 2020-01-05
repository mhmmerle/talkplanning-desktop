package email.haemmerle.talkplanning.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.DayOfWeek.SATURDAY
import java.time.DayOfWeek.WEDNESDAY
import java.time.LocalDate
import java.time.LocalTime

internal class SettingsTest {

    val sut = Settings()

    @Test
    fun `setting meeting time reduces previous meetingTimes when they overlap`() {
        //prepare
        sut.setMeetingTime(
                WEDNESDAY,
                LocalTime.of(15, 30),
                LocalDate.of(2000, 1, 1))
        //when
        sut.setMeetingTime(
                SATURDAY,
                LocalTime.of(19, 0),
                LocalDate.of(2001, 1, 1))
        //then
        assertThat(sut.getMeetingTimes().first().to).isEqualTo(LocalDate.of(2000, 12, 31))
        assertThat(sut.getMeetingTimes().last().to).isEqualTo(END_OF_TIME)
    }

    @Test
    fun `setting meeting time deletes previous meetingTimes when they are covered`() {
        //prepare
        sut.setMeetingTime(
                WEDNESDAY,
                LocalTime.of(15, 30),
                LocalDate.of(2001, 1, 1))
        //when
        sut.setMeetingTime(
                SATURDAY,
                LocalTime.of(19, 0),
                LocalDate.of(2000, 1, 1))
        //then
        assertThat(sut.getMeetingTimes().single().from).isEqualTo(LocalDate.of(2000, 1, 1))
        assertThat(sut.getMeetingTimes().single().to).isEqualTo(END_OF_TIME)
    }
}