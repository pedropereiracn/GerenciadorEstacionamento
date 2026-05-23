package persistencia;

import java.io.IOException;

public interface Persistivel<T> {

    void salvar(T objeto, String caminho) throws IOException;

    T carregar(String caminho) throws IOException, ClassNotFoundException;
}
