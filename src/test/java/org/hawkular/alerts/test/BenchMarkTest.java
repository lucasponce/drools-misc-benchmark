/*
 * Copyright 2014-2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.alerts.test;

import org.hawkular.alerts.api.common.condition.ConditionMatch;
import org.hawkular.alerts.api.common.condition.ThresholdCondition;
import org.hawkular.alerts.api.common.event.Metric;
import org.hawkular.alerts.api.common.trigger.Trigger;
import org.hawkular.alerts.cep.CepEngine;
import org.hawkular.alerts.impl.BasicCepEngineImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author lponce
 */
public class BenchMarkTest {

    @Test
    public void checkFiles() {

        ReadFileRules initRules = new ReadFileRules();

        String resourcesPath = this.getClass().getClassLoader().getResource(".").getPath();

        initRules.initManualRules(resourcesPath);

        Assert.assertFalse(initRules.getManualRules().isEmpty());
        Assert.assertFalse(initRules.getManualThresholds().isEmpty());
        Assert.assertFalse(initRules.getManualTriggers().isEmpty());

        initRules.initGeneratedRules(resourcesPath);

        Assert.assertFalse(initRules.getGeneratedRules().isEmpty());
    }

    @Test
    public void testManualRules() {

        String resourcesPath = this.getClass().getClassLoader().getResource(".").getPath();

        ReadFileRules initRules = new ReadFileRules();
        initRules.initManualRules(resourcesPath);

        List alerts = new ArrayList();

        BasicCepEngineImpl cep = new BasicCepEngineImpl();
        cep.init();

        Map<String, String> rules = initRules.getManualRules();
        for (String ruleId : rules.keySet()) {
            cep.addRule(ruleId, rules.get(ruleId));
        }

        Collection<ThresholdCondition> thresholds = initRules.getManualThresholds();
        cep.addFacts(thresholds);

        Collection<Trigger> triggers = initRules.getManualTriggers();
        cep.addFacts(triggers);

        cep.addGlobal("alerts", alerts);

        long startTime = System.currentTimeMillis();

        int MAX_METRICS = 10000;

        Trigger triggerFilter = new Trigger();
        Metric metricFilter = new Metric("dummy", 0l, 0d);
        ConditionMatch conditionFilter = new ConditionMatch("", 0, 0, "");

        for (int i = 0; i < MAX_METRICS; i++) {
            long random = (Math.round(Math.random() * 100) % 2) + 1;
            double value = Math.random() * 25;
            String name = "Metric-0" + random;
            Metric metric = new Metric(name, System.currentTimeMillis(), value);
            System.out.println(metric);
            cep.addFact(metric);

            cep.fire();

            Collection<Trigger> facts = cep.getFacts(triggerFilter);
            boolean allTriggers = true;
            for (Trigger trigger : facts) {
                if (!trigger.isActive()) {
                    trigger.setActive(true);
                    cep.updateFact(trigger);
                } else {
                    allTriggers = false;
                }
            }
            /*
                For the test if all Triggers are disabled, we will clean Metrics
             */
            if (allTriggers) {
                Collection<Metric> metrics = cep.getFacts(metricFilter);
                for (Metric m : metrics) {
                    cep.removeFact(m);
                }
                Collection<ConditionMatch> conditions = cep.getFacts(conditionFilter);
                for (ConditionMatch  c : conditions) {
                    cep.removeFact(c);
                }
            }
        }

        long finishTime = System.currentTimeMillis();

        System.out.println("TOTAL metrics: " + MAX_METRICS + " took " + (finishTime - startTime) + " ms");
    }

    @Test
    public void testGeneratedRules() {

        String resourcesPath = this.getClass().getClassLoader().getResource(".").getPath();

        ReadFileRules initRules = new ReadFileRules();
        initRules.initGeneratedRules(resourcesPath);

        List alerts = new ArrayList();

        BasicCepEngineImpl cep = new BasicCepEngineImpl();
        cep.init();

        Map<String, String> rules = initRules.getGeneratedRules();
        for (String ruleId : rules.keySet()) {
            cep.addRule(ruleId, rules.get(ruleId));
        }

        Collection<Trigger> triggers = initRules.getGeneratedTriggers();
        cep.addFacts(triggers);

        cep.addGlobal("alerts", alerts);

        long startTime = System.currentTimeMillis();

        int MAX_METRICS = 10000;

        Trigger triggerFilter = new Trigger();
        Metric metricFilter = new Metric("dummy", 0l, 0d);
        ConditionMatch conditionFilter = new ConditionMatch("", 0, 0, "");

        for (int i = 0; i < MAX_METRICS; i++) {
            long random = (Math.round(Math.random() * 100) % 2) + 1;
            double value = Math.random() * 25;
            String name = "Metric-0" + random;
            Metric metric = new Metric(name, System.currentTimeMillis(), value);
            System.out.println(metric);
            cep.addFact(metric);

            cep.fire();

            Collection<Trigger> facts = cep.getFacts(triggerFilter);
            boolean allTriggers = true;
            for (Trigger trigger : facts) {
                if (!trigger.isActive()) {
                    trigger.setActive(true);
                    cep.updateFact(trigger);
                } else {
                    allTriggers = false;
                }
            }
            /*
                For the test if all Triggers are disabled, we will clean Metrics
             */
            if (allTriggers) {
                Collection<Metric> metrics = cep.getFacts(metricFilter);
                for (Metric m : metrics) {
                    cep.removeFact(m);
                }
                Collection<ConditionMatch> conditions = cep.getFacts(conditionFilter);
                for (ConditionMatch  c : conditions) {
                    cep.removeFact(c);
                }
            }
        }

        long finishTime = System.currentTimeMillis();

        System.out.println("TOTAL metrics: " + MAX_METRICS + " took " + (finishTime - startTime) + " ms");
    }

}
