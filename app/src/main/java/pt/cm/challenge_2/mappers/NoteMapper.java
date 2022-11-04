package pt.cm.challenge_2.mappers;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pt.cm.challenge_2.Interfaces.NoteMapperInterface;
import pt.cm.challenge_2.database.entities.Note;
import pt.cm.challenge_2.dtos.NoteDTO;


public class NoteMapper implements NoteMapperInterface {


    ModelMapper modelMapper = new ModelMapper();
    private <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
    
    @Override
    public Note toEntityNote(NoteDTO noteDTO){
        return modelMapper.map(noteDTO,Note.class);
    }

    @Override
    public NoteDTO toNoteDTO(Note note)
    {
        return modelMapper.map(note,NoteDTO.class);
    }

    @Override
    public List<Note> toEntityNotes(List<NoteDTO> notesDTO){
        return mapList(notesDTO,Note.class);
    }

    @Override
    public List<NoteDTO> toNotesDTO(List<Note> notes)
    {
        return mapList(notes,NoteDTO.class);
    }

}
