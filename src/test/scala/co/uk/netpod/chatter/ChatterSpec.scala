/**
 *
 */
package co.uk.netpod.chatter

import org.scalatest._
import co.uk.netpod._

/**
 * @author michaeldecourci
 *
 */
class ChatterSpec extends FeatureSpec with GivenWhenThen with BeforeAndAfter {
	
	var application: ChatterApplication = _;
	
	before {
		application = new ChatterApplication();
	}
	
	def delay(m : Long) = {
		Thread.sleep(m*1000)
	}
	
	feature("a console-based social networking application") {
	  
	  info("As a user")
	  info("I want to be able to publish messages to a personal timeline")
	  info("So that others can see my published messages")
	  
	  scenario("publish messages to a personal timeline") {
	    
	  	given("social networking application")
	  	when("when a user posts a message")
	  	application.post("Alice", "I love the weather today")
	  	application.post("Bob", "Oh, we lost!")
	  	application.post("Bob", "at least it's sunny")
	  	
	  	then("the the message is put on a timeline of that user")
	  	var t = application.getTimeline("Alice")
	  	var msg = t.getMsg(0)
	  	assert(msg === "I love the weather today");

	  	t = application.getTimeline("Bob")
	  	msg = t.getMsg(0)
	  	assert(msg === "Oh, we lost!");
	  	msg = t.getMsg(1)
	  	assert(msg === "at least it's sunny");
	  }
	  
	  scenario("View other user's timeline") {
	    given("a user timeline")
	    when("reading")
	    then("the message postings must be viewed in order of postings")
	  	
	    application.post("Alice", "I love the weather today")
	  	delay(5)
	  	application.post("Bob", "Oh, we lost!")
	  	delay(1)
	  	application.post("Bob", "at least it's sunny")
	  	delay(1)

	  	and("the time shown as a duration from now")
	    var msg = application.read("Alice")
	  	assert(msg === "I love the weather today (7 seconds ago)");

	    msg = application.read("Bob")
	  	assert(msg === "Oh, we lost! (2 seconds ago)\nat least it's sunny (1 second ago)");
	  }

	  scenario("Follow other user timelines, and view an aggregated list(wall) of all subscriptions") {
	    given("a user wall (an aggregated list of all followers postings)")
	    application.post("Alice", "I love the weather today")
	  	delay(5)
	  	application.post("Bob", "Oh, we lost!")
	  	delay(1)
	  	application.post("Bob", "at least it's sunny")
	  	delay(1)
	  	application.post("Charlie", "I'm in New York today! Anyone wants to have a coffee?")
	  	delay(1)

	  	when("following other users")
	  	application.follow("Alice", "Charlie")
	    
	    then("show all followed user postings on my wall")
	    and("in order of postings")
	    var wall = application.getWall("Charlie")
	  	assert(wall === "Charlie - I'm in New York today! Anyone wants to have a coffee? (1 second ago)\nAlice - I love the weather today (8 seconds ago)");
	    
	    and("the time shown as a duration from now")
	  	application.follow("Bob", "Charlie")
	    wall = application.getWall("Charlie")
	  	assert(wall === "Charlie - I'm in New York today! Anyone wants to have a coffee? (1 second ago)\nBob - Oh, we lost! (3 seconds ago)\nBob - at least it's sunny (2 seconds ago)\nAlice - I love the weather today (8 seconds ago)");

	  }
	}
}