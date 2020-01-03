package email.haemmerle.talkplanning.persistence

import email.haemmerle.talkplanning.model.Talk
import email.haemmerle.talkplanning.model.TalkRepo
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class H2TalkRepo : TalkRepo {
    init {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(TalkTable)
        }
    }

    override fun save(talk: Talk) {
        transaction {
            TalkTable.insert {
                it[number] = EntityID(talk.number, TalkTable)
                it[title] = talk.title
            }
        }
    }

    override fun findAll(): List<Talk> {
        return transaction {
            return@transaction TalkTable.selectAll().orderBy(TalkTable.number).toList().map {
                Talk(it[TalkTable.number].value, it[TalkTable.title])
            }
        }
    }

    override fun delete(number: Int) {
        transaction { TalkTable.deleteWhere { TalkTable.number.eq(number) } }
    }

    override fun find(number: Int): Talk {
        return transaction { TalkTable.select { TalkTable.number.eq(number) }.single().let {
            return@transaction Talk(it[TalkTable.number].value, it[TalkTable.title])
        } }
    }
}

object TalkTable : IntIdTable("Talks") {
    val number = integer("number").entityId()
    val title = varchar("title", 250).uniqueIndex()
}

/*
class TalkEntity(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, TalkEntity>(TalkTable)

    var number by TalkTable.number
    var title by TalkTable.title
}
*/
