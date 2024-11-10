package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final int THIRTYONE = 31;
    private static final int THIRTY = 30;
    private static final int TWENTYEIGHT = 28;

    private enum Month{
        GENNAIO, FEBBRAIO, MARZO, APRILE, MAGGIO, GIUGNO,   
        LUGLIO, AGOSTO, SETTEMBRE, OTTOBRE, NOVEMBRE, DICEMBRE;

        /**
         * 
         * 
         * @param input
         * 
         * @return an enum of Month that most represents the String taken as input
         */
        public static Month fromString(String input){
            Objects.requireNonNull(input);
            if(input.length() > 9){
                throw new IllegalArgumentException("No such month with that name");
            }

            List<String> modifiedMonths = new ArrayList<>();
            Month[] enuMonths = Month.values();
            Month result = null;
            int i = 0;

            for (String month : MONTHS) {
                if(month.length() >= input.length()){
                    modifiedMonths.add(month.substring(0, input.length()));
                }  
            }    
            for (String string : modifiedMonths) {
                if(string.compareToIgnoreCase(input) == 0){
                    result = enuMonths[i];
                }
                i++;
            }
            try {
                return Objects.requireNonNull(result);

            } catch (NullPointerException e) {
                throw new IllegalArgumentException("No such month with that name", e);
            }
            
        }
    }

    private static final ArrayList<String> MONTHS = new ArrayList<>(List.of(
        "GENNAIO","FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO",
        "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE")
    );

    private static final Map<Month, Integer> MONTH_WITH_DAYS = new HashMap<>();
    static{
        for (Month month : Month.values()) {
            if(month == Month.APRILE || month == Month.NOVEMBRE || month == Month.GIUGNO || month == Month.SETTEMBRE){
                MONTH_WITH_DAYS.put(month, THIRTY);
            } else if(month == Month.FEBBRAIO) {
                MONTH_WITH_DAYS.put(month, TWENTYEIGHT);
            } else {
                MONTH_WITH_DAYS.put(month, THIRTYONE);
            }
        }
    }

    private static final class SortByMonthOrder implements Comparator<String> {

        @Override
        public int compare(String string1, String string2) {
            
            return Month.fromString(string1).compareTo(Month.fromString(string2));
        }
    }

    private static final class SortByDate implements Comparator<String> {

        @Override
        public int compare(String string1, String string2) {
            int daysOfString1 = MONTH_WITH_DAYS.get(Month.fromString(string1));
            int daysOfString2 = MONTH_WITH_DAYS.get(Month.fromString(string2));

            return Integer.compare(daysOfString1, daysOfString2);            
        }
    }

    @Override
    public Comparator<String> sortByDays(){
        return new SortByDate();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}
