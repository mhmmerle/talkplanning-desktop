package email.haemmerle.talkplanning.model

class Publisher(val firstName: String, val lastName: String, val congregationId: Int, val id: Int? = null, initalTalks: List<Int> = listOf()) {
    private val talks : MutableList<Int> = mutableListOf()

    init {
        talks.addAll(initalTalks)
    }
    fun addTalk(value: Talk) {
        talks.add(value.number)
    }
    fun getTalks() = talks.toList()
}

interface PublisherRepo {

    fun save(publisher: Publisher)

    fun findAll(): List<Publisher>

    fun delete(id: Int)

}