package email.haemmerle.talkplanning

import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.After
import org.junit.Before
import org.junit.Test

class ExposedTest {

    @Before
    fun setup() {
        Database.connect("jdbc:h2:~/.talkplanning", driver = "org.h2.Driver")
        transaction {
            SchemaUtils.create(Cities)
        }
    }

    @After
    fun teardown() {
        transaction {
            SchemaUtils.drop(Cities)
        }
    }

    @Test
    fun canHandleDsl() {
        transaction {
            val newYorkId = Cities.insert {
                it[name] = "New York"
            } get Cities.id

            assertThat(Cities.selectAll().single()[Cities.name]).isEqualTo("New York")
        }
    }

    object Cities : IntIdTable() {
        val name = varchar("name", 50)
    }

    class City (id: EntityID<Int>) : IntEntity(id) {
        companion object : IntEntityClass<City>(Cities)

        var name by Cities.name
    }

    @Test
    fun canHandleDao() {
        Database.connect("jdbc:h2:~/.talkplanning", driver = "org.h2.Driver")
        transaction {
            val stPete = City.new {
                name = "St. Petersburg"
            }
            assertThat(Cities.selectAll().single()[Cities.name]).isEqualTo("St. Petersburg")
        }
    }

}