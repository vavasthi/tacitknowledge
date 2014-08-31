/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package temporarytesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vavasthi
 */
public class TemporaryTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String sd = new String("27 Mar 91 17:35:40 -500");
        if (sd.matches("\\d+\\s+\\D{3}?\\s+\\d+\\s+\\d+:\\d+:\\d+\\s+[+-]\\d{1,4}?")) {
            try {
                System.out.println("String matches");
                
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM y HH:mm:ss X");
                Date d = sdf.parse(sd);
                System.out.println(d);
            } catch (ParseException ex) {
                Logger.getLogger(TemporaryTesting.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }

    }
    
}
