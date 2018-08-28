package catwithbow.firstproject.MyClasses

class Employee(name: String, surname: String, age: Int) : Person(name, surname, age) {

    override fun isEligibleToVote(): Boolean {
        return true
    }
}