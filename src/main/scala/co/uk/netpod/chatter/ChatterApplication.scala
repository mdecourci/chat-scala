/**
 *
 */
package co.uk.netpod.chatter

import java.util.Calendar

/**
 * @author michaeldecourci
 *
 */
class ChatterApplication {
  var timelines : List[Timeline] = List()
  var walls : List[Wall] = List()
  
  def post(byUser : String, msg : String) : Unit = {
	var timeline = getTimeline(byUser)
    
	// add message and time
	timeline.putMsg(msg);
  }
  
  def read(user : String) : String = {
    var timeline = getTimeline(user);
    return timeline.read();
  }
   
  def getTimeline(ofUser : String) : Timeline = {
    var foundLine = for (t <- timelines; if t.owner.equals(ofUser)) yield t

    if (foundLine.isEmpty) {
      var t = new Timeline(ofUser)
      timelines = t :: timelines;
      return t
    } else {
      return foundLine(0);
    }
  
  }
  
  def follow(followedUser : String, byUser : String) : Unit = {
    var foundWall = for (t <- walls; if t.ownerTimeline.owner.equals(byUser)) yield t;
    if (foundWall.isEmpty) {
    	walls = new Wall(getTimeline(byUser)) :: walls
    }
    foundWall = for (t <- walls; if t.ownerTimeline.owner.equals(byUser)) yield t;
    var wall = foundWall(0);
	wall.follow(getTimeline(followedUser))
  }
  
  def getWall(user : String) : String = {
    var foundWall = for (t <- walls; if t.ownerTimeline.owner.equals(user)) yield t;
    if (foundWall.isEmpty) {
      return ""
    }
    
    var wall = foundWall(0);
	return wall.getWriting();
  } 
}