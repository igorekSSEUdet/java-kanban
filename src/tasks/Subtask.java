package tasks;

public class Subtask extends Task {

    int epicId;

    public Subtask(String name, String description, String status, int number, int epicId) {
        super(name, description, status, number);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}