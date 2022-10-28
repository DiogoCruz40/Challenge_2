package pt.cm.challenge_2.dtos;

public class NoteDTO {
    private String title;
    private String note;

    public NoteDTO(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() { return note; }

    public void setNote(String note) {
        this.note = note;
    }
}
