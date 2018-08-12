package beginner.lectures.part2oop

/**
  * Created by Alexander Krasovsky on 30.06.2018.
  */
object Generics extends App {

  class MyList[+A] {
    //use the type A
    def add[B >: A](element: B): MyList[B] = ???
    /*
    A - Cat
    B - Animal
    we return the list of Animals after adding a Dog
     */
  }

  class MyMap[Key, Value]

  trait MyListTrait[A]


  var listOfIntegers = new MyList[Int]
  var listOfStrings = new MyList[String]

  //generic methods
  object MyList {
    def empty[A]: MyList[A] = ???
  }

  val emptyListOfIntegers = MyList.empty[Int]

  //variable problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  //1. Covariant list
  class CovariantList[+T]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]

  class InvariantList[A]

  //2. NO = INVARIANCE
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  //3. Hell no!. CONTRAVARIANCE
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  //bounded types, accepts only subtypes of animal
  //lower bound
  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  //upper bound
  class Cage2[A >: Animal](animal: A)
  val cage2 = new Cage(new Dog)


}
