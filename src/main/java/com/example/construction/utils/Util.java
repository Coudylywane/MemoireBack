package com.example.construction.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.authenticator.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.apache.commons.lang3.StringUtils;



import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Util {
//    private static final Logger logger = LoggerFactory.getLogger(Qrcode.class);

    private Util() {
    }

    /**
     * Generate a {@link Pageable} base on the configuration values provided
     *
     * @param page The Page number
     * @param perPage The max amount of elements per page
     * @param orderBy The name of the fiel to order
     * @param direction The order direction (ASC or DESC)
     * @return a {@link Pageable} configured for the search
     */

    public static Pageable getPageable(final int page, final int perPage, final String orderBy,
                                       final String direction) {
        Pageable pageable;
        if (StringUtils.isNotEmpty(orderBy) && StringUtils.isNotEmpty(direction)) {
            Sort.Direction dir;
            if (direction.equalsIgnoreCase("asc")) {
                dir = Sort.Direction.ASC;
            } else {
                dir = Sort.Direction.DESC;
            }
            Sort sort = Sort.by(dir, orderBy);
            pageable = PageRequest.of(page, perPage, sort);
        } else {
            pageable = PageRequest.of(page, perPage);
        }
        return pageable;
    }


    /**
     * Verify if number containt +221
     * @param phoneNumber
     * @return phoneNumber
     */
//    public static String verifyPhoneNumber(String phoneNumber){
//        if(phoneNumber.matches("^(70|76|77|78|75)[0-9]{7}")){
//            return Constants.SMS_INDICATIF+phoneNumber;
//        }
//        return phoneNumber;
//    }

    /**
     *  Formatting date and time according to a pattern using LocalDateTime.
     * @param localDateTime
     * @return
     */
    public static String formatDate(LocalDateTime localDateTime,String pattern){
        if (localDateTime!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.FRENCH);
            return localDateTime.format(formatter);
        }
        return null;
    }



    public static String formatDate(LocalDateTime date){
        if(date != null){
            if(date.getDayOfMonth() <10 && date.getMonthValue() <10){
                return "0"+date.getDayOfMonth()+"/0"+date.getMonthValue()+"/"+date.getYear();
            }else {
                if(date.getDayOfMonth() <10 ){
                    return "0"+date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
                }
                if(date.getMonthValue() < 10){
                    return date.getDayOfMonth()+"/0"+date.getMonthValue()+"/"+date.getYear();
                }
            }
            return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear();
        }
        return null;
    }

    public static String formatMonthAndDay(LocalDateTime date){
        if(date != null){
            if(date.getDayOfMonth() <10 && date.getMonthValue() <10){
                return "0"+date.getMonthValue()+"0"+date.getDayOfMonth();
            }else {
                if(date.getDayOfMonth() <10 ){
                    return date.getMonthValue()+"0"+date.getDayOfMonth();
                }
                if(date.getMonthValue() < 10){
                    return "0"+date.getMonthValue()+date.getDayOfMonth();
                }
            }
            return date.getMonthValue()+""+date.getDayOfMonth();
        }
        return null;
    }


    public static String formatDateToTetter(Date date){
        String[] mothLetter =  {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin",
                "Juillet", "Août", "Septembre", "Octobre", "Nonvembre", "Décembre"};
        if(date != null){
            if(date.getDate() < 10){
                return "0"+date.getDay()+" "+mothLetter[date.getMonth()-1]+" "+date.getYear();
            }
            return date.getDay()+" "+mothLetter[date.getMonth()-1]+" "+date.getYear();

        }
        return null;
    }

    public static String formatAmount(double montant){
        Locale locale = new Locale("fr", "SN");
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        String moneyString = formatter.format(montant);
        return moneyString;
    }


//    public static String formatAmountToLetter(double montant){
//        long montantLong = (long) montant;
//        String moneyString = FrenchNumberToWords.convert(montantLong);
//        return moneyString;
//    }


//    public static MultipartFile htmlToMultipartFile(String htmlBody, String fileName) {
//        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocumentFromString(htmlBody);
//            renderer.layout();
//            renderer.createPDF(outputStream, false);
//            renderer.finishPDF();
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//            logger.info("Util: Html to convert MultipartFile Success  ");
//            return new MockMultipartFile(fileName, fileName, "", inputStream);
//        } catch (Exception e) {
//            logger.error("Util: Html to convert MultipartFile Erreur "+ e.getMessage());
//            return null;
//        }
//
//    }

//    public static void htmlToPdf(String htmlBody, String path) {
//        try {
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocumentFromString(htmlBody);
//            renderer.layout();
//            renderer.createPDF(outputStream, false);
//            renderer.finishPDF();
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//            FileOutputStream output = new FileOutputStream(path);
//            output.write(inputStream.readAllBytes());
//            output.close();
//            logger.info("Util: Html to convert pdf Success  ");
//        } catch (Exception e) {
//            logger.error("Util:  Html to convert pdf Erreur "+ e.getMessage());
//        }
//
//    }
    public static boolean validateEmployeeNumber(String employeeNumber) {
        if (employeeNumber == null || employeeNumber.length() != 7) {
            return false;
        }

        char[] mat = employeeNumber.toCharArray();
        int oddPosition = Character.getNumericValue(mat[0]) + Character.getNumericValue(mat[2])
                + Character.getNumericValue(mat[4]);
        int evenPosition = Character.getNumericValue(mat[1]) + Character.getNumericValue(mat[3])
                + Character.getNumericValue(mat[5]);
        return Math.abs(oddPosition - evenPosition) == caracteres.get(Character.toUpperCase(mat[6]));
    }

    public static String convertArrayListToJson(List<?> arrayList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(arrayList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
//    public static String generateIdentifier(String context, int paddingSize, Supplier<Optional<String>> lastIdentifierSupplier) {
//        StringBuilder identifier = new StringBuilder();
//        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
//        String currentDate = Util.formatMonthAndDay(LocalDateTime.now()); // Format "MMDD"
//        int sequenceNumber = 1;
//
//        Optional<String> lastIdentifierOptional = lastIdentifierSupplier.get();
//        if (lastIdentifierOptional.isPresent() && lastIdentifierOptional.get() != null) {
//            String lastIdentifier = lastIdentifierOptional.get();
//            String lastYear = lastIdentifier.substring(context.length(), context.length() + 4);
//            String lastDate = lastIdentifier.substring(context.length() + 5, context.length() + 9);
//
//            if (lastYear.equals(String.valueOf(currentYear)) && lastDate.equals(currentDate)) {
//                String sequencePart = lastIdentifier.substring(context.length() + 10);
//                sequenceNumber = Integer.parseInt(sequencePart) + 1;
//            }
//        }
//        identifier.append(context != null ? context : "");
//        identifier.append(currentYear).append("-");
//        identifier.append(currentDate).append("-");
//
//        String sequenceFormatted = String.format("%0" + paddingSize + "d", sequenceNumber);
//        identifier.append(sequenceFormatted);
//
//        return identifier.toString();
//    }

    private static final Map<Character, Integer> caracteres = Map.ofEntries(
            Map.entry('A', 1),
            Map.entry('B', 2),
            Map.entry('C', 3),
            Map.entry('D', 4),
            Map.entry('E', 5),
            Map.entry('F', 6),
            Map.entry('G', 7),
            Map.entry('H', 8),
            Map.entry('I', 9),
            Map.entry('J', 10),
            Map.entry('K', 11),
            Map.entry('L', 12),
            Map.entry('M', 13),
            Map.entry('N', 14),
            Map.entry('O', 15),
            Map.entry('P', 16),
            Map.entry('Q', 17),
            Map.entry('R', 18),
            Map.entry('S', 19),
            Map.entry('T', 20),
            Map.entry('U', 21),
            Map.entry('V', 22),
            Map.entry('W', 23),
            Map.entry('X', 24),
            Map.entry('Y', 25),
            Map.entry('Z', 26)
    );

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
