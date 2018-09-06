package advanced.excercises

import advanced.lectures.part4implicits.TypeClasses.{User, john}

object EqualityPlayground {

 /*
  Exercise: Equality type class
  compare users by name and email
 */

  val john = User("John", 32, "john@rockthejvm.com")
  
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object EqualByEmail extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.email == b.email
  }

  implicit object EqualByName extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  /*
    Exercise: implement the TC pattern for the equality TC
  */
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val anotherJohn = User("John", 45, "anotherJohn@rtvm.com")
  println(Equal(john, anotherJohn)) // AD-HOC polymorphism


}
