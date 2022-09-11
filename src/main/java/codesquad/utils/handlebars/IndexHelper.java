package codesquad.utils.handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class IndexHelper {
    public int setStartingIndexNumber(int index) {
        return index + 3;
    }
}