package email.haemmerle.talkplanning.persistence

import org.jetbrains.exposed.dao.IntIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.TransactionManager


fun <T: Table> T.insertOrUpdate(id : Any?, where: (SqlExpressionBuilder.()-> Op<Boolean>), body: T.(UpdateBuilder<Int>)->Unit) {
    if (id != null) {
        val query = UpdateStatement(this, 1, where.let { SqlExpressionBuilder.it() })
        body(query)
        query.execute(TransactionManager.current())!!
    } else {
        val query = InsertStatement<Number>(this)
        body(query)
        query.execute(TransactionManager.current())
    }
}
