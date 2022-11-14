package tasks;

public class Task {
    private String name;//навзвание
    private String description;//описание
    private String status;//статус
    private int number;//индивидуальный номер

    public Task(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description, int number) {
        this.name = name;
        this.description = description;
        this.number = number;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;

    }

    public Task(String name, String description, String status, int number) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    void status() {
        String NEW = "NEW";
        String IN_PROGRESS = "IN_PROGRESS";
        final String DONE = "DONE";
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", number=" + number +
                '}';
    }
}