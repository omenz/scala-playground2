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
  //polymorphic call
  override def toString: String = s"[$printElements]"
}

object Empty extends MyList[Nothing] {
  override def head: Nothing= throw new NoSuchElementException
  override def tail: MyList[Nothing] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  override def printElements: String = ""
}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList[B] = new Cons(element, this)

  override def printElements: String =
    if (t.isEmpty) "" + h
    else h + " " + t.printElements
}


object ListTest extends App {
  val list: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val listOfStrings: MyList[String] = new Cons("pew", new Cons("pew2", new Cons("pew3", Empty)))
  println(list.tail.head)
  println(list.add(4).head)
  println(list.isEmpty)
  println(list)
  println(listOfStrings)
}