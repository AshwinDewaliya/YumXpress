
package yumxpress.utility;

import org.apache.commons.validator.routines.EmailValidator;



public class Validator {
    public static boolean isValidEmail(String emailId){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(emailId);
    }
    public static boolean isValidMobile(String mobileNo){
        if(mobileNo.length()!=10)
        return false;
        char [] mobArr = mobileNo.toCharArray();
        for(char ch:mobArr){
            if(Character.isDigit(ch)==false)
                return false;
        }
        return true;
    }
}
