package io.opentelemetry.kotlin.detekt

import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider

class DiscouragedImportRuleSetProvider : RuleSetProvider {

    override fun instance(config: Config): RuleSet = RuleSet(
        id = "discouraged-import",
        rules = listOf(DiscouragedImportRule(config))
    )

    override val ruleSetId: String = "custom-rules"
}
