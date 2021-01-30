package de.uni_hannover.cah.observer;

/**
 * used for client and server
 */
public interface SimpleObservable {

   void setObserver(SimpleObserver observer);

    void removeObserver();

}
