package com.example.onandroidnoimage;

public class Ticket {
    public int id, chieu;
    public String gadi, gaden;
    public long dongia;
    public Ticket(String gadi, String gaden, long dongia, int chieu){
        this.gadi = gadi;
        this.gaden = gaden;
        this.dongia = dongia;
        this.chieu = chieu;
    }
    public Ticket(int id, String gadi, String gaden, long dongia, int chieu){
        this.id = id;
        this.gadi = gadi;
        this.gaden = gaden;
        this.dongia = dongia;
        this.chieu = chieu;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChieu() {
        return chieu;
    }

    public void setChieu(int chieu) {
        this.chieu = chieu;
    }

    public String getGadi() {
        return gadi;
    }

    public void setGadi(String gadi) {
        this.gadi = gadi;
    }

    public String getGaden() {
        return gaden;
    }

    public void setGaden(String gaden) {
        this.gaden = gaden;
    }

    public long getDongia() {
        return dongia;
    }

    public void setDongia(long dongia) {
        this.dongia = dongia;
    }
}
