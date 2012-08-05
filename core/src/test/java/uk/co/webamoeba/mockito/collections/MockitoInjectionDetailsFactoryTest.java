package uk.co.webamoeba.mockito.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

/**
 * @author James Kennard
 */
public class MockitoInjectionDetailsFactoryTest {

    private MockitoInjectionDetailsFactory factory = new MockitoInjectionDetailsFactory();

    @Test
    public void shouldCreateInjectionDetails() {
	// Given
	ClassWithAnnnotations object = new ClassWithAnnnotations();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(1, injectionDetails.getInjectees().size());
	assertSame(object.injectee, injectionDetails.getInjectees().iterator().next());
	assertEquals(1, injectionDetails.getInjectables().size());
	assertSame(object.injectable, injectionDetails.getInjectables().iterator().next());
    }

    @Test
    public void shouldCreateInjectionDetailsGivenInheritance() {
	// Given
	ExtendedClassWithAnnnotations object = new ExtendedClassWithAnnnotations();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(1, injectionDetails.getInjectees().size());
	assertSame(((ClassWithAnnnotations) object).injectee, injectionDetails.getInjectees().iterator().next());
	assertEquals(2, injectionDetails.getInjectables().size());
	assertTrue(injectionDetails.getInjectables().contains(object.extendedInjectable));
	assertTrue(injectionDetails.getInjectables().contains(object.injectable));
    }

    @Test
    public void shouldCreateInjectionDetailsGivenMultipleInjectMocksAnnotations() {
	// Given
	ClassWithTwoInjectMocksAnnnotation object = new ClassWithTwoInjectMocksAnnnotation();

	// When
	InjectionDetails injectionDetails = factory.createInjectionDetails(object);

	// Then
	assertEquals(2, injectionDetails.getInjectees().size());
	assertTrue(injectionDetails.getInjectees().contains(object.injectable1));
	assertTrue(injectionDetails.getInjectees().contains(object.injectable2));
    }

    private class ClassWithAnnnotations {

	@InjectMocks
	private OutputStream injectee = new ByteArrayOutputStream();

	@Mock
	protected InputStream injectable = mock(InputStream.class);
    }

    private class ClassWithTwoInjectMocksAnnnotation {

	@InjectMocks
	OutputStream injectable1 = new ByteArrayOutputStream();

	@InjectMocks
	public InputStream injectable2 = new ByteArrayInputStream("".getBytes());
    }

    private class ExtendedClassWithAnnnotations extends ClassWithAnnnotations {

	@Mock
	private OutputStream extendedInjectable = mock(OutputStream.class);
    }
}
