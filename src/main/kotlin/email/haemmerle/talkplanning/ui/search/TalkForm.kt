package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.model.Talk
import email.haemmerle.talkplanning.model.TalkRepo
import email.haemmerle.talkplanning.ui.mainView
import email.haemmerle.talkplanning.ui.numberField
import email.haemmerle.talkplanning.ui.textField
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class TalkForm(val talkModel: TalkModel = TalkModel()) : View() {
    val talkRepo: TalkRepo by di()

    override val root = form {
        fieldset("Vortragsthema erfassen") {
            numberField(talkModel.number)
            textField(talkModel.title)
        }
        buttonbar {
            button("Abbrechen") {
                isCancelButton = true
                action { mainView.showSearch() }
            }
            button("Speichern") {
                isDefaultButton = true
                action {
                    talkRepo.save(Talk(
                            talkModel.number.value,
                            talkModel.title.value))
                    mainView.showSearch()
                }
            }
        }
    }
}

class TalkModel(number: Int = 0, title: String = "") {
    val number = SimpleIntegerProperty(this, "Nummer", number)
    val title = SimpleStringProperty(this, "Titel", title)
}