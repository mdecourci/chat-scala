/**
 *
 */
package co.uk.netpod.chatter

/**
 * @author michaeldecourci
 *
 */
class Wall(userTimeline : Timeline) {
	val ownerTimeline : Timeline = userTimeline
	var followedTimeLines : List[Timeline] = List()
	
	def follow(timeline : Timeline) : Unit = {
	    var alreadyFollowed = for (t <- followedTimeLines; if t.owner.equals(timeline.owner)) yield t;
	    if (alreadyFollowed.isEmpty) {
	      followedTimeLines = timeline :: followedTimeLines;
	    }
	}
	
	def getWriting() : String = {
	  var writing = getWriting(ownerTimeline);
	  for(i <- 0 to followedTimeLines.size-1) {
	    var lines = followedTimeLines(i).readLines;
	    writing = writing + "\n" + getWriting(followedTimeLines(i));
	  }
	  return writing;
	}
	
	def getWriting(timeline : Timeline) : String = {
	  var postings = timeline.readLines;
	  var writing = "";
	  var newline = "";
	  for (posting<-postings) {
	    if (!writing.isEmpty()) {
	      newline = "\n";
	    }
	    writing = writing + newline + timeline.owner + " - " + posting;
	  }
	  return writing;
	}
}