package email.haemmerle.talkplanning

import email.haemmerle.talkplanning.model.CongregationRepo
import email.haemmerle.talkplanning.model.PublisherRepo
import email.haemmerle.talkplanning.model.TalkRepo
import email.haemmerle.talkplanning.persistence.H2CongregationRepo
import email.haemmerle.talkplanning.persistence.H2PublisherRepo
import email.haemmerle.talkplanning.persistence.H2TalkRepo
import email.haemmerle.talkplanning.ui.MainView
import email.haemmerle.talkplanning.ui.search.CongregationForm
import email.haemmerle.talkplanning.ui.search.SearchView
import org.jetbrains.exposed.sql.Database
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.dsl.module
import tornadofx.App
import tornadofx.DIContainer
import tornadofx.FX
import tornadofx.launch
import kotlin.reflect.KClass


fun main(args: Array<String>) {
    startKoin {
        modules(module {
            single { H2CongregationRepo() as CongregationRepo }
            single { H2PublisherRepo() as PublisherRepo }
            single { H2TalkRepo() as TalkRepo }
            single { MainView() }
            single { SearchView() }
        })
    }
    FX.dicontainer = object : DIContainer, KoinComponent {
        override fun <T : Any> getInstance(type: KClass<T>): T {
            return getKoin().get(clazz = type, qualifier = null, parameters = null)
        }
    }
    Database.connect("jdbc:h2:~/.talkplanning;mode=MySQL", driver = "org.h2.Driver")
    launch<Talkplanning>(args)
}

class Talkplanning : App(MainView::class)