/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.alebenkov.zadaca_1;

/**
 *
 * @author csx
 */
public class provjeraArgumenata {
    
    //kreiraj strinbuilder i u njega spremam argumente radi lakse manipulacije
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        System.out.println(sb.toString());
    }
    
}
