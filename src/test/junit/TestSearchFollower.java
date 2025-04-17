package test.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.controllers.SearchFollowController;
import logic.exceptions.AddFollowerException;
import logic.exceptions.FollowRequestException;
import logic.exceptions.SystemException;

public class TestSearchFollower {
	
	/* Autor: Alessandro De Angelis - Test 1 */
	@Test
	public void testSendFollowRequestToUserNotFollowed() {
		SearchFollowController searchFollowController = new SearchFollowController();
		int result = 0;
		try {
			searchFollowController.sendFollowRequest("AlessDea", "Erik97");
			result = 1;
		} catch (SystemException | FollowRequestException | AddFollowerException e) {
			result = 0;
		}
		
		assertEquals(1, result);
	}
	
	/* Autor: Alessandro De Angelis - Test 2 */
	@Test
	public void testSendFollowRequestToUserAlreadyFollowed() {
		SearchFollowController searchFollowController = new SearchFollowController();
		int result = 0;
		try {
			searchFollowController.sendFollowRequest("Cecco", "Erik97");
			result = 1;
		} catch (SystemException | FollowRequestException | AddFollowerException e) {
			result = 0;
		}
		
		assertEquals(0, result);
	}
	
}
