package excercises

/**
  * Created by Alexander Krasovsky on 29.06.2018.
  */
abstract class MyList[+A]() {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]
  //polymorphic call
  override def toString: String = s"[$printElements]"
}

object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  override def printElements: String = ""
  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements

  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    def transformHelper(list: MyList[A], accumulator: MyList[B]): MyList[B] = {
      if (list.isEmpty) {
        accumulator
      } else {
        val mapped = transformer(list.head)
        transformHelper(list.tail, new Cons[B](mapped, accumulator))
      }
    }
    transformHelper(this, Empty)
  }


  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    def transformHelper(list: MyList[A], accumulator: MyList[B]): MyList[B] = {
      if (list.isEmpty) {
        accumulator
      } else {
        val mapped = transformer(list.head)
        val accumulatorWithMappedList = addAllToList(mapped, accumulator)
        transformHelper(list.tail, accumulatorWithMappedList)
      }
    }
    def addAllToList(src: MyList[B], dst: MyList[B]): MyList[B] = {
      if (src.isEmpty)
        dst
      else
        addAllToList(src.tail, dst.add(src.head))
    }
    transformHelper(this, Empty)
  }

  override def filter(predicate: MyPredicate[A]): MyList[A] = {
    def filterHelper(list: MyList[A], accumulator: MyList[A]): MyList[A] = {
      if (list.isEmpty) {
        accumulator
      } else {
        if (predicate(list.head))
          filterHelper(list.tail, new Cons(list.head, accumulator))
        else
          filterHelper(list.tail, accumulator)
      }
    }
    filterHelper(this, Empty)
  }
}


object ListTest extends App {
  val list: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("pew", new Cons("pew2", new Cons("pew3", Empty)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list)
  println(listOfStrings)

  val transformer: MyTransformer[Int, String] = (element: Int) => element.toString

  val flatMapTransformer: MyTransformer[Int, MyList[String]] = (element: Int) =>
    new Cons(element.toString, new Cons[String]((element + 1).toString, Empty))

  val filter: MyPredicate[Int] = (element: Int) => element == 2
  println(list.map(transformer))
  println(list.flatMap(flatMapTransformer))
  println(list.filter(filter))
}

trait MyPredicate[-T] {
  def apply(element: T): Boolean
}

trait MyTransformer[-A, B] {
  def apply(element: A): B
}