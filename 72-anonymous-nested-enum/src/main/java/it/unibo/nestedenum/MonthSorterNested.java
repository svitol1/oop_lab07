package it.unibo.nestedenum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    public static enum Month{
        GENNAIO, FEBBRAIO, MARZO, APRILE, MAGGIO, GIUGNO,   
        LUGLIO, AGOSTO, SETTEMBRE, OTTOBRE, NOVEMBRE, DICEMBRE
    }

    private static final ArrayList<String> months = new ArrayList<>(List.of(
        "GENNAIO","FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO",
        "LUGLIO", "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"));

    
    /**
     * 
     * 
     * @param input
     * 
     * @return an enum of Month that most represents the String taken as input
     */
    public Month fromString(String input){
        if(input.length() > 9){
            return null;
        }
        
        ArrayList<String> modifiedMonths = null;
        Month[] enuMonths = Month.values();
        int i = 0;

        for (String string : months) {
            try {
                modifiedMonths.add(string.substring(0, input.length()));    
            } catch (IndexOutOfBoundsException e) {
                /* means that the lenght of the input is 
                 * greater than the lenght of some months
                 */
            }
            
        }
        
        for (String string : modifiedMonths) {
            if(string.compareToIgnoreCase(input) == 0){
                return enuMonths[i];
            }
            i++;
        }

        return null;
    } 

    @Override
    public Comparator<String> sortByDays(){
        return null;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return null;
    }
}
