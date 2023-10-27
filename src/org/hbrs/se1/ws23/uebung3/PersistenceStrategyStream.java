package org.hbrs.se1.ws23.uebung3;

import org.hbrs.se1.ws23.uebung2.Member;

import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    //lol.java
    // URL of file, in which the objects are stored
    private String location = "objects.ser";
    FileOutputStream fos;
    ObjectOutputStream oos;
    FileInputStream fis;
    ObjectInputStream ois;

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for opening the connection to a stream (here: Input- and Output-Stream)
     * In case of having problems while opening the streams, leave the code in methods load
     * and save.
     */
    public void openConnection() throws PersistenceException {
        openConnectionOut();
        openConnectionIn();
    }
    @Override
    public void closeConnection() throws PersistenceException {
        closeConnectionIn();
        closeConnectionOut();
    }

    public void openConnectionOut() throws PersistenceException {
        try {
            fos = new FileOutputStream(location);
            oos = new ObjectOutputStream(fos);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"uff");
        }
    }

    public void closeConnectionOut() throws PersistenceException {
        try {
            oos.close();
            fos.close();
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"uff");
        }
    }

    public void openConnectionIn() throws PersistenceException {
        try {
            fis = new FileInputStream(location);
            ois = new ObjectInputStream(fis);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"uff");
        }
    }

    public void closeConnectionIn() throws PersistenceException {
        try {
            ois.close();
            fis.close();
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"uff");
        }
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     */
    public void save(List<E> member) throws PersistenceException  {
        openConnectionOut();
        try {
            oos.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"uff");
        }
        closeConnectionOut();
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    @SuppressWarnings("unchecked")
    public List<E> load() throws PersistenceException  {
        List<E> newListe = null;
        openConnectionIn();
        try {
            // Read the object from the input stream
            Object o = ois.readObject();
            if (o instanceof List) {
                newListe = (List<E>) o;
            } else {
                // Handle unexpected data in the input stream
                throw new PersistenceException(PersistenceException.ExceptionType.InvalidDataFormat, "Unexpected data format");
            }
        } catch (ClassNotFoundException | IOException e) {
            // Log the specific exception and rethrow it
            e.printStackTrace();
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "Error while reading data from the input stream");
        } finally {
            closeConnectionIn();
        }
        return newListe;
    }

}
