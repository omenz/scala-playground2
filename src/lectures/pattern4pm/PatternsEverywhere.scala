package lectures.pattern4pm

object PatternsEverywhere extends App {

  // big idea #1
  try {
    //code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // catches are actually matches
  /*
  try {
    //code
  } catch(e) {
   e match {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
   }
  }
   */

  // big idea #2
  val list = List(1, 2, 3 ,4)
  val evenOnes = for {
    x <- list if x % 2 == 0
  } yield 10 * x

  // generators are also based on pattern matching
  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple
  println(b)
  //multiple value definition based on PATTERN MATCHING


  val head :: tail = list
  println(head)
  println(tail)

  // big idea #4 - NEW
  // partial functions are based on the pattern matching
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the one"
    case _ => "something else"
  }
  //is same as:
  val mappedList2 = list.map {x => x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the one"
      case _ => "something else"
    }
  }

  println(mappedList)
}
