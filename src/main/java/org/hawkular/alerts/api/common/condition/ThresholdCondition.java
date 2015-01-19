package org.hawkular.alerts.api.common.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * It defines a threshold and type of operator.
 */
public class ThresholdCondition extends Condition {
    private static final Logger LOG = LoggerFactory.getLogger(ThresholdCondition.class);

    public enum Operator {
        LT, GT, LTE, GTE
    };

    private String metricId;
    private Operator operator;
    private Double threshold;

    /*
        JSON jackson library needs a default constructor for RESTEasy binding
     */
    public ThresholdCondition() {
       this("DefaultId", "DefaultId", 1, 1, Operator.GTE, 0d);
    }
    
    public ThresholdCondition(String triggerId, String metricId, int conditionSetSize, int conditionSetIndex, Operator operator, Double threshold) {
        super(triggerId, conditionSetSize, conditionSetIndex);
        this.metricId = metricId;
        this.operator = operator;
        this.threshold = threshold;
    }

    public String getMetricId() {
        return metricId;
    }

    public void setMetricId(String metricId) {
        this.metricId = metricId;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public String getLog(double value) {
        final StringBuilder sb = new StringBuilder();
        sb.append(triggerId).append(": ").append(" ");
        sb.append(getOperator().name()).append(" ");
        sb.append(threshold).append(" ");
        sb.append('{').append(value).append('}');
        return sb.toString();
    }

    static public boolean match(Operator operator, double threshold, double value) {
        switch (operator) {
            case LT:
                return value < threshold;
            case GT:
                return value > threshold;
            case LTE:
                return value <= threshold;
            case GTE:
                return value >= threshold;
            default:
                LOG.error("Unknown operator: " + operator.name());
                return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThresholdCondition)) return false;
        if (!super.equals(o)) return false;

        ThresholdCondition that = (ThresholdCondition) o;

        if (metricId != null ? !metricId.equals(that.metricId) : that.metricId != null) return false;
        if (operator != that.operator) return false;
        if (threshold != null ? !threshold.equals(that.threshold) : that.threshold != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (metricId != null ? metricId.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (threshold != null ? threshold.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ThresholdCondition{" +
                "metricId='" + metricId + '\'' +
                ", operator=" + operator +
                ", threshold=" + threshold +
                '}';
    }
}
