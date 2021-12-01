package varios;
import java.util.Random;

public class CadenaAleatoria {
	private static final String cadenaAlfaNumerica = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvxyz"
            + "0123456789"; 
	public static String generar(int longitud) {
        // chose a Character random from this String
 
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int indice = (int) (cadenaAlfaNumerica.length() * Math.random());
            // add Character one by one in end of sb
            sb.append(cadenaAlfaNumerica.charAt(indice));
        }
        return sb.toString();
	}
}
