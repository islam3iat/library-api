package com.example.LibraryManagementSystem.author;

import com.example.LibraryManagementSystem.Exception.ApiRequestException;
import com.example.LibraryManagementSystem.Exception.NotFoundException;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    public void addAuthor(NewAuthor request){
        Author author=new Author();
        author.setFirstName(request.firstName());
        author.setLastName(request.lastName());
        author.setDateOfBrith(request.dateOfBrith());
        authorRepository.save(author);
    }
    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }
    public Author getAuthor(int id){
        return authorRepository.findById(id).orElseThrow(()-> {
            NotFoundException notFoundException = new NotFoundException("customer with id [%s] not found".formatted(id));
            log.error("error in getting customer with id {}",id,notFoundException);
            return notFoundException;});
    }
    public void removeAuthor(int id){
        if (!authorRepository.existsAuthorById(id))
            throw new NotFoundException("author with [%s] not found".formatted(id));
            authorRepository.deleteById(id);
    }
    public void updateAuthor(int id,NewAuthor newAuthor){

        Author author=authorRepository.findById(id).
                orElseThrow(() ->
                new NotFoundException("author with [%s] not found".formatted(id)));
        Boolean change=false;
        if(newAuthor.firstName()!=null&& !newAuthor.firstName().equals(author.getFirstName())) {
            author.setFirstName(newAuthor.firstName());
            change=true;
        }
        if(newAuthor.lastName()!=null&& !newAuthor.lastName().equals(author.getLastName())) {
            author.setLastName(newAuthor.lastName());
            change=true;
        }
        if(newAuthor.dateOfBrith()!=null&& !newAuthor.dateOfBrith().equals(author.getDateOfBrith())) {
            author.setDateOfBrith(newAuthor.dateOfBrith());
            change=true;
        }
        if(!change) {
            throw new ApiRequestException("no change has be made");
        }
        authorRepository.save(author);

    }

}
