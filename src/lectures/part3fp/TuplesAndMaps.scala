package lectures.part3fp

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

  SocialNetwork.add("Peter")
  SocialNetwork.add("John")
  SocialNetwork.add("Sue")
  SocialNetwork.add("Jenny")
  SocialNetwork.add("Chloe")
  SocialNetwork.add("Bob")
  SocialNetwork.print()

  SocialNetwork.remove("Sue")
  SocialNetwork.print()

  SocialNetwork.friend("Peter", "John")
  SocialNetwork.friend("Peter", "Jenny")
  SocialNetwork.friend("Peter", "Chloe")
  SocialNetwork.friend("John", "Jenny")
  SocialNetwork.print()

  SocialNetwork.unfriend("Jenny", "Peter")
  SocialNetwork.print()

  println(SocialNetwork.friendsCount("Peter"))
  println(SocialNetwork.friendsCount("Jenny"))

  println(SocialNetwork.mostFriends)

  println(SocialNetwork.peopleWithNoFriendsCount)

  println(SocialNetwork.isThereAConnection("Peter", "John"))
  println(SocialNetwork.isThereAConnection("Peter", "Bob"))
}


object SocialNetwork {

  private var network: Map[String, Set[String]] = Map()

  def add(person: String): Unit = network = network + (person -> Set())

  def remove(person: String): Unit = network = network - person

  def friend(a: String, b: String): Unit =
    network = network + (a -> (network(a) + b)) + (b -> (network(b) + a))

  def unfriend(a: String, b: String): Unit =
    network = network + (a -> (network(a) - b)) +  (b -> (network(b) - a))

  def friendsCount(person: String): Int = network(person).size

  def mostFriends: (String, Set[String]) = network.maxBy(_._2.size)

  def peopleWithNoFriendsCount: Int = network.count(_._2.isEmpty)

  def isThereAConnection(person1: String, person2: String): Boolean = {
    if (network(person1).contains(person2)) true
    else network(person1)
      .flatMap(network(_))
      .contains(person2)
  }

  def print(): Unit = println(network)

}