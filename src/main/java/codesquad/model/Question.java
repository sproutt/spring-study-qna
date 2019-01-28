package codesquad.model;

//TODO 1. use @Entity and map the Question class to Database Table
//TODO 2. use @Id, @GeneratedValue annotation, make them as Question's key variable
//TODO 3. use @Column annotation, mapping each table's field to table's column
//TODO 4. Make QuestionRepository, this could extends CrudRepository
public class Question {
    private int index;
    private String writer;
    private String title;
    private String contents;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
