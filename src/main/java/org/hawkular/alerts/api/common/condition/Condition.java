package org.hawkular.alerts.api.common.condition;

/**
 * Base class for conditions.
 */
public abstract class Condition {

    /**
     * Owning trigger
     */
    protected String triggerId;

    /**
     * e.g. 2 [conditions]
     */
    protected int conditionSetSize;

    /**
     * e.g. 1 [of 2 conditions]
     */
    protected int conditionSetIndex;

    public Condition(String triggerId, int conditionSetSize, int conditionSetIndex) {
        this.triggerId = triggerId;
        this.conditionSetSize = conditionSetSize;
        this.conditionSetIndex = conditionSetIndex;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public int getConditionSetSize() {
        return conditionSetSize;
    }

    public void setConditionSetSize(int conditionSetSize) {
        this.conditionSetSize = conditionSetSize;
    }

    public int getConditionSetIndex() {
        return conditionSetIndex;
    }

    public void setConditionSetIndex(int conditionSetIndex) {
        this.conditionSetIndex = conditionSetIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Condition)) return false;

        Condition condition = (Condition) o;

        if (conditionSetIndex != condition.conditionSetIndex) return false;
        if (conditionSetSize != condition.conditionSetSize) return false;
        if (triggerId != null ? !triggerId.equals(condition.triggerId) : condition.triggerId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = triggerId != null ? triggerId.hashCode() : 0;
        result = 31 * result + conditionSetSize;
        result = 31 * result + conditionSetIndex;
        return result;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "triggerId='" + triggerId + '\'' +
                ", conditionSetSize=" + conditionSetSize +
                ", conditionSetIndex=" + conditionSetIndex +
                '}';
    }
}
