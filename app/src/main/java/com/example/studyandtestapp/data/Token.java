package com.example.studyandtestapp.data;

public class Token {


    /**
     * token : eyJhbGciOiJIUzI1NiIsImlhdCI6MTU1NzE1NDkyMywiZXhwIjoxODcyNTE0OTIzfQ.eyJjb25maXJtIjo0MH0.QL0kQMat8wnpdQgwt05bRozK8IQtHhBJYJE36oloFyE
     * uid : 40
     * urole : 3
     */

    private String token;
    private int uid;
    private int urole;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUrole() {
        return urole;
    }

    public void setUrole(int urole) {
        this.urole = urole;
    }
}
