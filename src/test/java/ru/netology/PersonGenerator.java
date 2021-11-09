package ru.netology;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

    @Data
    @RequiredArgsConstructor
    public class PersonGenerator {
        private String login;
        private String password;
        private String status;
        private String locale;

        public PersonGenerator(String locale) {
            this.locale = locale;
        }

        public void generatePersonWithValidPassword(){
            Faker faker = new Faker(new Locale(locale));
            login = faker.name().username();
            password = getValidPassword();
            status = getActiveStatus();
        }

        public void generatePersonWithInvalidPassword(){
            Faker faker = new Faker(new Locale(locale));
            login = faker.name().username();
            password = getInvalidPassword();
            status = getActiveStatus();
        }

        public void generatePersonWithBlockedStatus(){
            Faker faker = new Faker(new Locale(locale));
            login = faker.name().username();
            password = getValidPassword();
            status = getBlockedStatus();
        }

        private String getValidPassword() {
            List<String> passwords = Arrays.asList("poM", "aRk", "Os", "js", "Mw", "oZ", "ra", "di");
            Random rndLetters = new Random();
            Random rndNumbers = new Random();
            return passwords.get(rndLetters.nextInt(passwords.size() - 1)) + rndNumbers.nextInt(1000000);
        }

        private String getInvalidPassword() {
            List<String> passwords = Arrays.asList("", " ");
            Random rndLetters = new Random();
            return passwords.get(rndLetters.nextInt(passwords.size() - 1));
        }

        private String getActiveStatus() {
            return "active";
        }

        private String getBlockedStatus() {
            return "blocked";
        }
    }