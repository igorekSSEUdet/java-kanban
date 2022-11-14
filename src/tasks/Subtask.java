package tasks;

public class Subtask extends Task {

    int epicId;

    public Subtask(String name, String description) {
        super(name, description);
    }

    public Subtask(String name, String description, String status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }
    public Subtask(String name, String description, String status, int epicId,int number) {
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