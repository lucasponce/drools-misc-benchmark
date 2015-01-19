package org.hawkular.alerts.api.common.event;

/**
 * An external incoming event.
 */
public class Metric extends Event {

    Double value;

    public Metric(String id, Long time, Double value) {
        super(id, time);
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Metric)) return false;
        if (!super.equals(o)) return false;

        Metric metric = (Metric) o;

        if (value != null ? !value.equals(metric.value) : metric.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Metric{");
        sb.append("value=").append(value);
        sb.append(", id='").append(getId()).append('\'');
        sb.append(", time=").append(getTime());
        sb.append('}');
        return sb.toString();
    }
}
