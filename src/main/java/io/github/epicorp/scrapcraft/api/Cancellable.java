package io.github.epicorp.scrapcraft.api;

public interface Cancellable {
	void cancel();
	boolean isCancelled();
}
