package email.haemmerle.talkplanning.model

import javafx.scene.control.Button

class Publisher(val firstName: String, val lastName: String, val congregationId: Int, val id: Int? = null, initalTalks: List<Int> = listOf()) {
    private val talks : MutableList<Int> = mutableListOf()

    init {
        talks.addAll(initalTalks)
    }

    fun addTalk(talk: Talk) {
        talks.add(talk.number)
    }

    fun removeTalk(talk : Talk) {
        talks.remove(talk.number)
    }

    fun getTalks() = talks.toList()
}

interface PublisherRepo {

    fun save(publisher: Publisher)

    fun findAll(): List<Publisher>

    fun findFor(congregationId: Int) : List<Publisher>

    fun delete(id: Int)

}