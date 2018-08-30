package advanced.lectures.part3concurrency


import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}

object FuturesPromises extends App {

  def calculateMeaningOfLife: Int = {
    Thread.sleep(2000)
    42
  }

  val aFuture = Future {
    calculateMeaningOfLife // calculates the meaning of life on ANOTHER thread
  } // (global) which is passed by the compiler

  println(aFuture.value) // Option[Try[Int]]

  println("Waiting on the future")
  aFuture.onComplete {
    case Success(meaningOfLife) => println(s"the meaning of life is $meaningOfLife")
    case Failure(e) => println(s"I have failed with $e")
  } // SOME thread
  Thread.sleep(3000)


  case class Profile(id: String, name: String) {
    def poke(anotherProfile: Profile) = {
      println(s"${this.name} poking ${anotherProfile.name}")
    }
  }

  object SocialNetwork {
    // "database"
    val names = Map(
      "fb.id.1-zuck" -> "Mark",
      "fb.id.2-bill" -> "Bill",
      "fb.id.2-dummy" -> "Dummy"
    )

    val friends = Map(
      "fb.id.1-zuck" -> "fb.id.2-bill"
    )

    val random = new Random()

    //API
    def fetchProfile(id: String): Future[Profile] = Future {
      // simulate fetching from DB
      Thread.sleep(random.nextInt(300))
      Profile(id, names(id))
    }

    def fetchBestFriend(profile: Profile): Future[Profile] = Future {
      Thread.sleep(random.nextInt(400))
      val bfId = friends(profile.id)
      Profile(bfId, names(bfId))
    }
  }

  // client: mark to poke bill
  var mark = SocialNetwork.fetchProfile("fb.id.1-zuck")
  mark.onComplete {
    case Success(markProfile) => {
      val bill = SocialNetwork.fetchBestFriend(markProfile)
      bill.onComplete {
        case Success(billProfile) => markProfile.poke(billProfile)
        case Failure(e) => e.printStackTrace()
      }
    }
    case Failure(e) => e.printStackTrace()
  }

  //functional composition of futures
  // map, flatMAp, filter
  val nameOnTheWall = mark.map(profile => profile.name)
  val marksBestFriend = mark.flatMap(profile => SocialNetwork.fetchBestFriend(profile))
  val zucksBestFriendRestricted = marksBestFriend.filter(profile => profile.name.startsWith("Z"))
  
  // for-comprehensions
  for {
    mark <- SocialNetwork.fetchProfile("fb.id.1-zuck")
    bill <- SocialNetwork.fetchBestFriend(mark)
  } mark.poke(bill)

  // fallbacks
  val aProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recover {
    case _: Throwable => Profile("fb.id.0-dummy", "Forever alone")
  }

  val aFetchedProfileNoMatterWhat = SocialNetwork.fetchProfile("unknown id").recoverWith {
    case _: Throwable => SocialNetwork.fetchProfile("fb.id.0-dummy")
  }

  val fallbackResult = SocialNetwork.fetchProfile("unknown id").fallbackTo(SocialNetwork.fetchProfile("fb.id.0-dummy"))

  // online banking app
  case class User(name: String)
  case class Transaction(sender: String, receiver: String, amount: Double, status: String)

  object BankingApp {
    val name = "Rock the JVM banking"

    def fetchUser(name: String): Future[User] = Future {
      //simulate fetching from the DB
      Thread.sleep(500)
      User(name)
    }

    def createTransaction(user: User, merchantName: String, amount: Double): Future[Transaction] = Future {
      //simulate some process
      Thread.sleep(1000)
      Transaction(user.name, merchantName, amount, "SUCCESS")
    }

    def purchase(username: String, item: String, merchantName: String, cost: Double): String = {
      // fetch the user from the DB
      // create a transaction
      // WAIT for the transaction to finish
      val transactionStatusFuture = for {
        user <- fetchUser(username)
        transaction <- createTransaction(user, merchantName, cost)
      } yield transaction.status

      println("this is async")
      Await.result(transactionStatusFuture, 2.seconds) // implicit conversions -> pimp my library
    }
  }
  
  println(BankingApp.purchase("Daniel", "iPhone 12", "rock the jvm store", 3000))

  // promises
  val promise = Promise[Int]() // "controller" over a future
  val future = promise.future
  
  // thread 1 - "consumer"
  future.onComplete {
    case Success(r) => println(s"[consumer] I've received $r")
  }

  // thread 2 - "producer"
  val producer = new Thread(() => {
    println("[producer] crunching numbers...")
    Thread.sleep(500)
    // "fulfilling" the promise
    promise.success(42)
    println("[producer] done")
  })

  producer.start()
  Thread.sleep(1000)


  /*
    1) fulfill a future IMMEDIATELY with a value
    2) inSequence(fa,fb)
    3) first(fa, fb) => new future with the first value of the two futures
    4) last(fa, fb) => new future with the last value
    5) retryUntil(action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1 - fulfill immediately
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // 2 - in sequence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] =
    first.flatMap(_ => second)

  // 3 - first out of two futures
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
//
//    def tryComplete(promise: Promise[A], result: Try[A])= result match {
//      case Success(r) => try {
//        promise.success(r)
//      } catch {
//        case _ =>
//      }
//      case Failure(t) => promise.failure(t)
//    }
//    fa.onComplete(tryComplete(promise, _))
//    fb.onComplete(tryComplete(promise, _))

    fa.onComplete(promise.tryComplete)//does the same thing
    fb.onComplete(promise.tryComplete)//does the same thing
    promise.future
  }

  // 4 - last out of the two futures
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {
    // 1 promise which both futures will try to complete
    // 2 promise which the LAST future will complete
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]
    val checkAndComplete = (result: Try[A]) =>
      if(!bothPromise.tryComplete(result))
        lastPromise.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)
    
    lastPromise.future
  }

  val fast = Future {
    Thread.sleep(100)
    42
  }

  val slow = Future {
    Thread.sleep(200)
    45
  }

  first(fast, slow).foreach(println)
  last(fast, slow).foreach(println)

  Thread.sleep(300)

  // 5. retry until
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
    .filter(condition)
    .recoverWith {
      case _ => retryUntil(action, condition)
    }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) => x < 50).foreach(result => println(s"settled at $result"))
  Thread.sleep(10000)
}
