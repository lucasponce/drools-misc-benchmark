package org.hawkular.alerts.cep;

import java.util.Collection;

/**
 * Interface that defines main API between AlertsService and CEP engine implementation.
 */
public interface CepEngine {

    void addRule(String id, String rule);

    void removeRule(String id);

    void addGlobal(String name, Object global);

    void addFact(Object fact);

    void addFacts(Collection facts);

    void removeFact(Object fact);

    void updateFact(Object fact);
    
    Collection getFacts(Object filter);

    void fire();

    void clear();

    void reset();
}
