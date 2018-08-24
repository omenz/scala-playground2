package advanced.lectures.part3concurrency

import java.util.concurrent.Executors

object Intro extends App {
  /*
    interface Runnable {
      public void run()
    }
   */
  val aThread = new Thread(() => println("Running in parallel"))

  aThread.start()
  // crete a JVM thread => OS thread

  aThread.join()// blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() => (1 to 5).foreach(_ => println("goodbye")))

  threadHello.start()
  threadGoodbye.start()

  // executors
  val pool = Executors.newFixedThreadPool(10)
  pool.execute(() => println("something in the thread pool"))

  pool.execute(() => {
    Thread.sleep(1000)
    println("done after 1 second")
  })

  pool.execute(() => {
    Thread.sleep(1000)
    println("almost done")
    Thread.sleep(1000)
    println("done after 2 seconds")
  })

//  pool.shutdown() //stops accepting new tasks
//  pool.execute(() => println("should not appear")) throws an exception in the calling thread
  
//  pool.shutdownNow() //interrupts running threads and stops accepting tasks
//  println(pool.isShutdown)// true

  def runInParallel = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

//  for (_ <- 1 to 10) runInParallel
  // race condition

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int) = {
    account.amount -= price // account.amount = account.amount - price
    println(s"I've bought $thing")
    println("my account is now " + account)
  }

  for (_ <- 1 to 3) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iPhone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(100)
    if (account.amount != 43000) println("AHA: " + account.amount)
    println()
  }
    /*
      thread1 (shoes): 50000
        - account = 50000 - 3000 = 47000
      thread2 (iphone): 50000
        - account = 50000 - 4000 = 46000 overwrites the memory of account.amount
      */
      // option #1: use synchronized()
    def buySafe(account: BankAccount, thing: String, price: Int) =
        account.synchronized {
          // no two threads can evalueate this at the same time
          account.amount -= price
          println(s"I've bought $thing")
          println(s"my account is now $account")
        }
    // option #2: use @volatile

  /**
    * Exercises
    *
    * 1) Construct 50 "inception" threads
    *     Thread1 -> thread2 -> thread3 -> ...
    *     println("hello from thread #3)
    *
    *     in REVERSE ORDER
    */


  def constructThread(threadNumber: Int, maxThreadCount: Int): Thread = new Thread(() => {
    if (threadNumber < maxThreadCount) {
      val newThread = constructThread(threadNumber + 1, maxThreadCount)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $threadNumber")
  })

  constructThread(0, 50).start()

  /*
    2
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  /*
    1) what is the biggest value possible for x?
    2) what is the SMALLEST value possible for x?
   */
  var message = ""
  val awesomeThread = new Thread(() => {
    Thread.sleep(1000)
    message = "Scala is awesome"
  })

  message = "Acala sucks"
  awesomeThread.start()
  Thread.sleep(2000)
  println(message)
  /*
    what's the value of message?
    is it guaranteed?
    why? why not?
   */


}
