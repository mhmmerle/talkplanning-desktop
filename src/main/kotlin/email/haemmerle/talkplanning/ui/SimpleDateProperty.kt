package email.haemmerle.talkplanning.ui

import javafx.beans.property.SimpleObjectProperty
import java.time.LocalDate

class SimpleDateProperty(bean: Any, name: String, initialValue: LocalDate) :
        SimpleObjectProperty<LocalDate>(bean, name, initialValue)
