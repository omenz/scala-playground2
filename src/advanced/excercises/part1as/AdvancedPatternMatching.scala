package advanced.excercises.part1as

object AdvancedPatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.") // :: infix pattern
    case _ =>
  }

  /*
    - constants
    - wildcards
    - case classes
    - tuples
    - some special magic like above
   */

  class Person(val name: String, val age: Int)

  object Person {
    // allows to pattern match non case classes
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))

    def unapply(age: Int): Option[(String)] =
      Some(if (age < 21) "minor" else "major")
  }

  object PersonPattern {
    // allows to pattern match non case classes
    def unapply(person: Person): Option[(String, Int)] =
      if (person.age < 21) None  //it is possible to add a condition here
      else Some((person.name, person.age))
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
    case Person(n, a) => s"Hi, my name is $n and I am $a yo."
    case PersonPattern(n, a) => s"Hi, my name is $n and I am $a yo."
  } // Person and PersonPattern do the same thing and can have any name

  println(greeting)

  val legalStatus = bob.age match {
    case Person(status) => s"My legal status is $status"
  }

  println(legalStatus)


  /*
    Exercise. Get rid of ugly pattern matches in matchProperty
   */
  val n: Int = 45
  val matchProperty = n match {
    case x if x < 10 => "single digit"
    case x if x % 2 == 0 => "an even number"
    case _ => "no property"
  }


  object singleDigit {
    def unapply(arg: Int): Option[String] =
      if (arg < 10) Some("single digit")
      else None
  }

  object even {
    def unapply(arg: Int): Option[String] =
      if (arg % 2 == 0) Some("an even number")
      else None
  }

  val anotherNumber = 24

  val matchPropertyV2 = anotherNumber match {
    case singleDigit(x) => x
    case even(x) => x
    case _ => "no property"
  }

  println(matchPropertyV2)
}
