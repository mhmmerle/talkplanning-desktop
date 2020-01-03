package email.haemmerle.talkplanning.persistence

import email.haemmerle.talkplanning.model.Publisher
import email.haemmerle.talkplanning.model.PublisherRepo
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class H2PublisherRepo : PublisherRepo {

    init {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(PublisherTable, PublisherTalksTable)
        }
    }

    override fun save(publisher: Publisher) {
        transaction {
            PublisherTable.insertOrUpdate(publisher.id, { PublisherTable.id.eq(publisher.id) }) {
                it[firstName] = publisher.firstName
                it[lastName] = publisher.lastName
                it[congregationId] = publisher.congregationId
            }
            if (publisher.id == null) return@transaction
            PublisherTalksTable.deleteWhere { PublisherTalksTable.publisherId.eq(publisher.id) }
            publisher.getTalks().forEach { talk ->
                PublisherTalksTable.insert {
                    it[publisherId] = publisher.id
                    it[talkNumber] = talk
                }
            }
        }
    }

    override fun findAll(): List<Publisher> {
        return transaction {
            return@transaction PublisherTable.selectAll()
                    .orderBy(PublisherTable.lastName).orderBy(PublisherTable.firstName).toList()
                    .map { it.toPublisher() }
        }
    }

    override fun findFor(congregationId: Int): List<Publisher> {
        return transaction {
            return@transaction PublisherTable
                    .select { PublisherTable.congregationId.eq(congregationId) }.toList()
                    .map { it.toPublisher() }
        }
    }


    fun ResultRow.toPublisher(): Publisher {
        return Publisher(
                this[PublisherTable.firstName],
                this[PublisherTable.lastName],
                this[PublisherTable.congregationId],
                this[PublisherTable.id].value,
                findTalksOf(this[PublisherTable.id].value))

    }

    private fun findTalksOf(publisherId: Int): List<Int> {
        return PublisherTalksTable.select {
            PublisherTalksTable.publisherId.eq(publisherId)
        }.toList().map {
            it[PublisherTalksTable.talkNumber]
        }
    }

    override fun delete(id: Int) {
        transaction {
            PublisherTalksTable.deleteWhere { PublisherTalksTable.publisherId.eq(id) }
            PublisherTable.deleteWhere { PublisherTable.id.eq(id) }
        }
    }
}

object PublisherTable : IntIdTable("Publishers") {
    val firstName = varchar("firstName", 150)
    val lastName = varchar("lastName", 150)
    val congregationId = integer("congregationId")
            .references(CongregationTable.id)
}
/*
class PublisherEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PublisherEntity>(PublisherTable)

    var firstName by PublisherTable.firstName
    var lastName by PublisherTable.lastName
    var congregationId by PublisherTable.congregationId
}*/

object PublisherTalksTable : IntIdTable("PublisherTalks") {
    val publisherId = integer("publisherId")
            .references(PublisherTable.id)
    val talkNumber = integer("talkNumber")
            .references(TalkTable.number)
}