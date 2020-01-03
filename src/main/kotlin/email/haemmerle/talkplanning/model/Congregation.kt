package email.haemmerle.talkplanning.model

class Congregation(val name: String, val id: Int? = null){
    override fun toString() = name
}

interface CongregationRepo {

    fun save(congregation: Congregation)

    fun findAll(): List<Congregation>

    fun find(congregationId: Int): Congregation

    fun delete(id: Int)

}

val unknownCongregation = Congregation("Unbekannt", 0)