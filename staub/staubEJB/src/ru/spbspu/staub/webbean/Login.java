package ru.spbspu.staub.webbean;

import javax.ejb.Local;

/**
 * TODO add descritpion
 *
 * @author Konstantin Grigoriev
 */
@Local
public interface Login {
    boolean authenticate();
}
