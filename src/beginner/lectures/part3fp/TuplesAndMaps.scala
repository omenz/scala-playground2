package beginner.lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"

  val aTuple = Tuple2(2, "hello, scala") //Tuple2[Int, String] = (Int, String)
  //or
  val aTuplee = (2, "hello, scala")

  println(aTuple._1)
  println(aTuple._2)

  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap)

  // Maps
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  //a -> b is syntactic sugar

  // map ops
  println(phonebook)
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  println(phonebook("Mary")) //no such element exception, use with default value

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatmap, filter

  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))
  // filterKeys
  println(phonebook.filterKeys(_.startsWith("J")))
  // mapValues
  println(phonebook.mapValues(number => number * 10))

  // conversions to other collections
  println(phonebook.toList) //list of tuples
  println(List(("Daniel", 555)).toMap)

  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  println(names.groupBy(name => name.charAt(0))) // map of lists with first char as key and a value is a list of names starting with it


}



object SocialNetworkApp extends App {

  var network = SocialNetwork()

  network = network.add("Peter")
  network = network.add("John")
  network = network.add("Sue")
  network = network.add("Jenny")
  network = network.add("Chloe")
  network = network.add("Bob")
  network = network.add("Amy")
  network.print()

  network = network.friend("Peter", "John")
  network = network.friend("Peter", "Jenny")
  network = network.friend("Peter", "Sue")
  network = network.friend("Peter", "Chloe")
  network = network.friend("John", "Jenny")
  network = network.friend("John", "Bob")
  network.print()

  network = network.remove("Sue")
  network.print()

  network = network.unfriend("Jenny", "Peter")
  network.print()

  println(network.friendsCount("Peter"))
  println(network.friendsCount("Jenny"))

  println(network.mostFriends)

  println(network.peopleWithNoFriendsCount)

  println(network.isThereAConnection("Peter", "John"))
  println(network.isThereAConnection("Peter", "Bob"))
  println(network.isThereAConnection("Peter", "Amy"))
}


case class SocialNetwork(network: Map[String, Set[String]] = Map()) {

  def add(person: String) = SocialNetwork(network + (person -> Set()))

  def friend(a: String, b: String) =
    SocialNetwork(network + (a -> (network(a) + b)) + (b -> (network(b) + a)))

  def unfriend(a: String, b: String) =
    SocialNetwork(network + (a -> (network(a) - b)) + (b -> (network(b) - a)))

  def remove(person: String): SocialNetwork = {
    @tailrec
    def removeAux(friends: Set[String], networkAcc: SocialNetwork): SocialNetwork =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, networkAcc.unfriend(person, friends.head))
    val unfriended = removeAux(network(person), this)
    SocialNetwork(unfriended.network - person)
  }

  def friendsCount(person: String): Int = network(person).size

  def mostFriends: (String, Set[String]) = network.maxBy(_._2.size)

  def peopleWithNoFriendsCount: Int = network.count(_._2.isEmpty)

  def isThereAConnection(a: String, b: String): Boolean = {
    @tailrec
    def bfs(target: String, tested: Set[String], discovered: Set[String]): Boolean = {
      if (discovered.isEmpty) false
      else {
        val person = discovered.head
        if (person == target) true
        else if (tested.contains(person)) bfs(target, tested, discovered.tail)
        else bfs(target, tested + person, discovered.tail ++ network(person))
      }
    }
    bfs(b, Set(), network(a) + a)
  }

  def print(): Unit = println(network)
}