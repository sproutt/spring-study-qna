package codesquad.utils.handlebars;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class IndexHelper {
    public int setStartingIndexNumberIs1(int index) {
        return index + 1;
    }
    public int setStartingIndexNumberIs3(int index) {
        return index + 3;
    }
}