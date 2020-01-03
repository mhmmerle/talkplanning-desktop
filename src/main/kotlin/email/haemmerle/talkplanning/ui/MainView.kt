package email.haemmerle.talkplanning.ui

import email.haemmerle.talkplanning.ui.search.CongregationForm
import email.haemmerle.talkplanning.ui.search.PublisherForm
import email.haemmerle.talkplanning.ui.search.SearchView
import email.haemmerle.talkplanning.ui.search.TalkForm
import tornadofx.*

class MainView : View() {
    val searchView : SearchView by di()

    override val root = borderpane {
        primaryStage.minWidth = 600.0
        primaryStage.minHeight = 600.0

        left = listmenu {
            useMaxSize = true
            item(text = "Kalender")
            item(text = "Suche").whenSelected { showSearch() }
            item(text = "Vortrag planen")
        }
        center = searchView.root
    }

    fun showSearch() {
        root.center = searchView.root
        searchView.showSearchResults()
    }

    fun showCreateCongregation() {
        root.center = CongregationForm().root
    }

    fun showCreatePublisher() {
        root.center = PublisherForm().root
    }

    fun showCreateTalk() {
        root.center = TalkForm().root
    }
}

val mainView : MainView = find(MainView::class)