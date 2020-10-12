package br.com.joao.ourdigitalbank.utils;

import br.com.joao.ourdigitalbank.model.client.Client;
import org.passay.*;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 50;
    private static final int QUANTITY_DIGITS = 2;
    private static final int QUANTITY_LETTERS = 2;

    public static boolean validateFormClient(Client client){
        return isValidEmailAddress(client.getEmail()) && isValidCPF(client.getCpf()) && isValidAge(client.getDateBirth());
    }

    public static boolean isValidEmailAddress(String email) {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }

    public static boolean isValidAge(LocalDate dateBirth){

        LocalDate currentDate = LocalDate.now();

        try{

            return ChronoUnit.YEARS.between(dateBirth, currentDate) >= 18;

        } catch (Exception e){

            return false;

        }
    }


        public static boolean isValidCPF(String CPF) {
            CPF = CPF.replaceAll("[.-]", "");
            if (CPF.equals("00000000000") ||
                    CPF.equals("11111111111") ||
                    CPF.equals("22222222222") || CPF.equals("33333333333") ||
                    CPF.equals("44444444444") || CPF.equals("55555555555") ||
                    CPF.equals("66666666666") || CPF.equals("77777777777") ||
                    CPF.equals("88888888888") || CPF.equals("99999999999") ||
                    (CPF.length() != 11))
                return(false);

            char dig10, dig11;
            int sm, i, r, num, peso;

            try {
                sm = 0;
                peso = 10;
                for (i=0; i<9; i++) {
                    num = (int)(CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig10 = '0';
                else dig10 = (char)(r + 48);

                sm = 0;
                peso = 11;
                for(i=0; i<10; i++) {
                    num = (int)(CPF.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso - 1;
                }

                r = 11 - (sm % 11);
                if ((r == 10) || (r == 11))
                    dig11 = '0';
                else dig11 = (char)(r + 48);

                if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                    return(true);
                else return(false);
            } catch (InputMismatchException erro) {
                return(false);
            }
        }

    public List<String> checkPasswordRules(String senha) throws Exception{
        if (senha.isBlank()){
            throw new Exception("Senha deve ser informada");
        }

        LengthRule lr = new LengthRule(MIN_SIZE, MAX_SIZE);

        WhitespaceRule wr = new WhitespaceRule();

        AlphabeticalCharacterRule ac = new AlphabeticalCharacterRule (QUANTITY_LETTERS);

        DigitCharacterRule dc = new DigitCharacterRule(QUANTITY_DIGITS);

        SpecialCharacterRule nac = new SpecialCharacterRule ();

        UppercaseCharacterRule uc = new UppercaseCharacterRule();

        List<Rule> ruleList = new ArrayList<Rule>();
        ruleList.add(lr);
        ruleList.add(wr);
        ruleList.add(ac);
        ruleList.add(dc);
        ruleList.add(nac);
        ruleList.add(uc);

        Properties props = new Properties();
        props.load(new FileInputStream("./src/main/resources/messages.properties"));
        MessageResolver resolver = new PropertiesMessageResolver(props);

        PasswordValidator validator = new PasswordValidator(resolver, ruleList);
        PasswordData passwordData = new PasswordData(new String(senha));

        RuleResult result = validator.validate(passwordData);
        if (!result.isValid()) {
            return validator.getMessages(result);
        }
        return null;
    }

}
