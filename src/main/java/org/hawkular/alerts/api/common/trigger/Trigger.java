package org.hawkular.alerts.api.common.trigger;

/**
 * It defines conditions and notifiers attached that will create an alert.
 */
public class Trigger extends TriggerTemplate {

    /**
     * Id must be unique
     */
    private String id;
    private boolean active;

    /*
        JSON jackson library needs a default constructor for RESTEasy binding
     */
    public Trigger() {
        this("DefaultId", null);
    }
    
    public Trigger(String id, String name) {
        super(name);

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be non-empty.");
        }
        this.id = id;
        this.active = true;
    }

    public Trigger(String id, String name, boolean active) {
        super(name);

        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Trigger id must be non-empty.");
        }
        this.id = id;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trigger)) return false;
        if (!super.equals(o)) return false;

        Trigger trigger = (Trigger) o;

        if (active != trigger.active) return false;
        if (id != null ? !id.equals(trigger.id) : trigger.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trigger{" +
                "id='" + id + '\'' +
                ", active=" + active +
                '}';
    }
}
