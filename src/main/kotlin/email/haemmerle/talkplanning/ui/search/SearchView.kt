package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.model.CongregationRepo
import email.haemmerle.talkplanning.ui.mainView
import javafx.scene.Node
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*


class SearchView : View() {
    fun update() {
        root.center.replaceWith(searchResults())
    }

    val congregationRepo: CongregationRepo by di()

    override val root = borderpane {
        top = hbox {
            paddingAll = 8
            add(SeachForm())
            pane { hgrow = Priority.ALWAYS }
            menubutton("Neu") {
                item("Versammlung").action {
                    mainView.showCreateCongregation()
                }
                item("Verkündiger")
                item("Vortrag")
            }
        }
        center = searchResults()

    }

    private fun searchResults(): Node {
        return vbox {
            isFillWidth = true
            useMaxHeight = true
            text("Versammlungen")
            congregationRepo.findAll().forEach {
                text("Versammlung ${it.name}")
            }
            style {
                backgroundColor += Color.YELLOW
            }
        }
    }
}