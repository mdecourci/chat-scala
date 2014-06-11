package co.uk.netpod.chatter

import java.util.Calendar
import java.util.Date

class Timeline(user : String) {
  val owner = user
  var postings : List[Posting] = List()
  
  def putMsg(msg : String) : Unit = {
    var now = Calendar.getInstance().getTime()
    postings = (new Posting(msg, now) :: postings.reverse).reverse
    println(owner + ", size = " + length)
  }
  
  def getMsg (i : Int) : String = {
    return postings(i).msg;
  }
  
  def read() : String = {
    var msg : String = "";
    var newline : String = "";
    var lines = readLines();
    for(line <- lines) {
      if (msg != "") {
        newline = "\n";
      }
      msg = msg + newline + line;
    }
    return msg;
  }

  def readLines() : List[String] = {
    var lines : List[String] = List();
    var len = length()
    var now = Calendar.getInstance().getTime()

    for(i <- 0 to len-1) {
      lines = (getMsg(i) + buildPostfix(getTimeFrom(now, i)) :: lines.reverse).reverse;
    }
    return lines;
  }
  
  def length() : Int = {
    return postings.length;
  }
  
  def getTimeFrom(from : Date, index : Int) : Int = {
    var fromCal = Calendar.getInstance()
    fromCal.setTime(from)
    var cal = Calendar.getInstance()
    cal.setTime(postings(index).dateTime)
     return fromCal.get(Calendar.SECOND) - cal.get(Calendar.SECOND)
  }
  
  class Posting (m : String, postTime : Date){
    val msg = m;
    val dateTime = postTime;
  }
  
  private def buildPostfix(seconds: Int): String = {
    var s = " ("
    s = s + seconds + " second"
    if (seconds > 1) {
      s = s + "s"
    }
    s = s + " ago)"
    return s
  }
}