package edu.rosehulman.csse374.revengd;

import java.util.List;

public interface ICoupleFinder {
	List<String> find(List<IClassContent> classes, List<String> classNames, boolean isRecursive);
}
