package advanced.lectures.part4implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)//overrides implicit from Predef
  println(List(1,4,5,3,2).sorted)
  // scala.Predef

  /*
    Implicits (used as implicit parameters):
      -val/var
      -object
      -accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)
//  implicit val personOrderingByName: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)//overrides implicit from Predef

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66),
  )
//
//  object Person {
//    implicit val personOrderingByName: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) > 0)
//  }

//  println(persons.sorted)


  /*
    Implicit scope
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions of all types involved in the method signature
      - List
      - Ordering
      - all types involved - A or any supertype
   */
  // def sorted[B >: A](implicit: ord: Ordering[B]): List[B]


  object AlphabeticNameOrdering {
    implicit val personOrderingByName: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) > 0)
  }

  object AgeOrdering {
    implicit val personOrderingByName: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  import AlphabeticNameOrdering._
  println(persons.sorted)

  /*
    Exercise.
    Organize implicits so that it is easy to use them. Orderings usage:
    - totalPrice = most used(50%)
    - by unit count = 25%
    - by unit price = 25%
   */
  case class Purchase(nUnits: Int, unitPrice: Double) {
    def total: Double = nUnits * unitPrice
  }

  //most used will be default
  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.total < b.total)
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.nUnits < b.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice < b.unitPrice)
  }

  val purchases = List(
    Purchase(10, 123),
    Purchase(1, 15),
    Purchase(3, 23),
    Purchase(20, 73),
  )

  //use a custom ordering
  import UnitCountOrdering._
  println(purchases.sorted)
}
