package pt.cm.challenge_2.Interfaces;

import java.util.List;

import pt.cm.challenge_2.database.entities.Note;
import pt.cm.challenge_2.dtos.NoteDTO;

public interface NoteMapperInterface {
     Note toEntityNote(NoteDTO noteDTO);
     NoteDTO toNoteDTO(Note note);
     List<Note> toEntityNotes(List<NoteDTO> notesDTO);
     List<NoteDTO> toNotesDTO(List<Note> notes);
}
