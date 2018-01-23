package ru.spbau.mit.tools.lang

import org.junit.Test
import kotlin.test.assertEquals

class KeyParserTest {
    private val parser = KeyParser()
    @Test
    fun basicGrammarTest() {
        assertEquals(parser.parse("49"), Repeat(1, listOf(Key(49))))
        assertEquals(parser.parse("[50 [+61 49 -61] 7 77] 11"),
                Repeat(11, listOf(
                        Key(50),
                        Repeat(7, listOf(
                                Press(61),
                                Key(49),
                                Release(61)
                        )),
                        Key(77)
                )))
        assertEquals(parser.parse("[50[  +61   49    -61     ]7   77  ]   11"),
                Repeat(11, listOf(
                        Key(50),
                        Repeat(7, listOf(
                                Press(61),
                                Key(49),
                                Release(61)
                        )),
                        Key(77)
                )))
    }
    @Test
    fun syntaxSugarTest() {
        assertEquals(parser.parse("k"), Repeat(1, listOf(Key(75))))
        assertEquals(parser.parse("+S h -S e l l o"), Repeat(1, listOf(
                Press(16),
                Key(72),
                Release(16),
                Key(69),
                Key(76),
                Key(76),
                Key(79))))
    }
}