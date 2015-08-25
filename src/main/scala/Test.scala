import scaldi.{Injector, Injectable, Module}

object HelloScaldi {
  def main(args: Array[String]) {

    val test=new Test;
    test.run
  }
}

class Test( )  extends Injectable {
  def run: Unit = {
    implicit  val injector:Injector = new UserModule
    val output:IService=inject[IService]
    println(output.execute("Scaldi"));
  }
}

class UserModule extends Module {
  bind [ITransport] to new MessageTransport
  bind [IService] to  new MessageService(inject[ITransport])

}

trait ITransport {
  def send(s: String)
}

trait IService {
  def execute(x: String): String
}

class MessageService(transport:ITransport) extends IService {

  override def execute(x: String): String = {
    val ret="Hello " + x
    transport.send(ret)
    return  ret
  }
}

class MessageTransport() extends ITransport {
  override def send(s: String) = println("Sending message: " + s)
}

