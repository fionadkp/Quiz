package org.example.object;

public class Artist {

    private String name;
    private int grammys;
    private int mtv;
    private int billboard;
    private int amas;
    private int vmas;
    private int emas;
    private int brits;
    private int winningCondition;

    public Artist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrammys() {
        return grammys;
    }

    public void setGrammys(int grammys) {
        this.grammys = grammys;
    }

    public int getMtv() {
        return mtv;
    }

    public void setMtv(int mtv) {
        this.mtv = mtv;
    }

    public int getBillboard() {
        return billboard;
    }

    public void setBillboard(int billboard) {
        this.billboard = billboard;
    }

    public int getAmas() {
        return amas;
    }

    public void setAmas(int amas) {
        this.amas = amas;
    }

    public int getVmas() {
        return vmas;
    }

    public void setVmas(int vmas) {
        this.vmas = vmas;
    }

    public int getEmas() {
        return emas;
    }

    public void setEmas(int emas) {
        this.emas = emas;
    }

    public int getBrits() {
        return brits;
    }

    public void setBrits(int brits) {
        this.brits = brits;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", grammys=" + grammys +
                ", mtv=" + mtv +
                ", billboard=" + billboard +
                ", amas=" + amas +
                ", vmas=" + vmas +
                ", emas=" + emas +
                ", brits=" + brits +
                '}';
    }

    public void setWinningCondition(int winningCondition) {
        this.winningCondition = winningCondition;
    }

    public int getWinningCondition() {
        return winningCondition;
    }
}
