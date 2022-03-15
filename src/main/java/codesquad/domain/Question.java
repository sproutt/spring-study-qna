package codesquad.domain;

public class Question {
    private Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private String time;

    public Question(Long id, String writer, String title, String contents, String time) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    public void createId(Long id) {
        this.id = id;
    }

    public void createWrittenTime(String writtenTime) {
        this.time = writtenTime;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getTime() {
        return time;
    }
}
