package beginner.lectures.part2oop

/**
  * Created by Alexander Krasovsky on 27.06.2018.
  */
object Inheritance extends App {

  //constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  class Animal(val creatureType: String) {
    protected def eat(): Unit = println("nomnom")
  }

  class Dog(override val creatureType: String) extends Animal(creatureType) {
    override def eat(): Unit = println("crunch crunch")
  }

  val dog = new Dog("K9")
  dog.eat

  //type substitution
  val unknownAnimal = new Dog("K9")
  unknownAnimal.eat

  //preventing overriding: use "final" on class members or class.
  //use keywords "seal" - allows extending classes only in the same file
}
