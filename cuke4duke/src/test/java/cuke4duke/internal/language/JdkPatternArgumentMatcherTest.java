package cuke4duke.internal.language;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class JdkPatternArgumentMatcherTest {
    @Test
    public void shouldDealWithOnlyAscii() throws UnsupportedEncodingException {
        assertVariables("Ja (.+) elsker (.+) landet", "Ja vi elsker dette landet", "vi", 3, "dette", 13);
    }

    @Test
    public void shouldDealWithUnicodeInsideCaptures() throws UnsupportedEncodingException {
        assertVariables("Ja (.+) elsker (.+) landet", "Ja vø elsker døtte landet", "vø", 3, "døtte", 14);
    }

    @Test
    public void shouldDealWithUnicodeOutsideCaptures() throws UnsupportedEncodingException {
        assertVariables("Jæ (.+) ålsker (.+) lændet", "Jæ vi ålsker dette lændet", "vi", 4, "dette", 15);
    }

    @Test
    public void shouldDealWithUnicodeEverywhere() throws UnsupportedEncodingException {
        assertVariables("Jæ (.+) ålsker (.+) lændet", "Jæ vø ålsker døtte lændet", "vø", 4, "døtte", 16);
    }

    @Test
    public void shouldDealWithOptionalCaptureGroups() throws UnsupportedEncodingException {
        assertVariables("It should( not)? be ok (.+)", "It should be ok dude", "", -1, "dude", 16);
        assertVariables("It should( still)? be ok (.+)", "It should still be ok mate", " still", 9, "mate", 22);
    }

    private void assertVariables(String regex, String string, String v1, int pos1, String v2, int pos2) throws UnsupportedEncodingException {
        List<StepArgument> args = JdkPatternArgumentMatcher.argumentsFrom(Pattern.compile(regex), string);
        assertEquals(2, args.size());
        assertEquals(v1, args.get(0).getVal());
        assertEquals(pos1, args.get(0).getByteOffset());
        assertEquals(v2, args.get(1).getVal());
        assertEquals(pos2, args.get(1).getByteOffset());
    }
}
