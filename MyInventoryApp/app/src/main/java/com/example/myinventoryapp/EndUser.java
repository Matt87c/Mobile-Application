package com.example.myinventoryapp;

public class EndUser {
        int id;
        String endUserName;
        String endUserPhoneNumber;
        String endUserEmail;
        String endUserPassword;

        public EndUser() {
            super();
        }

        public EndUser(int i, String endUserName, String endUserPhoneNumber, String endUserEmail, String endUserPassword) {
            super();
            this.id = i;
            this.endUserName = endUserName;
            this.endUserPhoneNumber = endUserPhoneNumber;
            this.endUserEmail = endUserEmail;
            this.endUserPassword = endUserPassword;
        }

        public EndUser(String endUserName, String endUserPhoneNumber, String endUserEmail, String endUserPassword) {
            this.endUserName = endUserName;
            this.endUserPhoneNumber = endUserPhoneNumber;
            this.endUserEmail = endUserEmail;
            this.endUserPassword = endUserPassword;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getEndUserName() {
            return endUserName;
        }
        public void setEndUserName(String name) {
            this.endUserName = endUserName;
        }
        public String getEndUserPhoneNumber() {
            return endUserPhoneNumber;
        }
        public void setEndUserPhoneNumber(String endUserPhoneNumber) {
            this.endUserPhoneNumber = endUserPhoneNumber;
        }
        public String getEndUserEmail() {
            return endUserEmail;
        }
        public void setEndUserEmail(String endUserEmail) {
            this.endUserEmail = endUserEmail;
        }
        public String getEndUserPassword() {
            return endUserPassword;
        }
        public void setEndUserPassword(String endUserPassword) {
            this.endUserPassword = endUserPassword;
        }
    }

