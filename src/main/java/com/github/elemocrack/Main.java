package com.github.elemocrack;

import java.util.Scanner;

public class Main {
    public static final int MAXIMOS_USUARIOS = 10;
    public static int usuariosRegistrados = 0;
    public static String[] nombre = new String[MAXIMOS_USUARIOS];
    public static String[] contraseña = new String[MAXIMOS_USUARIOS];
    public static double[] dinero = new double[MAXIMOS_USUARIOS];

    public static void main(String[] args) {
        cuentasDefault();
        Scanner sc = new Scanner(System.in);
        int intentos = 2;
        boolean logueado = false;
        int idUsuario = 0;
        System.out.println("Bienvenido");
        while (true) {
            System.out.println(" 1 Registrarse \n 2 Iniciar sesion");
            if (sc.hasNextInt()) {
                int opciones = sc.nextInt();
                switch (opciones) {
                    case 1:
                        System.out.println("Ingrese su nombre de cuenta");
                        String nombreDeCuenta = sc.next();
                        if (existeCuenta(nombreDeCuenta)) {
                            System.out.println("Esta cuenta ya esta registrada");
                            break;
                        }
                        System.out.println("Ingrese su contraseña");
                        String contraseñaDeCuenta = sc.next();
                        System.out.println("Ingrese el dinero a depositar por primera vez");
                        if (sc.hasNextDouble()) {
                            double dineroDepositado = sc.nextDouble();
                            nuevoUsuario(nombreDeCuenta, contraseñaDeCuenta, dineroDepositado);
                        } else {
                            System.out.println("Tiene que ingresar un monto numerico");
                            sc.nextLine();
                            break;

                        }
                        break;
                    case 2:
                        sesion:
                        while (true) {
                            if (!logueado) {
                                System.out.println("Ingrese su nombre");
                                String nombreIngresado = sc.next();
                                System.out.println("Ingrese su contraseña");
                                String contraseñaIngresada = sc.next();
                                if (intentos > 0) {
                                    for (int i = 0; i < MAXIMOS_USUARIOS; i++) {
                                        if (nombre[i] != null && contraseña[i] != null) {
                                            if (nombre[i].equals(nombreIngresado) && contraseña[i].equals(contraseñaIngresada)) {
                                                System.out.println("Nombre correcto");
                                                logueado = true;
                                                idUsuario = i;
                                                break;
                                            }
                                        }
                                    }
                                    if (logueado == false) {
                                        System.out.println("Nombre o contraseña invalidos");
                                    }
                                } else {
                                    System.out.println("Te quedaste sin intentos");
                                    return;
                                }
                                intentos--;
                            } else {
                                System.out.println("Ingrese una opcion\n 1 para retirar\n 2 para ingresar\n 3 para consultar saldo\n 4 para salir de tu cuenta\n 5 para apagar el cajero");
                                if (sc.hasNextInt()) {
                                    int opcion = sc.nextInt();
                                    switch (opcion) {
                                        case 1:
                                            retiro(idUsuario);
                                            break;
                                        case 2:
                                            ingreso(idUsuario);
                                            break;
                                        case 3:
                                            consulta(idUsuario);
                                            break;
                                        case 4:
                                            logueado = false;
                                            break sesion;
                                        case 5:
                                            return;
                                        default:
                                            System.out.println("No es opcion valida");
                                            break;
                                    }
                                } else {
                                    System.out.println("Las opciones son numericas");
                                    sc.nextLine();
                                }
                            }
                        }
                        break;
                    case 3:
                        //Case para debug de codigo
                        mostrarCuentas();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("No es opcion valida");
                }
            } else {
                System.out.println("Las opciones son numericas");
                sc.nextLine();
            }
        }
    }

    public static void retiro(int i) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese monto a retirar");
        if (sc.hasNextDouble()) {
            double retiro = sc.nextDouble();
            if (retiro > dinero[i]) {
                System.out.println("No tiene tanto dinero");
            } else {
                double monto = dinero[i] - retiro;
                dinero[i] = monto;
                System.out.println("Ahora tiene: " + monto + " pesos");
            }
        } else {
            System.out.println("Tiene que ingresar un monto numerico");
            sc.nextLine();
        }
    }

    public static void ingreso(int i) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese monto a ingresar");
        if (sc.hasNextDouble()) {
            double retiro = sc.nextDouble();
            double monto = dinero[i] + retiro;
            dinero[i] = monto;
            System.out.println("Ahora tiene: " + monto + " pesos");
        } else {
            System.out.println("Tiene que ingresar un monto numerico");
            sc.nextLine();
        }
    }

    public static void consulta(int i) {
        System.out.println("Usted tiene: " + dinero[i] + " pesos");
    }

    public static void nuevoUsuario(String nombre, String contraseña, double dinero) {
        if (usuariosRegistrados >= MAXIMOS_USUARIOS) {
            System.out.println("No se pueden registrar mas cuentas");
            return;
        }

        Main.nombre[usuariosRegistrados] = nombre;
        Main.contraseña[usuariosRegistrados] = contraseña;
        Main.dinero[usuariosRegistrados] = dinero;
        usuariosRegistrados++;


    }

    public static void cuentasDefault() {
        nuevoUsuario("Janire", "341824", 15000);
        nuevoUsuario("Sebastian", "2314", 7000);
        nuevoUsuario("Luciano", "78342", 10000);
        nuevoUsuario("Ismael", "260526", 5000);
        nuevoUsuario("BancoAdmin", "admin12340", 90000);
    }

    public static void mostrarCuentas() {
        //metodo para debug de codigo
        System.out.println("Usuarios: ");
        for (int i = 0; i < usuariosRegistrados; i++) {
            System.out.println(Main.nombre[i]);
        }
        System.out.println("Contraseñas: ");
        for (int i = 0; i < usuariosRegistrados; i++) {
            System.out.println(Main.contraseña[i]);
        }
        System.out.println("Montos: ");
        for (int i = 0; i < usuariosRegistrados; i++) {
            System.out.println(Main.dinero[i]);
        }
    }

    public static boolean existeCuenta(String nombreDeCuenta) {
        for (int i = 0; i < usuariosRegistrados; i++) {
            if (nombre[i].equalsIgnoreCase(nombreDeCuenta)) {
                return true;
            }
        }
        return false;
    }
}