package codesquad.domain.question;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import codesquad.domain.answer.Answer;
import codesquad.domain.user.User;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long index;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	@Column(nullable = false, length = 200)
	private String title;

	@Lob
	@Column(nullable = false, columnDefinition = "TEXT")
	private String contents;

	@Column
	private boolean state;

	@OneToMany(mappedBy ="question")
	private List<Answer> answers;

	public boolean isSameIndex(Long index) {
		if(this.index.equals(index)) {
			return true;
		}
		return false;
	}

	public boolean isSameWriter(User writer){
		if(this.writer.getId().equals(writer.getId())){
			return true;
		}
		return false;
	}

	public void update(Question question){
		this.title = question.title;
		this.contents = question.contents;
	}

	public void delete(){
		this.state = false;
	}

	public boolean checkEmptyAnswerList(){
		if(answers.isEmpty()){
			return true;
		}
		return false;
	}

	public boolean checkExistAnotherAnswerWriter(User writer){
		for (Answer answer : answers) {
			if(!answer.isSameWriter(writer)){
				return false;
			}
		}
		return true;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public int getAnswersSize(){
		return answers.size();
	}
	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
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
