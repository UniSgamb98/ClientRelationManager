package orodent.clientrelationmanager.utils;

import java.io.*;

public abstract class IOManager {
    public static void write(Object obj, String fileName) {
        try {
            File fileOne = new File("bin\\" + fileName);
            FileOutputStream fos = new FileOutputStream(fileOne);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception e){
            System.out.println("Scrittura file fallita: "+ fileName);
        }
    }

    public static Object read(String file) throws IOException, ClassNotFoundException {
        File toRead = new File("bin\\" + file);
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object temp = ois.readObject();
        ois.close();
        fis.close();
        return temp;
    }
}
