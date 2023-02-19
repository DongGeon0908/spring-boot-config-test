package com.goofy.springbootconfiguration

import mu.KotlinLogging
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringBootConfigurationApplication {
    private val logger = KotlinLogging.logger {}


    /**
     * condition matching이 완료된 것들에 대한 로깅 진행
     *
     * - spring default
     * - spring web
     * - spring data
     */
    @Bean
    fun getConditionInfos(conditionEvaluationReport: ConditionEvaluationReport): ApplicationRunner {
        return ApplicationRunner {
            conditionEvaluationReport.conditionAndOutcomesBySource.entries
                .filter { co -> co.value.isFullMatch }
                .filter { co -> co.key.indexOf("Jmx") < 0 }
                .forEach {
                    logger.info { "------------------" + it.key }
                    it.value.forEach { report -> logger.info { report.outcome } }
                    logger.info { "-------------------" }
                }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SpringBootConfigurationApplication>(*args)
}
