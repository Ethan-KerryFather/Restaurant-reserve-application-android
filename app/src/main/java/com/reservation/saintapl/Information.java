package com.reservation.saintapl;

import java.util.Random;

public class Information {
    Random random = new Random();
    private String customerName;
    private String customerPhoneNum;
    private int customerReservationNumber;

    Information(String name, String phoneNum){
        this.customerName = name;
        this.customerPhoneNum = phoneNum;

    }

    Information(){
        this.customerReservationNumber = random.nextInt(8999)+1000;
    }

    public int getReserveNum(){
        return this.customerReservationNumber;
    }

}
