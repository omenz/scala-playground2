package advanced.lectures.part5typeSystem

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  class Cage[T]
  // yes - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // no - invariance
  class ICage[T]
//  val icage: ICage[Animal] = new ICage[Cat] //doesn't compile

  // hell no - opposite = contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](val animal: T) // invariant

  //covariant positions
  class CovariantCage[+T](val animal: T) // covariant position

//  class ContravariantCage[-T](val animal: T) // does not compile
  /*
    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */

//  class CovariantVariableCage[+T](val animal: T)// types of vars are in CONTRAVARIANT POSITION
  /*
    val ccage: CCage[Animal] = new CCage[Cat](new Cat)
    ccage.animal = new Crocodile
   */
//  class ContravariantVariableCage[+T](var animal: T)// also in COVARIANT POSITION

  /*
     val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */
  class InvariantVariableCage[T](var animal: T) // ok

//  trait AnotherCovariantCage[+T] {
//    def addAnimal(animal: T) // CONTRAVARIANT POSITION
//  }

  /*
    val ccage: CCage[Animal] = new CCage[Dog]
    ccage.add(new Cat)
   */

  class AnotherContravariantCage[-T] {
    def addAnimal(animal: T) = true
  }
  val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
  acc.addAnimal(new Cat)
  class Kitty extends Cat
  acc.addAnimal(new Kitty)


  class MyList[+A] {
    // B extends a super class of A
    def add[B >: A](element: B): MyList[B] = new MyList[B]// widening the type
  }

  var emptyList = new MyList[Kitty]
  val animals = emptyList.add(new Kitty)
  val moreAnimals = animals.add(new Cat)
  val evenMoreAnimals = moreAnimals.add(new Dog)

  // return types
  class PetShop[-T] {
//    def get(isItaPuppy: Boolean): T // METHOD RETURN TYPES ARE IN COVARIANT POSITION
    /*
      val catShop = new PetShop[Animal] {
        def get(isItaPuppy: Boolean): Animal = new Cat
      }

      val dogShop: PetShop[Dog] = catShop
      dogShop.get(true) // EVIL CAT!
     */

    // S is a subclass of T
    def get[S <: T](isItaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
  }

  val shop: PetShop[Dog] = new PetShop[Animal]
//  val evilCat = shop.get(true, new Cat)
  class TerraNova extends Dog
  val bigFurry = shop.get(true, new TerraNova)

  /*
    Big rule
    - method arguments are in CONTRAVARIANT position
    - return types are in COVARIANT position
   */

  /**
    * 1. Invariant, covariant, contravariant
    *   class Parking[T](things: List[T]) {
    *     park(vehicle: T)
    *     impound(vehicles: List[T])
    *     checkVehicles(conditions: String): List[T]
    *   }
    *
    * 2. used someone else's API: IList[T]
    * 3. Parking = monad
    *   - flatMap
    */

  class Vehicles
  class Bike extends Vehicles
  class Car extends Vehicles

  class InvariantParking[T](things: List[T]) {
    def park(vehicle: T): InvariantParking[T] = ???
    def impound(vehicles: List[T]): InvariantParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[B](f: T => InvariantParking[B]): InvariantParking[B] = ???
  }

  class CovariantParking[+T](things: List[T]) {
    def park[B >: T](vehicle: B): CovariantParking[B] = ???
    def impound[B >: T](vehicles: List[B]): CovariantParking[B] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[B](f: T => CovariantParking[B]): CovariantParking[B] = ???
  }

  class ContravariantParking[-T](things: List[T]) {
    def park(vehicle: T): ContravariantParking[T] = ???
    def impound(vehicles: List[T]): ContravariantParking[T] = ???
    def checkVehicles[B <: T](conditions: String): List[B] = ???

    // mind blown, double contravariant position for type argument R which is covariant
    def flatMap[R <: T, B](f: R => ContravariantParking[B]): ContravariantParking[B] = ???
  }


  /*
    Rule of thumb
    - use covariance = COLLECTION OF THINGS
    - use contravariance = GROUP OF ACTIONS
   */

  class IList[T]

  class CovariantParking2[+T](things: List[T]) {
    def park[B >: T](vehicle: B): CovariantParking2[B] = ???
    def impound[B >: T](vehicles: IList[B]): CovariantParking2[B] = ???
    def checkVehicles[B >: T](conditions: String): IList[B] = ???
  }

  class ContravariantParking2[-T](things: List[T]) {
    def park(vehicle: T): ContravariantParking2[T] = ???
    def impound[B <: T](vehicles: IList[B]): ContravariantParking2[B] = ???
    def checkVehicles[B <: T](conditions: String): IList[B] = ???
  }

  // flatMap
}
