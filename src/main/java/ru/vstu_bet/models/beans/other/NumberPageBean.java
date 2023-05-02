package ru.vstu_bet.models.beans.other;

import java.util.ArrayList;

public class NumberPageBean {
    private int numberPage;
    private int countElements;

    private ArrayList<Integer> dataPages = new ArrayList<>();

    public int getCountElements() {
        return countElements;
    }

    public NumberPageBean(int numberPage, int countElements, int countDataPages) {
        this.numberPage = numberPage;
        this.countElements = countElements;
        setDataPages(countDataPages);
    }

    public void setDataPages(int dataPages) {
        this.dataPages = new ArrayList<Integer>();
        for (int i = 0; i < dataPages; i++) {
            this.dataPages.add(i+1);
        }
    }

    public ArrayList<Integer> getDataPages() {
        return dataPages;
    }

    public void setDataPages(ArrayList<Integer> dataPages) {
        this.dataPages = dataPages;
    }

    public NumberPageBean(int numberPage, int countElements) {
        this.numberPage = numberPage;
        this.countElements = countElements;
    }

    public void setCountElements(int countElements) {
        this.countElements = countElements;
    }

    public int getNumberPage() {
        return numberPage;
    }

    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }

    public NumberPageBean() {
    }
}
