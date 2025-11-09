package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions;

public class PermissionException extends  Exception{

    public PermissionException(){
        super("User doesn't have permission to this operation");
    }
}
