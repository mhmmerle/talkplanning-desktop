package email.haemmerle.talkplanning.ui

import email.haemmerle.talkplanning.ui.search.CongregationForm
import email.haemmerle.talkplanning.ui.search.SearchView
import tornadofx.*

class MainView : View() {
    val searchView : SearchView by di()
    val congregationForm : CongregationForm by di()

    override val root = borderpane {
        primaryStage.minWidth = 600.0
        primaryStage.minHeight = 600.0

        left = listmenu {
            useMaxSize = true
            item(text = "Kalender")
            item(text = "Suche")
            item(text = "Vortrag planen")
        }
        center = searchView.root
    }

    fun showSearch() {
        root.center = searchView.root
        searchView.update()
    }

    fun showCreateCongregation() {
        root.center = congregationForm.root
    }
}

val mainView : MainView = find(MainView::class)