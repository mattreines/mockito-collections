package uk.co.webamoeba.mockito.collections.sample;

import static org.junit.Assert.assertArrayEquals;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.webamoeba.mockito.collections.MockitoCollections;

/**
 * This test shows how you can use Mockito Collections to deal simply with a {@link Set} of delegates that return
 * values using spies. Comments are included throughout the file to help clarify what's going on.
 *  
 * @author Matt Reines
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HandlerManagerAlternativeSpyTest {
	// HandlerManager containing a Set of Handlers
	@InjectMocks
	private HandlerManager manager;

	// Spy to be injected
	// Name means it will be always first even though it is to be injected into a Set (alphabetical)
	@Spy
	private MockHandler handler1;

	// Another Spy to be injected
	// Name means it will always be second even though it is to be injected into a Set (alphabetical)
	@Spy
	private MockHandler handler2;

	// Setup making use of Mockito Collections for injection of handlers
	@Before
	public void before() {
		MockitoCollections.initialise(this, true);
	}

	@Test
	public void shouldCallAllHandlers() {
		// Given
		String thingToHandle = "Something";

		// When
		String[] strings = manager.handle(thingToHandle);

		// Then
		// Normal use of assert
		// Is array (order is important) but we can guarantee order of Set of Handlers using Mockito Collections
		// No need to verify see http://monkeyisland.pl/2008/04/26/asking-and-telling/
		assertArrayEquals(new String[] { "value1", "value1" }, strings);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailToHandleGivenNull() {
		// Given
		String thingToHandle = null;

		// When
		manager.handle(thingToHandle);

		// Then
		// exception thrown
	}

}
