
package groupx.hw4.chordfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import groupx.hw4.chordfinder.service.ChordFinderService;

public class ChordFinderServiceTest {

	@Test
	public void testFindChords_C() {
		ChordFinderService service = new ChordFinderService();
		assertEquals(toAnswerList("C maj"), service.findChords(List.of("C", "E", "G")));
	}

	@Test
	public void testFindChords_Cmaj7() {
		ChordFinderService service = new ChordFinderService();
		assertEquals(toAnswerList("C maj7"), service.findChords(List.of("C", "E", "G", "B")));
	}

	@Test
	public void testFindChords_C7() {
		ChordFinderService service = new ChordFinderService();
		assertEquals(toAnswerList("C 7"), service.findChords(List.of("C", "E", "G", "Bb")));
	}

	@Test
	public void testFindChords_Caug() {
		ChordFinderService service = new ChordFinderService();
		assertEquals(toAnswerList("C 7"), service.findChords(List.of("C", "E", "G", "Bb")));
	}

	@Test
	public void testFindChords_Cminb6() {
		ChordFinderService service = new ChordFinderService();
		assertEquals(toAnswerList("C minb6"), service.findChords(List.of("C", "Eb", "G", "Ab")));
	}

	private Set<String> toAnswerList(String... chordNames) {
		return Arrays.stream(chordNames).collect(Collectors.toSet());
	}

}
