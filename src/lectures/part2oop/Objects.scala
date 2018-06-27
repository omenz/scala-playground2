package lectures.part2oop

/**
  * Created by Alexander Krasovsky on 27.06.2018.
  */
object Objects extends App {

  object Person {
    //"static" class level functionality
    val N_EYES = 2
    def canFly: Boolean = false
    //factory method
    def from(mother: Person, father: Person): Person = new Person("Bobbie")
    //or you can use
    def apply(mother: Person, father: Person): Person = from(mother, father)
  }

  class Person(val name: String) {
    //instance level functionality
  }
  //COMPANIONS
  val person1 = Person //singleton
  val person2 = Person
  println(person1 == person2)

  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val bobbie = Person.from(mary, john)
  val bobbie2 = Person(mary, john)


  //Scala applications = Scala object with
  //def main(args: Array[String]): Unit
}
