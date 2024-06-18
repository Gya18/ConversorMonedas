package com.alura.ConversorDeMoneda.principal;

import java.util.Scanner;

import com.alura.ConversorDeMoneda.model.Datos;
import com.alura.ConversorDeMoneda.service.ConsumoAPI;
import com.alura.ConversorDeMoneda.service.ConvierteDatos;

public class Principal {
    private static final String URL_TEMPLATE = "https://v6.exchangerate-api.com/v6/ae2875452970056540899382/latest/";
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final ConvierteDatos convierteDatos = new ConvierteDatos();

    private String buildUrl(String currencyCode){
        return URL_TEMPLATE + currencyCode;
    }

    public void muestraElMenu() {
        Scanner lectura = new Scanner(System.in);
        var opcion = 0;

        String menu = """
                -----------------------------------------------------------------------------------------------------
                ¡¡Bienvenido al conversor de moneda!!
                -----------------------------------------------------------------------------------------------------
                Seleccione tu opción de moneda base:

                1) ARS - Moneda Argentina
                2) BOB - Moneda Bolivia 
                3) BRL - Moneda Brazil
                4) CLP - Moneda Chile
                5) COP - Moneda Colombia
                6) USD - Moneda Estadounidense
                7) PEN - Moneda Perú
                8) Salir
                -----------------------------------------------------------------------------------------------------""";

        while (opcion != 8) {
            System.out.println(menu);
            opcion = lectura.nextInt();

            String monedaBase = "";
            switch (opcion) {
                case 1:
                    monedaBase = "ARS";
                    break;
                case 2:
                    monedaBase = "BOB";
                    break;
                case 3:
                    monedaBase = "BRL";
                    break;
                case 4:
                    monedaBase = "CLP";
                    break;
                case 5:
                    monedaBase = "COP";
                    break;
                case 6:
                    monedaBase = "USD";
                    break;
                case 7:
                    monedaBase = "PEN";
                    break;
                case 8:
                    System.out.println("Saliendo del programas...");
                    return;
                default:
                    System.out.println("Opción no valida");
                    continue;
            }

            var json = consumoAPI.obtenerDatos(buildUrl(monedaBase));
            var datos = convierteDatos.obtenerDatos(json, Datos.class);

            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println("\nningrese la cantidad que desea convertir de " + monedaBase + ":");
            double cantidad = lectura.nextDouble();

            String conversionMenu = """
                -----------------------------------------------------------------------------------------------------
                Elija a que moneda quiere convertir con base en: """ + monedaBase + """

                -----------------------------------------------------------------------------------------------------
                    1) ARS - Moneda Argentina
                    2) BOB - Moneda Bolivia 
                    3) BRL - Moneda Brazil
                    4) CLP - Moneda Chile
                    5) COP - Moneda Colombia
                    6) USD - Moneda Estadounidense
                    7) PEN - Moneda Perú
                    8) Salir
                ----------------------------------------------------------------------------------------------------- """;


            var conversionOpcion = 0;
            while (conversionOpcion !=8) {
                System.out.println(conversionMenu);
                conversionOpcion = lectura.nextInt();

                double tasaCambio = 0.0;
                String monedaDestino = "";

                switch (conversionOpcion) {
                    case 1:
                        tasaCambio = datos.conversionRates().get("ARS");
                        monedaDestino = "ARS";
                        break;
                    case 2:
                        tasaCambio = datos.conversionRates().get("BOB");
                        monedaDestino = "BOB";;
                        break;
                    case 3:
                        tasaCambio = datos.conversionRates().get("BRL");
                        monedaDestino = "BRL";
                        break;
                    case 4:
                        tasaCambio = datos.conversionRates().get("CLP");
                        monedaDestino = "CLP";
                        break;
                    case 5:
                        tasaCambio = datos.conversionRates().get("COP");
                        monedaDestino = "COP";
                        break;
                    case 6:
                        tasaCambio = datos.conversionRates().get("USD");
                        monedaDestino = "USD";
                        break;
                    case 7:
                        tasaCambio = datos.conversionRates().get("PEN");
                        monedaDestino = "PEN";
                        break;
                    case 8:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                        continue;
                }
                if (conversionOpcion >= 1 && conversionOpcion <= 7) {
                    double cantidadConvertida = cantidad * tasaCambio;
                    System.out.println("-----------------------------------------------------------------------------------------------------");
                    System.out.println("\nCantidad convertida de " + cantidad + " "
                            + monedaBase + " a " + monedaDestino + ": "
                            + cantidadConvertida);
                    System.out.println("");
                }
            }
        }
    }
}
