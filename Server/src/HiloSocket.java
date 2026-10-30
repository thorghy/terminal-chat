import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import codigos.CodigosRespuesta;

public class HiloSocket extends Thread {
    Socket socket;
    Servidor servidor;

    BufferedReader inputCliente;
    BufferedWriter outCliente;

    public HiloSocket(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            String clienteIP = socket.getInetAddress().getHostAddress();
            int clientePuerto = socket.getPort();

            inputCliente = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outCliente = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String codigo = inputCliente.readLine();
                System.out.println(clienteIP + ":" + clientePuerto + ": " + codigo);

                

                String nombreSala;
                String contraSala;

                CodigosRespuesta resultado;

                switch (codigo) {
                    case "VER_SALAS":
                        ArrayList<String> salas = servidor.getSalas();

                        if (salas.isEmpty()) {
                            resultado = CodigosRespuesta.SIN_SALAS;
                        } else {
                            resultado = CodigosRespuesta.OK;
                        }

                        responderConsulta(resultado);
                        
                        if (resultado == CodigosRespuesta.OK) {
                            for (String sala : salas) {
                                outCliente.write(sala);
                                outCliente.write(" ");
                            }
                            outCliente.newLine();
                            outCliente.flush();
                        }

                        break;
                    case "CREAR_SALA":
                        nombreSala = inputCliente.readLine();
                        contraSala = inputCliente.readLine();

                        resultado = servidor.crearSala(nombreSala, contraSala);

                        responderConsulta(resultado);

                        break;
                    case "BORRAR_SALA":
                        nombreSala = inputCliente.readLine();
                        contraSala = inputCliente.readLine();

                        resultado = servidor.borrarSala(nombreSala, contraSala);

                        responderConsulta(resultado);
                        break;
                    case "UNIRSE_SALA":
                        nombreSala = inputCliente.readLine();
                        contraSala = inputCliente.readLine();

                        //resultado = servidor.solicitarUnirMiembro(nombreSala, contraSala, contraSala);
                        break;
                    case "SALIR_SALA":

                        break;
                    default:
                        outCliente.write(400);
                        outCliente.newLine();
                        outCliente.flush();
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            System.out.println("El usuario se desconectó.");
        }
    }

    private void responderConsulta(CodigosRespuesta codigo) throws IOException {
        try {
            outCliente.write(codigo.getCodigo());
            outCliente.flush();

        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

}
