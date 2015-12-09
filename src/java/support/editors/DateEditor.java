/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package support.editors;

import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;
import support.Converter;

/**
 *
 * @author Rice Pavel
 */
@Component
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Converter.getDate(text));
    }

}
