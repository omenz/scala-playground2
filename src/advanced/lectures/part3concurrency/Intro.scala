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
  
}
