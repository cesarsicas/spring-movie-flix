package br.com.cesarsicas.spring_movie_flix.SpringMovieFlix.modules.title.data.remote.model;

public enum AutocompleteFilterResultType {
    TITLES_AND_PEOPLE(1),
    TITLES_ONLY(2),
    MOVIES_ONLY(3),
    TV_SHOWS_ONLY(4),
    PEOPLE_ONLY(5);

    public final int value;

    AutocompleteFilterResultType(int value) {
        this.value = value;
    }
}
