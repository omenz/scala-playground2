package advanced.lectures.part5typeSystem

object FBoundedPolymorphism extends App {

//  trait Animal {
//    def breed: List[Animal]
//  }

//  class Cat extends Animal {
//    override def breed: List[Animal] = ??? // we want a List[Cat]!
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Animal] = ??? // we want a Dog here
//  }
//

  // Solution 1 - naive
//  class Cat extends Animal {
//    override def breed: List[Cat] = ??? // we want a List[Cat]!
//  }
//
//  class Dog extends Animal {
//    override def breed: List[Cat] = ??? // we want a Dog here and don't want a Cat!
//  }


  // Solution 2 - FBP (F-Bounded Polymorphism)
//  trait Animal[A <: Animal[A]] { // recursive type: F-Bounded Polymorphism
//    def breed: List[A]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Cat] = ??? // we want a List[Cat]!
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Dog] = ??? // we want a Dog here
//  }
//
//  trait Entity[E <: Entity[E]] // present in ORMs
//  class Person extends Comparable[Person] {  // FBP
//    override def compareTo(o: Person): Int = ???
//  }
//
//  class Crocodile extends Animal[Dog] {
//    override def breed: List[Dog] = ??? // MUST BE A LIST OF Crocodiles!!
//  }

  // Solution 3 - FBP + self-types

//  trait Animal[A <: Animal[A]] { self: A =>
//    def breed: List[A]
//  }
//
//  class Cat extends Animal[Cat] {
//    override def breed: List[Cat] = ??? // we want a List[Cat]!
//  }
//
//  class Dog extends Animal[Dog] {
//    override def breed: List[Dog] = ??? // we want a Dog here
//  }
//
//  //now it works!
////  class Crocodile extends Animal[Dog] {
////    override def breed: List[Dog] = ??? // MUST BE A LIST OF Crocodiles!!
////  }
//
//  // but this doesn't
//  trait Fish extends Animal[Fish]
//  class Shark extends Fish {
//    override def breed: List[Fish] = ???  // WRONG!!!
//  }
//  class Cod extends Fish {
//    override def breed: List[Fish] = ???
//  }

  // Exercise

  // Solution 4 type classes!
//  trait Animal
//  trait CanBreed[A] {
//    def breed(a: A): List[A]
//  }
//
//  class Dog extends Animal
//  object Dog {
//    implicit object DogsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//
//  implicit class CanBreedOps[A](animal: A) {
//    def breed(implicit canBreed: CanBreed[A]): List[A] =
//      canBreed.breed(animal)
//  }
//
//  val dog = new Dog
//  dog.breed // List[Dog]!!
//  /*
//    new CanBreedOps[Dog](dog).breed
//    implicit value to pass to breed: Dog.DogsCanBreed
//   */
//
//  class Cat extends Animal
//  object Cat {
//    implicit object DogsCanBreed extends CanBreed[Dog] {
//      override def breed(a: Dog): List[Dog] = List()
//    }
//  }
//
//  val cat = new Cat
//  cat.breed // compilation fails

  // Solution #5
  trait Animal[A] { // pure type classes
    def breed(a: A): List[A]
  }

  class Dog
  object Dog {
    implicit object DogAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  class Cat
  object Cat {
    implicit object CatAnimal extends Animal[Dog] {
      override def breed(a: Dog): List[Dog] = List()
    }
  }

  implicit class AnimalOps[A](animal: A) {
    def breed(implicit animalTypeClassInstance: Animal[A]): List[A] =
      animalTypeClassInstance.breed(animal)
  }

  val dog = new Dog
  dog.breed

  val cat = new Cat
//  cat.breed // compilation fails


}
