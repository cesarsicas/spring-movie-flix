package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.domain.exceptions;

public class DefaultUserNotFoundException extends  Exception {
    public DefaultUserNotFoundException(){
        super("User doesn't have permission to this operation");
    }
}
