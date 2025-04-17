package test.junit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.controllers.PlanController;
import logic.exceptions.DatesException;
import logic.exceptions.TravRoomException;

public class TestPlanController {
	
	/* Autor: Valerio Crecco - Test 1 */
	@Test
	public void testValidateDatesFirstLessThanSecond() {
		PlanController planController = new PlanController();
		int result = 0;
		try {
			planController.validateDates("2021/02/12", "2021/02/15");
			result = 1;
		} catch (DatesException e) {
			result = 0;
		}
		
		assertEquals(1, result);
	}
	
	/* Autor: Valerio Crecco - Test 2 */
	@Test
	public void testValidateDatesFirstGreaterThanSecond() {
		PlanController planController = new PlanController();
		int result = 0;
		try {
			planController.validateDates("2021/02/20", "2021/02/15");
			result = 1;
		} catch (DatesException e) {
			result = 0;
		}
		
		assertEquals(0, result);
	}
	
	/* Autor: Valerio Crecco - Test 3 */
	@Test
	public void testValidateTravelersAndRoomsPositive() {
		PlanController planController = new PlanController();
		int result = 0;
		try {
			planController.validateTravelersAndRooms("5", "2");
			result = 1;
		} catch (TravRoomException e) {
			result = 0;
		}
		
		assertEquals(1, result);
	}	
	
	/* Autor: Alessandro De Angelis - Test 3 */
	@Test
	public void testValidateTravelersAndRoomsNegative() {
		PlanController planController = new PlanController();
		int result = 0;
		try {
			planController.validateTravelersAndRooms("2", "5");
			result = 1;
		} catch (TravRoomException e) {
			result = 0;
		}
		
		assertEquals(0, result);
	}	
	
}
