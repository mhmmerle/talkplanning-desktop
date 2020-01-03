package email.haemmerle.talkplanning.ui.search

import email.haemmerle.talkplanning.model.*
import email.haemmerle.talkplanning.ui.comboField
import email.haemmerle.talkplanning.ui.mainView
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.Node
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import tornadofx.*


class SearchView : View() {
    private val talkRepo: TalkRepo by di()
    val congregationRepo: CongregationRepo by di()
    val publisherRepo: PublisherRepo by di()

    lateinit var searchResultPane : Node
    lateinit var detailPane : Node
    lateinit var viewMenuPane : Node

    override val root = borderpane {
        top = hbox {
            paddingAll = 8
            add(SeachForm())
            pane { hgrow = Priority.ALWAYS }
            viewMenuPane = vbox { }
            showViewMenu()
        }
        center = hbox {
            paddingAll = 8
            searchResultPane = vbox { }
            showSearchResults()
            detailPane = vbox { }
        }
    }

    private fun showViewMenu() {
        viewMenuPane.replaceChildren(menubutton("Neu") {
            item("Versammlung").action {
                mainView.showCreateCongregation()
            }
            item("Verkündiger").action {
                mainView.showCreatePublisher()
            }
            item("Vortrag").action {
                mainView.showCreateTalk()
            }
        })
    }

    fun showSearchResults() {
        searchResultPane.replaceChildren(vbox(8) {
            isFillWidth = true
            useMaxHeight = true
            text("Versammlungen")
            congregationRepo.findAll().forEach {
                button("Versammlung ${it.name}").action {
                    showCongregation(it)
                }
            }
            text("Verkündiger")
            publisherRepo.findAll().forEach {
                button("Verkündiger ${it.firstName}  ${it.lastName} in ${congregationRepo.get(it.congregationId)}").action{
                    showPublisher(it)
                }
            }
        })
    }

    private fun showPublisher(publisher: Publisher) {
        detailPane.replaceChildren(vbox {
            text("Verkündiger ${publisher.firstName} ${publisher.lastName}")
            text("Versammlung ${congregationRepo.get(publisher.congregationId)}")
            text("Vorträge")

            publisher.getTalks().forEach {
                val talk = talkRepo.find(it).apply{
                    text("$number - $title")
                }
            }

            form {
                fieldset {
                    val talkToAdd = SimpleObjectProperty<Talk>(talkRepo.findAll().first(), "Vortrag")
                    comboField(talkToAdd, talkRepo.findAll())
                    button ("Hinzufügen") {
                        action {
                            publisher.addTalk(talkToAdd.value)
                            publisherRepo.save(publisher)
                        }
                    }
                }
            }

            buttonbar {
                button ("Vortrag hinzufügen") {
                    action {

                    }
                }
                button ("Löschen").action {
                    publisherRepo.delete(publisher.id!!)
                    detailPane.replaceChildren(VBox())
                    showSearchResults()
                } }
        })
    }

    private fun showCongregation(congregation: Congregation) {
        detailPane.replaceChildren(vbox{
            text("Versammlung ${congregation.name}")

            buttonbar {
                button ("Löschen") {
                    action {
                        congregationRepo.delete(congregation.id!!)
                        detailPane.replaceChildren(VBox())
                        showSearchResults()
                    }
                }
            }
        })
    }
}