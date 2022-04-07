package codesquad.domain.answer;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import codesquad.domain.question.Question;
import codesquad.domain.user.User;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long index;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
	private Question question;

	@Lob
	@Column(nullable = false, columnDefinition = "TEXT")
	private String contents;

	public Answer(){
	}

	public Answer(User writer, String contents, Question question){
		this.writer = writer;
		this.contents = contents;
		this.question = question;
	}

	public boolean isSameWriter(User writer){
		if(this.writer.getId().equals(writer.getId())){
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Answer answer = (Answer)o;
		return Objects.equals(index, answer.index) && Objects.equals(question, answer.question)
				&& Objects.equals(contents, answer.contents);
	}
}
