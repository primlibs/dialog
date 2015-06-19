/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package support;

/**
 *
 * @author bezdatiuzer
 */
public class JsonResponce {
    private Boolean status = null;
    private String message = "";
    /*private JsonResponce JsonResponce(){
        return JsonResponce();
    }*/
    
    public static JsonResponce getInstance(){
        return new JsonResponce();
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
