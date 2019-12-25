package email.haemmerle.talkplanning

import tornadofx.*
import kotlin.test.Test

class TornadoFXTest {

    @Test fun canCreateView() {
        launch<MyApp>()
    }

    class MyApp: App(MyView::class)

    class MyView: View() {
        override val root = vbox {
            button("Press me")
            label("Waiting")
        }
    }
}
