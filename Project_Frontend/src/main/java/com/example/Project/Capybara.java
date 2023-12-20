package com.example.Project;


public class Capybara {

    private Long id;
    private String name;
    private int age;

    public Capybara(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    protected Capybara() {}
}
