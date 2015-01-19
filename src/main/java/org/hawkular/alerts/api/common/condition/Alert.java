package org.hawkular.alerts.api.common.condition;

import java.util.HashSet;
import java.util.Set;

/**
 * An alert representation thrown from AlertsService
 */
public class Alert {

    private String triggerId;
    private Set<ConditionMatch> matches;
    private long time;

    public Alert(String triggerId) {
        this.triggerId = triggerId;
        this.matches = new HashSet();
        this.time = System.currentTimeMillis();
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public Set<ConditionMatch> getMatches() {
        return matches;
    }

    public void setMatches(Set<ConditionMatch> matches) {
        this.matches = matches;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void addConditionMatch(ConditionMatch conditionMatch) {
        if (conditionMatch == null) {
            throw new IllegalArgumentException("ConditionMatch must be non-empty.");
        }
        matches.add(conditionMatch);
    }

    public void addConditionMatches(Set<ConditionMatch> conditionMatches) {
        if (conditionMatches == null) {
            throw new IllegalArgumentException("ConditionMatch must be non-empty.");
        }
        matches.addAll(conditionMatches);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert)) return false;

        Alert alert = (Alert) o;

        if (time != alert.time) return false;
        if (matches != null ? !matches.equals(alert.matches) : alert.matches != null) return false;
        if (triggerId != null ? !triggerId.equals(alert.triggerId) : alert.triggerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = triggerId != null ? triggerId.hashCode() : 0;
        result = 31 * result + (matches != null ? matches.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "triggerId='" + triggerId + '\'' +
                ", matches=" + matches +
                ", time=" + time +
                '}';
    }
}
