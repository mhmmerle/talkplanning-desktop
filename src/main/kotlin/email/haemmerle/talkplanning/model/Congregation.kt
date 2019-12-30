package email.haemmerle.talkplanning.model

class Congregation(val name: String)

interface CongregationRepo {

    fun save(congregation: Congregation)

    fun findAll(): List<Congregation>
}