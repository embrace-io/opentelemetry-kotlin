package io.opentelemetry.kotlin.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Finding
import io.gitlab.arturbosch.detekt.test.TestConfig
import io.gitlab.arturbosch.detekt.test.lint
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.Test

class DiscouragedImportRuleTest {

    @Test
    fun `test default`() {
        val rule = DiscouragedImportRule(Config.empty)
        val code = readKotlinCodeFixture()
        val findings = rule.lint(code)
        assertTrue(findings.isEmpty())
    }

    @Test
    fun `test forbidden import`() {
        val config = TestConfig(
            Pair("restrictedPackages", listOf("com.example")),
        )
        val rule = DiscouragedImportRule(config)
        val code = readKotlinCodeFixture()
        val findings = rule.lint(code)
        assertDiscouragedImportFound(findings)
    }

    @Test
    fun `test forbidden import, allowed class`() {
        val config = TestConfig(
            Pair("restrictedPackages", listOf("com.example")),
            Pair("allowedFileIds", listOf("io.embrace.opentelemetry.kotlin.Test")),
        )
        val rule = DiscouragedImportRule(config)
        val code = readKotlinCodeFixture()
        val findings = rule.lint(code)
        assertTrue(findings.isEmpty())
    }

    @Test
    fun `test forbidden import, disallowed class`() {
        val config = TestConfig(
            Pair("restrictedPackages", listOf("com.example")),
            Pair("allowedFileIds", listOf("io.embrace.opentelemetry.kotlin.SomeOtherTest")),
        )
        val rule = DiscouragedImportRule(config)
        val code = readKotlinCodeFixture()
        val findings = rule.lint(code)
        assertDiscouragedImportFound(findings)
    }

    private fun assertDiscouragedImportFound(findings: List<Finding>) {
        val finding = findings.single()
        assertEquals("DiscouragedImport", finding.id)
        assertEquals(
            "Import from discouraged package 'com.example.discouraged.B' is not allowed.",
            finding.message
        )
    }

    private fun readKotlinCodeFixture(): String {
        val classLoader = DiscouragedImportRule::class.java.classLoader
        val res = checkNotNull(classLoader.getResource("DiscouragedImport.kt"))
        return res.readText()
    }
}
