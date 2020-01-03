package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.model.*
import email.haemmerle.talkplanning.ui.comboField
import email.haemmerle.talkplanning.ui.mainView
import email.haemmerle.talkplanning.ui.textField
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*


class PublisherForm(val publisherModel : PublisherModel = PublisherModel()) : View() {
    val publisherRepo: PublisherRepo by di()
    val congregationRepo : CongregationRepo by di()

    override val root = form {
        fieldset("Verkündiger erfassen") {
            textField(publisherModel.firstName)
            textField(publisherModel.lastName)
            comboField(publisherModel.congregation, congregationRepo.findAll())
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
                publisherRepo.save(Publisher(
                        publisherModel.firstName.value,
                        publisherModel.lastName.value,
                        publisherModel.congregation.value.id?:0))
                mainView.showSearch()
            }
        }
    }
}

class PublisherModel(firstName : String = "", lastName : String = "", congregation : Congregation = unknownCongregation) {
    val firstName = SimpleStringProperty(this, "Vorname", firstName)
    val lastName = SimpleStringProperty(this, "Nachname", lastName)
    val congregation = SimpleObjectProperty<Congregation>(this, "Versammlung", congregation)
}