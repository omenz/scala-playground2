package lectures.part3fp

object MapFlatmapFilterFor extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  println(list.map(_ + 1))

  println(list.filter(_ % 2 == 0))

  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))


  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")


  println(colors.flatMap(color => chars.flatMap(char => numbers.filter(_ % 2 == 0).map(num => s"$char$num-$color"))))

  //for-comprehensions is the same as expression above
  val forCombinations = for {
    n <- numbers if n % 2 == 0 //translated to filter call, if guard
    c <- chars
    color <- colors
  } yield s"$c$n-$color"
  println(forCombinations)

  for {
    n <- numbers
  } println(n)

  //syntax overload
  list.map { x =>
    x * 2
  }
}
