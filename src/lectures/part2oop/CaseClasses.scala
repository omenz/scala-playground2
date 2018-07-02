package lectures.part2oop

/**
  * Created by Alexander Krasovsky on 02.07.2018.
  */
object CaseClasses extends App {

  /*
    equals, hashcode, toString
   */

  case class Person(name: String, age: Int)

  //1. Case Class constructor parameters can be accessed as fields
  val jim = new Person("Jim", 33)
  println(jim.age)
  println(jim.name)

  //2. Sensible toString
  println(jim)

  //3. Hash code and equals
  val jim2 = new Person("Jim", 33)
  println(jim == jim2)

  //4. Copy method
  val jim3 = jim2.copy(age = 44)
  println(jim3)

  //5. CC have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23)

  //6. CC are serializable
  //Akka

  //7. CC have extractor patterns == CCs can be used in PATTERN MATCHING

  //Case class is the same as Case Object, but it only has companion object
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }
}
