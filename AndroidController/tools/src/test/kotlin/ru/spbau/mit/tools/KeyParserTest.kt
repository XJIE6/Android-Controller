package ru.spbau.mit.tools

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import org.junit.Test
import kotlin.test.assertEquals

class KeyParserTest {
    private val parser = KeyParser()
    @Test
    fun getGrammar() {
        assertEquals(parser.grammar.parseToEnd("k49"), Key(49))
        assertEquals(parser.grammar.parseToEnd("[k50 [p61 k49 r61] 7 k77] 11"),
                Repeat(11, listOf(
                        Key(50),
                        Repeat(7, listOf(
                                Press(61),
                                Key(49),
                                Release(61)
                        )),
                        Key(77)
                )))
        assertEquals(parser.grammar.parseToEnd("[k50[  p61   k49    r61     ]7   k77  ]   11"),
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
}