package cn.hsernos.pojo;

public class HserStatisticsVO {
    private String times;
    private Integer turnover;
    private Integer sum;
    private Double benefits;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Integer getTurnover() {
        return turnover;
    }

    public void setTurnover(Integer turnover) {
        this.turnover = turnover;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Double getBenefits() {
        return benefits;
    }

    public void setBenefits(Double benefits) {
        this.benefits = benefits;
    }

    @Override
    public String toString() {
        return "HserStatisticsVO{" +
                "times='" + times + '\'' +
                ", turnover=" + turnover +
                ", sum=" + sum +
                ", benefits=" + benefits +
                '}';
    }
}
