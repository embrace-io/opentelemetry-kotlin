package io.embrace.opentelemetry.kotlin.detekt

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtImportDirective

/**
 * Detects imports that are discouraged from use in the project. It's possible to configure an
 * allowList of sourceFiles that can be skipped from this detection.
 */
class DiscouragedImportRule(config: Config) : Rule(config) {

    override val issue: Issue = Issue(
        id = "DiscouragedImport",
        severity = Severity.CodeSmell,
        description = "Detects usage of discouraged imports unless explicitly allowed by file path.",
        debt = Debt.FIVE_MINS
    )

    private val restrictedPackages = valueOrDefault("restrictedPackages", emptyList<String>())
    private val allowedFileIds = valueOrDefault("allowedFileIds", emptyList<String>())
    private lateinit var fileId: String

    override fun preVisit(root: KtFile) {
        val relativeName = root.name.substringAfterLast("/").removeSuffix(".kt")
        fileId = "${root.packageFqName.asString()}.$relativeName"
        super.preVisit(root)
    }

    override fun visitImportDirective(importDirective: KtImportDirective) {
        super.visitImportDirective(importDirective)
        if (fileId in allowedFileIds) {
            return
        }

        val import = importDirective.importPath?.pathStr ?: return

        if (restrictedPackages.any(import::startsWith)) {
            report(
                CodeSmell(
                    issue,
                    Entity.from(importDirective),
                    message = "Import from discouraged package '$import' is not allowed."
                )
            )
        }
    }
}
