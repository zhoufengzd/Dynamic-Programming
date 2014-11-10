package AlgorithmX;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MedianWatcher {
	public MedianWatcher() {
		_lowerRange = new PriorityQueue<Integer>(new MaxHeapComparator());
		_upperRange = new PriorityQueue<Integer>();
	}

	public void feed(Integer num) {
		// When a number comes in. Need to:
		// 1. Decides with set to go
		// If same size, put it to lower set.
		// If lower set has extra value, put it to upper set
		// Rule: _lowerSet.size() >= _upperSet.size()
		// 2. Check if rotation needed.

		// Larger number should always goes to _minHeap
		boolean isUppsetValue = _upperRange.size() > 0 && num > _upperRange.peek();

		if (_lowerRange.size() == _upperRange.size()) {
			if (!isUppsetValue) {
				_lowerRange.add(num);
			}
			else { // switch needed!
				_lowerRange.add(_upperRange.poll());
				_upperRange.add(num);
			}
		}
		else {
			if (isUppsetValue) {
				_upperRange.add(num);
			}
			else {
				_lowerRange.add(num);
				_upperRange.add(_lowerRange.poll());
			}
		}

		System.out.println("num: " + num);
		System.out.println("_lowerSet: " + _lowerRange);
		System.out.println("_upperSet: " + _upperRange);
	}

	public Double getMedian() {
		if (_lowerRange.size() == _upperRange.size())
			return (_lowerRange.peek() + _upperRange.peek()) / 2.0;
		else
			return new Double(_lowerRange.peek());
	}

	private class MaxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	}

	public static void main(String[] args) {
		MedianWatcher streamMedian = new MedianWatcher();
		streamMedian.feed(1);
		System.out.println("Median: " + streamMedian.getMedian()); // should be 1
		streamMedian.feed(5);
		streamMedian.feed(10);
		streamMedian.feed(12);
		streamMedian.feed(2);
		System.out.println("Median: " + streamMedian.getMedian()); // should be 5
		streamMedian.feed(3);
		streamMedian.feed(8);
		streamMedian.feed(9);
		System.out.println("Median: " + streamMedian.getMedian()); // should be 6.5
	}

	// To get median in a sequence of numbers: 1, 3, 4, ... M, M+delta, ... N.
	// assume M, M+delta are the two numbers mostly close to median. N is total element count.
	// If N % 2 == 1, median = M
	// if N % 2 == 0, median = (M + (M+delta)) / 2.0
	// Then the question becomes:
	// 1. How to keep M is the biggest of all the number in the left, and
	// keep N as the biggest of all the numbers in the right sequence
	// Answer: The answer is priority queue.
	// 2. How to maintain the element count on the left and right?
	// Answer: shift element
	private Queue<Integer> _lowerRange; // root is the maximum value of the set
	private Queue<Integer> _upperRange; // root is the minimum value of the set
}
