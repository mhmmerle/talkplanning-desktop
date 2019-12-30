package email.haemmerle.talkplanning.ui

import javafx.beans.property.SimpleStringProperty
import javafx.event.EventTarget
import tornadofx.*


fun <T> EventTarget.comboField(label: String, values: List<T>) {
    field {
        label(label)
        combobox(values = values) {
            selectionModel.selectFirst()
        }
    }
}

fun EventTarget.checkboxField(label: String, default : Boolean = false) {
    field {
        paddingTop = 9
        paddingBottom = 9
        checkbox(label) {
            isSelected = default
        }
    }
}

fun EventTarget.textField(property: SimpleStringProperty) {
    field(property.name) {
        textfield(property)
    }
}