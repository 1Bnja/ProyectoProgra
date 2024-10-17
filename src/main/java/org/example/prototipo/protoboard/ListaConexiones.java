package org.example.prototipo.protoboard;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class ListaConexiones {

    private static ListaConexiones instanciaUnica;
    private List<Conexion> conexiones = new ArrayList<>();



    private ListaConexiones() {
        // Código de inicialización si es necesario
    }


    public static ListaConexiones obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new ListaConexiones(); // Se crea la instancia si no existe.
        }
        return instanciaUnica;
    }

    public void mostrarMensaje() {
        System.out.println("Instancia única del Singleton.");
    }

    public void agregarConexion(Conexion conexion) {
        conexiones.add(conexion);
    }

    public List<Conexion> getConexiones() {
        return conexiones;
    }

    public void setConexiones(Node elementoA, Node elementoB, int signo){
        for (int i = 0 ; i < conexiones.size() ; i++) { //se busca en la lista de elementos agregados

            if (conexiones.get(i).getA() instanceof Bateria) { //busca una bateria
                conexiones.get(i).setB(elementoB);

                if(signo==-1){
                    conexiones.get(i).setA_negativo(signo);
                    conexiones.get(i).setB_negativo(signo);
                } if (signo==1){
                    conexiones.get(i).setA_positivo(signo);
                    conexiones.get(i).setB_positivo(signo);
                }

            }

        }
    }

}
