package co.uk.netpod.chatter

import scala.util.control.Breaks._
import java.io.BufferedReader
import java.io.InputStreamReader

object ChatCommand {
  
  var application: ChatterApplication = new ChatterApplication();

  def main(args: Array[String]) {
    
    val in = new BufferedReader(new InputStreamReader(System.in));
    
    breakable {
      while (true) {
        println("> ");
        val input = in.readLine();
        if (input == "") break;
        processInput(input);
      }
    }
  }
  
  def processInput(input: String) : Unit = {
    var args : Array[String] = input.split("->");

    if (!args.isEmpty && args.length == 2) {
      application.post(args(0).trim(), args(1).trim());
    } else if (!args.isEmpty) {
      args = input.split("follows");
      
      if (!args.isEmpty && args.length == 2) {
        application.follow(args(1).trim(), args(0).trim());        
      } else if (!args.isEmpty && input.contains("wall")) {
        args = input.split("wall");
        if (!args.isEmpty && args.length == 1) {
          println(application.getWall(args(0).trim()));
        }
      } else if (!args.isEmpty && args.length == 1) {
        println(application.read(args(0).trim()));
      }
    } 
  }
}
