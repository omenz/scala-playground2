package beginner.excercises

/**
  * Created by Alexander Krasovsky on 29.06.2018.
  */
abstract class MyList[+A]() {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  //higher order functions
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]
  def ++[B >: A](list: MyList[B]): MyList[B]
  def forEach(fn: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B
  //polymorphic call
  override def toString: String = s"[$printElements]"
}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)
  override def printElements: String = ""
  override def map[B](transformer: Nothing => B): MyList[B] = Empty
  def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty
  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty
  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
  override def forEach(fn: Nothing => Unit): Unit = {}
  override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = this
  override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
    if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Empty
  override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  override def map[B](transformer: A => B): MyList[B] = {
    Cons(transformer(h), tail.map(transformer))
  }

  def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }

  override def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(h)) Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  override def ++[B >: A](list: MyList[B]): MyList[B] =
    new Cons[B](h, t ++ list)

  override def forEach(fn: A => Unit): Unit = {
    fn(h)
    t.forEach(fn)
  }

  override def sort(compare: (A, A) => Int): MyList[A] = {
    def insert(x: A, sortedList: MyList[A]): MyList[A] =
      if (sortedList.isEmpty) new Cons[A](x, Empty)
      else if (compare(x, sortedList.head) <= 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }
  override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] =
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Cons(zip(h, list.head), t.zipWith(list.tail, zip))

  override def fold[B](start: B)(operator: (B, A) => B): B =
    t.fold(operator(start, h))(operator)

}


object ListTest extends App {
  val list: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val listClone: MyList[Int] = Cons(1, Cons(2, Cons(3, Empty)))
  val listOfStrings: MyList[String] = Cons("pew", Cons("pew2", Cons("pew3", Empty)))
  val listOfStrings2: MyList[String] = Cons("pew", Cons("pew2", Cons("pew3", Empty)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list)
  println(listOfStrings)

  val transformer: Int => String = _.toString

  val flatMapTransformer: Int => MyList[String] =
    element => Cons(element.toString, new Cons[String]((element + 1).toString, Empty))

  val filter: Int => Boolean = _ == 2
  println(list.map(transformer))
  println(list.flatMap(flatMapTransformer))
  println(list.filter(filter))

  //works thanks to case class
  println(list == listClone)

  println(list.forEach(println(_)))
  println(listOfStrings.zipWith[String, String](listOfStrings2, _ + " " + _))
  println(list.fold(0)(_ + _))


  def toCurry(f: (Int, Int) => Int): Int => Int => Int =
    x => y => f(x, y)

  def fromCurry(f: Int => Int => Int): (Int, Int) => Int =
    (x, y) => f(x)(y)

  def compose[A, B, T](f: A => B, g: T => A): T => B =
    x => f(g(x))

  def andThen[A, B, C](f: A => B, g: B => C): A => C =
    x => g(f(x))

  def superAdder: Int => Int => Int = toCurry(_ + _)
  def add4 = superAdder(4)
  print(add4(17))

  val simpleAdder = fromCurry(superAdder)
  println(simpleAdder(4, 17))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)

  println(composed(4))
  println(ordered(4))

  //it supports comprehension because it has map, flatMap and filter methods implemented
  print("Comprehension: ")
  val comprehensionTest = for {
    n <- list
  } yield print(n + " ")

}

