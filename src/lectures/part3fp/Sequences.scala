package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  val aSequence = Seq(1, 2, 3, 4)

  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(5, 6, 7))
  println(aSequence.sorted)

  // Ranges
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(print)                    
  (1 until 10).foreach(print)

  // Lists
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList
  println(prepended)
  val appended = 42 +: aList :+ 54
  println(appended)

  val apples5 = List.fill(5)("apple")
  println(apples5)
  println(aList.mkString("-"))    //1-2-3

  // Arrays
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3)
  println(threeElements)
  threeElements.foreach(println)

  // mutation
  numbers(2) = 0  // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" "))

  val numberSeq: Seq[Int] = numbers // implicit conversion, underlined by IDE
  println(numberSeq) // Seq -> WrappedArray(1, 2, 0, 4)

  // Vectors
  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // vectors vs lists
  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }

    times.sum * 1.0 / maxRuns
  }

  // keeps reference to tail
  // updating an element in the middle takes to long
  val numbersList = (1 to maxCapacity).toList
  // depth of the tree is small
  // needs to replace an entire 32-element chunk
  val numbersVector = (1 to maxCapacity).toVector

  println(getWriteTime(numbersList))
  println(getWriteTime(numbersVector))
}
