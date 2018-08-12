package beginner.lectures.pattern4pm

import excercises.{Cons, Empty, MyList}


object AllThePatterns extends App {

  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "The Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard

  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchVariables = x match {
    case something => s"I've found $something"
  }

  // 3. tuples
  val tuple = (1, 2)
  val matchATuple = tuple match {
    case (1, 2) =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }
  // PMs can be NESTED

  // 4 - case classes - constructor pattern
  // PM can be nested with Case Classes as well
  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchList = aList match {
    case Empty =>
    case Cons(head, Cons(subhead, subtail)) =>
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => //extractor - advanced, match lists with 4 elements where the first one is 1
    case List(1, _*) => // list of arbitrary(any) length - advanced
    case 1 :: List(_) => //infix pattern
    case List(1, 2, 3) :+ 42 => //infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ Cons(_, _) => // name binding => use the name later(here)
    case Cons(1, rest @ Cons(2, _)) => // name binding inside nested patterns
  }

  // 8 - multi-patterns
  val multiPattern = aList match {
    case Empty | Cons(0, _) => //compound pattern (multi-pattern)
    case _ =>
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _)) if specialElement % 2 == 0 =>
  }


  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings" // type is erased and actually it matches against List only
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch)
  // JVM trick question
}
