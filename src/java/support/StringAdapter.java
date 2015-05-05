package support;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * класс для работы со строками
 *
 * @author Pavel Rice
 */
public class StringAdapter {

  private StringAdapter(){
  }
  private static final Map<Character, String> charMap = new HashMap<Character, String>();

  static {
    charMap.put('А', "A");
    charMap.put('Б', "B");
    charMap.put('В', "V");
    charMap.put('Г', "G");
    charMap.put('Д', "D");
    charMap.put('Е', "E");
    charMap.put('Ё', "E");
    charMap.put('Ж', "Zh");
    charMap.put('З', "Z");
    charMap.put('И', "I");
    charMap.put('Й', "I");
    charMap.put('К', "K");
    charMap.put('Л', "L");
    charMap.put('М', "M");
    charMap.put('Н', "N");
    charMap.put('О', "O");
    charMap.put('П', "P");
    charMap.put('Р', "R");
    charMap.put('С', "S");
    charMap.put('Т', "T");
    charMap.put('У', "U");
    charMap.put('Ф', "F");
    charMap.put('Х', "H");
    charMap.put('Ц', "C");
    charMap.put('Ч', "Ch");
    charMap.put('Ш', "Sh");
    charMap.put('Щ', "Sh");
    charMap.put('Ъ', "'");
    charMap.put('Ы', "Y");
    charMap.put('Ь', "'");
    charMap.put('Э', "E");
    charMap.put('Ю', "U");
    charMap.put('Я', "Ya");
    charMap.put('а', "a");
    charMap.put('б', "b");
    charMap.put('в', "v");
    charMap.put('г', "g");
    charMap.put('д', "d");
    charMap.put('е', "e");
    charMap.put('ё', "e");
    charMap.put('ж', "zh");
    charMap.put('з', "z");
    charMap.put('и', "i");
    charMap.put('й', "i");
    charMap.put('к', "k");
    charMap.put('л', "l");
    charMap.put('м', "m");
    charMap.put('н', "n");
    charMap.put('о', "o");
    charMap.put('п', "p");
    charMap.put('р', "r");
    charMap.put('с', "s");
    charMap.put('т', "t");
    charMap.put('у', "u");
    charMap.put('ф', "f");
    charMap.put('х', "h");
    charMap.put('ц', "c");
    charMap.put('ч', "ch");
    charMap.put('ш', "sh");
    charMap.put('щ', "sh");
    charMap.put('ъ', "'");
    charMap.put('ы', "y");
    charMap.put('ь', "'");
    charMap.put('э', "e");
    charMap.put('ю', "u");
    charMap.put('я', "ya");
    charMap.put(' ', "_");
  }

  /**
   * переводит в верхний регистр первый символ строки
   *
   * @param string
   * @return
   */
  public static String ucFirst(String string) {
    char[] first = {string.charAt(0)};
    String stringFirst = String.valueOf(first);
    stringFirst = stringFirst.toUpperCase();
    String stringSecond = string.substring(1);
    String resultString = stringFirst + stringSecond;
    return resultString;
  }

  public static String transliterateFileName(String string) {
    return transliterate(string).replace('\"', '\'').replace(" ", "_");
  }
  
  /**
   * принимает русский текст, возвращает текст в транслите
   * @param string
   * @return 
   */
  public static String transliterate(String string) {
    StringBuilder transliteratedString = new StringBuilder();
    for (int i = 0; i < string.length(); i++) {
      Character ch = string.charAt(i);
      String charFromMap = charMap.get(ch);
      if (charFromMap == null) {
        transliteratedString.append(ch);
      } else {
        transliteratedString.append(charFromMap);
      }
    }
    return transliteratedString.toString();
  }

  /**
   * принимает русский текст, возвращает текст в транслите
   * @param string
   * @return 
   */
  public static String transliterate(Object ob) {
    StringBuilder transliteratedString = new StringBuilder();
    String string = "";
    if (ob != null) {
      string = ob.toString();
      for (int i = 0; i < string.length(); i++) {
        Character ch = string.charAt(i);
        String charFromMap = charMap.get(ch);
        if (charFromMap == null) {
          transliteratedString.append(ch);
        } else {
          transliteratedString.append(charFromMap);
        }
      }
      return transliteratedString.toString();
    }
    return string;
  }

  /**
   * принимает массив строк с русским текстом, возвращает массив строк с текстом в транслите
   * @param string
   * @return 
   */
  public static List<Object> transliterate(List<Object> li) {
    List<Object> re = new ArrayList<Object>();
    for (Object ob : li) {
      re.add(transliterate(ob));
    }
    return re;
  }

  /**
   * возвращает true, если каждый из параметров не равен null и не равен пустой строке
   * @param params
   * @return 
   */
  public static boolean NotNull(Object... params) {
    Boolean res = true;
    for (Object param : params) {
      if (res == true && param != null && !param.toString().equals("")) {
        res = true;
      } else {
        res = false;
      }
    }
    return res;
  }
  
  
  
  /**
   * возвращает true, если параметр равен null либо пустой строке
   * @param param
   * @return 
   */
  public static boolean isNull(Object param) {
    return (param == null || param.toString().isEmpty());
  }
  
  /**
   * возвращает объект в виде строки, если объект равен null, То возвращает пустую строку
   * @param object
   * @return 
   */
  public static String getString(Object object) {
    if (object == null) {
      return "";
    } else {
      return object.toString();
    }
  }

  /**
   * возвращает полный stackTrace для переданного объекта Exception
   * @param ex
   * @return 
   */
  public static String getStackExeption(Throwable ex) {
    String res = "";
    if (ex != null) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ex.printStackTrace(pw);
      res = sw.toString();
      pw.close();
      try {
        sw.close();
      } catch (Exception e) {
        res = "Ошибка при попытке заурыть поток";
      }
    }
    return res;
  }
  
  public static String cutString(String sentence, int maxLength){
    if (sentence.length() < maxLength) {
          return sentence;
        } else {
          sentence = sentence.substring(0, maxLength);
        }
    return sentence;
  } 
}
