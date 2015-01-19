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

import org.hawkular.alerts.api.common.condition.ThresholdCondition;
import org.hawkular.alerts.api.common.trigger.Trigger;
import org.hawkular.alerts.api.common.trigger.TriggerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author lponce
 */
public class ReadFileRules {
    private static final Logger LOG = LoggerFactory.getLogger(ReadFileRules.class);
    private static final String GENERATED = "generated";
    private static final String MANUAL = "manual";

    private Map<String, String> generatedRules = new HashMap<>();
    private Map<String, Trigger> generatedTriggers = new HashMap();

    private Map<String, Trigger> manualTriggers = new HashMap();
    private Map<ThresholdKey, ThresholdCondition> manualThresholds = new HashMap();
    private Map<String, String> manualRules = new HashMap<>();

    public void initGeneratedRules(String basePath) {

        if (basePath == null) {
            LOG.error("basePath must not be null");
            return;
        }

        File initFolder = new File(basePath, GENERATED);


        /*
            Rules initialization
         */
        File rules = new File(initFolder, "rules");
        if (rules.exists() && rules.isDirectory()) {
            for (File rule : rules.listFiles()) {
                String ruleId = rule.getName();
                try {
                    String ruleContent = new String(Files.readAllBytes(Paths.get(rule.toURI())), "UTF-8");
                    generatedRules.put(ruleId, ruleContent);
                } catch (IOException e) {
                    LOG.error(e.toString(), e);
                }
            }
        } else {
            LOG.error("rules folder not found. Skipping rules initialization.");
        }

        /*
            Triggers
         */
        File triggers = new File(initFolder, "triggers.txt");
        if (triggers.exists() && triggers.isFile()) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(Paths.get(triggers.toURI()), Charset.forName("UTF-8"));
            } catch (IOException e) {
                LOG.error(e.toString(), e);
            }
            if (lines != null && !lines.isEmpty()) {
                for (String line : lines) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        String triggerId = fields[0];
                        boolean active = new Boolean(fields[1]).booleanValue();
                        String name = fields[2];
                        String description = fields[3];
                        TriggerTemplate.Match match = TriggerTemplate.Match.valueOf(fields[4]);
                        String[] notifiers = fields[5].split("\\|");

                        Trigger trigger = new Trigger(triggerId, name, active);
                        trigger.setDescription(description);
                        trigger.setMatch(match);
                        for (String notifier : notifiers) {
                            trigger.addNotifier(notifier);
                        }
                        generatedTriggers.put(trigger.getId(), trigger);
                    }
                }
            }
        } else {
            LOG.error("triggers.txt file not found. Skipping triggers initialization.");
        }

    }

    public void initManualRules(String basePath) {

        if (basePath == null) {
            LOG.error("basePath must not be null");
            return;
        }

        File initFolder = new File(basePath, MANUAL);

        /*
            Rules initialization
         */
        File rules = new File(initFolder, "rules");
        if (rules.exists() && rules.isDirectory()) {
            for (File rule : rules.listFiles()) {
                String ruleId = rule.getName();
                try {
                    String ruleContent = new String(Files.readAllBytes(Paths.get(rule.toURI())), "UTF-8");
                    manualRules.put(ruleId, ruleContent);
                } catch (IOException e) {
                    LOG.error(e.toString(), e);
                }
            }
        } else {
            LOG.error("rules folder not found. Skipping rules initialization.");
        }

        /*
            Triggers
         */
        File triggers = new File(initFolder, "triggers.txt");
        if (triggers.exists() && triggers.isFile()) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(Paths.get(triggers.toURI()), Charset.forName("UTF-8"));
            } catch (IOException e) {
                LOG.error(e.toString(), e);
            }
            if (lines != null && !lines.isEmpty()) {
                for (String line : lines) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        String triggerId = fields[0];
                        boolean active = new Boolean(fields[1]).booleanValue();
                        String name = fields[2];
                        String description = fields[3];
                        TriggerTemplate.Match match = TriggerTemplate.Match.valueOf(fields[4]);
                        String[] notifiers = fields[5].split("\\|");

                        Trigger trigger = new Trigger(triggerId, name, active);
                        trigger.setDescription(description);
                        trigger.setMatch(match);
                        for (String notifier : notifiers) {
                            trigger.addNotifier(notifier);
                        }
                        manualTriggers.put(trigger.getId(), trigger);
                    }
                }
            }
        } else {
            LOG.error("triggers.txt file not found. Skipping triggers initialization.");
        }

        /*
            Thresholds
         */
        File thresholds = new File(initFolder, "thresholds.txt");
        if (thresholds.exists() && thresholds.isFile()) {
            List<String> lines = null;
            try {
                lines = Files.readAllLines(Paths.get(thresholds.toURI()), Charset.forName("UTF-8"));
            } catch (IOException e) {
                LOG.error(e.toString(), e);
            }
            if (lines != null && !lines.isEmpty()) {
                for (String line : lines) {
                    if (line.startsWith("#")) {
                        continue;
                    }
                    String[] fields = line.split(",");
                    if (fields.length == 6) {
                        String triggerId = fields[0];
                        String metricId = fields[1];
                        int conditionSetSize = new Integer(fields[2]).intValue();
                        int conditionSetIndex = new Integer(fields[3]).intValue();
                        ThresholdCondition.Operator operator = ThresholdCondition.Operator.valueOf(fields[4]);
                        Double value = new Double(fields[5]).doubleValue();

                        ThresholdCondition threshold = new ThresholdCondition(triggerId, metricId, conditionSetSize,
                                conditionSetIndex, operator, value);
                        addThreshold(threshold);
                    }
                }
            }
        } else {
            LOG.error("thresholds.txt file not found. Skipping thresholds initialization.");
        }
    }

    private void addThreshold(ThresholdCondition threshold) {
        if (threshold == null
                || threshold.getTriggerId() == null
                || threshold.getMetricId() == null) {
            throw new IllegalArgumentException("Threshold must not be null");
        }
        ThresholdKey key = new ThresholdKey();
        key.triggerId = threshold.getTriggerId();
        key.metricId = threshold.getMetricId();
        manualThresholds.put(key, threshold);
    }

    public Collection<Trigger> getManualTriggers() {
        return Collections.unmodifiableMap(manualTriggers).values();
    }

    public Collection<ThresholdCondition> getManualThresholds() {
        return Collections.unmodifiableMap(manualThresholds).values();
    }

    public Map<String, String> getManualRules() {
        return Collections.unmodifiableMap(manualRules);
    }

    public Map<String, String> getGeneratedRules() {
        return Collections.unmodifiableMap(generatedRules);
    }

    public Collection<Trigger> getGeneratedTriggers() {
        return Collections.unmodifiableMap(generatedTriggers).values();
    }

    public class ThresholdKey {
        public String triggerId;
        public String metricId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ThresholdKey)) return false;

            ThresholdKey that = (ThresholdKey) o;

            if (metricId != null ? !metricId.equals(that.metricId) : that.metricId != null) return false;
            if (triggerId != null ? !triggerId.equals(that.triggerId) : that.triggerId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = triggerId != null ? triggerId.hashCode() : 0;
            result = 31 * result + (metricId != null ? metricId.hashCode() : 0);
            return result;
        }
    }

}
