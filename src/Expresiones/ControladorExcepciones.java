
package Expresiones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorExcepciones {
    
    public static boolean cedula(String cedula){
        Pattern pat = Pattern.compile("^[0-9]{10}$");
        Matcher mat = pat.matcher(cedula);
        return mat.matches();
    }
    
    public static boolean correo(String email){
        Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pat.matcher(email);
        return mat.find();
    }
    
    public static boolean Verificacion(String cedula) {
        int num = 1;
        int suma = 0;
        int cedulaArray[] = new int[10];
        for (int j = 0; j < cedulaArray.length; j++) {
            cedulaArray[j] = Character.getNumericValue(cedula.charAt(j));
        }
        int cedulaCopia[] = new int[9];
        for (int i = 0; i < cedulaCopia.length; i++) {
            num++;
            cedulaCopia[i] = cedulaArray[i] * num;
            if (num == 2) {
                num = 0;
            }
            if (cedulaCopia[i] > 9) {
                cedulaCopia[i] -= 9;
            }
            suma += cedulaCopia[i];
        }
        int resultado = 10 - (suma % 10);
        if (resultado == 10) {
            resultado = 0;
        }
        if (resultado == cedulaArray[9]) {
            return true;
        } else {
            return false;
        }
    }
}
