package persistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class PersistenciaService<T extends Serializable> implements Persistivel<T> {

    @Override
    public void salvar(T objeto, String caminho) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminho))) {
            out.writeObject(objeto);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T carregar(String caminho) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminho))) {
            return (T) in.readObject();
        }
    }
}
