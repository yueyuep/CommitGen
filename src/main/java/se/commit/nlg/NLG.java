package se.commit.nlg;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

import com.github.gumtreediff.client.diff.ChangeData;

public class NLG {
    
    public static String generateChangeSentence(Set<ChangeData> changeItems, String operation, ChangeType changeType) {
        
        //Initialize the description and preposition translation engine
        Translator.initializeTranslation();
        
        String sentences = "";
       
        Set<String> classes = new HashSet<>();
        Set<String> lines = new HashSet<>();
        
        for (ChangeData data : changeItems) {
            String [] items = data.getType().split(":");
            String type = items[0].trim();
            String name = null;
            if (items.length > 1) 
                name = items[1].trim();
            
            if (type.equals("TypeDeclaration")) {
                classes.add(data.getClassName());
            
            } else {
                //when type has name with it
                if (items.length > 1) {
                    if (data.getMethodName().isEmpty()) {
                        
                        lines.add(
                                name + " " + 
                                Translator.getTranslationByType(type, changeType) + " " + 
                                Translator.getClassPrepositionByType(type, changeType) + " " +
                                data.getClassName() + " class");
                                              
                    } else {
                        lines.add(
                                name + " " + 
                                Translator.getTranslationByType(type, changeType) + " " +
                                Translator.getMethodPrepositionByType(type, changeType) + " " +
                                data.getMethodName() + " method " + 
                                Translator.getClassPrepositionByType(type, changeType) + " " +
                                data.getClassName() + " class");
                    }
                    
                } else { //No name, only type
                    if (data.getMethodName().isEmpty()) {
                        lines.add(
                                Translator.getTranslationByType(type, changeType) + " " + 
                                Translator.getClassPrepositionByType(type, changeType) + " " +
                                data.getClassName() + " class");
                    } else {
                        lines.add(
                                Translator.getTranslationByType(type, changeType) + " " +
                                Translator.getMethodPrepositionByType(type, changeType) + " " +
                                data.getMethodName() + " method " + 
                                Translator.getClassPrepositionByType(type, changeType) + " " +
                                data.getClassName() + " class");
                    }
                }
            }
        }
        
        int start = 0;
        if (classes.size() > 0) {
            sentences += operation + " class ";
            start = classes.size();
            for (String name : classes) {
                if (classes.size() == 1) sentences += name;
                else if (start == 1) sentences += "and " + name;
                else sentences += name + ", ";
                start--;
            }
        }
        
        //System.out.println(lines.size());
        //System.out.println(lines);
        
        if (lines.size() > 0) {
            sentences += operation + " ";
            start = lines.size();
            for (String line : lines) {
                if (lines.size() == 1) sentences += line;
                else if (start == 1) sentences += "and " + line;
                else sentences += line + ", ";
                start--;
            }
        }        
        
        return sentences;
    }
    
    public static String strInsertSentence(Set<ChangeData> insertItems) {
        String result = "Inserted Items\n=========================\n";
        Iterator<ChangeData> iter = insertItems.iterator();
        
        while(iter.hasNext()) {
            result += iter.next().toString() + "\n";
        }
        
        return result;
    }

    public static String strUpdateSentence(Set<ChangeData> updateItems) {
        String result = "Updated Items\n=========================\n";
        Iterator<ChangeData> iter = updateItems.iterator();
        
        while(iter.hasNext()) {
            result += iter.next().toString() + "\n";
        }
        
        return result;
    }
    
    public static String strDeleteSentence(Set<ChangeData> deletedItems) {
        String result = "Deleted Items\n=========================\n";
        Iterator<ChangeData> iter = deletedItems.iterator();
        
        while(iter.hasNext()) {
            result += iter.next().toString() + "\n";
        }
        
        return result;
    }
}