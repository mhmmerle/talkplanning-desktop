package email.haemmerle.talkplanning.model

class Talk (val number : Int, val title : String){

    override fun toString(): String {
        return "$number - $title"
    }
}

interface TalkRepo {

    fun save(talk: Talk)

    fun findAll(): List<Talk>

    fun delete(number: Int)

    fun find(number: Int): Talk

}