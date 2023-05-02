package ru.vstu_bet.controllers;

import lombok.Data;

@Data
public class PageWorker {
    private int COUNT_ELEM = 3;
    private int number_page = 1;
    private int left = 1;
    private int right = 1;
    private int maxSzList = 0;

    public PageWorker(int COUNT_ELEM, int maxSzList, int number_page) {
        this.COUNT_ELEM = COUNT_ELEM;
        this.maxSzList = maxSzList;
        this.number_page = number_page;
    }

    private void findLR() {
        left = (number_page-1)* COUNT_ELEM;
        right = (number_page* COUNT_ELEM);
    }

    public void correct_number_page () {
        int maxPages = getMaxPages ();
        findLR();
        if (right> maxSzList){
            if ((right- COUNT_ELEM) >  maxSzList) {
                number_page = maxPages;
                findLR();
            }
            if (right > maxSzList){
                right = maxSzList;
            }
        }
    }

    public int getMaxPages () {
        return (maxSzList % COUNT_ELEM) == 0 ? maxSzList /
                COUNT_ELEM:maxSzList /
                COUNT_ELEM+1;
    }
}
