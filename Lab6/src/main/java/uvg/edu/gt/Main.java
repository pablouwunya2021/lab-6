import java.io.*;
import java.util.*;

// Interfaz para el Factory
interface MapFactory {
    Map<String, String> createMap();
}

// Implementaciones del Factory
class HashMapFactory implements MapFactory {
    public Map<String, String> createMap() {
        return new HashMap<>();
    }
}

class TreeMapFactory implements MapFactory {
    public Map<String, String> createMap() {
        return new TreeMap<>();
    }
}

class LinkedHashMapFactory implements MapFactory {
    public Map<String, String> createMap() {
        return new LinkedHashMap<>();
    }
}

// Clase principal del programa
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Selección de la implementación del Map
        System.out.println("Seleccione la implementación del Map:");
        System.out.println("1) HashMap");
        System.out.println("2) TreeMap");
        System.out.println("3) LinkedHashMap");
        int choice = scanner.nextInt();

        MapFactory mapFactory;
        switch (choice) {
            case 1:
                mapFactory = new HashMapFactory();
                break;
            case 2:
                mapFactory = new TreeMapFactory();
                break;
            case 3:
                mapFactory = new LinkedHashMapFactory();
                break;
            default:
                System.out.println("Opción no válida. Se utilizará HashMap por defecto.");
                mapFactory = new HashMapFactory();
        }

        // Lectura del archivo de cartas
        Map<String, String> cardMap = mapFactory.createMap();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Pbloc\\IdeaProjects\\Lab6\\src\\main\\resources\\cards_desc.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                String cardName = parts[0].trim();
                String cardType = parts[1].trim();
                cardMap.put(cardName, cardType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Lógica para interactuar con el usuario y gestionar las cartas
        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1) Agregar una carta a la colección del usuario.");
            System.out.println("2) Mostrar el tipo de una carta específica.");
            System.out.println("3) Mostrar el nombre, tipo y cantidad de cada carta en la colección del usuario.");
            System.out.println("4) Mostrar el nombre, tipo y cantidad de cada carta en la colección del usuario, ordenadas por tipo.");
            System.out.println("5) Mostrar el nombre y tipo de todas las cartas existentes.");
            System.out.println("6) Mostrar el nombre y tipo de todas las cartas existentes, ordenadas por tipo.");
            System.out.println("0) Salir.");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    System.out.println("Ingrese el nombre de la carta a agregar:");
                    String newCardName = scanner.nextLine();
                    System.out.println("Ingrese el tipo de la carta:");
                    String newCardType = scanner.nextLine();
                    cardMap.put(newCardName, newCardType);
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Pbloc\\IdeaProjects\\Lab6\\src\\main\\resources\\cards_desc.txt", true))) {
                        bw.write(newCardName + "|" + newCardType);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Carta agregada a la colección y guardada en el archivo.");
                    break;
                case 2:
                    System.out.println("Ingrese el nombre de la carta:");
                    String cardToCheck = scanner.nextLine();
                    if (cardMap.containsKey(cardToCheck)) {
                        System.out.println("Tipo de la carta: " + cardMap.get(cardToCheck));
                    } else {
                        System.out.println("La carta ingresada no se encuentra entre las cartas disponibles.");
                    }
                    break;
                case 3:
                    // Mostrar la colección del usuario
                    Map<String, Integer> cardCount = new HashMap<>();
                    for (String card : cardMap.keySet()) {
                        cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
                    }
                    for (Map.Entry<String, Integer> entry : cardCount.entrySet()) {
                        System.out.println("Carta: " + entry.getKey() + ", Cantidad: " + entry.getValue() + ", Tipo: " + cardMap.get(entry.getKey()));
                    }
                    break;
                case 4:
                    // Mostrar la colección del usuario ordenada por tipo
                    Map<String, List<String>> cardsByType = new TreeMap<>();
                    for (Map.Entry<String, String> entry : cardMap.entrySet()) {
                        String type = entry.getValue();
                        String card = entry.getKey();
                        cardsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(card);
                    }
                    for (Map.Entry<String, List<String>> entry : cardsByType.entrySet()) {
                        System.out.println("Tipo: " + entry.getKey());
                        for (String card : entry.getValue()) {
                            System.out.println("Carta: " + card);
                        }
                    }
                    break;
                case 5:
                    // Mostrar todas las cartas existentes
                    for (Map.Entry<String, String> entry : cardMap.entrySet()) {
                        System.out.println("Carta: " + entry.getKey() + ", Tipo: " + entry.getValue());
                    }
                    break;
                case 6:
                    // Mostrar todas las cartas existentes ordenadas por tipo y luego por nombre dentro de cada tipo
                    Map<String, List<String>> sortedCardsByType = new TreeMap<>();
                    for (Map.Entry<String, String> entry : cardMap.entrySet()) {
                        String type = entry.getValue();
                        String card = entry.getKey();
                        sortedCardsByType.computeIfAbsent(type, k -> new ArrayList<>()).add(card);
                    }
                    for (Map.Entry<String, List<String>> entry : sortedCardsByType.entrySet()) {
                        System.out.println("Tipo: " + entry.getKey());
                        for (String card : entry.getValue()) {
                            System.out.println("\tCarta: " + card);
                        }
                    }
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
