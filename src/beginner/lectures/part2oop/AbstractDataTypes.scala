package beginner.lectures.part2oop

/**
  * Created by Alexander Krasovsky on 29.06.2018.
  */
object AbstractDataTypes extends App {

  //abstract, can have both abstract and non abstract methods and attributes
  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }


  class Dog extends Animal {
    override val creatureType: String = "Canine"
    override def eat: Unit = println("crunch crunch")
  }

  // traits, do not have constructions parameters
  trait Carnivore {
    def eat(animal: Animal): Unit
  }

  trait ColdBlooded

  //can have multiple traits and can extend one Class
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "Reptile"
    override def eat: Unit = println("nomnomnonm")
    override def eat(animal: Animal): Unit = println(s"I'ma croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)
}
