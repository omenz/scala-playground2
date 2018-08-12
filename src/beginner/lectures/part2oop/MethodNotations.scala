package beginner.lectures.part2oop

/**
  * Created by Alexander Krasovsky on 26.06.2018.
  */
object MethodNotations extends App {

  class Person(val name: String, favoriteMovie: String, val age: Int) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def hangOutWith(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(nickname: String): Person = new Person(s"${this.name} (the $nickname)", favoriteMovie, age)
    def unary_! : String = s"$name, what the heck?!"
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie."
    def apply(times: Int): String = s"$name watchedn $favoriteMovie $times times."
    def learns(something: String): String = s"$name learns $something"
    def learnsScala(): String = this learns "Scala"

  }

  val mary = new Person("Mary", "Inception", 22)
  println(mary.likes("Inception"))
  println(mary likes "Inception") //equivalent
  //infix notation = operator notation(syntactic sugar)

  //"operators" in Scala
  val tom = new Person("Tom", "Fight Club", 43)
  println(mary hangOutWith tom)
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2))
  //ALL OPERATORS ARE METHODS

  //prefix notation
  val x = -1 //equivalent with 1.unary_-
  val y = 1.unary_-
  // unary prefix works only with + - ~ !

  println(!mary)
  println(mary.unary_!)

  //postfix notation
  println(mary.isAlive)
  println(mary isAlive)

  //apply
  println(mary.apply())
  println(mary())//equivalent

  println((mary + "Rockstar").name)
  println((+mary).age)
  mary learnsScala

  println(mary(2))
}
