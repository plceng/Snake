package dev.link.snake;

public interface ScoreObservable {
	public void addScoreObserver(ScoreObserver so);
	public void removeScoreObserver(ScoreObserver so);
	public void notifyScoreObservers();
}
