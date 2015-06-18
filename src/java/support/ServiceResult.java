/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package support;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * результат какого-либо действия
 * 
 * @author Rice Pavel
 */
public class ServiceResult {
  
  private Object value = "";
    
  private final List<String> errors = new ArrayList();
  private final List<String> messages = new ArrayList();

  public List<String> getErrors() {
    return errors;
  }
 
  public boolean hasErrors() {
    return !errors.isEmpty();
  }
  
  public void addErrors(List<String> errors) {
      this.errors.addAll(errors);
  }
  
  public void addError(String error) {
    errors.add(error);
  }
  
  public void clearErrors() {
    errors.clear();
  }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public List<String> getMessages() {
    return messages;
  }
 
  public boolean hasMessages() {
    return !messages.isEmpty();
  }
  
  public void addMessages(List<String> messages) {
      this.messages.addAll(errors);
  }
  
  public void addMessage(String message) {
    messages.add(message);
  }
  
  public void clearMessages() {
    messages.clear();
  }
  
}
