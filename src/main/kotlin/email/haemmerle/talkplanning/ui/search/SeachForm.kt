package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.ui.checkboxField
import email.haemmerle.talkplanning.ui.comboField
import tornadofx.*

class SeachForm : Form() {
    val filters = SearchFilters()

    init {
        paddingAll = 0
        hbox(8) {
            textfield {
                text = "Suchtext"
            }
            button("Filter").action {
                toggleFilters()
            }
            button("Suchen")
        }
        add(filters)
    }

    private fun toggleFilters() {
        filters.isManaged = !filters.isManaged
        filters.isVisible = !filters.isVisible
    }
}


class SearchFilters : Fieldset() {
    init {
        hbox(16) {
            vbox {
                checkboxField("Nur Favoriten")
                checkboxField("Nur Aktiv", true)
            }
            vbox {
                comboField("Land", countries)
                comboField("Typ", types)
            }
        }
        hide()
    }
}


class Country(val name: String) {
    override fun toString(): String {
        return name
    }
}

val countries = listOf(
        Country(""),
        Country("Deutschland"),
        Country("Schweiz"),
        Country("Österreich"))

class Type(val name: String) {
    override fun toString(): String {
        return name
    }
}

val types = listOf(
        Type(""),
        Type("Versammlung"),
        Type("Verkündiger"),
        Type("Redner"),
        Type("Koordinator"),
        Type("Vortragskoordinator"))