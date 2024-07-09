package convertor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanReed = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al conversor de monedas\n" +
                    "1) Dólar => Peso argentino\n" +
                    "2) Peso argentino => Dólar\n" +
                    "3) Dólar => Real brasileño\n" +
                    "4) Real brasileño => Dólar\n" +
                    "5) Dólar => Peso colombiano\n" +
                    "6) Peso colombiano => Dólar\n" +
                    "7) Salir");
            var search = scanReed.nextLine();

            if (search.equalsIgnoreCase("7")) {
                System.out.println("Gracias por usar nuestros servicios \n" +
                        "Adiós.");
                break;
            }

            String API = "https://v6.exchangerate-api.com/v6/4f247790b6c625ca8b1d6326/latest/USD";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API)).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            double usdToArs = conversionRates.get("ARS").getAsDouble();
            double usdToBrl = conversionRates.get("BRL").getAsDouble();
            double usdToCop = conversionRates.get("COP").getAsDouble();

            System.out.println("Tasas de cambio actuales:");
            System.out.println("1 USD = " + usdToArs + " ARS");
            System.out.println("1 USD = " + usdToBrl + " BRL");
            System.out.println("1 USD = " + usdToCop + " COP");

            double amount;
            switch (search) {
                case "1":
                    System.out.println("Ingrese la cantidad en USD:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " dolares = " + " Equivale a " + (amount * usdToArs) + " pesos argentinos");
                    break;
                case "2":
                    System.out.println("Ingrese la cantidad en ARS:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " pesos argentinos = " + " Equivale a " + (amount / usdToArs) + " dolares");
                    break;
                case "3":
                    System.out.println("Ingrese la cantidad en USD:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " dolares = " + " Equivale a " + (amount * usdToBrl) + " reales brasileños");
                    break;
                case "4":
                    System.out.println("Ingrese la cantidad en BRL:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " reales brasileños = " + " Equivale a " + (amount / usdToBrl)+" dolares");
                    break;
                case "5":
                    System.out.println("Ingrese la cantidad en USD:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " dolares = " + " Equivale a " + (amount * usdToCop)+ " pesos colombianos");
                    break;
                case "6":
                    System.out.println("Ingrese la cantidad en COP:");
                    amount = scanReed.nextDouble();
                    System.out.println(amount + " pesos colombianos = " + " Equivale " + (amount / usdToCop) + " dolares");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

            scanReed.nextLine();
        }
    }
}
