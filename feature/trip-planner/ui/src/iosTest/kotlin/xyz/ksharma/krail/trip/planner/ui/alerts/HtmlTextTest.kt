package xyz.ksharma.krail.trip.planner.ui.alerts

import kotlin.test.Test
import kotlin.test.assertEquals

class HtmlTextTest {

    @Test
    fun testReplaceHtmlTags_withDiv() {
        val input = "Hello</div>World"
        val expected = "Hello\nWorld"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withLi() {
        val input = "Item 1</li>Item 2"
        val expected = "Item 1\nItem 2"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withUl() {
        val input = "<ul>Item 1</ul>"
        val expected = "Item 1"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withAnchor() {
        val input = "<a href=\"http://example.com\">Link</a>"
        val expected = "Link"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withNbsp() {
        val input = "Hello&nbsp;World"
        val expected = "Hello World"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withMultipleHtmlTags() {
        val input = "<div>Hello</div><ul>Item 1</ul><li>Item 2</li>"
        val expected = "Hello\nItem 1\nItem 2"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withMultipleNewLines() {
        val input = "Hello\n\n\nWorld"
        val expected = "Hello\n\nWorld"
        assertEquals(expected, replaceHtmlTags(input))
    }

    @Test
    fun testReplaceHtmlTags_withComplexHtml() {
        val input = "<div>Hello</div><a href=\"http://example.com\">Link</a>&nbsp;World"
        val expected = "Hello\n Link World"
        assertEquals(expected, replaceHtmlTags(input))
    }
}
