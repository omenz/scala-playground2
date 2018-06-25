package lectures.part2oop

import java.time.LocalDateTime

/**
  * Created by Alexander Krasovsky on 25.06.2018.
  */
object OOBasics extends App {

  val person = new Person("John", 22)

  println(person.age)

}

class Person(name: String, val age: Int) {

  val x = 2

  println(2 + x)

  def greet(name: String): Unit = println(s"Hello $name, my name is ${this.name}")

  //overloading
  def greet(): Unit = println(s"Hi, my name is $name")

  //constructors
  def this(name: String) = this(name, 0)

  def this() = this("John Doe")
}

class Writer(val firstName: String, val surname: String, val year: Int) {
  def fullName(): String = s"$firstName $surname"

  def canEqual(other: Any): Boolean = other.isInstanceOf[Writer]

  override def equals(other: Any): Boolean = other match {
    case that: Writer =>
      (that canEqual this) &&
        firstName == that.firstName &&
        surname == that.surname &&
        year == that.year
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(firstName, surname, year)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

class Novel(val name: String, val yearOfRelease: Int, var author: Writer) {

  def authorAge():Int = LocalDateTime.now().getYear - author.year

  def isWrittenBy(author: Writer): Boolean = author.equals(this.author)

  def copy(newYearOfRelease: Int): Novel = new Novel(name, newYearOfRelease, author)
}

