package pt.cm.challenge_2.dtos;

public class NoteDTO {
    private int id;
    private String title;
    private String note;

    public NoteDTO(int id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() { return note; }

    public void setNote(String note) {
        this.note = note;
    }
}
