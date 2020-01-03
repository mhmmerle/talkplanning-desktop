package email.haemmerle.talkplanning.persistence

import email.haemmerle.talkplanning.model.Congregation
import email.haemmerle.talkplanning.model.CongregationRepo
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class H2CongregationRepo : CongregationRepo {

    init {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(CongregationTable)
        }
    }

    override fun save(congregation: Congregation) {
        transaction {
            CongregationEntity.new{name = congregation.name}
        }
    }

    override fun findAll(): List<Congregation> {
        return transaction {
            return@transaction CongregationTable.selectAll().toList()
                    .map { Congregation(it[CongregationTable.name], it[CongregationTable.id].value) }
        }
    }

    override fun get(congregationId: Int): Congregation {
        return transaction {
            val result = CongregationTable.select{CongregationTable.id.eq(congregationId)}.single()
            return@transaction Congregation(result[CongregationTable.name], result[CongregationTable.id].value)
        }
    }

    override fun delete(congregationId: Int) {
        transaction { CongregationTable.deleteWhere { CongregationTable.id.eq(congregationId) } }
    }
}

object CongregationTable : IntIdTable("Congregations") {
    val name = varchar("name", 150)
}

class CongregationEntity (id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CongregationEntity>(CongregationTable)
    var name by CongregationTable.name
}