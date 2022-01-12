package ru.javaops.lunch_vote.to;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.lunch_vote.model.Restaurant;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class VoteStat implements Comparable<VoteStat> {
    private Restaurant restaurant;

    private long voteCount;

    @Override
    public int compareTo(VoteStat o) {
        return (int) (o.getVoteCount() - this.getVoteCount());
    }
}
