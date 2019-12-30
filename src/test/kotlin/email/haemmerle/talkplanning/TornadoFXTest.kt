package email.haemmerle.talkplanning

import email.haemmerle.talkplanning.ui.MainView
import tornadofx.*
import kotlin.test.Test

class TornadoFXTest {

    @Test
    fun canCreateView() {
        launch<Talkplanning>()
    }

    class Talkplanning : App(MainView::class)
}

