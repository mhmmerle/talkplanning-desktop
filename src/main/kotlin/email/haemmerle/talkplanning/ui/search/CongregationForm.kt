package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.model.Congregation
import email.haemmerle.talkplanning.model.CongregationRepo
import email.haemmerle.talkplanning.ui.mainView
import email.haemmerle.talkplanning.ui.textField
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class CongregationForm(val congregationModel: CongregationModel = CongregationModel()) : View() {
    val congregationRepo: CongregationRepo by di()

    override val root = form {
        fieldset("Versammlung erfassen") {
            textField(congregationModel.name)
        }
        buttonbar {
            button("Abbrechen") {
                isCancelButton = true
            }.action {
                mainView.showSearch()
            }
            button("Speichern") {
                isDefaultButton = true
            }.action {
                congregationRepo.save(Congregation(congregationModel.name.value))
                mainView.showSearch()
            }
        }
    }
}

class CongregationModel(name: String = "") {
    val name = SimpleStringProperty(this, "Name", name)
}