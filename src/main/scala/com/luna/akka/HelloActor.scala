package com.luna.akka

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by zehui on 2017/7/14.
  */
class HelloActor extends Actor {
  def receive = {
    case "hello" => println("Hi!")
    case "How time flies!" => println("Yes! Time flies!")
    //before  akka 2.4  :shutdown
    case "KILL" => context.system.terminate()
    case _ => println("Welcome!")

  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  val helloActor = system.actorOf(Props[HelloActor], name = "helloActor")
  helloActor ! "hello"
  helloActor ! "I am from China!"

  Thread sleep 1000
  helloActor ! "How time flies!"
  helloActor ! "KILL"


}
