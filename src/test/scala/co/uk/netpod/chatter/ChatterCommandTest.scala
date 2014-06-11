package co.uk.netpod.chatter

import org.scalatest._
import org.scalamock.scalatest._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.ArgumentCaptor

class ChatterCommandSpec extends FunSuite with BeforeAndAfterEach with MockitoSugar {
  
	var mockCollaborator : ChatterApplication = _;

	override def beforeEach() : Unit = {
      mockCollaborator = mock[ChatterApplication];
      ChatCommand.application = mockCollaborator;
	}

	override def afterEach() : Unit = {
  	  reset(mockCollaborator);
	}
	
    test("A user posts with the postings command") {

      ChatCommand.processInput("Alice -> I love the weather today");
  
  	  val capturedString1 = ArgumentCaptor.forClass(classOf[String]);
  	  val capturedString2 = ArgumentCaptor.forClass(classOf[String]);
  	  verify(mockCollaborator).post(capturedString1.capture, capturedString2.capture);
	  
  	  assert(capturedString1.getValue() === "Alice");
  	  assert(capturedString2.getValue() === "I love the weather today");
   }
  	
    test("A user reads postings with the read command") {
      
  	  val capturedString = ArgumentCaptor.forClass(classOf[String]);
      when(mockCollaborator.read(capturedString.capture)).thenReturn("Test Read");

      ChatCommand.processInput("Alice");
	  
  	  assert(capturedString.getValue() === "Alice");
  	}
  	
    test("A user follows postings with the following command") {

      ChatCommand.processInput("Charlie follows Bob");

      val capturedFollowed = ArgumentCaptor.forClass(classOf[String]);
  	  val capturedFollowedBy = ArgumentCaptor.forClass(classOf[String]);
  	  verify(mockCollaborator).follow(capturedFollowed.capture, capturedFollowedBy.capture);
	  
  	  assert(capturedFollowed.getValue() === "Bob");
  	  assert(capturedFollowedBy.getValue() === "Charlie");
  	}
    
    test("A user sees their wall with the wall command") {

  	  val capturedString = ArgumentCaptor.forClass(classOf[String]);
      when(mockCollaborator.getWall(capturedString.capture)).thenReturn("Test Read");

      ChatCommand.processInput("Charlie wall");

      assert(capturedString.getValue() === "Charlie");
  	}
}