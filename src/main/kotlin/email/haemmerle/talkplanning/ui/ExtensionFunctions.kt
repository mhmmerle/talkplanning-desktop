package email.haemmerle.talkplanning.ui

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventTarget
import tornadofx.*


fun <T> EventTarget.comboField(label: String, values: List<T>): Field {
    return field {
        label(label)
        combobox(values = values) {
            selectionModel.selectFirst()
        }
    }
}

fun <T> EventTarget.comboField(property: SimpleObjectProperty<T>, values: List<T>): Field {
    return field {
        label(property.name)
        combobox(property, values = values) {
            selectionModel.selectFirst()
            makeAutocompletable ()
        }
    }
}

fun EventTarget.checkboxField(label: String, default : Boolean = false): Field {
    return field {
        paddingTop = 9
        paddingBottom = 9
        checkbox(label) {
            isSelected = default
        }
    }
}

fun EventTarget.textField(property: SimpleStringProperty): Field {
    return field(property.name) {
        textfield(property).whenVisible { requestFocus() }
    }
}

fun EventTarget.numberField(property: SimpleIntegerProperty): Field {
    return field(property.name) {
        textfield(property).whenVisible { requestFocus() }
    }
}