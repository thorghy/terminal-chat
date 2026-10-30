import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.classfile.BufWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import codigos.CodigosRespuesta;

public class Usuario {
    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 6767)) {
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    while (true) {
                        String userInput;
                        String codRespuesta;

                        imprimirMenu();
                        userInput = scanner.nextLine();

                        switch (userInput) {
                            case "1":
                                verSalas_Local(writer, reader);
                                break;
                            case "3":
                                crearSala_Local(writer, reader, scanner);
                                break;

                            case "4":
                                borrarSala_Local(writer, reader, scanner);

                                break;
                            default:
                                break;
                        }

                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    private void imprimirMenu() {
        System.out.println("===== MENU =====");
        System.out.println("1. Ver salas disponibles");
        System.out.println("2. Unirse a una sala");
        System.out.println("---------------");
        System.out.println("3. Crear una sala");
        System.out.println("4. Borrar una sala");
        System.out.println("---------------");
    }

    private void verSalas_Local(PrintWriter writer, BufferedReader reader) throws IOException {
        try {
            CodigosRespuesta codRespuesta;

            writer.println("VER_SALAS");

            codRespuesta = CodigosRespuesta.getTituloCodigo(reader.read());
            imprimirMensaje(codRespuesta);

            if (codRespuesta == CodigosRespuesta.OK) {
                System.out.println(reader.readLine());
            }
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    private void crearSala_Local(PrintWriter writer, BufferedReader reader, Scanner scanner) throws IOException {
        try {
            ArrayList<String> bufferedInputs = new ArrayList<>();
            CodigosRespuesta codRespuesta;

            writer.println("CREAR_SALA");

            System.out.println("Introduzca nombre de la sala...");
            bufferedInputs.add(scanner.nextLine());

            System.out.println("Introduzca contraseña...");
            bufferedInputs.add(scanner.nextLine());

            enviarInputsBuffer(bufferedInputs, writer);
            codRespuesta = CodigosRespuesta.getTituloCodigo(reader.read());

            imprimirMensaje(codRespuesta);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    private void borrarSala_Local(PrintWriter writer, BufferedReader reader, Scanner scanner) throws IOException {
        try {
            ArrayList<String> bufferedInputs = new ArrayList<>();
            CodigosRespuesta codRespuesta;
            
            writer.println("BORRAR_SALA");

            System.out.println("Introduzca nombre de la sala...");
            bufferedInputs.add(scanner.nextLine());

            System.out.println("Introduzca contraseña...");
            bufferedInputs.add(scanner.nextLine());

            enviarInputsBuffer(bufferedInputs, writer);
            codRespuesta = CodigosRespuesta.getTituloCodigo(reader.read());

            imprimirMensaje(codRespuesta);
        } catch (IOException ex) {
            throw new IOException(ex);
        }
    }

    private void enviarInputsBuffer(ArrayList<String> bufferedInputs, PrintWriter writer) {
        for (String input : bufferedInputs) {
            writer.println(input);
        }
        bufferedInputs.clear();
    }

    private void imprimirMensaje(CodigosRespuesta codigo) {
        try {
            System.out.println(codigo.getDescripcion());
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }

}
