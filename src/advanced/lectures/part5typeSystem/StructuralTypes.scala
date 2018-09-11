package advanced.lectures.part5typeSystem

object StructuralTypes extends App {

  // structural types

  type JavaClosable = java.io.Closeable

  class HipsterCloseable {
    def close(): Unit = println("yeah yeah I'm closing")
    def closeSilently(): Unit = println("not making a sound")
  }

//  def closeQuietly(closeable: JavaClosable OR HipsterClosable) // how to accomplish ?!

  type UnifiedCloseable = {
    def close(): Unit
  } // STRUCTURAL TYPE

  def closeQuietly(unifiedCloseable: UnifiedCloseable): Unit = unifiedCloseable.close()

  closeQuietly(new JavaClosable {
    override def close(): Unit = println("closed quietly")
  })
  closeQuietly(new HipsterCloseable)

  // TYPE REFINEMENTS

  type AdvancedCloseable = JavaClosable {
    def closeSilently(): Unit
  }

  class AdvancedJavaCloseable extends JavaClosable {
    override def close(): Unit = println("Java closes")
    def closeSilently(): Unit = println("Java closes silently")
  }

  def closeShh(advCloseable: AdvancedCloseable): Unit = advCloseable.closeSilently()

  closeShh(new AdvancedJavaCloseable)
//  closeShh(new HipsterCloseable) // compiler checks that HipsterCloseable is not AdvancedCloseable

  // using structural types as standalone types
  def altClose(closeable: { def close(): Unit }): Unit = closeable.close()

  // type-checking => duck typing
  type SoundMaker = {
    def makeSound(): Unit
  }

  class Dog {
    def makeSound(): Unit = println("bark!")
  }

  class Car {
    def makeSound(): Unit = println("vroom!")
  }

  // static duck typing
  val dog: SoundMaker = new Dog
  val car: SoundMaker = new Car

  // CAVEAT: based on reflection, performance impact
  /*
    Exercises
   */
  trait CBL[+T] {
    def head: T
    def tail: CBL[T]
  }

  class Human {
    def head: Brain = new Brain
  }

  class Brain {
    override def toString: String = "BRAINZ!"
  }

  def f[T](somethingWithAHead: { def head: T }): Unit = println(somethingWithAHead.head)

  /*
    f is compatible with CBL and with a Human?
   */

  f(new Human)
  f(new CBL[Human] {
    override def head: Human = new Human
    override def tail: CBL[Human] = ???
  })

  // 2.
  object HeadEqualizer {
    type Headable[T] = { def head: T }
    def ===[T](a: Headable[T], b: Headable[T]): Boolean = a.head == b.head
  }

  /*
    is it compatible with CBL and Human?
   */

  // yes but it is not type safe, we can compare Human with a list of strings in this case
  HeadEqualizer.===(new Human, new Human)
  HeadEqualizer.===(new Human, new CBL[String] {
    override def head: String = "b"
    override def tail: CBL[String] = ???
  })
}
