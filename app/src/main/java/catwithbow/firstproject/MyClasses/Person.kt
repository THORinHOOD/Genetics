package catwithbow.firstproject.MyClasses

open class Person {
    protected var name : String
        get() = this.name
    protected var age : Int
        get() = this.age
    protected var surname : String
        get() = this.surname

    constructor(name : String, surname : String, age : Int) {
        this.name = name
        this.age = age
        this.surname = surname
    }

    fun isTeenager() : Boolean {
        return age in 12..17
    }

    open fun isEligibleToVote() : Boolean{
        return age >= 18
    }
}