package Bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TareaBot extends TelegramLongPollingBot{


    //Lista con los chats id de mis classmates
    private static final List<Long> CHATS = new ArrayList<>(List.of(6108736830L,5747730047L,6082604734L,1534824490L,5792621349L));

    @Override
    public String getBotUsername() {
        return "KravenTLbot";
    }

    @Override
    public String getBotToken() {
        return "6887244452:AAGVAE-lyhAMRDiZaje4zOU_Ytf0A69dlFE";
    }

    // El método onUpdateReceived(Update update) se usa para manejar todas las actualizaciones que el bot recibe
    @Override
    public void onUpdateReceived(Update update) {
        String nombre = update.getMessage().getFrom().getFirstName();
        String apellido = update.getMessage().getFrom().getLastName();
        String nickname = update.getMessage().getFrom().getUserName();
        LocalDateTime dia = LocalDateTime.now(); // Para el día
        DateTimeFormatter hora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // Para la hora

        // Verificar si la actualización contiene un mensaje y si ese mensaje tiene texto
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println("Hola " + nickname + " Tu nombre es: " + nombre + " y tu apellido es: " + apellido);
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            System.out.println("User id: " + chat_id + " Message: " + message_text);

            // Manejo de mensajes
            if (message_text.toLowerCase().equals("/start")) {
                sendText(chat_id, "Bienvenido a KravenTLbot! 🙌\n" +
                        "Aquí tienes una lista de comandos disponibles:\n" +
                        "/info - Muestra tu información de usuario.\n" +
                        "/progra - Muestra una breve descripción de Programación II.\n" +
                        "/hola - Te saluda con la fecha y hora actuales.\n" +
                        "/convert [cantidad] [moneda] - Convierte entre EUR y GTQ. Ejemplo: /convert 10 EUR\n" +
                        "/grupal [enviar un mensaje a varias personas]👌");
            }

            // Responder el hola
            if (message_text.toLowerCase().equals("hola")) {
                sendText(chat_id, "Hola " + nombre + " Gusto saludarte");
            }

            // Comando /info
            if (message_text.toLowerCase().equals("/info")) {
                sendText(chat_id, nombre + apellido + " 0905-23-14003 4to semestre");
            }

            // Comando /progra
            if (message_text.toLowerCase().equals("/progra")) {
                sendText(chat_id, "Programación II es una clase esencial para cualquier estudiante que quiera ser un programador competente...");
            }

            // Comando /hola
            if (message_text.toLowerCase().equals("/hola")) {
                String respuesta = "Hola " + nombre + ", hoy es: " + dia.format(hora);
                sendText(chat_id, respuesta);
            }

            // Comando /convert
            if (message_text.toLowerCase().startsWith("/convert")) {
                try {
                    String[] parts = message_text.split(" ");
                    double euros = Double.parseDouble(parts[1]);
                    double cambio = 8.51;
                    double quetzales = euros * cambio;
                    sendText(chat_id, "La cantidad en Euros es de: " + euros + " EUR\n" + "Equivalente a: " + quetzales + " GTQ");
                } catch (Exception e) {
                    sendText(chat_id, "Por favor, ingresa una cantidad válida en Euros después del comando. Ejemplo: /conversion 10");
                }
            }

            // Comando /grupal para enviar mensajes a varios chats
            if (message_text.toLowerCase().startsWith("/grupal")) {
                //Extraer el mensaje despues del comando
                String send = message_text.replaceAll("(?i)/grupal", "").trim(); // Para obtener el mensaje que se va a enviar

                //para ver si se escribio algo o no dentro del comando
                if(!send.isEmpty()){
                    //Poner inicial mayuscula
                    send=send.substring(0,1).toUpperCase()+send.substring(1);
                    //iterar la lista de los chats
                    for (Long chatid : CHATS) {
                        sendText(chatid, send);
                    }
                    //por si no puso nada despues del comando
                }else{
                    sendText(chat_id, "Uso incorrecto del comando. Escribe algo despues del /grupal");
                }
            }
        }
    }

    // Método para enviar mensajes
    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) // Quién recibirá el mensaje
                .text(what).build();    // Contenido del mensaje
        try {
            execute(sm); // Enviar el mensaje
        } catch (TelegramApiException e) {
            throw new RuntimeException(e); // Cualquier error será capturado aquí
        }
    }
}
