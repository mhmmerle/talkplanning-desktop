package email.haemmerle.talkplanning.ui.settings

import email.haemmerle.talkplanning.model.END_OF_TIME
import email.haemmerle.talkplanning.model.START_OF_TIME
import email.haemmerle.talkplanning.model.Settings
import email.haemmerle.talkplanning.model.SettingsRepo
import email.haemmerle.talkplanning.ui.*
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import tornadofx.*
import tornadofx.Dimension.LinearUnits.pt
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale.GERMAN

class SettingsForm(initSettings : Settings? = null) : View() {
    val settingsRepo: SettingsRepo by di()
    val meetingTimeModel = MeetingTimeModel()

    val settings : Settings

    init {
        this.settings = initSettings ?: settingsRepo.get()
    }

    override val root = vbox {
        vbox (8) {
            paddingAll = 8
            h1("Einstellungen")
            h2("Versammlungszeiten")
            settings.getMeetingTimes().forEach {
                text("""Von ${format(it.from)} bis ${format(it.to)}
    |                   am ${it.meetingDay.getDisplayName(TextStyle.FULL, GERMAN)} 
    |                   um ${it.meetingTime} Uhr""".trimMargin())
            }
        }
        form{
            fieldset ("Versammlungzeit ändern")  {
                comboField(meetingTimeModel.day, DayOfWeek.values().toList())
                field ("Zeit der Versammlung"){
                    textfield (meetingTimeModel.hour) {
                        maxWidth = 40.0
                    }
                    text(":")
                    textfield (meetingTimeModel.minute){
                        maxWidth = 40.0
                    }
                }
                field ("Gültig ab") {
                    datepicker(meetingTimeModel.from)
                }
                button ("Setzen") {
                    action {
                        settings.setMeetingTime(
                                meetingTimeModel.day.value,
                                LocalTime.of(meetingTimeModel.hour.value, meetingTimeModel.minute.value),
                                meetingTimeModel.from.value)
                        mainView.showSettings(settings)
                    }
                }
            }
            buttonbar {
                button("Abbrechen") {
                    isCancelButton = true
                    action {
                        mainView.showSettings()
                    }
                }
                button("Speichern") {
                    isDefaultButton = true
                    action {
                        settingsRepo.save(settings)
                        mainView.showSettings()
                    }
                }
            }
        }
    }

    fun format(date: LocalDate?): String? {
        return  if (date == START_OF_TIME) "Anfang der Zeit"
                else if (date == END_OF_TIME) "Ende der Zeit"
                else DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", GERMAN).format(date)
    }
}

class MeetingTimeModel(day : DayOfWeek = DayOfWeek.MONDAY) {
    val day = SimpleObjectProperty(this, "Tag der Versammlung", day)
    val hour = SimpleIntegerProperty(this, "Stunde", 0)
    val minute = SimpleIntegerProperty(this, "Minute", 0)
    val from = SimpleDateProperty(this, "Gültig ab", LocalDate.now())
}

