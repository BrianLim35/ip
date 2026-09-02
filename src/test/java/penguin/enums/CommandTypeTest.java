package penguin.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandTypeTest {
    @Test
    void commandType_fromKeyword_validKeyword_returnsMatchingType() {
        assertEquals(CommandType.FIND, CommandType.fromKeyword("find"));
    }

    @Test
    void commandType_fromKeyword_unknownKeyword_returnsNull() {
        assertNull(CommandType.fromKeyword("unknown"));
    }

    @Test
    void commandType_getKeyword_validType_returnsKeyword() {
        assertEquals("deadline", CommandType.DEADLINE.getKeyword());
    }
}
