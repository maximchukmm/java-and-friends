package edu.frameworks.org.spring.spel;

class TemplateDemo {
    private static final int MINUTES_IN_HOUR = 60;
    private String workActivityName;
    private int minutes;
    private String temp = "TEMPTEMP";

    public TemplateDemo(String workActivityName, int minutes) {
        this.workActivityName = workActivityName;
        this.minutes = minutes;
    }

    public String convertMinutesToString() {
        StringBuilder messageBuilder = new StringBuilder();
        if (minutes >= MINUTES_IN_HOUR) {
            int hours = minutes / MINUTES_IN_HOUR;
            minutes %= MINUTES_IN_HOUR;
            messageBuilder
                .append(String.valueOf(hours))
                .append(" ")
                .append("час");
        }
        if (minutes > 0)
            messageBuilder
                .append(" ")
                .append(String.valueOf(minutes))
                .append(" ")
                .append("минут");
        return messageBuilder.toString();
    }

    public String getWorkActivityName() {
        return workActivityName;
    }

    public void setWorkActivityName(String workActivityName) {
        this.workActivityName = workActivityName;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
