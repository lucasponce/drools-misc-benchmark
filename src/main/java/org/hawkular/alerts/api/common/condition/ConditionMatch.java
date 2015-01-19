package org.hawkular.alerts.api.common.condition;

/**
 * It represents a matching state of several conditions
 */
public class ConditionMatch extends Condition {

    private String log;
    private Long time;

    public ConditionMatch(Condition condition, String log) {
        this(condition.getTriggerId(), condition.getConditionSetSize(), condition.getConditionSetIndex(), log);
    }

    public ConditionMatch(String triggerId, int conditionSetSize, int conditionSetIndex, String log) {
        super(triggerId, conditionSetSize, conditionSetIndex);
        this.log = log;
        this.time = System.currentTimeMillis();
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConditionMatch)) return false;
        if (!super.equals(o)) return false;

        ConditionMatch that = (ConditionMatch) o;

        if (log != null ? !log.equals(that.log) : that.log != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (log != null ? log.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConditionMatch{" +
                "log='" + log + '\'' +
                ", time=" + time +
                '}';
    }
}
