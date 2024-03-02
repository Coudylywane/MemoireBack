package com.example.construction.jwtutils;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {

    public static boolean estVide(String valeur) {
        return valeur == null || valeur.trim().isEmpty();
    }


//    public String valideDesignation(String designation, String key) {
//        if (estVide(designation)) {
//            return key + " : le champ est obligatoire";
//        }
//        return null; // La validation a réussi
//    }

    public String valideDesignation(Object value, String key) {
        if (!(value instanceof String)) {
            return key + " : la valeur n'est pas une chaîne de caractères";
        }
        String designation = (String) value;
        if (estVide(designation)) {
            return key + " : le champ est obligatoire";
        }
        return null; // La validation a réussi
    }

    public String valideDescription(Object value, String key) {
        if (!(value instanceof String)) {
            return key + " : la valeur n'est pas une chaîne de caractères";
        }
        String description = (String) value;
        if (estVide(description)) {
            return key + " : le champ est obligatoire";
        }
        return null; // La validation a réussi
    }


    // Validation téléphone
    public static void valideTelephone(String telephone, String key, String[] arrayError) {
        if (estVide(telephone)) {
            arrayError[0] = key + " : le champ est obligatoire";
        }
    }

//    // Validation login
//    public static void valideLogin(String login, String key, String[] arrayError) {
//        if (estVide(login)) {
//            arrayError[0] = key + " : le champ est obligatoire";
//        }
//    }
//
//    // Validation password
//    public static void validePassword(String password, String key, String[] arrayError) {
//        if (estVide(password)) {
//            arrayError[0] = key + " : le champ est obligatoire";
//        }
//    }



}

