package lectures.part2oop

/**
  * Created by Alexander Krasovsky on 30.06.2018.
  */
object AnonimousClasses extends App {

  trait Animal {
    def eat(): Unit
  }

  //anonymous class
  val funnyAnimal: Animal = new Animal {
    override def eat(): Unit = println("ha ha ha")
  }

  println(funnyAnimal.getClass)//class lectures.part2oop.AnonimousClasses$$anon$1

  class Person(val name: String) {
    def sayHi: Unit = println(s"pewepw $name")
  }

  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"pewpew eeeew $name")
  }

}
