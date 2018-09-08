package advanced.lectures.part4implicits

import java.util.Optional
import java.{util => ju}

object ScalaJavaConversions extends App {

  import collection.JavaConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet)


  val scalaSet = javaSet.asScala

  /*
    Iterator
    Iterable
    ju.List - scala.mutable.Buffer
    ju.Set - scala.mutable.Set
    ju.Map - scala.mutable.Map
   */

  import collection.mutable._
  val numberBuffer = ArrayBuffer[Int](1,2,3)
  val juNumbersBuffer = numberBuffer.asJava

  println(juNumbersBuffer.asScala eq numberBuffer)//true

  val numbers = List(1,2,3)
  val juNumbers = numbers.asJava
  val backToScala = juNumbers.asScala
  println(backToScala eq numbers)// shallow eq false
  println(backToScala == numbers)// deep eq true

//  juNumbers.add(7)//will result in exception, it is immutable list

  /*
    Exercise
    create a Scala-Java Optional-Option conversion
      -asScala
   */

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get) else None
  )


//  implicit class OptionalToScala[T](opt: Optional[T]) {
//    def asScala: Option[T] =
//      if (opt.isPresent) Some(opt.get)
//      else None
//  }

  val juOptional: Optional[String] = Optional.of("pewpew")
  println(juOptional.asScala)

}
