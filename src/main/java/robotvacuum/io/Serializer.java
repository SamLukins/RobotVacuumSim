package robotvacuum.io;

import robotvacuum.house.House;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Austen Seidler
 */
public class Serializer {
    /**
     * Saves a house to a file.
     *
     * @param h         the house to save
     * @param houseName the name of the file to save the house to
     * @throws IOException if FileOutputStream has a problem
     */
    public void serializeHouse(House h, String houseName) throws IOException {
        FileOutputStream fileOutput = new FileOutputStream("savedHouses/" + houseName + ".txt");
        try (ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput)) {
            objectOutput.writeObject(h);
            objectOutput.flush();
        }
    }

    /**
     * Retrieves a house from a file.
     *
     * @param houseName the name of the file to retrieve the house from
     * @return the house stored in the file
     * @throws IOException            if FileInputStream has a problem
     * @throws ClassNotFoundException if ObjectInputStream::readObject has a problem
     */
    public House deserializeHouse(String houseName) throws IOException, ClassNotFoundException {
        FileInputStream fileInput = new FileInputStream("savedHouses/" + houseName + ".txt");
        House h;
        try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
            h = (House) objectInput.readObject();
        }
        return h;
    }
}
