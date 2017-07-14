package com.luna.akka

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * A class of actor playing ping-pong
  * Created by zehui on 2017/7/14.
  */

case object PingMessage

case object PongMessage

case object StartMessage

case object StopMessage


class PingActor(pong: ActorRef) extends Actor {
  def receive = {
    case StartMessage => println("OK，I ping!")
      pong ! PingMessage
    case PongMessage =>
      // of course player is better...
      Thread.sleep(500)
      sender ! PingMessage
    case StopMessage =>
      println("Okay... I am tired too , let's play another day!")
      context.system.terminate()
  }
}

class PongActor extends Actor {
  var count = 0

  def incrementAndGrumble: Unit = {
    count += 1
    println("I am tired,I said " + count.toString + " times!")
  }

  def receive = {
    case PingMessage =>
      Thread.sleep(1000)
      if (count < 10) {
        incrementAndGrumble
        println("ok,you challange me!I Pong!")
        sender ! PongMessage
      } else {
        println("I am too tired! I quit!")
        sender ! StopMessage
        context.stop(self)
      }
  }
}

object PingPongActor extends App {
  val system = ActorSystem("PingPongSystem")

  val pongActor = system.actorOf(Props[PongActor], name = "pong")
  //val pingActor = system.actorOf(Props(new PingActor(pongActor)), name = "ping")
  val pingActor = system.actorOf(Props(new PingActor(pongActor)), name = "ping")

  pingActor ! StartMessage

}
